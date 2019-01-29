package com.scs.splitscreenchaos.entities;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.scs.splitscreenfpsengine.Settings;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.entities.AbstractPhysicalEntity;
import com.scs.splitscreenfpsengine.jme.JMEModelFunctions;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class MageTower extends AbstractPhysicalEntity {

	public MageTower(SplitScreenFpsEngine _game, AbstractGameModule _module, Vector3f startPos) {
		super(_game, _module, "MageTower");

		Spatial model = game.getAssetManager().loadModel("Models/mage_tower.blend");
		JMEModelFunctions.moveYOriginTo(model, 0f);

		this.getMainNode().setLocalTranslation(startPos);
		this.mainNode.attachChild(model);

		rigidBodyControl = new RigidBodyControl(0);
		mainNode.addControl(rigidBodyControl);

		model.setUserData(Settings.ENTITY, this);
		rigidBodyControl.setUserObject(this);

		module.addEntity(this);

	}


}
