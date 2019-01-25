package com.scs.splitscreenchaos.abilities;

import java.util.ArrayList;

import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenfpsengine.MultiplayerVoxelWorldMain;
import com.scs.splitscreenfpsengine.abilities.AbstractAbility;
import com.scs.splitscreenfpsengine.abilities.IAbility;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

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
		abilities.add(new SummonWyvernSpell(game, module, p));
		
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


	public void selectNext() {
		this.activate(0);
	}
	
	
	public void selectPrev() {
		index--;
		if (index < 0) {
			index = this.abilities.size() - 1;
		}
		this.player.ability[0] = this.abilities.get(index);
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
