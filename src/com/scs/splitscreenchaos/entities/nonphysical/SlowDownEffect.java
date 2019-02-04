package com.scs.splitscreenchaos.entities.nonphysical;

import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.entities.AbstractPlayersAvatar;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class SlowDownEffect extends AbstractTimedEffect {

	private AbstractCreature creature;
	
	public SlowDownEffect(SplitScreenFpsEngine _game, AbstractGameModule _module, AbstractCreature _cre) {
		super(_game, _module, "SlowDownEffect", 10);
		
		creature = _cre;
		
		this.creature.speed = this.creature.speed / 4;

	}

	
	@Override
	protected void effectFinished() {
		creature.speed  = creature.speed * 4;
		
	}

}
