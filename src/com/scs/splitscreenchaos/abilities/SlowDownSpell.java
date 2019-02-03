package com.scs.splitscreenchaos.abilities;

import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenchaos.entities.nonphysical.SlowDownEffect;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class SlowDownSpell extends AbstractSpell {

	public SlowDownSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "SlowDownSpell", 10, -1);
	}


	@Override
	public boolean cast() {
		AbstractCreature creature = (AbstractCreature)module.getWithRay(this.avatar, AbstractCreature.class, -1);
		if (creature != null) {
			if (creature.getSide() != this.getWizard().getSide()) {
				new SlowDownEffect(game, module, creature);
				return true;
			}
		}
		avatar.hud.appendToLog("No valid target found");
		return false;
	}


}
