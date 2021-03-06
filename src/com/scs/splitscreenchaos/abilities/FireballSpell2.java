package com.scs.splitscreenchaos.abilities;

import com.scs.splitscreenchaos.entities.HomingFireball;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class FireballSpell2 extends AbstractSpell {

	public FireballSpell2(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "Fireball", 5, -1);
	}


	@Override
	public boolean cast() {
		AbstractCreature creature = (AbstractCreature)module.getWithRay(this.avatar, AbstractCreature.class, -1);
		if (creature != null) {
			if (creature.getSide() != this.getWizard().getSide()) {
				new HomingFireball(game, module, this.getWizard(), creature);
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


	@Override
	protected boolean showCastEffect() {
		return false;
	}
	
	
}
