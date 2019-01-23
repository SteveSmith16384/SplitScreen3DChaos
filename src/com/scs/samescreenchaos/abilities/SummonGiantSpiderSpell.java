package com.scs.samescreenchaos.abilities;

import com.jme3.math.Vector3f;
import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;
import com.scs.samescreenchaos.entities.WizardAvatar;
import com.scs.samescreenchaos.entities.creatures.AbstractCreature;
import com.scs.samescreenchaos.entities.creatures.GiantSpider;

public class SummonGiantSpiderSpell extends AbstractSummonSpell {

	public SummonGiantSpiderSpell(MultiplayerVoxelWorldMain _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "Giant Spider");
	}

	@Override
	protected AbstractCreature createCreature(Vector3f pos) {
		GiantSpider golem = new GiantSpider(game, module, pos, (WizardAvatar)this.player);
		return golem;
	}

}
