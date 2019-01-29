package com.scs.splitscreenchaos.abilities;

import com.scs.splitscreenchaos.entities.AbstractCorpse;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class RaiseDeadSpell extends AbstractSpell {

	public RaiseDeadSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "RaiseDeadSpell", 1);
	}


	@Override
	public boolean cast() {
		AbstractCorpse corpse = (AbstractCorpse)module.getWithRay(this.player, AbstractCorpse.class, 8);
		if (corpse != null) {
			corpse.resurrect((WizardAvatar)player);
			return true;
		}
		return false;
	}


}
