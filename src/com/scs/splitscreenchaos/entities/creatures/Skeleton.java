package com.scs.splitscreenchaos.entities.creatures;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.models.ICreatureModel;
import com.scs.splitscreenchaos.models.SkeletonModel;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class Skeleton extends AbstractCreature {

	public Skeleton(SplitScreenFpsEngine _game, AbstractGameModule _module, Vector3f startPos, WizardAvatar owner) {
		super(_game, _module, "Skeleton", startPos, owner, 1f, 1, 1, 2, true);
	}

	
	@Override
	public ICreatureModel getCreatureModel() {
		return new SkeletonModel(game.getAssetManager());
	}



}
