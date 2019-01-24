package com.scs.samescreenchaos.abilities;

import com.jme3.math.Vector3f;
import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;
import com.scs.samescreenchaos.entities.WizardAvatar;
import com.scs.samescreenchaos.entities.creatures.AbstractCreature;
import com.scs.samescreenchaos.entities.creatures.GoldenDragon;
import com.scs.samescreenchaos.entities.creatures.RedDragon;

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
