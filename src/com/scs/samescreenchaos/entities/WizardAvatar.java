package com.scs.samescreenchaos.entities;

import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial.CullHint;
import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.entities.AbstractPlayersAvatar;
import com.scs.multiplayervoxelworld.input.IInputDevice;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;
import com.scs.samescreenchaos.ChaosSettings;
import com.scs.samescreenchaos.abilities.CycleThroughAbilitiesAbility;
import com.scs.samescreenchaos.abilities.SummonGolemSpell;
import com.scs.samescreenchaos.models.WizardModel;

public class WizardAvatar extends AbstractPlayersAvatar {

	public WizardAvatar(MultiplayerVoxelWorldMain _game, AbstractGameModule _module, int _playerID, Camera _cam, IInputDevice _input, int _side) {
		super(_game, _module, _playerID, _cam, _input, _side);

		this.ability[0] = new SummonGolemSpell(game, _module, this);
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

}
