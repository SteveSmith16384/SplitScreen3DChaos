package com.scs.splitscreenchaos.abilities;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenchaos.entities.creatures.GreenDragon;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class SummonGreenDragonSpell extends AbstractSummonSpell {

	public SummonGreenDragonSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "Green Dragon", 70);
	}

	@Override
	protected AbstractCreature createCreature(Vector3f pos) {
		GreenDragon golem = new GreenDragon(game, module, pos, (WizardAvatar)this.avatar);
		return golem;
	}

}
