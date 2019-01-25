package com.scs.splitscreenchaos.abilities;

import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenfpsengine.MultiplayerVoxelWorldMain;
import com.scs.splitscreenfpsengine.Settings;
import com.scs.splitscreenfpsengine.abilities.AbstractAbility;
import com.scs.splitscreenfpsengine.abilities.IAbility;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

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
			//todo return false;
		}
		boolean success = cast();
		if (success) {
			this.getWizard().mana -= cost;
		}
		return success;
	}
	
	
	protected abstract boolean cast();
	
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