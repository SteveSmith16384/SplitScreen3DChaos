package com.scs.splitscreenchaos.abilities;

import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.entities.AbstractBullet;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public abstract class AbstractShootingSpell extends AbstractSpell {

	public AbstractShootingSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar _player, String _name, int _cost) {
		super(_game, module, _player, _name, _cost, -1);
	}


	public boolean cast() {
		AbstractBullet b = this.getBullet();
		return true;
	}
	
	
	protected abstract AbstractBullet getBullet();
}
