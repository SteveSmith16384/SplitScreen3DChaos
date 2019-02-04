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

// Anims on 'scorpion (Node)': [sleep, die, idle, attack, wakeup, idle_02, neutral, attack_position, attack_02, walk, fallasleep]
public class ScorpionModel implements ICreatureModel {

	//public static final float MODEL_HEIGHT = 1.7f;

	private AssetManager assetManager;
	private Spatial model;

	// Anim
	private AnimChannel channel;
	private AbstractCreature.Anim currAnimCode = AbstractCreature.Anim.None;

	public ScorpionModel(AssetManager _assetManager) {
		assetManager = _assetManager;

		model = assetManager.loadModel("Models/scorpion/scorpion.blend");
		
		model.setShadowMode(ShadowMode.Cast);
		//JMEAngleFunctions.rotateToWorldDirection(model, new Vector3f(-1, 0, 0)); // Point model fwds
		//JMEModelFunctions.scaleModelToHeight(model, MODEL_HEIGHT);
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
			channel.setAnim("idle");
			break;

		case Walk:
			channel.setLoopMode(LoopMode.Loop);
			channel.setAnim("walk");
			break;

		case Attack:
			channel.setLoopMode(LoopMode.Loop);
			channel.setAnim("attack"); // No attack anim
			break;

		case Died:
			channel.setLoopMode(LoopMode.Loop);
			channel.setAnim("die"); // No died anim
			break;

		case Frozen:
			channel.setLoopMode(LoopMode.DontLoop);
			channel.setAnim("fallasleep"); // No died anim
			channel.reset(false);
			break;

		default:
			Settings.pe(this.getClass().getSimpleName() + ": Unable to show anim " + animCode);
		}

		currAnimCode = animCode;

	}

}

