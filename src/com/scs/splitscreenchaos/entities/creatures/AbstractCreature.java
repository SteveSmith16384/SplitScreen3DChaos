package com.scs.splitscreenchaos.entities.creatures;

import com.jme3.asset.TextureKey;
import com.jme3.bounding.BoundingBox;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.scs.splitscreenchaos.ChaosGameModule;
import com.scs.splitscreenchaos.ChaosSettings;
import com.scs.splitscreenchaos.GameMechanics;
import com.scs.splitscreenchaos.Stats;
import com.scs.splitscreenchaos.components.IAttackable;
import com.scs.splitscreenchaos.components.ICreatureModel;
import com.scs.splitscreenchaos.entities.CreatureCorpse;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenfpsengine.MyBetterCharacterControl;
import com.scs.splitscreenfpsengine.Settings;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.components.IAffectedByPhysics;
import com.scs.splitscreenfpsengine.components.IDamagable;
import com.scs.splitscreenfpsengine.components.IEntity;
import com.scs.splitscreenfpsengine.components.INotifiedOfCollision;
import com.scs.splitscreenfpsengine.components.IProcessable;
import com.scs.splitscreenfpsengine.components.IShowTextOnHud;
import com.scs.splitscreenfpsengine.entities.AbstractPhysicalEntity;
import com.scs.splitscreenfpsengine.entities.ParticleShockwave;
import com.scs.splitscreenfpsengine.entities.ParticleSpark;
import com.scs.splitscreenfpsengine.jme.JMEAngleFunctions;
import com.scs.splitscreenfpsengine.jme.JMEModelFunctions;

import ssmith.util.RealtimeInterval;

public abstract class AbstractCreature extends AbstractPhysicalEntity implements IProcessable, IDamagable, INotifiedOfCollision, IAttackable, IAffectedByPhysics, IShowTextOnHud {

	private static final float TURN_SPEED = 4f;
	private static final float WEIGHT = 1f;

	public enum Anim {None, Idle, Walk, Attack, Died, Frozen}; 

	private ICreatureModel model;
	private MyBetterCharacterControl playerControl;
	private Vector3f walkDir = new Vector3f();

	public boolean undead;
	private float health, maxHealth;
	public float att, def, speed;
	private boolean dead = false;
	private boolean frozen = false;

	private long removeAt;
	public WizardAvatar owner;
	private Geometry orbGeom;
	private StringBuilder hudStats = new StringBuilder();
	private boolean shockwaveShown = false;
	private float rad;
	
	// AI
	private Vector3f targetPos;
	private IAttackable physicalTarget;
	private IAttackable lockedInCombat;
	private float attackDist = -1; // How far away the target currently is when attacking
	private float avoidUntil = 0;
	private RealtimeInterval attackInterval = new RealtimeInterval(2000);


	public AbstractCreature(SplitScreenFpsEngine _game, ChaosGameModule _module, String name, Vector3f startPos, WizardAvatar _owner, boolean _undead) {
		super(_game, _module, name);

		owner = _owner;

		Stats stats = _module.stats.getStats(name);
		speed = stats.speed;
		att = stats.att;
		def = stats.def;
		health = stats.health;
		maxHealth = stats.health;
		undead = _undead;

		model = getCreatureModel();
		JMEModelFunctions.moveYOriginTo(model.getModel(), 0f);
		JMEModelFunctions.centreXZ(model.getModel());
		this.getMainNode().attachChild(model.getModel());
		this.getMainNode().setLocalTranslation(startPos);

		BoundingBox bv = (BoundingBox)model.getModel().getWorldBound();
		//playerControl = new BetterCharacterControl(bb.getZExtent()*.9f, bb.getZExtent()*2, 1000f); ASAS
		rad = Math.max(bv.getXExtent(), bv.getZExtent());
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
			Sphere sphere = new Sphere(16, 16, bv.getYExtent()/5, true, false); // Make orb size relative to creature
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

		//playerControl.setWalkDirection(new Vector3f(0, 0, 0));

		if (frozen) {
			this.model.setCreatureAnim(Anim.Frozen);
			//playerControl.setWalkDirection(new Vector3f(0, 0, 0));
			return;
		}

		if (orbGeom != null) {
			JMEAngleFunctions.rotateYAxisBy2(this.orbGeom, tpfSecs*100);
		}

		// Reset any vars
		if (physicalTarget != null) {
			if (physicalTarget.isAlive() && physicalTarget.canBeSeen()) {
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
						Settings.p("Combat between " + this.name + " and " + lockedInCombat.getName());
						Vector3f sparkPos = this.getCentre().add(this.lockedInCombat.getLocation()).multLocal(.5f);
						new ParticleSpark(game, module, sparkPos);
						float tot = GameMechanics.combat(att, lockedInCombat.getDef());
						if (tot > 0) {
							lockedInCombat.damaged(tot, "combat with " + this.name);
						}
					}
				}
			} else if (avoidUntil > 0) {
				this.turnAwayFromDestination(TURN_SPEED/6);
				this.moveBwds();
			} else if (this.targetPos != null) {
				this.model.setCreatureAnim(Anim.Walk);
				this.turnTowardsDestination(targetPos);
				this.moveFwds();
				if (this.distance(this.targetPos) < this.rad*2) {
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
		playerControl.setWalkDirection(walkDir);
		walkDir.set(0, 0, 0);
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
		walkDir.addLocal(this.playerControl.getViewDirection().mult(speed));
		walkDir.y = 0;
		//Settings.p(this + " walking " + walkDirection);
		//playerControl.setWalkDirection(walkDir.multLocal(speed));
		//walkDir.multLocal(speed);
	}


	private void moveBwds() {
		walkDir.addLocal(this.playerControl.getViewDirection().mult(-speed));
		walkDir.y = 0;
		//playerControl.setWalkDirection(walkDir.multLocal(-speed));
		//walkDir.multLocal(-speed);
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
				//Settings.p(this + " now locked in combat with " + other);
				this.lockedInCombat = co;
				co.setLockedInCombat(this);
				this.attackDist = this.distance(other) * 1.5f;
			} else {
				//No!  avoidUntil = 4;
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
			CreatureCorpse corpse = new CreatureCorpse(game, module, this);
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
		if (ChaosSettings.DISABLE_AI) {
			return null;
		}
		
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
		this.physicalTarget = null; // Otherwise they go for the wizard who cast Subversion
		this.targetPos = null;
		addOrb();
	}


	@Override
	public String getHudText() {
		hudStats.setLength(0);		
		hudStats.append(this.name + "\nHealth: " + (int)this.health);
		return hudStats.toString();
	}


	@Override
	public void applyForce(Vector3f dir) {
		this.walkDir.addLocal(dir);
	}


	public void setFrozen(boolean b) {
		this.frozen = b;
		if (b) {
			walkDir.set(0, 0, 0);
			playerControl.setWalkDirection(walkDir);
		}
	}
	
	
	public void resurrect(WizardAvatar wiz) {
		undead = true;
		dead = false;
		owner = wiz;
		this.targetPos = null;
		this.lockedInCombat = null;
		physicalTarget = null;
		this.addOrb();
	}

}
