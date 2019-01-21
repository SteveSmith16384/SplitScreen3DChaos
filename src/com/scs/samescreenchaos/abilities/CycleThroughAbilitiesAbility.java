package com.scs.samescreenchaos.abilities;

import java.util.ArrayList;

import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.abilities.AbstractAbility;
import com.scs.multiplayervoxelworld.abilities.IAbility;
import com.scs.multiplayervoxelworld.entities.AbstractPlayersAvatar;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;

public class CycleThroughAbilitiesAbility extends AbstractAbility {
	
	private ArrayList<IAbility> abilities = new ArrayList();
	private int index;

	public CycleThroughAbilitiesAbility(MultiplayerVoxelWorldMain _game, AbstractGameModule module, AbstractPlayersAvatar p) {
		super(_game, module, p);
		
		abilities.add(new SummonGolemSpell(game, module, p));
		abilities.add(new WallSpell(game, module, p));
		abilities.add(new TeleportSpell(game, module, p));
	}


	@Override
	public boolean process(float interpol) {
		return false;
	}


	@Override
	public boolean activate(float interpol) {
		index++;
		if (index >= this.abilities.size()) {
			index = 0;
		}
		this.player.ability[0] = this.abilities.get(index);
		return true;
	}
	

	@Override
	public boolean onlyActivateOnClick() {
		return true;
	}

	
	@Override
	public String getHudText() {
		return "[CycleThru]";
	}

}
