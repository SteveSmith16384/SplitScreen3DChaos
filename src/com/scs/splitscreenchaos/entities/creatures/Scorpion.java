package com.scs.splitscreenchaos.entities.creatures;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.ChaosGameModule;
import com.scs.splitscreenchaos.components.ICreatureModel;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.models.ScorpionModel;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;

public class Scorpion extends AbstractCreature {

	public Scorpion(SplitScreenFpsEngine _game, ChaosGameModule _module, Vector3f startPos, WizardAvatar owner) {
		super(_game, _module, "Scorpion", startPos, owner, false);
	}

	
	@Override
	public ICreatureModel getCreatureModel() {
		return new ScorpionModel(game.getAssetManager());
	}

}
