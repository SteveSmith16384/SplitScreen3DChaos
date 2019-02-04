package com.scs.splitscreenchaos.entities.nonphysical;

import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenfpsengine.Settings;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class FreezeEffect extends AbstractTimedEffect {

	private AbstractCreature creature;
	
	public FreezeEffect(SplitScreenFpsEngine _game, AbstractGameModule _module, AbstractCreature _creature) {
		super(_game, _module, "FreezeEffect", 10);
		
		creature = _creature;
		
		creature.setFrozen(true);
		
		Settings.p(creature + " is frozen");
		
	}

	
	@Override
	protected void effectFinished() {
		creature.setFrozen(false);
		Settings.p(creature + " is no longer frozen");
		
	}

}
