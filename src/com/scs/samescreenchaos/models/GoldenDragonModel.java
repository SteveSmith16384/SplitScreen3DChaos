package com.scs.samescreenchaos.models;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.scs.multiplayervoxelworld.Settings;
import com.scs.multiplayervoxelworld.jme.JMEAngleFunctions;
import com.scs.multiplayervoxelworld.jme.JMEModelFunctions;
import com.scs.samescreenchaos.entities.creatures.AbstractCreature;

// Anims on 'dragon (Node)': [sleep, die, wait, wait_03, wait_02, attack, wakeup, collide, neutral, attack_02, walk, fallasleep]

public class GoldenDragonModel implements ICreatureModel {

	public static final float MODEL_WIDTH = 0.9f;

	private AssetManager assetManager;
	private Spatial model;

	// Anim
	private AnimChannel channel;
	private AbstractCreature.Anim currAnimCode = AbstractCreature.Anim.None;

	public GoldenDragonModel(AssetManager _assetManager) {
		assetManager = _assetManager;

		model = assetManager.loadModel("Models/dragon/dragon.blend");
		JMEModelFunctions.setTextureOnSpatial(assetManager, model, "Models/dragon/dragon.png");
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



	public void setAnim(AbstractCreature.Anim animCode) {
		if (currAnimCode == animCode) {
			return;			
		}

		switch (animCode) {
		case Idle:
			channel.setLoopMode(LoopMode.Loop);
			channel.setAnim("wait");
			break;

		case Walk:
			channel.setLoopMode(LoopMode.Loop);
			channel.setAnim("walk");
			break;

		case Attack:
			channel.setLoopMode(LoopMode.Loop);
			channel.setAnim("attack");
			break;

		case Died:
			channel.setLoopMode(LoopMode.DontLoop);
			channel.setAnim("die");
			break;

		default:
			Settings.pe(this.getClass().getSimpleName() + ": Unable to show anim " + animCode);
		}

		currAnimCode = animCode;

	}

}

