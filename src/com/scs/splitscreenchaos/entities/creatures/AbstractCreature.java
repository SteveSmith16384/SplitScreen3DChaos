package com.scs.splitscreenchaos.entities.creatures;

import com.jme3.asset.TextureKey;
import com.jme3.bounding.BoundingBox;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.scs.splitscreenchaos.GameMechanics;
import com.scs.splitscreenchaos.components.IAttackable;
import com.scs.splitscreenchaos.components.ICreatureModel;
import com.scs.splitscreenchaos.entities.AbstractCorpse;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenfpsengine.MyBetterCharacterControl;
import com.scs.splitscreenfpsengine.Settings;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.components.IDamagable;
import com.scs.splitscreenfpsengine.components.IEntity;
import com.scs.splitscreenfpsengine.components.INotifiedOfCollision;
import com.scs.splitscreenfpsengine.components.IProcessable;
import com.scs.splitscreenfpsengine.entities.AbstractPhysicalEntity;
import com.scs.splitscreenfpsengine.entities.ParticleShockwave;
import com.scs.splitscreenfpsengine.jme.JMEAngleFunctions;
import com.scs.splitscreenfpsengine.jme.JMEModelFunctions;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

import ssmith.util.RealtimeInterval;


public abstract class AbstractCreature extends AbstractPhysicalEntity implements IProcessable, IDamagable, INotifiedOfCollision, IAttackable {

	private static final float TURN_SPEED = 3f;
	private static final float WEIGHT = 1f;

	public enum Anim {None, Idle, Walk, Attack, Died, Frozen}; 

	private ICreatureModel model;
	private MyBetterCharacterControl playerControl;

	public boolean undead;
	private float health, maxHealth;
	protected float att, def;
	public float speed;
	private boolean dead = false;
	public boolean frozen = false;

	private long removeAt;
	public WizardAvatar owner;
	private Geometry orbGeom;
	private StringBuilder hudStats = new StringBuilder();
	private boolean shockwaveShown = false;
	
	// AI
	private Vector3f targetPos;
	private IAttackable physicalTarget;
	private IAttackable lockedInCombat;
	private float attackDist = -1; // How far away the target currently is when attacking
	private float avoidUntil = 0;
	private RealtimeInterval attackInterval = new RealtimeInterval(2000);


	public AbstractCreature(SplitScreenFpsEngine _game, AbstractGameModule _module, String name, Vector3f startPos, WizardAvatar _owner, float _speed, float _att, float _def, float _health, boolean _undead) {
		super(_game, _module, name);

		owner = _owner;
		speed = _speed/2;
		att = _att;
		def = _def;
		health = _health;
		maxHealth = _health;
		undead = _undead;

		model = getCreatureModel();
		JMEModelFunctions.moveYOriginTo(model.getModel(), 0f);
		JMEModelFunctions.centreXZ(model.getModel());
		this.getMainNode().attachChild(model.getModel());
		this.getMainNode().setLocalTranslation(startPos);

		BoundingBox bv = (BoundingBox)model.getModel().getWorldBound();
		//playerControl = new BetterCharacterControl(bb.getZExtent()*.9f, bb.getZExtent()*2, 1000f); ASAS
		float rad = Math.max(bv.getXExtent(), bv.getZExtent());
		float height = bv.getYExtent()*3;
		if (rad > height/2) {
			height = rad*2; // Ensure propertions work for physics
		}
		playerControl = new MyBetterCharacterControl(rad, height, WEIGHT); // Make the radius slightly bigger to take into account animations and creatures overlapping
		playerControl.setJumpForce(new Vector3f(0, Settings.JUMP_FORCE, 0)); 
		this.getMainNode().addControl(playerControl);

		playerControl.getPhysicsRigidBody().setUserObject(this);

		addOrb();
		model.setCreatureAnim(Anim.Idle);

	}


