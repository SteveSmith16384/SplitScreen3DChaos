package com.scs.samescreenchaos.abilities;

import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.entities.AbstractPlayersAvatar;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;

public class LighteningSpell extends AbstractShootingSpell {

	public LighteningSpell(MultiplayerVoxelWorldMain _game, AbstractGameModule module, AbstractPlayersAvatar _player) {
		super(_game, module, _player, "Lightening Bolt", 1);
	}

}
