package com.scs.splitscreenchaos.models;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scs.splitscreenchaos.components.ICreatureModel;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenfpsengine.Settings;
import com.scs.splitscreenfpsengine.jme.JMEAngleFunctions;
import com.scs.splitscreenfpsengine.jme.JMEModelFunctions;

// Anims on 'Wyvern (Node)': [Walk, Idle.001, Idle, Walk.001]
public class WyvernModel implements ICreatureModel {

	public static final float MODEL_WIDTH = 0.9f;

	private AssetManager assetManager;
	private Spatial model;

	// Anim
	private AnimChannel channel;
	private AbstractCreature.Anim currAnimCode = AbstractCreature.Anim.None;

	public WyvernModel(AssetManager _assetManager) {
		assetManager = _assetManager;

		model = assetManager.loadModel("Models/wyvern/wyvern.blend");
		JMEModelFunctions.setTextureOnSpatial(assetManager, model, "Models/wyvern/wyvern.png");
		model.setShadowMode(ShadowMode.Cast);
		JMEAngleFunctions.rotateToWorldDirection(model, new Vector3f(-1, 0, 0)); // Point model fwds
		JMEModelFunctions.scaleModelToWidth(model, MODEL_WIDTH);
		JMEModelFunctions.moveYOriginTo(model, 0f);

		AnimControl control = JMEModelFunctions.getNodeWithControls(null, (Node)model);
		channel = control.createChannel();

	}


	public Spatial getModel() {
		return model;
	}



	public void setCreatureAnim(AbstractCreature.Anim animCode) {
		if (currAnimCode == animCode) {
			return;			
		}

		switch (animCode) {
		case Idle:
			channel.setLoopMode(LoopMode.Loop);
			channel.setAnim("Idle");
			break;

		case Walk:
			channel.setLoopMode(LoopMode.Loop);
			channel.setAnim("walk");
			break;

		case Attack:
			channel.setLoopMode(LoopMode.Loop);
			channel.setAnim("walk"); // No attack anim
			break;

		case Died:
			channel.setLoopMode(LoopMode.Loop);
			channel.setAnim("Idle"); // No died anim
			break;

		default:
			Settings.pe(this.getClass().getSimpleName() + ": Unable to show anim " + animCode);
		}

		currAnimCode = animCode;

	}

}

