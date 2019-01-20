package com.scs.samescreenchaos.entities;

import com.jme3.renderer.Camera;
import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.entities.AbstractPlayersAvatar;
import com.scs.multiplayervoxelworld.input.IInputDevice;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;
import com.scs.samescreenchaos.abilities.CycleThroughAbilitiesAbility;

public class WizardsPlayersAvatars extends AbstractPlayersAvatar {

	public WizardsPlayersAvatars(MultiplayerVoxelWorldMain _game, AbstractGameModule _module, int _playerID, Camera _cam, IInputDevice _input, int _side) {
		super(_game, _module, _playerID, _cam, _input, _side);

		//this.abilityOther = new RemoveBlockAbility(_module, this);
		this.ability[1] = new CycleThroughAbilitiesAbility(game, _module, this);

	}

}
