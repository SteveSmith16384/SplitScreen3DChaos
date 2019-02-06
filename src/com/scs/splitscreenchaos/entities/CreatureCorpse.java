package com.scs.splitscreenchaos.entities;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Spatial;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenfpsengine.Settings;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.components.IShowTextOnHud;
import com.scs.splitscreenfpsengine.entities.AbstractPhysicalEntity;
import com.scs.splitscreenfpsengine.jme.JMEModelFunctions;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class CreatureCorpse extends AbstractPhysicalEntity implements IShowTextOnHud {

	private AbstractCreature creature;

	public CreatureCorpse(SplitScreenFpsEngine _game, AbstractGameModule _module, AbstractCreature _creature) {
		super(_game, _module, _creature.name + " (corpse)");

		creature = _creature;

		//Spatial corpse = creature.getCreatureModel().getModel();// BeamLaserModel.Factory(game.getAssetManager(), origin, origin.add(shooter.getShootDir().multLocal(1)), ColorRGBA.Pink);
		Spatial corpse = game.getAssetManager().loadModel("Models/skull/skull_monster.obj");
		JMEModelFunctions.scaleModelToHeight(corpse, .3f);
		
		this.getMainNode().setLocalTranslation(creature.getCentre());
		this.mainNode.attachChild(corpse);

		rigidBodyControl = new RigidBodyControl(1f);
		mainNode.addControl(rigidBodyControl);

		corpse.setUserData(Settings.ENTITY, this);
		rigidBodyControl.setUserObject(this);

		module.addEntity(this);

	}


	public void resurrect(WizardAvatar wiz) {
		this.markForRemoval();
		creature.resurrect(wiz);
		module.addEntity(creature);
	}


	@Override
	public String getHudText() {
		return "Corpse of " + this.creature.name;
	}


}
