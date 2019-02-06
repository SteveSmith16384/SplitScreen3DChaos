package com.scs.splitscreenchaos.entities.creatures;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.ChaosGameModule;
import com.scs.splitscreenchaos.components.ICreatureModel;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.models.GreenDragonModel;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;

public class GreenDragon extends AbstractCreature {

	public GreenDragon(SplitScreenFpsEngine _game, ChaosGameModule _module, Vector3f startPos, WizardAvatar _side) {
		super(_game, _module, "Green Dragon", startPos, _side, false);
	}

	
	@Override
	public ICreatureModel getCreatureModel() {
		return new GreenDragonModel(game.getAssetManager());
	}


}
