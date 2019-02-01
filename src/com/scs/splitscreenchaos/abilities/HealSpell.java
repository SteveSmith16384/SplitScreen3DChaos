package com.scs.splitscreenchaos.abilities;

import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class HealSpell extends AbstractSpell {

	public HealSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "HealSpell", 10, -1);
	}


	@Override
	public boolean cast() {
		AbstractCreature ape = (AbstractCreature)module.getWithRay(this.avatar, AbstractCreature.class, -1);
		if (ape != null && ape.getOwner() == this.avatar) {
			ape.restoreHealth();
			return true;
		}
		return false;
	}


}
