package com.scs.splitscreenchaos.entities;

import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import com.scs.splitscreenchaos.GameMechanics;
import com.scs.splitscreenchaos.abilities.CycleThroughAbilitiesAbility;
import com.scs.splitscreenchaos.abilities.FireballSpell;
import com.scs.splitscreenchaos.components.IAttackable;
import com.scs.splitscreenchaos.effects.FloorSelector;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenchaos.models.WizardModel;
import com.scs.splitscreenfpsengine.MultiplayerVoxelWorldMain;
import com.scs.splitscreenfpsengine.Settings;
import com.scs.splitscreenfpsengine.components.IDamagable;
import com.scs.splitscreenfpsengine.components.IEntity;
import com.scs.splitscreenfpsengine.components.INotifiedOfCollision;
import com.scs.splitscreenfpsengine.entities.AbstractPhysicalEntity;
import com.scs.splitscreenfpsengine.entities.AbstractPlayersAvatar;
import com.scs.splitscreenfpsengine.input.IInputDevice;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class WizardAvatar extends AbstractPlayersAvatar implements IWizard, IAttackable, IDamagable, INotifiedOfCollision {

	private static final float ATT = 1;
	private static final float DEF = 1;

	public AbstractPhysicalEntity selectedEntity; //e.g. selected creature
	public float mana;
	protected float health;
	public boolean killed = false;

	public WizardAvatar(MultiplayerVoxelWorldMain _game, AbstractGameModule _module, int _playerID, Camera _cam, IInputDevice _input, int _side) {
		super(_game, _module, _playerID, _cam, _input, _side);

		mana = 100;
		health = 100;//module.getPlayersHealth(playerID);

		this.ability[0] = new FireballSpell(game, _module, this);
		this.ability[1] = new CycleThroughAbilitiesAbility(game, _module, this);

		new FloorSelector(game, module, this);
	}


	@Override
	protected Spatial getPlayersModel(MultiplayerVoxelWorldMain game, int pid) {
		WizardModel wiz = new WizardModel(game.getAssetManager(), pid);
		/*if (ChaosSettings.HIDE_AVATARS) {
			wiz.getModel().setCullHint(CullHint.Always);
		}*/
		return wiz.getModel();
	}


	@Override
	public void process(float tpfSecs) {
		if (killed) {
			return;
		}

		if (this.input.isCycleAbilityPressed(true)) {
			CycleThroughAbilitiesAbility c = (CycleThroughAbilitiesAbility)this.ability[1];
			c.selectNext();
		} else if (this.input.isCycleAbilityPressed(false)) {
			CycleThroughAbilitiesAbility c = (CycleThroughAbilitiesAbility)this.ability[1];
			c.selectPrev();

		}

		this.mana += tpfSecs;
		super.process(tpfSecs);

		//this.floorSelector.process(tpfSecs);

		if (this.playerControl.getPhysicsRigidBody().getPhysicsLocation().y < -10f) {
			killed("Too low");
			return;
		}

	}


	@Override
	public boolean isAlive() {
		return !killed;
	}


	@Override
	public float getDef() {
		return DEF;
	}


	@Override
	public void damaged(float amt, String reason) {
		if (!killed) { // check not already dead
			this.health -= amt;
			if (this.health <= 0) {
				killed(reason);
			}
		}
	}


	private void killed(String reason) {
		killed = true;
		Settings.p("Player died: " + reason);
		//todo - die anim - this.pl

		//this.restarting = true;
		//this.restartTime = MultiplayerVoxelWorldMain.properties.GetRestartTimeSecs();
		//invulnerableTime = RESTART_DUR*3;

		// Move us below the map
		//Vector3f pos = this.getMainNode().getWorldTranslation().clone();//.floor_phy.getPhysicsLocation().clone();
		//playerControl.warp(pos);

		// Remove all owned creatures
		for (IEntity e : module.entities) {
			if (e instanceof AbstractCreature) {
				AbstractCreature ac = (AbstractCreature)e;
				if (ac.getOwner() == this) {
					ac.killed("Owner killed", true);
				}
			}
		}

	}

	@Override
	public void notifiedOfCollision(AbstractPhysicalEntity other) {
		if (other instanceof IAttackable) {
			IAttackable co = (IAttackable)other;
			float tot = GameMechanics.combat(ATT, co.getDef());
			if (tot > 0) {
				co.damaged(tot, "combat with " + this.name);
			}
		}

	}


	@Override
	public float getCameraHeight() {
		return 1f; // todo - check
	}


}
