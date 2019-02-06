package com.scs.splitscreenchaos.entities.creatures;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.ChaosGameModule;
import com.scs.splitscreenchaos.components.ICreatureModel;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.models.SkeletonModel;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;

public class Skeleton extends AbstractCreature {

	public Skeleton(SplitScreenFpsEngine _game, ChaosGameModule _module, Vector3f startPos, WizardAvatar owner) {
		super(_game, _module, "Skeleton", startPos, owner, true);
	}

	
	@Override
	public ICreatureModel getCreatureModel() {
		return new SkeletonModel(game.getAssetManager());
	}



}
