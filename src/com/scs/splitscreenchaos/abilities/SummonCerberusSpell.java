package com.scs.splitscreenchaos.abilities;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.ChaosGameModule;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenchaos.entities.creatures.Cerberus;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class SummonCerberusSpell extends AbstractSummonSpell {

	public SummonCerberusSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "Summon Cerberus", 50);
	}

	@Override
	protected AbstractCreature createCreature(Vector3f pos) {
		Cerberus golem = new Cerberus(game, (ChaosGameModule)module, pos, (WizardAvatar)this.avatar);
		return golem;
	}

}
