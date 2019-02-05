package com.scs.splitscreenchaos.abilities;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.components.IAttackable;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenfpsengine.Settings;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.abilities.AbstractAbility;
import com.scs.splitscreenfpsengine.entities.AbstractPhysicalEntity;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class CommandCreatureSpell extends AbstractAbility {

	public CommandCreatureSpell(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar p) {
		super(_game, module, p, "Command Creature");
	}


	@Override
	public boolean activate(float interpol) {
		AbstractPhysicalEntity creature = (AbstractPhysicalEntity)module.getWithRay(this.avatar, AbstractPhysicalEntity.class, -1);
		if (creature != null) {
			WizardAvatar w = (WizardAvatar)this.avatar;
			if (creature instanceof AbstractCreature) {
				AbstractCreature c = (AbstractCreature)creature;
				if (c.getOwner() == avatar) {
					w.selectedEntity = creature;
					Settings.p(creature + " selected");
					this.avatar.hud.refresh();
					return true;
				} else {
					if (w.selectedEntity != null && w.selectedEntity instanceof AbstractCreature) {
						AbstractCreature owned = (AbstractCreature)w.selectedEntity;
						owned.setTarget((IAttackable)c);
						Settings.p("Target selected");
						return true;
					}
				}
			} else {
				Vector3f position = module.getFloorPointWithRay(w, -1);
				if (position != null) {
					if (w.selectedEntity != null && w.selectedEntity instanceof AbstractCreature) {
						AbstractCreature c = (AbstractCreature)w.selectedEntity;
						c.setTarget(position);
						Settings.p("Destination " + position + " selected");
						this.avatar.hud.appendToLog("Destination selected for " + w.selectedEntity.name);
						return true;
					}
				}
			}
		} else {
			Settings.p("Nothing found");
		}
		return false;
	}


	@Override
	public boolean process(float tpfSecs) {
		return false;
	}


	@Override
	public String getHudText() {
		return "Command Creature";
	}


}
