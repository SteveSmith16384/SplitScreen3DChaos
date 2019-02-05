package com.scs.splitscreenchaos.abilities;

import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenchaos.entities.nonphysical.FreezeEffect;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class FreezeSpell extends AbstractSpell {

	public FreezeSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "Freeze", 5, -1);
	}


	@Override
	public boolean cast() {
		AbstractCreature creature = (AbstractCreature)module.getWithRay(this.avatar, AbstractCreature.class, -1);
		if (creature != null) {
			if (creature.getSide() != this.getWizard().getSide()) {
				new FreezeEffect(game, module, creature);
				game.soundSystem.playSound("Sound/freeze.wav", 1, false);
				return true;
			}
		}
		avatar.hud.appendToLog("No valid target found");
		return false;
	}


	@Override
	public boolean showFloorSelector() {
		return false;
	}

}
