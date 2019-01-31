package com.scs.splitscreenchaos.abilities;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.scs.splitscreenchaos.components.IAttackable;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.Settings;
import com.scs.splitscreenfpsengine.entities.AbstractPhysicalEntity;
import com.scs.splitscreenfpsengine.entities.FloorOrCeiling;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class CommandCreatureSpell extends AbstractSpell {

	public CommandCreatureSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "CommandCreatureSpell", 0, -1);
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
				if (ape instanceof IAttackable) {
					WizardAvatar w = (WizardAvatar)this.player;
					AbstractCreature c = (AbstractCreature)ape;
					if (c.getOwner() == player) {
						w.selectedEntity = ape;
						Settings.p(ape + " selected");
						return true;
					} else {
						if (w.selectedEntity != null && w.selectedEntity instanceof AbstractCreature) {
							AbstractCreature owned = (AbstractCreature)w.selectedEntity;
							owned.setTarget((IAttackable)ape);
							Settings.p("Target selected");
							return true;
						}
					}
				} else if (ape instanceof FloorOrCeiling) {
					Vector3f position = result.getContactPoint();
					WizardAvatar w = (WizardAvatar)this.player;
					if (w.selectedEntity != null && w.selectedEntity instanceof AbstractCreature) {
						AbstractCreature c = (AbstractCreature)w.selectedEntity;
						c.setTarget(position);
						Settings.p("Destination " + position + " selected");
					}
					return true;
				} else {
					//Settings.p(ape + " selected");
				}
			}
		}
		return false;
	}


}
