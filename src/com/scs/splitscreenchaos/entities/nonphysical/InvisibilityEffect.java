package com.scs.splitscreenchaos.entities.nonphysical;

import com.jme3.scene.Spatial.CullHint;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.entities.AbstractPlayersAvatar;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class InvisibilityEffect extends AbstractTimedEffect {

	private AbstractPlayersAvatar wiz;
	
	public InvisibilityEffect(SplitScreenFpsEngine _game, AbstractGameModule _module, AbstractPlayersAvatar _wiz) {
		super(_game, _module, "InvisibilityEffect", 10);
		
		wiz = _wiz;
		wiz.getMainNode().setCullHint(CullHint.Always);
	}

	
	@Override
	protected void effectFinished() {
		wiz.getMainNode().setCullHint(CullHint.Inherit);
		wiz.hud.appendToLog("Invisibility expired");
		
	}

}
