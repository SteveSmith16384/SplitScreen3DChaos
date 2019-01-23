package com.scs.samescreenchaos.entities.creatures;

import com.jme3.math.Vector3f;
import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;
import com.scs.samescreenchaos.models.GoldenDragonModel;
import com.scs.samescreenchaos.models.ICreatureModel;

public class GoldenDragon extends AbstractCreature {

	public GoldenDragon(MultiplayerVoxelWorldMain _game, AbstractGameModule _module, Vector3f startPos, int _side) {
		super(_game, _module, "Golden Dragon", startPos, _side, 0.8f);
	}

	
	@Override
	protected ICreatureModel getCreatureModel() {
		return new GoldenDragonModel(game.getAssetManager());
	}


}
