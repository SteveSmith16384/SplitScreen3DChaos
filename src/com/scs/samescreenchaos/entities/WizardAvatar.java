package com.scs.samescreenchaos.entities;

import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial.CullHint;
import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.Settings;
import com.scs.multiplayervoxelworld.components.IDamagable;
import com.scs.multiplayervoxelworld.components.IEntity;
import com.scs.multiplayervoxelworld.entities.AbstractPhysicalEntity;
import com.scs.multiplayervoxelworld.entities.AbstractPlayersAvatar;
import com.scs.multiplayervoxelworld.input.IInputDevice;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;
import com.scs.samescreenchaos.ChaosSettings;
import com.scs.samescreenchaos.abilities.CycleThroughAbilitiesAbility;
import com.scs.samescreenchaos.abilities.FireballSpell;
import com.scs.samescreenchaos.components.ITargetByAI;
import com.scs.samescreenchaos.entities.creatures.AbstractCreature;
import com.scs.samescreenchaos.models.WizardModel;

public class WizardAvatar extends AbstractPlayersAvatar implements ITargetByAI, IDamagable {

	public AbstractPhysicalEntity selectedEntity; //e.g. selected creature
	public float mana;
	protected float health;

	public WizardAvatar(MultiplayerVoxelWorldMain _game, AbstractGameModule _module, int _playerID, Camera _cam, IInputDevice _input, int _side) {
		super(_game, _module, _playerID, _cam, _input, _side);

		mana = 100;
		health = 100;//module.getPlayersHealth(playerID);

		this.ability[0] = new FireballSpell(game, _module, this);
		this.ability[1] = new CycleThroughAbilitiesAbility(game, _module, this);

	}

	@Override
	protected Spatial getPlayersModel(MultiplayerVoxelWorldMain game, int pid) {
		WizardModel wiz = new WizardModel(game.getAssetManager());//.getModel();
		if (ChaosSettings.HIDE_AVATARS) {
			wiz.getModel().setCullHint(CullHint.Always);
		}
		return wiz.getModel();
	}


	@Override
	public void process(float tpf) {
		this.mana += tpf;

		super.process(tpf);

		if (this.playerControl.getPhysicsRigidBody().getPhysicsLocation().y < -1f) { // scs catching here after died!
			killed("Too low");
			return;
		}

	}


	@Override
	public boolean isAlive() {
		return health > 0;
	}


	@Override
	public float getDef() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void damaged(float amt, String reason) {
		// todo - check not already dead
		this.health -= amt;
		if (this.health <= 0) {
			killed(reason);
		}
	}


	private void killed(String reason) {
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
					ac.killed("Owner killed");
				}
			}
		}

	}


}
