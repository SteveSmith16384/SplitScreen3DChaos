package com.scs.splitscreenchaos.misc;

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
		Spatial model = assetManager.loadModel("Models/lipsofsuna_tree/tree2.blend");
		JMEModelFunctions.setTextureOnSpatial(assetManager, model, "Models/lipsofsuna_tree/tree4.png");

		//JMEModelFunctions.scaleModelToHeight(model, 2f);
		
		return model;
	}
	
	
	public String getAnimNode() {
		return "Skeli (Node)";
	}
	

	public String getAnimToShow() {
		return "idle";
	}
	


}
