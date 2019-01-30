package com.scs.splitscreenchaos.entities.nonphysical;

import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class FreezeEffect extends AbstractTimedEffect {

	private AbstractCreature creature;
	
	public FreezeEffect(SplitScreenFpsEngine _game, AbstractGameModule _module, AbstractCreature _creature) {
		super(_game, _module, "FreezeEffect", 6);
		
		creature = _creature;
		
		creature.frozen = true;

	}

	
	@Override
	protected void effectFinished() {
		creature.frozen = false;
		
	}

}
