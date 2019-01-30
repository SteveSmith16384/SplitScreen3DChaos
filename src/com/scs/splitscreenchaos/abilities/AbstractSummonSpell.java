package com.scs.splitscreenchaos.abilities;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.ChaosSettings;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.entities.FloorOrCeiling;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public abstract class AbstractSummonSpell extends AbstractSpell {

	public AbstractSummonSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p, String name, int cost) {
		super(_game, module, p, name, cost);
	}


	@Override
	public boolean cast() {
		/*Ray ray = new Ray(this.player.getCamera().getLocation(), this.player.getCamera().getDirection());

		CollisionResults results = new CollisionResults();
		module.getRootNode().collideWith(ray, results);

		CollisionResult result = results.getClosestCollision();
		if (result != null) {
			if (result.getDistance() > 1f) { // So we don't build a block on top of ourselves
				if (result.getDistance() < 8f) {
					Geometry g = result.getGeometry();
					AbstractPhysicalEntity ape = (AbstractPhysicalEntity)AbstractGameModule.getEntityFromSpatial(g);
					if (ape instanceof FloorOrCeiling) {*/
		Vector3f position = module.getPointWithRay(this.getWizard(), FloorOrCeiling.class, 8f);
		if (position != null) {
			player.hud.appendToLog("Summoning " + name);
			//Vector3f position = result.getContactPoint();
			position.y = ChaosSettings.SUMMON_Y_POS; // Drop from sky
			AbstractCreature c = this.createCreature(position);
			c.getMainNode().lookAt(player.getLocation(), Vector3f.UNIT_Y); // Look at wizard
			module.addEntity(c);
			return true;
		}
		return false;
	}


	protected abstract AbstractCreature createCreature(Vector3f pos);
}
