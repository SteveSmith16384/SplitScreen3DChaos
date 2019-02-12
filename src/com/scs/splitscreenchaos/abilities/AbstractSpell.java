package com.scs.splitscreenchaos.abilities;

import com.scs.splitscreenchaos.ChaosSettings;
import com.scs.splitscreenchaos.effects.ParticleCastEffect;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenfpsengine.Settings;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.abilities.AbstractAbility;
import com.scs.splitscreenfpsengine.abilities.IAbility;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public abstract class AbstractSpell extends AbstractAbility implements IAbility {

	private int cost;
	private float range;

	public AbstractSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar _player, String _name, int _cost, float _range) {
		super(_game, module, _player, _name);

		cost = _cost;
		range = _range;
	}


	@Override
	public boolean activate(float interpol) {
		if (this.getWizard().mana < cost && ChaosSettings.INFINITE_MANA == false) {
			Settings.p("Not enough mana!");
			this.avatar.hud.appendToLog("Not enough mana!");
			return false;
		}
		boolean success = cast();
		if (success) {
			if (showCastEffect()) {
				new ParticleCastEffect(game, module, this.avatar);
			}
			this.getWizard().mana -= cost;
			Settings.p(this.name + " cast");
			this.avatar.hud.appendToLog(this.name + " cast");

			if (ChaosSettings.REMOVE_SPELLS_WHEN_CAST) {
				if (this.avatar.ability[0] instanceof CycleThroughAbilitiesAbility) {
					CycleThroughAbilitiesAbility cy = (CycleThroughAbilitiesAbility)this.avatar.ability[0];
					cy.abilities.remove(this);
				}
			}
		}
		return success;
	}


	/**
	 * Override if required
	 * @return
	 */
	protected boolean showCastEffect() {
		return true;
	}
	
	
	protected abstract boolean cast();

	protected WizardAvatar getWizard() {
		return (WizardAvatar)this.avatar;
	}


	@Override
	public boolean process(float interpol) {
		return false;
	}


	@Override
	public String getHudText() {
		return name + " (" + cost + ")";
	}


	@Override
	public boolean onlyActivateOnClick() {
		return true;
	}


	public float getRange() {
		return range;
	}
	
	
	/**
	 * Override if required
	 * @return
	 */
	public boolean showFloorSelector() {
		return true;
	}

}
