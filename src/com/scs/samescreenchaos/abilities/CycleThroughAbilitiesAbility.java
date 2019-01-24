package com.scs.samescreenchaos.abilities;

import java.util.ArrayList;

import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.abilities.AbstractAbility;
import com.scs.multiplayervoxelworld.abilities.IAbility;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;
import com.scs.samescreenchaos.entities.WizardAvatar;

public class CycleThroughAbilitiesAbility extends AbstractAbility {
	
	private ArrayList<IAbility> abilities = new ArrayList<>();
	private int index;

	public CycleThroughAbilitiesAbility(MultiplayerVoxelWorldMain _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p);
		
		abilities.add(new SummonGolemSpell(game, module, p));
		abilities.add(new SummonGiantSpiderSpell(game, module, p));
		abilities.add(new SummonZombieSpell(game, module, p));
		abilities.add(new SummonGoldenDragonSpell(game, module, p));
		abilities.add(new SummonGreenDragonSpell(game, module, p));
		abilities.add(new SummonRedDragonSpell(game, module, p));
		
		abilities.add(new FireballSpell(game, module, p));
		abilities.add(new WallSpell(game, module, p));
		abilities.add(new WindSpell(game, module, p));
		abilities.add(new TeleportSpell(game, module, p));
		abilities.add(new CommandCreatureSpell(game, module, p));
		abilities.add(new SubversionSpell(game, module, p));
		abilities.add(new TurmoilSpell(game, module, p));
		abilities.add(new ResurrectSpell(game, module, p));
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
