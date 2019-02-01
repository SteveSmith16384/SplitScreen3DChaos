package com.scs.splitscreenchaos.abilities;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.entities.FloorOrCeiling;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class TeleportSpell extends AbstractSpell {

	public TeleportSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "Teleport", 20, -1);
	}


	@Override
	public boolean cast() {
		Vector3f position = module.getFloorPointWithRay(this.getWizard(), -1);
		if (position != null) {
			avatar.playerControl.warp(position);
			return true;
		}
		return false;
	}

}
