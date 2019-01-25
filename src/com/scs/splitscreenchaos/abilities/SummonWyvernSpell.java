package com.scs.splitscreenchaos.abilities;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenchaos.entities.creatures.Wyvern;
import com.scs.splitscreenfpsengine.MultiplayerVoxelWorldMain;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class SummonWyvernSpell extends AbstractSummonSpell {

	public SummonWyvernSpell(MultiplayerVoxelWorldMain _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "Wyvern");
	}

	@Override
	protected AbstractCreature createCreature(Vector3f pos) {
		Wyvern golem = new Wyvern(game, module, pos, (WizardAvatar)this.player);
		return golem;
	}

}
