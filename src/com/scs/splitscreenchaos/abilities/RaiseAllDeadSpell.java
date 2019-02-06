package com.scs.splitscreenchaos.abilities;

import com.scs.splitscreenchaos.entities.CreatureCorpse;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.components.IEntity;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class RaiseAllDeadSpell extends AbstractSpell {

	public RaiseAllDeadSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "Raise ALL Dead", 80, -1);
	}


	@Override
	public boolean cast() {
		for (IEntity e : module.entities) {
			if (e instanceof CreatureCorpse) {
				CreatureCorpse corpse = (CreatureCorpse)e;
				corpse.resurrect(null);
				this.avatar.hud.appendToLog(corpse.name + " resurrected!");
			}
		}
		return true;
	}


	@Override
	public boolean showFloorSelector() {
		return false;
	}
}
