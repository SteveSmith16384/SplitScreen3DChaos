package com.scs.splitscreenchaos.abilities;

import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenfpsengine.MultiplayerVoxelWorldMain;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class HealSpell extends AbstractSpell {

	public HealSpell(MultiplayerVoxelWorldMain _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "HealSpell", 1);
	}


	@Override
	public boolean cast() {
		AbstractCreature ape = (AbstractCreature)module.getWithRay(this.player, AbstractCreature.class);
		if (ape != null && ape.getOwner() == this.player) {
			ape.restoreHealth();
			return true;
		}
		return false;
	}


}