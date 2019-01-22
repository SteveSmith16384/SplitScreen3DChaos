package com.scs.samescreenchaos.entities.creatures;

import com.jme3.math.Vector3f;
import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.MyBetterCharacterControl;
import com.scs.multiplayervoxelworld.Settings;
import com.scs.multiplayervoxelworld.components.IDamagable;
import com.scs.multiplayervoxelworld.components.INotifiedOfCollision;
import com.scs.multiplayervoxelworld.components.IProcessable;
import com.scs.multiplayervoxelworld.entities.AbstractPhysicalEntity;
import com.scs.multiplayervoxelworld.jme.JMEAngleFunctions;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;
import com.scs.samescreenchaos.models.GolemModel;
import com.scs.samescreenchaos.models.ICreatureModel;

import ssmith.util.RealtimeInterval;

public abstract class AbstractCreature extends AbstractPhysicalEntity implements IProcessable, IDamagable, INotifiedOfCollision {

	private static final float TURN_SPEED = 1f;

	private static final float PLAYER_HEIGHT = 3f;
	private static final float WEIGHT = 1f;

	public enum Anim {None, Idle, Walk, Attack, Died}; // AvoidBlockage 
	private enum AIMode {AwaitingCommand, WalkToPoint, AttackCreature}; // AvoidBlockage 

	private ICreatureModel model;
	private MyBetterCharacterControl playerControl;

	private float health = 1;
	private float speed;
	private boolean dead = false;
	private long removeAt;

	public int side;

	// AI
	private AIMode aiMode = AIMode.AwaitingCommand;
	private Vector3f targetPos;
	private AbstractPhysicalEntity physicalTarget;
	private float avoidUntil = 0;
	private RealtimeInterval checkPosInterval = new RealtimeInterval(2000);
	private Vector3f prevPos = new Vector3f();

	public AbstractCreature(MultiplayerVoxelWorldMain _game, AbstractGameModule _module, String name, Vector3f startPos, int _side, float _speed) {
		super(_game, _module, name);

		side = _side;
		speed = _speed;

		model = getCreatureModel(); new GolemModel(game.getAssetManager());
		this.getMainNode().attachChild(model.getModel());
		this.getMainNode().setLocalTranslation(startPos);

		playerControl = new MyBetterCharacterControl(PLAYER_HEIGHT/3, PLAYER_HEIGHT, WEIGHT); // todo - calc from model size
		playerControl.setJumpForce(new Vector3f(0, Settings.JUMP_FORCE, 0)); 
		this.getMainNode().addControl(playerControl);

		playerControl.getPhysicsRigidBody().setUserObject(this);
		
		model.setAnim(Anim.Idle);

	}
	
	
	protected abstract ICreatureModel getCreatureModel();


	@Override
	public void process(float tpfSecs) {
		if (module.isGameOver()) {
			return;
		}
		if (!dead) {
			switch (aiMode) {
			case AwaitingCommand:
				model.setAnim(Anim.Idle);
				break;

			case WalkToPoint:
			case AttackCreature:
				model.setAnim(Anim.Walk);
				if (aiMode == AIMode.AttackCreature) {
					this.targetPos = this.physicalTarget.getLocation();
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
				} else {
					turnAwayFromDestination();
					this.avoidUntil -= tpfSecs;
					if (avoidUntil < 0) {
						aiMode = AIMode.WalkToPoint;
					}
				}
				this.moveFwds();
				break;
				
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
		return 1;
	}


	@Override
	public void damaged(float amt, String reason) {
		if (dead) {
			return;
		}
		this.health -= amt;
		if (this.health <= 0) {
			Settings.p(this + " killed");
			dead = true;
			model.setAnim(Anim.Died);
			removeAt = System.currentTimeMillis() + 2000;
		}

	}
	
	
	public void setTarget(AbstractPhysicalEntity t) {
		this.physicalTarget = t;
	}


	public void setTarget(Vector3f pos) {
		this.physicalTarget = null;
		this.targetPos = pos.clone();
	}


	@Override
	public void notifiedOfCollision(AbstractPhysicalEntity other) {
		if (other instanceof AbstractCreature) {
			AbstractCreature co = (AbstractCreature)other;
			if (this.side != co.side) {
				this.setTarget(other);
			}
		}
	}

}
