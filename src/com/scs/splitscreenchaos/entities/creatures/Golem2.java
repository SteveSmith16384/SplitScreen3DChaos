package com.scs.splitscreenchaos.entities.creatures;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.models.GolemModel;
import com.scs.splitscreenchaos.models.ICreatureModel;
import com.scs.splitscreenfpsengine.MultiplayerVoxelWorldMain;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class Golem2 extends AbstractCreature {

	public Golem2(MultiplayerVoxelWorldMain _game, AbstractGameModule _module, Vector3f startPos, WizardAvatar _side) {
		super(_game, _module, "Golem", startPos, _side, 0.3f, 1, 1, 8, false);
	}

	
	@Override
	public ICreatureModel getCreatureModel() {
		return new GolemModel(game.getAssetManager());
	}


}
