package com.scs.splitscreenchaos.abilities;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenchaos.entities.creatures.GoldenDragon;
import com.scs.splitscreenchaos.entities.creatures.RedDragon;
import com.scs.splitscreenfpsengine.MultiplayerVoxelWorldMain;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class SummonRedDragonSpell extends AbstractSummonSpell {

	public SummonRedDragonSpell(MultiplayerVoxelWorldMain _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "Red Dragon");
	}

	@Override
	protected AbstractCreature createCreature(Vector3f pos) {
		RedDragon golem = new RedDragon(game, module, pos, (WizardAvatar)this.player);
		return golem;
	}

}
