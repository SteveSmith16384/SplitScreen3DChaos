package com.scs.splitscreenchaos.entities.creatures;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.components.ICreatureModel;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.models.BeholderModel;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class Beholder extends AbstractCreature {

	public Beholder(SplitScreenFpsEngine _game, AbstractGameModule _module, Vector3f startPos, WizardAvatar owner) {
		super(_game, _module, "Beholder", startPos, owner, 3f, 6, 3, 2, false);
	}

	
	@Override
	public ICreatureModel getCreatureModel() {
		return new BeholderModel(game.getAssetManager());
	}


}
