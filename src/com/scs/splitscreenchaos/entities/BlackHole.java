package com.scs.splitscreenchaos.entities;

import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.components.IAffectedByPhysics;
import com.scs.splitscreenfpsengine.components.IEntity;
import com.scs.splitscreenfpsengine.components.IExpiringEffect;
import com.scs.splitscreenfpsengine.components.IProcessable;
import com.scs.splitscreenfpsengine.entities.AbstractPhysicalEntity;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

import ssmith.util.RealtimeInterval;

public class BlackHole extends AbstractPhysicalEntity implements IProcessable, IExpiringEffect {

	private RealtimeInterval interval = new RealtimeInterval(200);
	private WizardAvatar wiz;
	private float timeLeft = 30;

	public BlackHole(SplitScreenFpsEngine _game, AbstractGameModule _module, WizardAvatar _wiz, Vector3f pos) {
		super(_game, _module, "BlackHole");

		wiz = _wiz;
		
		Mesh sphere = new Sphere(8, 8, .2f, true, false);
		Geometry ball_geo = new Geometry("BlackHole", sphere);
		ball_geo.setShadowMode(ShadowMode.Cast);
		TextureKey key = new TextureKey( "Textures/black.png");
		Texture tex = game.getAssetManager().loadTexture(key);
		Material mat = new Material(game.getAssetManager(),"Common/MatDefs/Light/Lighting.j3md");
		mat.setTexture("DiffuseMap", tex);
		ball_geo.setMaterial(mat);

		this.mainNode.attachChild(ball_geo);
		mainNode.setLocalTranslation(pos);
		
		module.addEntity(this);

		//super.rigidBodyControl = new RigidBodyControl(0);
		//mainNode.addControl(rigidBodyControl);
		//geometry.setUserData(Settings.ENTITY, this);
	}

	
	@Override
	public void process(float tpfSecs) {
		if (interval.hitInterval()) {
			for (IEntity e : module.entities) {
				if (e instanceof AbstractCreature) {
					if (e instanceof IAffectedByPhysics) {
						AbstractCreature cre = (AbstractCreature)e;
						if (cre.getOwner() != this.wiz) {
							IAffectedByPhysics abp = (IAffectedByPhysics)e;
							//Vector3f dir = cre.getCenter().subtract(this.getLocation()).normalizeLocal();
							Vector3f dir = this.getCentre().subtract(cre.getCentre()).normalizeLocal();
							abp.applyForce(dir.multLocal(15));
						}
					}
				}
			}
		}
		
	}


	@Override
	public float getTimeRemaining() {
		return timeLeft;
	}


	@Override
	public void setTimeRemaining(float t) {
		timeLeft = t;
		
	}


	@Override
	public void effectFinished() {
		this.markForRemoval();
		
	}

}
