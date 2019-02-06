package com.scs.splitscreenchaos.entities;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.ChaosGameModule;
import com.scs.splitscreenchaos.components.IAttackable;
import com.scs.splitscreenchaos.components.ICreatureModel;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenchaos.models.WizardModel;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.components.IDamagable;

public class AIWizard extends AbstractCreature implements IWizard, IAttackable, IDamagable {

	private int wizid;
	private float mana;
	
	public AIWizard(SplitScreenFpsEngine _game, ChaosGameModule _module, Vector3f startPos, int _wizid) {
		super(_game, _module, "Wizard", startPos, null, false);
		
		wizid = _wizid;
	}

	
	@Override
	public ICreatureModel getCreatureModel() {
		return new WizardModel(game.getAssetManager(), wizid);
	}

	
	@Override
	public void process(float tpfSecs) {
		this.mana += tpfSecs/2;
		if (mana > 200) {
			mana = 200;
		}
		
		// todo - cast spells
	}


	@Override
	public void setLockedInCombat(IAttackable other) {
		// Do nothing
		
	}
	
}
