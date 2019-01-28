package com.scs.splitscreenchaos.entities.creatures;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.models.GoldenDragonModel;
import com.scs.splitscreenchaos.models.ICreatureModel;
import com.scs.splitscreenfpsengine.MultiplayerVoxelWorldMain;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class GoldenDragon extends AbstractCreature {

	public GoldenDragon(MultiplayerVoxelWorldMain _game, AbstractGameModule _module, Vector3f startPos, WizardAvatar _owner) {
		super(_game, _module, "Golden Dragon", startPos, _owner, 3, 9, 9, 9, false);
	}

	
	@Override
	public ICreatureModel getCreatureModel() {
		return new GoldenDragonModel(game.getAssetManager());
	}


}
