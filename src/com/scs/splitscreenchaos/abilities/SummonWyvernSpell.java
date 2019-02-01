package com.scs.splitscreenchaos.abilities;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenchaos.entities.creatures.Wyvern;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class SummonWyvernSpell extends AbstractSummonSpell {

	public SummonWyvernSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "Wyvern", 40);
	}

	@Override
	protected AbstractCreature createCreature(Vector3f pos) {
		Wyvern golem = new Wyvern(game, module, pos, (WizardAvatar)this.avatar);
		return golem;
	}

}
