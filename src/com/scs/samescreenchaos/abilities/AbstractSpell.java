package com.scs.samescreenchaos.abilities;

import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.Settings;
import com.scs.multiplayervoxelworld.abilities.AbstractAbility;
import com.scs.multiplayervoxelworld.abilities.IAbility;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;
import com.scs.samescreenchaos.entities.WizardAvatar;

public abstract class AbstractSpell extends AbstractAbility implements IAbility {

	protected String name;
	private int cost;
	
	public AbstractSpell(MultiplayerVoxelWorldMain _game, AbstractGameModule module, WizardAvatar _player, String _name, int _cost) {
		super(_game, module, _player);
		
		name = _name;
		cost = _cost;
	}


	@Override
	public boolean activate(float interpol) {
		if (this.getWizard().mana < cost) {
			Settings.p("Not enough mana");
			return false;
		}
		return true;
	}
	
	
	protected WizardAvatar getWizard() {
		return (WizardAvatar)this.player;
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
