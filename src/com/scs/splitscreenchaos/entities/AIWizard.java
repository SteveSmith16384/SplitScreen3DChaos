package com.scs.splitscreenchaos.entities;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.components.IAttackable;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenchaos.models.ICreatureModel;
import com.scs.splitscreenchaos.models.WizardModel;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.components.IDamagable;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class AIWizard extends AbstractCreature implements IWizard, IAttackable, IDamagable {

	private int wizid;
	
	public AIWizard(SplitScreenFpsEngine _game, AbstractGameModule _module, Vector3f startPos, int _wizid) {
		super(_game, _module, "Wizard", startPos, null, 1, 1, 1, 1, false);
		
		wizid = _wizid;
	}

	@Override
	public ICreatureModel getCreatureModel() {
		return new WizardModel(game.getAssetManager(), wizid);
	}

	@Override
	public void process(float tpfSecs) {
		// todo
	}
	
}
