package com.scs.splitscreenchaos.abilities;

import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.nonphysical.WindEffect;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class WindSpell extends AbstractSpell {

	public WindSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar _player) {
		super(_game, module, _player, "Wind", 30, 0);
	}

	public boolean cast() {
		new WindEffect(game, module, (WizardAvatar)player, player.camDir);
		return true;
	}
}
