package com.scs.splitscreenchaos.abilities;

import com.scs.splitscreenchaos.entities.AbstractCorpse;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class RaiseDeadSpell extends AbstractSpell {

	private static final float RANGE = 8f;
	
	public RaiseDeadSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "RaiseDeadSpell", 1, RANGE);
	}


	@Override
	public boolean cast() {
		AbstractCorpse corpse = (AbstractCorpse)module.getWithRay(this.player, AbstractCorpse.class, RANGE);
		if (corpse != null) {
			corpse.resurrect((WizardAvatar)player);
			return true;
		}
		return false;
	}


}
