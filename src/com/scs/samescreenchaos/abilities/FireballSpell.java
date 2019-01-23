package com.scs.samescreenchaos.abilities;

import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.entities.AbstractBullet;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;
import com.scs.samescreenchaos.entities.Fireball;
import com.scs.samescreenchaos.entities.WizardAvatar;

public class FireballSpell extends AbstractShootingSpell {

	public FireballSpell(MultiplayerVoxelWorldMain _game, AbstractGameModule module, WizardAvatar _player) {
		super(_game, module, _player, "Fireball", 1);
	}

	@Override
	protected AbstractBullet getBullet() {
		return new Fireball(game, module, player);
	}

}
