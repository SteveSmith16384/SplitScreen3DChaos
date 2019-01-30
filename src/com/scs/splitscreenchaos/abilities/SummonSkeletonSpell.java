package com.scs.splitscreenchaos.abilities;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenchaos.entities.creatures.Skeleton;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class SummonSkeletonSpell extends AbstractSummonSpell {

	public SummonSkeletonSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "Skeleton", 1);
	}

	@Override
	protected AbstractCreature createCreature(Vector3f pos) {
		Skeleton golem = new Skeleton(game, module, pos, (WizardAvatar)this.player);
		return golem;
	}

}
