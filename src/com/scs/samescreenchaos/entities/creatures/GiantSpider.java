package com.scs.samescreenchaos.entities.creatures;

import com.jme3.math.Vector3f;
import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;
import com.scs.samescreenchaos.entities.WizardAvatar;
import com.scs.samescreenchaos.models.ICreatureModel;
import com.scs.samescreenchaos.models.SpiderModel;

public class GiantSpider extends AbstractCreature {

	public GiantSpider(MultiplayerVoxelWorldMain _game, AbstractGameModule _module, Vector3f startPos, WizardAvatar owner) {
		super(_game, _module, "GiantSpider", startPos, owner, 0.3f, 1, 1);
	}

	
	@Override
	protected ICreatureModel getCreatureModel() {
		return new SpiderModel(game.getAssetManager());
	}


}
