package com.scs.samescreenchaos.abilities;

import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.entities.AbstractBullet;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;
import com.scs.samescreenchaos.entities.WizardAvatar;

public abstract class AbstractShootingSpell extends AbstractSpell {

	public AbstractShootingSpell(MultiplayerVoxelWorldMain _game, AbstractGameModule module, WizardAvatar _player, String _name, int _cost) {
		super(_game, module, _player, _name, _cost);
	}


	public boolean cast() {
		AbstractBullet b = this.getBullet();
		return true;
	}
	
	
	protected abstract AbstractBullet getBullet();
}
