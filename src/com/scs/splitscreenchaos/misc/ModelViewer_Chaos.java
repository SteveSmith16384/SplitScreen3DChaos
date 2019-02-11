package com.scs.splitscreenchaos.misc;

import java.io.File;
import java.io.IOException;

import com.jme3.export.binary.BinaryExporter;
import com.jme3.scene.Spatial;
import com.scs.splitscreenfpsengine.Settings;
import com.scs.splitscreenfpsengine.jme.JMEAngleFunctions;
import com.scs.splitscreenfpsengine.jme.JMEModelFunctions;
import com.scs.splitscreenfpsengine.misc.ModelViewer;


public class ModelViewer_Chaos extends ModelViewer {

	public ModelViewer_Chaos() {
	}

	public static void main(String[] args) {
		System.out.println(com.jme3.system.JmeVersion.FULL_NAME);
		ModelViewer_Chaos app = new ModelViewer_Chaos();
		app.showSettings = false;

		app.start();
	}


	public Spatial getModel() {
		Spatial model = JMEModelFunctions.loadModel(assetManager, "Models/cerberus/cerberus_v002.blend", false);
		JMEModelFunctions.setTextureOnSpatial(assetManager, model, "Models/cerberus/cerberus_texture.png");
		model.updateGeometricState();
		
		//JMEAngleFunctions.rotateToWorldDirection(model, new Vector3f(0, 0, 1)); // Point model fwds
		//JMEAngleFunctions.rotateXAxisBy(model, 90);
		JMEModelFunctions.moveYOriginTo(model, 0f);

		//JMEModelFunctions.scaleModelToHeight(model, 2f);

		String path = "Models/cerberus/cerberus_v002.blend";
		String j3oName = path.substring(path.lastIndexOf("/")+1) + ".j3o";
			//String j3oPath = "Models/" + j3oName;
		File file = new File("assets/Models/" + j3oName);
		file.delete();
		BinaryExporter exporter = BinaryExporter.getInstance();
		try {
			exporter.save(model, file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return model;
	}
	
	
	public String getAnimNode() {
		return "xxxCerberus (Node)";
	}
	

	public String getAnimToShow() {
		return "xxxalk";
	}
	


}
