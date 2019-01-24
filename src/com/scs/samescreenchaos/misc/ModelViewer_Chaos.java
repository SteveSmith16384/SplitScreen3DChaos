package com.scs.samescreenchaos.misc;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scs.multiplayervoxelworld.jme.JMEModelFunctions;
import com.scs.multiplayervoxelworld.misc.ModelViewer;


public class ModelViewer_Chaos extends ModelViewer {

	public ModelViewer_Chaos() {
	}

	public static void main(String[] args) {
		ModelViewer_Chaos app = new ModelViewer_Chaos();
		app.showSettings = false;

		app.start();
	}


	public Spatial getModel() {
		Node model = (Node)assetManager.loadModel("Models/Goblin/Goblin.blend");
		//JMEModelFunctions.setTextureOnSpatial(assetManager, model, "Models/tree-creature/Tree-Creature_Diffuse_Map.png");

		//JMEModelFunctions.scaleModelToHeight(model, 2f);
		
		return model;
	}
	
	
	public String getAnimNode() {
		return "Goblin (Node)";
	}
	

	public String getAnimToShow() {
		return "Attack-two handedAction";
	}
	


}
