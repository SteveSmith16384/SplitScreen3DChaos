package com.scs.samescreenchaos.abilities;

import com.scs.multiplayervoxelworld.abilities.IAbility;
import com.scs.multiplayervoxelworld.entities.AbstractPlayersAvatar;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;

public abstract class AbstractSpell implements IAbility {

	protected AbstractGameModule module;
	protected String name;
	protected AbstractPlayersAvatar player;
	
	public AbstractSpell(AbstractGameModule _module, AbstractPlayersAvatar _player, String _name) {
		name = _name;
		module = _module;
		player = _player;
	}


	/*public boolean activate(float interpol) {
		return cast(interpol);
	}*/
	
	
	public boolean process(float interpol) {
		return false;
	}

	
	public String getHudText() {
		return name;
	}

	
}
