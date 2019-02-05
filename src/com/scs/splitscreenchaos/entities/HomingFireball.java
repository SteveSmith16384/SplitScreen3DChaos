package com.scs.splitscreenchaos.entities;

import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.scs.splitscreenfpsengine.Settings;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.components.IProcessable;
import com.scs.splitscreenfpsengine.entities.AbstractPhysicalEntity;
import com.scs.splitscreenfpsengine.entities.ParticleExplosion;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class HomingFireball extends AbstractPhysicalEntity implements IProcessable {

	private WizardAvatar wiz;
	private AbstractPhysicalEntity target;
	private ParticleEmitter fire;
	
	public HomingFireball(SplitScreenFpsEngine _game, AbstractGameModule _module, WizardAvatar _wiz, AbstractPhysicalEntity _target) {
		super(_game, _module, "HomingFireball");

		wiz = _wiz;
		target = _target;
		/*
		Mesh sphere = new Sphere(8, 8, .2f, true, false);
		Geometry ball_geo = new Geometry("Fireball", sphere);
		//ball_geo.setShadowMode(ShadowMode.Cast);
		TextureKey key = new TextureKey( "Textures/black.png");
		Texture tex = game.getAssetManager().loadTexture(key);
		Material mat = new Material(game.getAssetManager(),"Common/MatDefs/Light/Lighting.j3md");
		mat.setTexture("DiffuseMap", tex);
		ball_geo.setMaterial(mat);

		this.mainNode.attachChild(ball_geo);*/
		
		fire = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
		Material mat_red = new Material(game.getAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
		mat_red.setTexture("Texture", game.getAssetManager().loadTexture("Effects/Explosion/flame.png"));
		fire.setMaterial(mat_red);
		fire.setImagesX(2);
		fire.setImagesY(2); // 2x2 texture animation
		fire.setEndColor(  new ColorRGBA(1f, 0f, 0f, 1f));   // red
		fire.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
		//fire.getParticleInfluencer().setInitialVelocity(new Vector3f(2, 0, 0));
		fire.getParticleInfluencer().setInitialVelocity(this.wiz.getShootDir().mult(2));
		fire.setStartSize(.4f);//.5f);
		fire.setEndSize(0.2f);
		fire.setGravity(0, 0, 0);
		fire.setLowLife(1f);
		fire.setHighLife(3f);
		fire.getParticleInfluencer().setVelocityVariation(0.3f);
		this.getMainNode().attachChild(fire);

		mainNode.setLocalTranslation(wiz.getBulletStartPosition());
		
		module.addEntity(this);

		//super.rigidBodyControl = new RigidBodyControl(0);
		//mainNode.addControl(rigidBodyControl);
		//geometry.setUserData(Settings.ENTITY, this);
	}

	
	@Override
	public void process(float tpfSecs) {
		Vector3f targetPos = this.target.getCenter();
		Vector3f newPos = targetPos.subtract(this.getLocation()).normalizeLocal().multLocal(.1f);
		this.getMainNode().setLocalTranslation(this.getLocation().add(newPos));

		float dist = this.distance(this.target.getLocation());
		if (dist < 1) {
			Settings.p(this.getName() + " has hit " + this.target.getName());
			new ParticleExplosion(game, module, this.getCenter());
			this.markForRemoval();
		}
	}


}
