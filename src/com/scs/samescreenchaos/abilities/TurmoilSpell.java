package com.scs.samescreenchaos.abilities;

import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;
import com.scs.samescreenchaos.entities.WizardAvatar;
import com.scs.samescreenchaos.entities.nonphysical.TurmoilEffect;

public class TurmoilSpell extends AbstractSpell {

	public TurmoilSpell(MultiplayerVoxelWorldMain _game, AbstractGameModule module, WizardAvatar _player) {
		super(_game, module, _player, "Turmoil", 1);
	}

	public boolean cast() {
		new TurmoilEffect(game, module);
		return true;
	}
}
