package com.scs.samescreenchaos.entities;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.Settings;
import com.scs.multiplayervoxelworld.entities.AbstractPhysicalEntity;
import com.scs.multiplayervoxelworld.jme.JMEAngleFunctions;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;
import com.scs.samescreenchaos.entities.creatures.AbstractCreature;
import com.scs.samescreenchaos.entities.creatures.AbstractCreature.Anim;

public class AbstractCorpse extends AbstractPhysicalEntity {

	private AbstractCreature creature;

	public AbstractCorpse(MultiplayerVoxelWorldMain _game, AbstractGameModule _module, AbstractCreature _creature) {
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
