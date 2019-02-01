package com.scs.splitscreenchaos.entities.creatures;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.components.ICreatureModel;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.models.CerberusModel;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class Cerberus extends AbstractCreature {

	public Cerberus(SplitScreenFpsEngine _game, AbstractGameModule _module, Vector3f startPos, WizardAvatar owner) {
		super(_game, _module, "Cerberus", startPos, owner, 3f, 3, 1, 3, false);
	}

	
	@Override
	public ICreatureModel getCreatureModel() {
		return new CerberusModel(game.getAssetManager());
	}


}