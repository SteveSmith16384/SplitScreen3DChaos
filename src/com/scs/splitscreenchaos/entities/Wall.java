package com.scs.splitscreenchaos.entities;

import com.jme3.asset.TextureKey;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.util.BufferUtils;
import com.scs.splitscreenfpsengine.Settings;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.components.IExpiringEffect;
import com.scs.splitscreenfpsengine.entities.AbstractPhysicalEntity;
import com.scs.splitscreenfpsengine.jme.JMEModelFunctions;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class Wall extends AbstractPhysicalEntity implements IExpiringEffect {

	private float timeRemaining = 60;
	
	public Wall(SplitScreenFpsEngine _game, AbstractGameModule _module, Vector3f startPos) {
		super(_game, _module, "Wall");

		float w = 1f;
		float h = 2f;
		float d = w;//.5f;
		
		Box box1 = new Box(w/2, h/2, d/2);

		box1.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(new float[]{
				0, h, w, h, w, 0, 0, 0, // back
				0, h, d, h, d, 0, 0, 0, // right
				0, h, w, h, w, 0, 0, 0, // front
				0, h, d, h, d, 0, 0, 0, // left
				w, 0, w, d, 0, d, 0, 0, // top
				w, 0, w, d, 0, d, 0, 0  // bottom
		}));

		Geometry geometry = new Geometry("FloorGeom", box1);
		TextureKey key3 = new TextureKey("Textures/skulls-texture-scary-horror-skulls.jpg");
		key3.setGenerateMips(true);
		Texture tex3 = game.getAssetManager().loadTexture(key3);
		tex3.setWrap(WrapMode.Repeat);

		Material floorMat = new Material(game.getAssetManager(),"Common/MatDefs/Light/Lighting.j3md");  // create a simple material
		floorMat.setTexture("DiffuseMap", tex3);
		geometry.setMaterial(floorMat);

		geometry.setLocalTranslation(0, h/2, 0); // Move it into position

		this.getMainNode().setLocalTranslation(startPos);
		this.mainNode.attachChild(geometry);

		rigidBodyControl = new RigidBodyControl(0);
		mainNode.addControl(rigidBodyControl);

		geometry.setUserData(Settings.ENTITY, this);
		rigidBodyControl.setUserObject(this);

		module.addEntity(this);

	}


	@Override
	public float getTimeRemaining() {
		return timeRemaining;
	}

	
	@Override
	public void setTimeRemaining(float t) {
		timeRemaining = t;
	}

	
	@Override
	public void effectFinished() {
		super.markForRemoval();
		//module.markEntityForRemoval(this);
	
	}



}
