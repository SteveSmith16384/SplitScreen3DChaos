package com.scs.splitscreenchaos.entities.creatures;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.ChaosGameModule;
import com.scs.splitscreenchaos.components.ICreatureModel;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.models.ZombieModel;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;

public class Zombie extends AbstractCreature {

	public Zombie(SplitScreenFpsEngine _game, ChaosGameModule _module, Vector3f startPos, WizardAvatar _side) {
		super(_game, _module, "Zombie", startPos, _side, true);
	}

	
	@Override
	public ICreatureModel getCreatureModel() {
		return new ZombieModel(game.getAssetManager());
	}


}
