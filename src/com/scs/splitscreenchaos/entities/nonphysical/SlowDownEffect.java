package com.scs.splitscreenchaos.entities.nonphysical;

import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.entities.AbstractPlayersAvatar;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class SlowDownEffect extends AbstractTimedEffect {

	private AbstractCreature wiz;
	
	public SlowDownEffect(SplitScreenFpsEngine _game, AbstractGameModule _module, AbstractCreature _wiz) {
		super(_game, _module, "SlowDownEffect", 10);
		
		wiz = _wiz;
		
		//todo this.wiz.moveSpeed = this.wiz.moveSpeed / 2;

	}

	
	@Override
	protected void effectFinished() {
		//todo wiz.moveSpeed = wiz.moveSpeed * 2;
		
	}

}
