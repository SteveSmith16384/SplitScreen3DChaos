package com.scs.samescreenchaos.abilities;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.scene.Geometry;
import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.Settings;
import com.scs.multiplayervoxelworld.entities.AbstractPhysicalEntity;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;
import com.scs.samescreenchaos.entities.WizardAvatar;
import com.scs.samescreenchaos.entities.creatures.AbstractCreature;

public class SubversionSpell extends AbstractSpell {

	public SubversionSpell(MultiplayerVoxelWorldMain _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "Subversion", 1);
	}


	@Override
	public boolean activate(float interpol) {
		Ray ray = new Ray(this.player.getCamera().getLocation(), this.player.getCamera().getDirection());

		CollisionResults results = new CollisionResults();
		module.getRootNode().collideWith(ray, results);

		CollisionResult result = results.getClosestCollision();
		if (result != null) {
			Geometry g = result.getGeometry();
			AbstractPhysicalEntity ape = (AbstractPhysicalEntity)AbstractGameModule.getEntityFromSpatial(g);
			if (ape instanceof AbstractCreature) {
				AbstractCreature creature = (AbstractCreature)ape;
				if (creature.side != player.getSide()) {
					creature.side = player.getSide();
					return true;
				}
			} else {
				Settings.p(ape + " selected");
			}
		}
		return false;
	}


}
