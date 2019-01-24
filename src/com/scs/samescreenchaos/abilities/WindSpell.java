package com.scs.samescreenchaos.abilities;

import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;
import com.scs.samescreenchaos.entities.WizardAvatar;
import com.scs.samescreenchaos.entities.nonphysical.WindEffect;

public class WindSpell extends AbstractSpell {

	public WindSpell(MultiplayerVoxelWorldMain _game, AbstractGameModule module, WizardAvatar _player) {
		super(_game, module, _player, "Wind", 1);
	}

	public boolean cast() {
		new WindEffect(game, module, player.camDir);
		return true;
	}
}
