package com.scs.splitscreenchaos.abilities;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.ChaosGameModule;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenchaos.entities.creatures.RedDragon;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class SummonRedDragonSpell extends AbstractSummonSpell {

	public SummonRedDragonSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "Summon Red Dragon", 80);
	}

	@Override
	protected AbstractCreature createCreature(Vector3f pos) {
		RedDragon golem = new RedDragon(game, (ChaosGameModule)module, pos, (WizardAvatar)this.avatar);
		return golem;
	}

}
