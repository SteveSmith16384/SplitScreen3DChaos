package com.scs.samescreenchaos.misc;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scs.multiplayervoxelworld.jme.JMEModelFunctions;
import com.scs.multiplayervoxelworld.misc.ModelViewer;

public class ModelViewer_Chaos extends ModelViewer {

	public ModelViewer_Chaos() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		ModelViewer_Chaos app = new ModelViewer_Chaos();
		app.showSettings = false;

		app.start();
	}


	public Spatial getModel() {
		Node model = (Node)assetManager.loadModel("Models/dragon/dragon.blend");

		//JMEModelFunctions.scaleModelToHeight(model, 2f);
		JMEModelFunctions.setTextureOnSpatial(assetManager, model, "Models/dragon/dragon.png");
		return model;
	}
	
	
	public String getAnimNode() {
		return "dragon (Node)";
	}
	

	public String getAnimToShow() {
		return "walk";
	}
	


}