	private void addOrb() {
		// Add sphere above to show side
		if (this.orbGeom != null) {
			this.orbGeom.removeFromParent();
		}
		if (this.owner != null) {
			BoundingBox bv = (BoundingBox)model.getModel().getWorldBound();
			Sphere sphere = new Sphere(8, 8, bv.getYExtent()/5, true, false); // Make orb size relative to creature
			sphere.setTextureMode(Sphere.TextureMode.Projected);
			orbGeom = new Geometry("DebuggingSphere", sphere);
			TextureKey key = new TextureKey(WizardAvatar.getOrbColour(owner.playerID));
			Texture tex = game.getAssetManager().loadTexture(key);
			Material mat = new Material(game.getAssetManager(),"Common/MatDefs/Light/Lighting.j3md");
			mat.setTexture("DiffuseMap", tex);
			orbGeom.setMaterial(mat);
			orbGeom.setLocalTranslation(0, bv.getYExtent()*2 + .2f, 0);
			this.getMainNode().attachChild(orbGeom);
		}

	}
	
	
	public abstract ICreatureModel getCreatureModel();


	@Override
	public void process(float tpfSecs) {
		if (module.isGameOver()) {
			return;
		}

		if (this.getMainNode().getWorldTranslation().y < -10) {
			this.killed("Fallen out of arena", true);
			this.markForRemoval();
			return;
		}

		playerControl.setWalkDirection(new Vector3f(0, 0, 0)); // todo - dcet

		if (frozen) {
			this.model.setCreatureAnim(Anim.Frozen);
			return;
		}


		// Reset any vars
		if (physicalTarget != null) {
			if (physicalTarget.isAlive()) {
				this.targetPos = this.physicalTarget.getLocation();
			} else {
				physicalTarget = null;
				targetPos = null;
			}
		}
		if (lockedInCombat != null) {
			if (lockedInCombat.isAlive()) {
				float dist = this.distance((AbstractPhysicalEntity)this.lockedInCombat);
				if (dist > this.attackDist) { // Stop us constantly pushing our attackee
					Settings.p(this + " no longer locked in combat (dist=" + dist + ")");
					lockedInCombat = null;
				}
			} else {
				lockedInCombat = null;
			}
		}
		if (avoidUntil > 0) {
			avoidUntil -= tpfSecs;
		}
		
		
		if (!dead) {
			if (lockedInCombat != null) {
				this.model.setCreatureAnim(Anim.Attack);
				turnTowardsDestination(lockedInCombat.getLocation());
				if (canAttack(lockedInCombat)) {
					if (attackInterval.hitInterval()) {
						Settings.p("Combat between " + this + " and " + lockedInCombat);
						float tot = GameMechanics.combat(att, lockedInCombat.getDef());
						if (tot > 0) {
							lockedInCombat.damaged(tot, "combat with " + this.name);
						}
					}
				}
			} else if (avoidUntil > 0) {
				this.turnAwayFromDestination(TURN_SPEED/4);
				this.moveBwds();
			} else if (this.targetPos != null) {
				this.model.setCreatureAnim(Anim.Walk);
				this.turnTowardsDestination(targetPos);
				this.moveFwds();
				if (this.distance(this.targetPos) < 1f) {
					targetPos = null;
				}
			} else {
				this.model.setCreatureAnim(Anim.Idle);
				physicalTarget = this.findClosestTarget();
			}

		} else {
			if (this.removeAt < System.currentTimeMillis()) {
				this.markForRemoval();
			}
		}
	}


	private void turnTowardsDestination(Vector3f pos) {
		float leftDist = this.leftNode.getWorldTranslation().distance(pos); 
		float rightDist = this.rightNode.getWorldTranslation().distance(pos); 
		if (leftDist > rightDist) {
			JMEAngleFunctions.turnSpatialLeft(this.mainNode, TURN_SPEED);
		} else {
			JMEAngleFunctions.turnSpatialLeft(this.mainNode, -TURN_SPEED);
		}
		this.playerControl.setViewDirection(mainNode.getWorldRotation().getRotationColumn(2));
	}


	private void turnAwayFromDestination(float turnSpeed) {
		if (targetPos != null) {
			float leftDist = this.leftNode.getWorldTranslation().distance(targetPos); 
			float rightDist = this.rightNode.getWorldTranslation().distance(targetPos); 
			if (leftDist < rightDist) {
				JMEAngleFunctions.turnSpatialLeft(this.mainNode, turnSpeed);
			} else {
				JMEAngleFunctions.turnSpatialLeft(this.mainNode, -turnSpeed);
			}
			this.playerControl.setViewDirection(mainNode.getWorldRotation().getRotationColumn(2));
		}
	}


