package com.scs.splitscreenchaos.abilities;

import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.nonphysical.RunFastEffect;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class RunFastSpell extends AbstractSpell {

	public RunFastSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar _player) {
		super(_game, module, _player, "RunFast", 10, 0);
	}


	@Override
	public boolean cast() {
		new RunFastEffect(game, module, player);
		return  true;
	}

}
