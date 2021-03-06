package com.scs.splitscreenchaos.entities;

import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Spatial;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.components.ICanShoot;
import com.scs.splitscreenfpsengine.entities.AbstractBullet;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class FireballBullet extends AbstractBullet {

	private ParticleEmitter fire;

	public FireballBullet(SplitScreenFpsEngine _game, AbstractGameModule _module, ICanShoot _shooter) {
		super(_game, _module, "Fireball", _shooter, 10);

	}

	@Override
	protected Spatial createBulletModel() {		
		/*if (Settings.DEBUG_FIREBALL_POS) {
			Mesh sphere = new Sphere(8, 8, .2f, true, false);
			Geometry ball_geo = new Geometry("DebuggingSphere", sphere);
			ball_geo.setShadowMode(ShadowMode.Cast);
			TextureKey key = new TextureKey( "Textures/greensun.jpg");
			Texture tex = game.getAssetManager().loadTexture(key);
			Material mat = new Material(game.getAssetManager(),"Common/MatDefs/Light/Lighting.j3md");
			mat.setTexture("DiffuseMap", tex);
			ball_geo.setMaterial(mat);
			return ball_geo;
			
		} else {*/
			fire = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
			Material mat_red = new Material(game.getAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
			mat_red.setTexture("Texture", game.getAssetManager().loadTexture("Effects/Explosion/flame.png"));
			fire.setMaterial(mat_red);
			fire.setImagesX(2);
			fire.setImagesY(2); // 2x2 texture animation
			fire.setEndColor(  new ColorRGBA(1f, 0f, 0f, 1f));   // red
			fire.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f)); // yellow
			//fire.getParticleInfluencer().setInitialVelocity(new Vector3f(2, 0, 0));
			fire.getParticleInfluencer().setInitialVelocity(this.shooter.getShootDir().mult(3));
			fire.setStartSize(.4f);//.5f);
			fire.setEndSize(0.2f);
			fire.setGravity(0, 0, 0);
			fire.setLowLife(1f);
			fire.setHighLife(3f);
			fire.getParticleInfluencer().setVelocityVariation(0.3f);
			
			return fire;
		//}
	}

}
