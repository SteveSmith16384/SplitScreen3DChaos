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
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature.Anim;
import com.scs.splitscreenfpsengine.Settings;
import com.scs.splitscreenfpsengine.components.IAvatarModel;
import com.scs.splitscreenfpsengine.entities.AbstractPlayersAvatar;
import com.scs.splitscreenfpsengine.jme.JMEAngleFunctions;
import com.scs.splitscreenfpsengine.jme.JMEModelFunctions;

/**
 * This class, and classes like this (i.e. a class for a model), are designed to keep all the model-specific settings in one place.
 * 
 * Anims: [Walk, Die, Hit, Idle, Attack]
 */
public class WizardModel implements IAvatarModel, ICreatureModel { // Used by wizard avatars and creatures (i.e. ai wizards)

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
	private AbstractPlayersAvatar.Anim currAnimCode = AbstractPlayersAvatar.Anim.None;
	private Anim currAnimCodeC = Anim.None;
	

	public WizardModel(AssetManager _assetManager, int playerid) {
		assetManager = _assetManager;

		model = assetManager.loadModel("Models/mage/mage.blend");
		JMEModelFunctions.setTextureOnSpatial(assetManager, model, getTex(playerid));
		model.setShadowMode(ShadowMode.Cast);
		JMEModelFunctions.scaleModelToHeight(model, MODEL_HEIGHT);
		JMEModelFunctions.moveYOriginTo(model, 0f);
		JMEAngleFunctions.rotateToWorldDirection(model, new Vector3f(-1, 0, 0)); // Point model fwds

		AnimControl control = JMEModelFunctions.getNodeWithControls(null, (Node)model);
		channel = control.createChannel();
		setAvatarAnim(AbstractPlayersAvatar.Anim.Idle); // Default
		setCreatureAnim(Anim.Idle); // Default
	}


	private String getTex(int pid) {
		switch (pid) {
		case 0:
			return "Models/mage/mage_yellow.png";
		case 1:
			return "Models/mage/mage_red.png";
		case 2:
			return "Models/mage/mage_cyan.png";
		case 3:
			return "Models/mage/mage_magenta.png";
		default: 
			throw new RuntimeException("Unknown tex for id: " + pid);
		}
	}


	public Spatial getModel() {
		return model;
	}


	@Override
	public void setAvatarAnim(AbstractPlayersAvatar.Anim animCode) {
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
			channel.setAnim("Die");
			break;

		default:
			Settings.pe(this.getClass().getSimpleName() + ": Unable to show anim " + animCode);
		}

		currAnimCode = animCode;


	}


	@Override
	public void setCreatureAnim(Anim anim) {
		if (currAnimCodeC == anim) {
			return;			
		}

		switch (anim) {
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
			channel.setAnim("Die");
			break;

		default:
			Settings.pe(this.getClass().getSimpleName() + ": Unable to show anim " + anim);
		}

		currAnimCodeC = anim;
		
	}


}
