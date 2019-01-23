package com.scs.samescreenchaos.abilities;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.Settings;
import com.scs.multiplayervoxelworld.entities.AbstractPhysicalEntity;
import com.scs.multiplayervoxelworld.entities.FloorOrCeiling;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;
import com.scs.samescreenchaos.ChaosSettings;
import com.scs.samescreenchaos.entities.WizardAvatar;
import com.scs.samescreenchaos.entities.creatures.AbstractCreature;

public abstract class AbstractSummonSpell extends AbstractSpell {

	public AbstractSummonSpell(MultiplayerVoxelWorldMain _game, AbstractGameModule module, WizardAvatar p, String name) {
		super(_game, module, p, name, 1);
	}


	@Override
	public boolean cast() {
		Ray ray = new Ray(this.player.getCamera().getLocation(), this.player.getCamera().getDirection());

		CollisionResults results = new CollisionResults();
		module.getRootNode().collideWith(ray, results);

		CollisionResult result = results.getClosestCollision();
		if (result != null) {
			if (result.getDistance() > 1f) { // So we don't build a block on top of ourselves
				Geometry g = result.getGeometry();
				AbstractPhysicalEntity ape = (AbstractPhysicalEntity)AbstractGameModule.getEntityFromSpatial(g);
				if (ape instanceof FloorOrCeiling) {
					Vector3f position = result.getContactPoint();
					position.y = ChaosSettings.SUMMON_Y_POS; // Drop from sky
					AbstractCreature c = this.createCreature(position);
					module.addEntity(c);
					//player.resources -= Settings.TURRET_COST;
					return true;
				} else {
					Settings.p(ape + " selected");
				}
			}
		}
		return false;
	}


	protected abstract AbstractCreature createCreature(Vector3f pos);
}
