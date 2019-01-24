package com.scs.samescreenchaos.abilities;

import com.jme3.math.Vector3f;
import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;
import com.scs.samescreenchaos.entities.WizardAvatar;
import com.scs.samescreenchaos.entities.creatures.AbstractCreature;
import com.scs.samescreenchaos.entities.creatures.GreenDragon;

public class SummonGreenDragonSpell extends AbstractSummonSpell {

	public SummonGreenDragonSpell(MultiplayerVoxelWorldMain _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "Green Dragon");
	}

	@Override
	protected AbstractCreature createCreature(Vector3f pos) {
		GreenDragon golem = new GreenDragon(game, module, pos, (WizardAvatar)this.player);
		return golem;
	}

}
