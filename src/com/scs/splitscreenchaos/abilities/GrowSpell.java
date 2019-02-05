package com.scs.splitscreenchaos.abilities;

import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenchaos.entities.nonphysical.GrowEffect;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class GrowSpell extends AbstractSpell {

	public GrowSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "Grow", 10, -1);
	}


	@Override
	public boolean cast() {
		AbstractCreature creature = (AbstractCreature)module.getWithRay(this.avatar, AbstractCreature.class, -1);
		if (creature != null) {
			if (creature.getSide() == this.getWizard().getSide()) {
				new GrowEffect(game, module, creature);
				game.soundSystem.playSound("Sound/qubodup-SpeedUp.ogg", 1, false);
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
