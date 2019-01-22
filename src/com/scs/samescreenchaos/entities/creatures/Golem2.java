package com.scs.samescreenchaos.entities.creatures;

import com.jme3.math.Vector3f;
import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.entities.AbstractPhysicalEntity;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;
import com.scs.samescreenchaos.models.GolemModel;
import com.scs.samescreenchaos.models.ICreatureModel;

public class Golem2 extends AbstractCreature {

	public Golem2(MultiplayerVoxelWorldMain _game, AbstractGameModule _module, Vector3f startPos, int _side) {
		super(_game, _module, "Golem", startPos, _side, 0.3f);
	}

	
	@Override
	protected ICreatureModel getCreatureModel() {
		return new GolemModel(game.getAssetManager());
	}


}
