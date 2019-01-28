package com.scs.splitscreenchaos.entities.nonphysical;

import com.scs.splitscreenfpsengine.MultiplayerVoxelWorldMain;
import com.scs.splitscreenfpsengine.entities.AbstractPlayersAvatar;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class RunFastEffect extends AbstractTimedEffect {

	private AbstractPlayersAvatar wiz;
	
	public RunFastEffect(MultiplayerVoxelWorldMain _game, AbstractGameModule _module, AbstractPlayersAvatar _wiz) {
		super(_game, _module, "RunFastEffect", 10);
		
		wiz = _wiz;
		
		this.wiz.moveSpeed = this.wiz.moveSpeed * 2;

	}

	
	@Override
	protected void effectFinished() {
		wiz.moveSpeed = wiz.moveSpeed/2; 
		wiz.hud.appendToLog("Run fast expired");
		
	}

}
