package com.scs.samescreenchaos.abilities;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.Settings;
import com.scs.multiplayervoxelworld.entities.AbstractPhysicalEntity;
import com.scs.multiplayervoxelworld.entities.AbstractPlayersAvatar;
import com.scs.multiplayervoxelworld.entities.VoxelTerrainEntity;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;

public class TeleportSpell extends AbstractSpell {

	public TeleportSpell(MultiplayerVoxelWorldMain _game, AbstractGameModule module, AbstractPlayersAvatar p) {
		super(_game, module, p, "Teleport", 1);
	}


	@Override
	public boolean process(float interpol) {
		return false;
	}


	@Override
	public boolean activate(float interpol) {
		/*if (this.player.resources < Settings.TURRET_COST) {
			Settings.p("Not enough resources");
			//return false;
		}*/
		Ray ray = new Ray(this.player.getCamera().getLocation(), this.player.getCamera().getDirection());

		CollisionResults results = new CollisionResults();
		module.getRootNode().collideWith(ray, results);

		CollisionResult result = results.getClosestCollision();
		if (result != null) {
			if (result.getDistance() > 1f) { // So we don't build a block on top of ourselves
				Geometry g = result.getGeometry();
				AbstractPhysicalEntity ape = (AbstractPhysicalEntity)AbstractGameModule.getEntityFromSpatial(g);
				if (ape instanceof VoxelTerrainEntity) {
					VoxelTerrainEntity vte = (VoxelTerrainEntity)ape;
					Vector3f position = result.getContactPoint();
					player.playerControl.warp(position);
					return true;
				} else {
					Settings.p(ape + " selected");
				}
			}
		}
		return false;
	}


	@Override
	public boolean onlyActivateOnClick() {
		return true;
	}


}
