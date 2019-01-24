package com.scs.samescreenchaos.entities.creatures;

import com.jme3.math.Vector3f;
import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;
import com.scs.samescreenchaos.entities.WizardAvatar;
import com.scs.samescreenchaos.models.GreenDragonModel;
import com.scs.samescreenchaos.models.ICreatureModel;

public class GreenDragon extends AbstractCreature {

	public GreenDragon(MultiplayerVoxelWorldMain _game, AbstractGameModule _module, Vector3f startPos, WizardAvatar _side) {
		super(_game, _module, "Green Dragon", startPos, _side, 0.8f, 1, 1);
	}

	
	@Override
	public ICreatureModel getCreatureModel() {
		return new GreenDragonModel(game.getAssetManager());
	}


}
