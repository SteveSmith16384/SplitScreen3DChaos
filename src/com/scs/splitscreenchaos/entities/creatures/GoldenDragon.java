package com.scs.splitscreenchaos.entities.creatures;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.ChaosGameModule;
import com.scs.splitscreenchaos.components.ICreatureModel;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.models.GoldenDragonModel;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;

public class GoldenDragon extends AbstractCreature {

	public GoldenDragon(SplitScreenFpsEngine _game, ChaosGameModule _module, Vector3f startPos, WizardAvatar _owner) {
		super(_game, _module, "Golden Dragon", startPos, _owner, false);
	}

	
	@Override
	public ICreatureModel getCreatureModel() {
		return new GoldenDragonModel(game.getAssetManager());
	}


}
