package com.scs.splitscreenchaos.entities.creatures;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.GameMechanics;
import com.scs.splitscreenchaos.components.IAttackable;
import com.scs.splitscreenchaos.entities.AbstractCorpse;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.models.ICreatureModel;
import com.scs.splitscreenfpsengine.MultiplayerVoxelWorldMain;
import com.scs.splitscreenfpsengine.MyBetterCharacterControl;
import com.scs.splitscreenfpsengine.Settings;
import com.scs.splitscreenfpsengine.components.IDamagable;
import com.scs.splitscreenfpsengine.components.INotifiedOfCollision;
import com.scs.splitscreenfpsengine.components.IProcessable;
import com.scs.splitscreenfpsengine.entities.AbstractPhysicalEntity;
import com.scs.splitscreenfpsengine.jme.JMEAngleFunctions;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

import ssmith.lang.NumberFunctions;
import ssmith.util.RealtimeInterval;

public abstract class AbstractCreature extends AbstractPhysicalEntity implements IProcessable, IDamagable, INotifiedOfCollision, IAttackable {

	private static final float TURN_SPEED = 1f;

	private static final float PLAYER_HEIGHT = 3f;
	private static final float WEIGHT = 1f;

	public enum Anim {None, Idle, Walk, Attack, Died}; // AvoidBlockage 
	private enum AIMode {AwaitingCommand, WalkToPoint, WalkToCreature, Attacking}; // AvoidBlockage 

	private ICreatureModel model;
	private MyBetterCharacterControl playerControl;

	private float health = 100f;
	protected float speed, att, def;
	private boolean dead = false;
	private long removeAt;
	public WizardAvatar owner;

	// AI
	private AIMode aiMode = AIMode.AwaitingCommand;
	private Vector3f targetPos;
	private IAttackable physicalTarget;
	private float avoidUntil = 0;
	private RealtimeInterval checkPosInterval = new RealtimeInterval(2000);
	private Vector3f prevPos = new Vector3f();
	private RealtimeInterval checkAttackInterval = new RealtimeInterval(2000);

	public AbstractCreature(MultiplayerVoxelWorldMain _game, AbstractGameModule _module, String name, Vector3f startPos, WizardAvatar _owner, float _speed, float _att, float _def) {
		super(_game, _module, name);

		owner = _owner;
		speed = _speed;
		att = _att;
		def = _def;

		model = getCreatureModel(); //new GolemModel(game.getAssetManager());
		this.getMainNode().attachChild(model.getModel());
		this.getMainNode().setLocalTranslation(startPos);

		playerControl = new MyBetterCharacterControl(PLAYER_HEIGHT/3, PLAYER_HEIGHT, WEIGHT); // todo - calc from model size
		playerControl.setJumpForce(new Vector3f(0, Settings.JUMP_FORCE, 0)); 
		this.getMainNode().addControl(playerControl);

		playerControl.getPhysicsRigidBody().setUserObject(this);

		model.setAnim(Anim.Idle);

	}


	public abstract ICreatureModel getCreatureModel();


	@Override
	public void process(float tpfSecs) {
		if (module.isGameOver()) {
			return;
		}

		// Reset any vars
		if (physicalTarget != null) {
			if (!physicalTarget.isAlive()) {
				physicalTarget = null;
				if (aiMode == AIMode.Attacking) {
					aiMode = AIMode.AwaitingCommand;
				}
			} else {
				this.targetPos = this.physicalTarget.getLocation();
			}
		}

		if (!dead) {
			switch (aiMode) {
			case AwaitingCommand:
				model.setAnim(Anim.Idle);
				break;

			case WalkToPoint:
			case WalkToCreature:
				model.setAnim(Anim.Walk);
				if (aiMode == AIMode.WalkToCreature) {
					if (physicalTarget != null) {
						this.targetPos = this.physicalTarget.getLocation();
					}
				}

				if (checkPosInterval.hitInterval()) {
					if (this.mainNode.getWorldTranslation().distance(this.prevPos) < .5f) {
						Settings.p(this + " stuck, changing dir");
						//this.aiMode = AIMode.AvoidBlockage;
					}
					prevPos.set(this.mainNode.getWorldTranslation());
				}

				if (this.aiMode == AIMode.WalkToPoint) {
					this.turnTowardsDestination();
					if (this.distance(this.targetPos) < .5f) {
						this.aiMode = AIMode.AwaitingCommand;
					}
				}/* else if (this.aiMode == AIMode.AvoidBlockage) {
					turnAwayFromDestination();
					this.avoidUntil -= tpfSecs;
					if (avoidUntil < 0) {
						aiMode = AIMode.WalkToPoint;
					}
				}*/
				this.moveFwds();
				break;

			case Attacking:
				model.setAnim(Anim.Attack);
				this.turnTowardsDestination();
				this.moveFwds();

			default:
				throw new RuntimeException("todo");
			}
		} else {
			if (this.removeAt < System.currentTimeMillis()) {
				this.markForRemoval();
			}
		}
	}


