package com.scs.splitscreenchaos.abilities;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenchaos.entities.creatures.GoldenDragon;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class SummonGoldenDragonSpell extends AbstractSummonSpell {

	public SummonGoldenDragonSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "Summon Golden Dragon", 90);
	}

	@Override
	protected AbstractCreature createCreature(Vector3f pos) {
		GoldenDragon golem = new GoldenDragon(game, module, pos, (WizardAvatar)this.avatar);
		return golem;
	}

}
