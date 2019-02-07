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

// Anims on 'Cerberus (Node)': [Walk, die, hit, Idle, Attack]

public class CerberusModel implements ICreatureModel {

	public static final float MODEL_HEIGHT = 0.7f;

	private AssetManager assetManager;
	private Spatial model;

	// Anim
	private AnimChannel channel;
	private AbstractCreature.Anim currAnimCode = AbstractCreature.Anim.None;

	public CerberusModel(AssetManager _assetManager) {
		assetManager = _assetManager;

		model = JMEModelFunctions.loadModel(assetManager, "Models/cerberus/cerberus_v002.blend", Settings.LOAD_J3OModels);
		JMEModelFunctions.setTextureOnSpatial(assetManager, model, "Models/cerberus/cerberus_texture.png");
		model.setShadowMode(ShadowMode.Cast);
		JMEModelFunctions.scaleModelToHeight(model, MODEL_HEIGHT);
		JMEModelFunctions.moveYOriginTo(model, 0f);
		//JMEAngleFunctions.rotateToWorldDirection(model, new Vector3f(0, 0, 0)); // Point model fwds

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
			channel.setAnim("Walk");
			break;

		case Attack:
			channel.setLoopMode(LoopMode.Loop);
			channel.setAnim("Attack");
			break;

		case Died:
			channel.setLoopMode(LoopMode.DontLoop);
			channel.setAnim("die");
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