	private void turnTowardsDestination() {
		if (targetPos != null) {
			float leftDist = this.leftNode.getWorldTranslation().distance(targetPos); 
			float rightDist = this.rightNode.getWorldTranslation().distance(targetPos); 
			if (leftDist > rightDist) {
				JMEAngleFunctions.turnSpatialLeft(this.mainNode, TURN_SPEED);
			} else {
				JMEAngleFunctions.turnSpatialLeft(this.mainNode, -TURN_SPEED);
			}
			this.playerControl.setViewDirection(mainNode.getWorldRotation().getRotationColumn(2));
		}
	}


	private void turnAwayFromDestination() {
		if (targetPos != null) {
			float leftDist = this.leftNode.getWorldTranslation().distance(targetPos); 
			float rightDist = this.rightNode.getWorldTranslation().distance(targetPos); 
			if (leftDist < rightDist) {
				JMEAngleFunctions.turnSpatialLeft(this.mainNode, TURN_SPEED);
			} else {
				JMEAngleFunctions.turnSpatialLeft(this.mainNode, -TURN_SPEED);
			}
			this.playerControl.setViewDirection(mainNode.getWorldRotation().getRotationColumn(2));
		}
	}


	private void moveFwds() {
		Vector3f walkDirection = this.playerControl.getViewDirection();
		walkDirection.y = 0;
		//Globals.p("Ant dir: " + walkDirection);
		playerControl.setWalkDirection(walkDirection.mult(speed));//1));

	}


	@Override
	public void actuallyRemove() {
		super.actuallyRemove();
		this.module.bulletAppState.getPhysicsSpace().remove(this.playerControl);

	}


	@Override
	public int getSide() {
		return owner.getSide();
	}


	@Override
	public void damaged(float amt, String reason) {
		if (dead) {
			return;
		}
		this.health -= amt;
		if (this.health <= 0) {
			killed(reason, false);
		}
	}


	public void killed(String reason, boolean permanent) {
		Settings.p(this + " killed by " + reason);
		dead = true;
		model.setAnim(Anim.Died);
		removeAt = System.currentTimeMillis() + 2000;

		if (!permanent) {
			AbstractCorpse corpse = new AbstractCorpse(game, module, this);
			module.addEntity(corpse);
		}
	}


	public void setTarget(IAttackable t) {
		this.physicalTarget = t;
		aiMode = AIMode.WalkToCreature;
	}


	public void setTarget(Vector3f pos) {
		this.physicalTarget = null;
		this.targetPos = pos.clone();
		aiMode = AIMode.WalkToPoint;
	}


	@Override
	public void setLocation(Vector3f pos) {
		this.playerControl.warp(pos);
	}


	@Override
	public void notifiedOfCollision(AbstractPhysicalEntity other) {
		if (other instanceof IAttackable) {
			IAttackable co = (IAttackable)other;
			if (this.getSide() != co.getSide()) {
				this.setTarget(co);
				this.aiMode = AIMode.Attacking;
				//if (other instanceof IDamagable) {
				//IDamagable id = (IDamagable)other;
				if (checkAttackInterval.hitInterval()) {
					//float tot = this.att - co.getDef() + NumberFunctions.rndFloat(-3,  3);
					float tot = GameMechanics.combat(att, co.getDef());
					if (tot > 0) {
						co.damaged(tot, "combat with " + this.name);
					}
				}
				//}

			}
		}
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

/*
	public void setAnim(Anim anim) {
		this.model.setAnim(anim);
	}
	*/
	
	/**
	 * For when resurrected.
	 */
	public void setAIToAwaitingCommand() {
		this.aiMode = AIMode.AwaitingCommand;
	}
	
}
