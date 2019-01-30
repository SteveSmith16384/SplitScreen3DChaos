package com.scs.splitscreenchaos.misc;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scs.splitscreenfpsengine.jme.JMEModelFunctions;
import com.scs.splitscreenfpsengine.misc.ModelViewer;


public class ModelViewer_Chaos extends ModelViewer {

	public ModelViewer_Chaos() {
	}

	public static void main(String[] args) {
		ModelViewer_Chaos app = new ModelViewer_Chaos();
		app.showSettings = false;

		app.start();
	}


	public Spatial getModel() {
		Spatial model = assetManager.loadModel("Models/cerberus_v002.blend");
		JMEModelFunctions.setTextureOnSpatial(assetManager, model, "Models/cerberus_texture.png");
		//JMEAngleFunctions.rotateToWorldDirection(model, new Vector3f(0, 0, 1)); // Point model fwds

		//JMEModelFunctions.scaleModelToHeight(model, 2f);
		return model;
	}
	
	
	public String getAnimNode() {
		return "Cerberus (Node)";
	}
	

	public String getAnimToShow() {
		return "Walk";
	}
	


}
