package com.scs.splitscreenchaos.abilities;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.ChaosSettings;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.entities.FloorOrCeiling;
import com.scs.splitscreenfpsengine.jme.JMEModelFunctions;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public abstract class AbstractSummonSpell extends AbstractSpell {
	
	private static final float RANGE = 8f;

	public AbstractSummonSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p, String name, int cost) {
		super(_game, module, p, name, cost, RANGE);
	}


	@Override
	public boolean cast() {
		Vector3f position = module.getFloorPointWithRay(this.getWizard(), RANGE);
		if (position != null) {
			//avatar.hud.appendToLog("Summoning " + name);
			Vector3f pos = JMEModelFunctions.getHeightAtPoint(position.x, position.z, game.getRootNode());
			position.y = pos.y + ChaosSettings.SUMMON_Y_POS; // Drop from sky
			AbstractCreature c = this.createCreature(position);
			c.getMainNode().lookAt(avatar.getLocation(), Vector3f.UNIT_Y); // Look at wizard
			module.addEntity(c);
			return true;
		}
		return false;
	}


	protected abstract AbstractCreature createCreature(Vector3f pos);
}
