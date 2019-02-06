package com.scs.splitscreenchaos.entities.creatures;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.ChaosGameModule;
import com.scs.splitscreenchaos.components.ICreatureModel;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.models.BeholderModel;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;

public class Beholder extends AbstractCreature {

	public Beholder(SplitScreenFpsEngine _game, ChaosGameModule _module, Vector3f startPos, WizardAvatar owner) {
		super(_game, _module, "Beholder", startPos, owner, false);
	}

	
	@Override
	public ICreatureModel getCreatureModel() {
		return new BeholderModel(game.getAssetManager());
	}


}
