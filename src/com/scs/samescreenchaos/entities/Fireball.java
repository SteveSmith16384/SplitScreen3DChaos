package com.scs.samescreenchaos.entities;

import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.components.ICanShoot;
import com.scs.multiplayervoxelworld.entities.AbstractBullet;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;

public class Fireball extends AbstractBullet {

	private ParticleEmitter fire;
	
	public Fireball(MultiplayerVoxelWorldMain _game, AbstractGameModule _module, ICanShoot _shooter) {
		super(_game, _module, "Fireball", _shooter);
		
	}

	@Override
	protected Spatial createBulletModel() {
		fire = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
		Material mat_red = new Material(game.getAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
		mat_red.setTexture("Texture", game.getAssetManager().loadTexture("Effects/Explosion/flame.png"));
		fire.setMaterial(mat_red);
		fire.setImagesX(2);
		fire.setImagesY(2); // 2x2 texture animation
		fire.setEndColor(  new ColorRGBA(1f, 0f, 0f, 1f));   // red
		fire.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
		//fire.getParticleInfluencer().setInitialVelocity(new Vector3f(2, 0, 0));
		fire.getParticleInfluencer().setInitialVelocity(this.shooter.getShootDir().mult(2));
		fire.setStartSize(1);//.5f);
		fire.setEndSize(0.1f);
		fire.setGravity(0, 0, 0);
		fire.setLowLife(1f);
		fire.setHighLife(3f);
		fire.getParticleInfluencer().setVelocityVariation(0.3f);

		return fire;
	}

}
