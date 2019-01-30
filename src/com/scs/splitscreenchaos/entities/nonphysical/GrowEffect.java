package com.scs.splitscreenchaos.entities.nonphysical;

import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class GrowEffect extends AbstractTimedEffect {

	private AbstractCreature creature;
	
	public GrowEffect(SplitScreenFpsEngine _game, AbstractGameModule _module, AbstractCreature _creature) {
		super(_game, _module, "GrowEffect", 6);
		
		creature = _creature;
		
		// todo creature.getMainNode().

	}

	
	@Override
	protected void effectFinished() {
		// todo
		
	}

}
