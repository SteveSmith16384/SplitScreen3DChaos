package com.scs.splitscreenchaos.entities;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.components.IAttackable;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenchaos.models.ICreatureModel;
import com.scs.splitscreenchaos.models.WizardModel;
import com.scs.splitscreenfpsengine.MultiplayerVoxelWorldMain;
import com.scs.splitscreenfpsengine.components.IDamagable;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class AIWizard extends AbstractCreature implements IWizard, IAttackable, IDamagable {

	public AIWizard(MultiplayerVoxelWorldMain _game, AbstractGameModule _module, Vector3f startPos) {
		super(_game, _module, "Wizard", startPos, null, 1, 1, 1, 1);
	}

	@Override
	public ICreatureModel getCreatureModel() {
		return new WizardModel(game.getAssetManager());
	}

	@Override
	public void process(float tpfSecs) {
		// todo
	}
	
}
