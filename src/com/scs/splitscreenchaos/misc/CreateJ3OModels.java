package com.scs.splitscreenchaos.misc;

import com.jme3.app.SimpleApplication;
import com.jme3.scene.Spatial;
import com.scs.splitscreenfpsengine.jme.JMEModelFunctions;

public class CreateJ3OModels extends SimpleApplication {

	public static void main(String[] args) {
		CreateJ3OModels app = new CreateJ3OModels();
		app.showSettings = false;

		app.start();
	}


	@Override
	public void simpleInitApp() {
		try {
			JMEModelFunctions.loadModel(assetManager, "Models/beholder/beholder.blend", true);
			JMEModelFunctions.loadModel(assetManager, "Models/dragon/dragon.blend", true);
			JMEModelFunctions.loadModel(assetManager, "Models/cerberus/cerberus_v002.blend", true);
			JMEModelFunctions.loadModel(assetManager, "Models/golem/golem_clean.blend", true);
			JMEModelFunctions.loadModel(assetManager, "Models/scorpion/scorpion.blend", true);
			JMEModelFunctions.loadModel(assetManager, "Models/boneman/boneman_running.blend", true);
			JMEModelFunctions.loadModel(assetManager, "Models/spider/Spider.blend", true);
			JMEModelFunctions.loadModel(assetManager, "Models/mage/mage.blend", true);
			JMEModelFunctions.loadModel(assetManager, "Models/wyvern/Wyvern.blend", true);
			JMEModelFunctions.loadModel(assetManager, "Models/new_thin_zombie/new_thin_zombie.blend", true);
			JMEModelFunctions.loadModel(assetManager, "Models/mage_tower/mage_tower.blend", true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	@Override
	public void simpleUpdate(float tpf) {
		this.stop();
	}


}
