package com.scs.splitscreenchaos.abilities;

import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenchaos.entities.nonphysical.ShrinkEffect;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class ShrinkSpell extends AbstractSpell {

	public ShrinkSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "Shrink", 10, -1);
	}


	@Override
	public boolean cast() {
		AbstractCreature creature = (AbstractCreature)module.getWithRay(this.avatar, AbstractCreature.class, -1);
		if (creature != null) {
			if (creature.getSide() != this.getWizard().getSide()) {
				new ShrinkEffect(game, module, creature);
				game.soundSystem.playSound("Sound/qubodup-SlowDown.ogg", 1, false);
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
