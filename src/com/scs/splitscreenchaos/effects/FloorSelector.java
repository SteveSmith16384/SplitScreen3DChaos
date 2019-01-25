package com.scs.splitscreenchaos.effects;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.scs.splitscreenchaos.ChaosSettings;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenfpsengine.MultiplayerVoxelWorldMain;
import com.scs.splitscreenfpsengine.Settings;
import com.scs.splitscreenfpsengine.entities.AbstractPhysicalEntity;
import com.scs.splitscreenfpsengine.entities.FloorOrCeiling;
import com.scs.splitscreenfpsengine.jme.JMEModelFunctions;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class FloorSelector extends Node {//extends AbstractPhysicalEntity implements IProcessable {

	private MultiplayerVoxelWorldMain game;
	private WizardAvatar wiz;
	
	public FloorSelector(MultiplayerVoxelWorldMain _game, WizardAvatar _wiz) {
		super("FloorSelector");

		game =_game;
		wiz = _wiz;

		Box box1 = new Box(.5f, .01f, .5f);
		Geometry geometry = new Geometry("SquareIndicatorBox", box1);
		//geometry.setShadowMode(ShadowMode.CastAndReceive);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/yellowsun.jpg"); // todo - diff colours
		geometry.setLocalTranslation(.5f, 0, .5f);
		this.attachChild(geometry);
		//this.mainNode.attachChild(geometry);
		//mainNode.updateModelBound();

	}


	public void process(float tpfSecs) {
		Ray ray = new Ray(this.wiz.getCamera().getLocation(), this.wiz.getCamera().getDirection());

		CollisionResults results = new CollisionResults();
		game.getRootNode().collideWith(ray, results);

		CollisionResult result = results.getClosestCollision();
		this.setCullHint(CullHint.Always);
		if (result != null) {
			Geometry g = result.getGeometry();
			AbstractPhysicalEntity ape = (AbstractPhysicalEntity)AbstractGameModule.getEntityFromSpatial(g);
			if (ape instanceof FloorOrCeiling) {
				this.setCullHint(CullHint.Inherit);
				this.setLocalTranslation(result.getContactPoint());
			} //else if (ape instanceof )
		}
	}


}
