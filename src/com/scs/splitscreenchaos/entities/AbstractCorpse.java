package com.scs.splitscreenchaos.entities;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.Settings;
import com.scs.splitscreenfpsengine.entities.AbstractPhysicalEntity;
import com.scs.splitscreenfpsengine.jme.JMEAngleFunctions;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class AbstractCorpse extends AbstractPhysicalEntity {

	private AbstractCreature creature;

	public AbstractCorpse(SplitScreenFpsEngine _game, AbstractGameModule _module, AbstractCreature _creature) {
		super(_game, _module, _creature.name + " (corpse)");

		creature = _creature;

		Spatial corpse = creature.getCreatureModel().getModel();// BeamLaserModel.Factory(game.getAssetManager(), origin, origin.add(shooter.getShootDir().multLocal(1)), ColorRGBA.Pink);
		JMEAngleFunctions.rotateToWorldDirection(corpse, new Vector3f(0, 1, 0)); // Point model upwards

		this.getMainNode().setLocalTranslation(creature.getLocation());
		this.mainNode.attachChild(corpse);

		rigidBodyControl = new RigidBodyControl(1f);
		mainNode.addControl(rigidBodyControl);

		corpse.setUserData(Settings.ENTITY, this);
		rigidBodyControl.setUserObject(this);

		module.addEntity(this);

	}


	public void resurrect(WizardAvatar wiz) {
		this.markForRemoval();
		creature.setAIToAwaitingCommand();
		module.addEntity(creature);
	}

}
