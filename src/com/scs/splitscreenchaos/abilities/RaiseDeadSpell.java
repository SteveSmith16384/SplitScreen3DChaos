package com.scs.splitscreenchaos.abilities;

import com.scs.splitscreenchaos.entities.CreatureCorpse;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class RaiseDeadSpell extends AbstractSpell {

	private static final float RANGE = 8f;
	
	public RaiseDeadSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "Raise Dead", 20, RANGE);
	}


	@Override
	public boolean cast() {
		CreatureCorpse corpse = (CreatureCorpse)module.getWithRay(this.avatar, CreatureCorpse.class, RANGE);
		if (corpse != null) {
			corpse.resurrect((WizardAvatar)avatar);
			return true;
		}
		avatar.hud.appendToLog("No valid corpse found");
		return false;
	}


	@Override
	public boolean showFloorSelector() {
		return false;
	}
}
