package com.scs.samescreenchaos.abilities;

import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.Settings;
import com.scs.multiplayervoxelworld.abilities.AbstractAbility;
import com.scs.multiplayervoxelworld.abilities.IAbility;
import com.scs.multiplayervoxelworld.entities.AbstractPlayersAvatar;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;

public abstract class AbstractSpell extends AbstractAbility implements IAbility {

	protected String name;
	private int cost;
	
	public AbstractSpell(MultiplayerVoxelWorldMain _game, AbstractGameModule module, AbstractPlayersAvatar _player, String _name, int _cost) {
		super(_game, module, _player);
		cost = _cost;
	}


	@Override
	public boolean activate(float interpol) {
		if (this.player.resources < cost) {
			Settings.p("Not enough resources");
			return false;
		}
		return true;
	}
	
	
	@Override
	public boolean process(float interpol) {
		return false;
	}

	
	@Override
	public String getHudText() {
		return name;
	}
	
	
	@Override
	public boolean onlyActivateOnClick() {
		return true;
	}


	
}
