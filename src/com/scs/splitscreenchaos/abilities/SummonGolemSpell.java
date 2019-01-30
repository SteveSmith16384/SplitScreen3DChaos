package com.scs.splitscreenchaos.abilities;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenchaos.entities.creatures.Golem;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class SummonGolemSpell extends AbstractSummonSpell {

	public SummonGolemSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "SummonGolemSpell", 1);
	}

	@Override
	protected AbstractCreature createCreature(Vector3f pos) {
		Golem golem = new Golem(game, module, pos, (WizardAvatar)this.player);
		return golem;
	}

}

