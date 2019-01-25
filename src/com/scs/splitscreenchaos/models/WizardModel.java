package com.scs.splitscreenchaos.models;


import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scs.splitscreenfpsengine.Settings;
import com.scs.splitscreenfpsengine.jme.JMEAngleFunctions;
import com.scs.splitscreenfpsengine.jme.JMEModelFunctions;

/**
 * This class, and classes like this (i.e. a class for a model), are designed to keep all the model-specific settings in one place.
 * 
 * Anims: [Walk, Die, Hit, Idle, Attack]
 */
public class WizardModel {

	public static final int ANIM_IDLE = 0;
	public static final int ANIM_WALKING = 1;
	public static final int ANIM_ATTACK = 2;
	public static final int ANIM_DIED = 3;

	//public static final float MODEL_WIDTH = 0.4f;
	//private static final float MODEL_DEPTH = 0.3f;
	public static final float MODEL_HEIGHT = 1.7f;

	private AssetManager assetManager;
	private Spatial model;

	// Anim
	private AnimChannel channel;
	private int currAnimCode = -1;

	public WizardModel(AssetManager _assetManager) {
		assetManager = _assetManager;

		model = assetManager.loadModel("Models/mage/mage.blend");
		JMEModelFunctions.setTextureOnSpatial(assetManager, model, "Models/mage/mage.png");
		model.setShadowMode(ShadowMode.Cast);
		JMEModelFunctions.scaleModelToHeight(model, MODEL_HEIGHT);
		JMEModelFunctions.moveYOriginTo(model, 0f);
		JMEAngleFunctions.rotateToWorldDirection(model, new Vector3f(-1, 0, 1)); // Point model fwds

		AnimControl control = JMEModelFunctions.getNodeWithControls(null, (Node)model);
		channel = control.createChannel();
		setAnim(ANIM_IDLE); // Default
	}


	public Spatial getModel() {
		return model;
	}


	public void setAnim(int animCode) {
		if (currAnimCode == animCode) {
			return;			
		}

		switch (animCode) {
		case ANIM_IDLE:
			channel.setLoopMode(LoopMode.Loop);
			channel.setAnim("Idle");
			break;

		case ANIM_WALKING:
			channel.setLoopMode(LoopMode.Loop);
			channel.setAnim("Walk");
			break;

		case ANIM_ATTACK:
			channel.setLoopMode(LoopMode.Loop);
			channel.setAnim("Attack");
			break;

		case ANIM_DIED:
			channel.setLoopMode(LoopMode.DontLoop);
			channel.setAnim("Die");
			break;

		default:
			Settings.pe(this.getClass().getSimpleName() + ": Unable to show anim " + animCode);
		}

		currAnimCode = animCode;


	}

}
