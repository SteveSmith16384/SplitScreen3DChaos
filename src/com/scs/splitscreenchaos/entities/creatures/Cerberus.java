package com.scs.splitscreenchaos.entities.creatures;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.ChaosGameModule;
import com.scs.splitscreenchaos.components.ICreatureModel;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.models.CerberusModel;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;

public class Cerberus extends AbstractCreature {

	public Cerberus(SplitScreenFpsEngine _game, ChaosGameModule _module, Vector3f startPos, WizardAvatar owner) {
		super(_game, _module, "Cerberus", startPos, owner, false);
	}

	
	@Override
	public ICreatureModel getCreatureModel() {
		return new CerberusModel(game.getAssetManager());
	}


}
