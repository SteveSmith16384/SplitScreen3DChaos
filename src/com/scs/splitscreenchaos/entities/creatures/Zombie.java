package com.scs.splitscreenchaos.entities.creatures;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.models.ICreatureModel;
import com.scs.splitscreenchaos.models.ZombieModel;
import com.scs.splitscreenfpsengine.MultiplayerVoxelWorldMain;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class Zombie extends AbstractCreature {

	public Zombie(MultiplayerVoxelWorldMain _game, AbstractGameModule _module, Vector3f startPos, WizardAvatar _side) {
		super(_game, _module, "Zombie", startPos, _side, 1, 1, 1);
	}

	
	@Override
	public ICreatureModel getCreatureModel() {
		return new ZombieModel(game.getAssetManager());
	}


}
