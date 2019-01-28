package com.scs.splitscreenchaos.entities.creatures;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.models.ICreatureModel;
import com.scs.splitscreenchaos.models.WyvernModel;
import com.scs.splitscreenfpsengine.MultiplayerVoxelWorldMain;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class Wyvern extends AbstractCreature {

	public Wyvern(MultiplayerVoxelWorldMain _game, AbstractGameModule _module, Vector3f startPos, WizardAvatar owner) {
		super(_game, _module, "Wyvern", startPos, owner, 1f, 1, 1, 3, false);
	}

	
	@Override
	public ICreatureModel getCreatureModel() {
		return new WyvernModel(game.getAssetManager());
	}


}
