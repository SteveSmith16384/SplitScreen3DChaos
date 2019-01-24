package com.scs.samescreenchaos.abilities;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.Settings;
import com.scs.multiplayervoxelworld.entities.AbstractPhysicalEntity;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;
import com.scs.samescreenchaos.ChaosGameModule;
import com.scs.samescreenchaos.blocks.WallBlock;
import com.scs.samescreenchaos.entities.AbstractCorpse;
import com.scs.samescreenchaos.entities.WizardAvatar;

public class ResurrectSpell extends AbstractSpell {

	public ResurrectSpell(MultiplayerVoxelWorldMain _game, AbstractGameModule module, WizardAvatar p) {
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
