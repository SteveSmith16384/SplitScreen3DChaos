package com.scs.splitscreenchaos.abilities;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.entities.BlackHole;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.jme.JMEModelFunctions;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class BlackHoleSpell extends AbstractSpell {
	
	public BlackHoleSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "Black Hole", 80, -1);
	}


	@Override
	public boolean cast() {
		Vector3f position = module.getFloorPointWithRay(this.getWizard(), -1);
		if (position != null) {
			Vector3f pos = JMEModelFunctions.getHeightAtPoint(position.x, position.z, game.getRootNode());
			pos.y += 1;
			new BlackHole(game, module, (WizardAvatar)avatar, pos);
			return true;
		}
		return false;
	}

}
