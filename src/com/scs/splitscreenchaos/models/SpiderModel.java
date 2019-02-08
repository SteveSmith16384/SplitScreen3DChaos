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

//Anims: SpiderStand, SpiderWalk
public class SpiderModel implements ICreatureModel {

	public static final float MODEL_WIDTH = 1.3f;

	private AssetManager assetManager;
	private Spatial model;

	// Anim
	private AnimChannel channel;
	private AbstractCreature.Anim currAnimCode = AbstractCreature.Anim.None;

	public SpiderModel(AssetManager _assetManager) {
		assetManager = _assetManager;

		model = JMEModelFunctions.loadModel(assetManager, "Models/spider/Spider.blend", Settings.LOAD_J3OModels);
		JMEModelFunctions.setTextureOnSpatial(assetManager, model, "Models/spider/Spider.png");
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
			channel.setAnim("SpiderStand");
			break;

		case Walk:
			channel.setLoopMode(LoopMode.Loop);
			channel.setAnim("SpiderWalk");
			break;

		case Attack:
			channel.setLoopMode(LoopMode.Loop);
			channel.setAnim("SpiderWalk"); // No attack anim
			break;

		case Died:
			channel.setLoopMode(LoopMode.Loop);
			channel.setAnim("SpiderStand"); // No died anim
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

