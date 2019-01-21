package com.scs.samescreenchaos.abilities;

import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.entities.AbstractPlayersAvatar;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;

public abstract class AbstractShootingSpell extends AbstractSpell {

	public AbstractShootingSpell(MultiplayerVoxelWorldMain _game, AbstractGameModule module, AbstractPlayersAvatar _player, String _name, int _cost) {
		super(_game, module, _player, _name, _cost);
	}

}
