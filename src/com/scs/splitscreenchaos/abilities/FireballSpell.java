package com.scs.splitscreenchaos.abilities;

import com.scs.splitscreenchaos.entities.Fireball;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenfpsengine.MultiplayerVoxelWorldMain;
import com.scs.splitscreenfpsengine.entities.AbstractBullet;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class FireballSpell extends AbstractShootingSpell {

	public FireballSpell(MultiplayerVoxelWorldMain _game, AbstractGameModule module, WizardAvatar _player) {
		super(_game, module, _player, "Fireball", 1);
	}

	@Override
	protected AbstractBullet getBullet() {
		return new Fireball(game, module, player);
	}

}
