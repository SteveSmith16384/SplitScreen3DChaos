package com.scs.splitscreenchaos.entities.creatures;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.models.ICreatureModel;
import com.scs.splitscreenchaos.models.RedDragonModel;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class RedDragon extends AbstractCreature {

	public RedDragon(SplitScreenFpsEngine _game, AbstractGameModule _module, Vector3f startPos, WizardAvatar _side) {
		super(_game, _module, "Red Dragon", startPos, _side, 3, 7, 9, 8, false);
	}

	
	@Override
	public ICreatureModel getCreatureModel() {
		return new RedDragonModel(game.getAssetManager());
	}


}
