package com.scs.splitscreenchaos.abilities;

import java.util.ArrayList;

import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.abilities.AbstractAbility;
import com.scs.splitscreenfpsengine.abilities.IAbility;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class CycleThroughAbilitiesAbility extends AbstractAbility {
	
	public ArrayList<IAbility> abilities = new ArrayList<>();
	private int index = 0;

	public CycleThroughAbilitiesAbility(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "CycleThroughAbilitiesAbility");
		
	
		abilities.add(new FireballSpell2(game, module, p));
		abilities.add(new FreezeSpell(game, module, p));
		abilities.add(new GrowSpell(game, module, p));
		abilities.add(new InvisibilitySpell(game, module, p));
		abilities.add(new HealSpell(game, module, p));
		abilities.add(new RaiseAllDeadSpell(game, module, p));
		abilities.add(new RaiseDeadSpell(game, module, p));
		abilities.add(new RunFastSpell(game, module, p));
		abilities.add(new ShrinkSpell(game, module, p));
		abilities.add(new SlowDownSpell(game, module, p));
		abilities.add(new SubversionSpell(game, module, p));
		abilities.add(new SummonCerberusSpell(game, module, p));
		abilities.add(new SummonGiantSpiderSpell(game, module, p));
		abilities.add(new SummonGoldenDragonSpell(game, module, p));
		abilities.add(new SummonGolemSpell(game, module, p));
		abilities.add(new SummonGreenDragonSpell(game, module, p));
		abilities.add(new SummonRedDragonSpell(game, module, p));
		abilities.add(new SummonScorpion(game, module, p));
		abilities.add(new SummonSkeletonSpell(game, module, p));
		abilities.add(new SummonZombieSpell(game, module, p));
		abilities.add(new SwapPositionsSpell(game, module, p));
		abilities.add(new TeleportSpell(game, module, p));
		abilities.add(new TurmoilSpell(game, module, p));
		abilities.add(new WallSpell(game, module, p));
		abilities.add(new WindSpell(game, module, p));

		abilities.add(new CommandCreatureSpell(game, module, p));
	}


	@Override
	public boolean process(float interpol) {
		return false;
	}


	public void selectPrev() {
		this.activate(0);
	}
	
	
	public void selectNext() {
		index--;
		if (index < 0) {
			index = this.abilities.size() - 1;
		}
		this.avatar.ability[0] = this.abilities.get(index);
	}
	
	
	@Override
	public boolean activate(float interpol) {
		index++;
		if (index >= this.abilities.size()) {
			index = 0;
		}
		this.avatar.ability[0] = this.abilities.get(index);
		return true;
	}
	

	@Override
	public boolean onlyActivateOnClick() {
		return true;
	}

	
	@Override
	public String getHudText() {
		return "";//[CycleThru]";
	}

}
