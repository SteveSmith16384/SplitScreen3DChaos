package com.scs.samescreenchaos.entities.creatures;

import com.jme3.math.Vector3f;
import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;
import com.scs.samescreenchaos.models.ICreatureModel;
import com.scs.samescreenchaos.models.ZombieModel;

public class Zombie extends AbstractCreature {

	public Zombie(MultiplayerVoxelWorldMain _game, AbstractGameModule _module, Vector3f startPos, int _side) {
		super(_game, _module, "Zombie", startPos, _side, 0.3f);
	}

	
	@Override
	protected ICreatureModel getCreatureModel() {
		return new ZombieModel(game.getAssetManager());
	}


}
