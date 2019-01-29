package com.scs.splitscreenchaos.abilities;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class SwapPositionsSpell extends AbstractSpell {

	public SwapPositionsSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "SwapPositions", 1);
	}


	@Override
	public boolean cast() {
		WizardAvatar enemyWizard = (WizardAvatar)module.getWithRay(this.player, WizardAvatar.class, -1);
		if (enemyWizard != null) {
			Vector3f enemyPos = enemyWizard.getLocation().clone();
			Vector3f ourPos = this.getWizard().getLocation().clone();
			
			this.getWizard().setLocation(enemyPos);
			enemyWizard.setLocation(ourPos);
			enemyWizard.hud.appendToLog("You have swapped positions!");
			return true;
		}
		return false;
	}


}
