package com.scs.splitscreenchaos.abilities;

import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.nonphysical.InvisibilityEffect;
import com.scs.splitscreenfpsengine.MultiplayerVoxelWorldMain;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class InvisibilitySpell extends AbstractSpell {

	public InvisibilitySpell(MultiplayerVoxelWorldMain _game, AbstractGameModule module, WizardAvatar _player) {
		super(_game, module, _player, "Invisibility", 1);
	}


	@Override
	public boolean cast() {
		new InvisibilityEffect(game, module, player);
		return  true;
	}

}
