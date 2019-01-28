package com.scs.splitscreenchaos.entities.creatures;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.models.GreenDragonModel;
import com.scs.splitscreenchaos.models.ICreatureModel;
import com.scs.splitscreenfpsengine.MultiplayerVoxelWorldMain;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class GreenDragon extends AbstractCreature {

	public GreenDragon(MultiplayerVoxelWorldMain _game, AbstractGameModule _module, Vector3f startPos, WizardAvatar _side) {
		super(_game, _module, "Green Dragon", startPos, _side, 3, 5, 8, 7, false);
	}

	
	@Override
	public ICreatureModel getCreatureModel() {
		return new GreenDragonModel(game.getAssetManager());
	}


}
