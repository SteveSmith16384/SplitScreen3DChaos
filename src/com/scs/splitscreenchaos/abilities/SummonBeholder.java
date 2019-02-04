package com.scs.splitscreenchaos.abilities;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenchaos.entities.creatures.Beholder;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class SummonBeholder extends AbstractSummonSpell {

	public SummonBeholder(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "SummonBeholder", 40);
	}

	@Override
	protected AbstractCreature createCreature(Vector3f pos) {
		return new Beholder(game, module, pos, (WizardAvatar)this.avatar);
	}

}
