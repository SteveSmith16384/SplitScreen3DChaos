package com.scs.splitscreenchaos.abilities;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenchaos.entities.creatures.Scorpion;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class SummonScorpion extends AbstractSummonSpell {

	public SummonScorpion(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "Summon Scorpion", 50);
	}

	@Override
	protected AbstractCreature createCreature(Vector3f pos) {
		return new Scorpion(game, module, pos, (WizardAvatar)this.avatar);
	}

}
