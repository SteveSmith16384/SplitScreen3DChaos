package com.scs.splitscreenchaos.abilities;

import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class SubversionSpell extends AbstractSpell {

	public SubversionSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "Subversion", 30, -1);
	}


	@Override
	public boolean cast() {
		AbstractCreature creature = (AbstractCreature)module.getWithRay(this.avatar, AbstractCreature.class, -1);
		if (creature != null) {
			if (creature.getOwner() != avatar) {
				creature.subverted((WizardAvatar)avatar);
				return true;
			}
		}
		return false;
	}


}
