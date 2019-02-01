package com.scs.splitscreenchaos.abilities;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.ChaosGameModule;
import com.scs.splitscreenchaos.blocks.WallBlock;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.entities.FloorOrCeiling;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class WallSpell extends AbstractSpell {

	private static final int SIZE = 1;

	public WallSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "Wall", 10, -1);
	}


	@Override
	public boolean cast() {
		Vector3f position = module.getPointWithRay(this.getWizard(), FloorOrCeiling.class, -1);
		if (position != null) {
			ChaosGameModule m = (ChaosGameModule)module;
			// todo - check area clear
			// todo - rrmove wall after a while
			// todo - don't use voxels
			m.vte.addRectRange_Actual(position, new Vector3f(SIZE, SIZE*2, SIZE), WallBlock.class);
			return true;
		}
		return false;
	}


}
