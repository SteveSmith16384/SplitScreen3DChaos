package com.scs.splitscreenchaos.entities.nonphysical;

import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class ShrinkEffect extends AbstractTimedEffect {

	private AbstractCreature creature;
	
	public ShrinkEffect(SplitScreenFpsEngine _game, AbstractGameModule _module, AbstractCreature _creature) {
		super(_game, _module, "ShrinkEffect", 30);
		
		creature = _creature;
		
		creature.getMainNode().setLocalScale(.5f);
		creature.speed = creature.speed / 1.5f;
		creature.att = creature.att / 1.5f;
		creature.def = creature.def / 1.5f;
	}

	
	@Override
	protected void effectFinished() {
		creature.getMainNode().setLocalScale(1);
		creature.speed = creature.speed * 1.5f;
		creature.att = creature.att * 1.5f;
		creature.def = creature.def * 1.5f;
		
	}

}
