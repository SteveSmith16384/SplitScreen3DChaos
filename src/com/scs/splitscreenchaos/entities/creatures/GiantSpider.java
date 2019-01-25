package com.scs.splitscreenchaos.entities.creatures;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.models.ICreatureModel;
import com.scs.splitscreenchaos.models.SpiderModel;
import com.scs.splitscreenfpsengine.MultiplayerVoxelWorldMain;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class GiantSpider extends AbstractCreature {

	public GiantSpider(MultiplayerVoxelWorldMain _game, AbstractGameModule _module, Vector3f startPos, WizardAvatar owner) {
		super(_game, _module, "GiantSpider", startPos, owner, 1f, 1, 1);
	}

	
	@Override
	public ICreatureModel getCreatureModel() {
		return new SpiderModel(game.getAssetManager());
	}


}
