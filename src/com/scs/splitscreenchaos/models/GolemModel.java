package com.scs.splitscreenchaos.models;


import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.asset.AssetManager;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scs.splitscreenchaos.components.ICreatureModel;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenfpsengine.Settings;
import com.scs.splitscreenfpsengine.jme.JMEModelFunctions;

/**
 * Anims are idle, smash, walk
 *
 */
public class GolemModel implements ICreatureModel {

	public static final float MODEL_HEIGHT = 2f;

	private AssetManager assetManager;
	private Spatial model;

	// Anim
	private AnimChannel channel;
	private AbstractCreature.Anim currAnimCode = AbstractCreature.Anim.None;

	public GolemModel(AssetManager _assetManager) {
		assetManager = _assetManager;

		model = JMEModelFunctions.loadModel(assetManager, "Models/golem/golem_clean.blend", Settings.LOAD_J3OModels);//assetManager.loadModel("Models/golem/golem_clean.blend");
		JMEModelFunctions.setTextureOnSpatial(assetManager, model, "Textures/lavarock.jpg");
		model.setShadowMode(ShadowMode.Cast);
		JMEModelFunctions.scaleModelToHeight(model, MODEL_HEIGHT);
		JMEModelFunctions.moveYOriginTo(model, 0f);
		//JMEAngleFunctions.rotateToWorldDirection(model, new Vector3f(0, 0, 0)); // Point model fwds

		AnimControl control = JMEModelFunctions.getNodeWithControls(null, (Node)model);
		channel = control.createChannel();

		//return model;
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
			channel.setAnim("idle");
			break;

		case Walk:
			channel.setLoopMode(LoopMode.Loop);
			channel.setAnim("walk");
			break;

		case Attack:
			channel.setLoopMode(LoopMode.Loop);
			channel.setAnim("smash");
			break;

		case Died:
			// No die anim
			//channel.setLoopMode(LoopMode.DontLoop);
			//channel.setAnim("smash");#
			break;

		case Frozen:
			channel.setLoopMode(LoopMode.DontLoop);
			channel.reset(false);
			break;

		default:
			Settings.pe(this.getClass().getSimpleName() + ": Unable to show anim " + animCode);
		}

		currAnimCode = animCode;

	}

}