	private void moveFwds() {
		Vector3f walkDirection = this.playerControl.getViewDirection();
		walkDirection.y = 0;
		//Settings.p(this + " walking " + walkDirection);
		playerControl.setWalkDirection(walkDirection.mult(speed));
	}


	private void moveBwds() {
		Vector3f walkDirection = this.playerControl.getViewDirection().mult(-1);
		walkDirection.y = 0;
		//Settings.p(this + " walking backwards " + walkDirection);
		playerControl.setWalkDirection(walkDirection.mult(speed));
	}


	@Override
	public void notifiedOfCollision(AbstractPhysicalEntity other) {
		if (shockwaveShown == false) {
			shockwaveShown = true;
			new ParticleShockwave(game, module, this.getLocation());
		}
		if (other == this.lockedInCombat) {
			return;
		}
		if (other instanceof IAttackable) {
			IAttackable co = (IAttackable)other;
			if (co.getSide() != this.getSide()) {
				Settings.p(this + " now locked in combat with " + other);
				this.lockedInCombat = co;
				co.setLockedInCombat(this);
				this.attackDist = this.distance(other) * 1.5f;
			} else {
				avoidUntil = 4;
			}
		}
	}


	@Override
	public void actuallyRemove() {
		super.actuallyRemove();
		this.module.bulletAppState.getPhysicsSpace().remove(this.playerControl);

	}


	@Override
	public int getSide() {
		if (owner != null) {
			return owner.getSide();
		}
		return -1;
	}


	@Override
	public void damaged(float amt, String reason) {
		if (dead) {
			return;
		}
		Settings.p(this.name + " wounded " + amt + " by " + reason);
		this.health -= amt;
		if (this.health <= 0) {
			killed(reason, false);
		}
	}


	public void restoreHealth() {
		this.health = this.maxHealth;
	}


	public void heal(float amt) {
		this.health += amt;
		if (this.health > maxHealth) {
			health = maxHealth;
		}
	}


	public void killed(String reason, boolean permanent) {
		Settings.p(this + " killed by " + reason);
		dead = true;
		model.setCreatureAnim(Anim.Died);
		removeAt = System.currentTimeMillis() + 2000;

		if (!permanent) {
			AbstractCorpse corpse = new AbstractCorpse(game, module, this);
			module.addEntity(corpse);
		}
	}


	public void setTarget(IAttackable t) {
		this.physicalTarget = t;
	}


	public void setTarget(Vector3f pos) {
		this.physicalTarget = null;
		this.targetPos = pos.clone();
		//aiMode = AIMode.WalkToPoint;
	}


	@Override
	public void setLocation(Vector3f pos) {
		this.playerControl.warp(pos);
	}


	public boolean isAlive() {
		return !dead;
	}


	public WizardAvatar getOwner() {
		return owner;
	}


	public void setOwner(WizardAvatar w) {
		this.owner = w;
	}


	@Override
	public float getDef() {
		return def;
	}


	private IAttackable findClosestTarget() {
		IAttackable closest = null;
		float closestDist = 9999;
		for (IEntity e : module.entities) {
			if (e instanceof IAttackable) {
				IAttackable golem = (IAttackable)e;
				if (golem.getSide() != this.getSide()) {
					if (canAttack(golem)) {
						float dist = this.distance(golem.getLocation());
						if (dist <= closestDist) {
							closestDist = dist;
							closest = golem;
						}
					}
				}
			}
		}
		return closest;
	}


	private boolean canAttack(IAttackable golem) {
		if (golem.canBeSeen()) {
			if (!golem.isUndead() || this.isUndead()) { // Don't attack undead unless we're undead
				//if (golem.getSide() != this.getSide()) {  
				return true;
				//}
			}
		}
		return false;
	}


	@Override
	public boolean isUndead() {
		return undead;
	}


	@Override
	public boolean canBeSeen() {
		return this.getMainNode().getCullHint() != CullHint.Always;
	}


	@Override
	public void setLockedInCombat(IAttackable other) {
		this.lockedInCombat = other;

	}

	
	public void subverted(WizardAvatar wiz) {
		this.owner = wiz;
		this.lockedInCombat = null;
		addOrb();
	}
	
	
	public String getStatsForHud() {
		hudStats.setLength(0);		
		hudStats.append(this.name + "\nHealth: " + (int)this.health);
		return hudStats.toString();
	}
}
