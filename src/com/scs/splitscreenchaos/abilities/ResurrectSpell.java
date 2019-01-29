package com.scs.splitscreenchaos.abilities;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.scene.Geometry;
import com.scs.splitscreenchaos.entities.AbstractCorpse;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.Settings;
import com.scs.splitscreenfpsengine.entities.AbstractPhysicalEntity;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class ResurrectSpell extends AbstractSpell {

	public ResurrectSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "Resurrect", 1);
	}


	@Override
	public boolean cast() {
		Ray ray = new Ray(this.player.getCamera().getLocation(), this.player.getCamera().getDirection());

		CollisionResults results = new CollisionResults();
		module.getRootNode().collideWith(ray, results);

		CollisionResult result = results.getClosestCollision();
		if (result != null) {
			Geometry g = result.getGeometry();
			AbstractPhysicalEntity ape = (AbstractPhysicalEntity)AbstractGameModule.getEntityFromSpatial(g);
			if (ape instanceof AbstractCorpse) {
				//Vector3f position = result.getContactPoint();
				//ChaosGameModule m = (ChaosGameModule)module;
				//m.vte.addRectRange_Actual(position, new Vector3f(SIZE, SIZE, SIZE), WallBlock.class);
				AbstractCorpse corpse = (AbstractCorpse)ape;
				corpse.resurrect((WizardAvatar)player);
				return true;
			} else {
				Settings.p(ape + " selected");
			}
		}
		return false;
	}


}
