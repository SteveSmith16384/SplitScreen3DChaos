package com.scs.splitscreenchaos.entities.creatures;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.components.ICreatureModel;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.models.GoldenDragonModel2;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class GoldenDragon extends AbstractCreature {

	public GoldenDragon(SplitScreenFpsEngine _game, AbstractGameModule _module, Vector3f startPos, WizardAvatar _owner) {
		super(_game, _module, "Golden Dragon", startPos, _owner, 3, 9, 9, 9, false);
	}

	
	@Override
	public ICreatureModel getCreatureModel() {
		return new GoldenDragonModel2(game.getAssetManager());
	}


}
