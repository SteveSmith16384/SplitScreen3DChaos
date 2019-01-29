package com.scs.splitscreenchaos.abilities;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenchaos.entities.creatures.GiantSpider;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class SummonGiantSpiderSpell extends AbstractSummonSpell {

	public SummonGiantSpiderSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "Giant Spider");
	}

	@Override
	protected AbstractCreature createCreature(Vector3f pos) {
		GiantSpider golem = new GiantSpider(game, module, pos, (WizardAvatar)this.player);
		return golem;
	}

}
