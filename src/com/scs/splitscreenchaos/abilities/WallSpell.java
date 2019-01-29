package com.scs.splitscreenchaos.abilities;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.scs.splitscreenchaos.ChaosGameModule;
import com.scs.splitscreenchaos.blocks.WallBlock;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.Settings;
import com.scs.splitscreenfpsengine.entities.AbstractPhysicalEntity;
import com.scs.splitscreenfpsengine.entities.FloorOrCeiling;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class WallSpell extends AbstractSpell {
	
	private static final int SIZE = 1;

	public WallSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "Wall", 1);
	}


	@Override
	public boolean cast() {
		Ray ray = new Ray(this.player.getCamera().getLocation(), this.player.getCamera().getDirection());

		CollisionResults results = new CollisionResults();
		module.getRootNode().collideWith(ray, results);

		CollisionResult result = results.getClosestCollision();
		if (result != null) {
			if (result.getDistance() > SIZE) { // So we don't build a block on top of ourselves
				Geometry g = result.getGeometry();
				AbstractPhysicalEntity ape = (AbstractPhysicalEntity)AbstractGameModule.getEntityFromSpatial(g);
				if (ape instanceof FloorOrCeiling) {
					Vector3f position = result.getContactPoint();
					ChaosGameModule m = (ChaosGameModule)module;
					m.vte.addRectRange_Actual(position, new Vector3f(SIZE, SIZE, SIZE), WallBlock.class);
					return true;
				} else {
					Settings.p(ape + " selected");
				}
			}
		}
		return false;
	}


}
