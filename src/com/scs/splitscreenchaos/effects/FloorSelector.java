package com.scs.splitscreenchaos.effects;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
import com.scs.splitscreenchaos.abilities.AbstractSpell;
import com.scs.splitscreenchaos.abilities.AbstractSummonSpell;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.components.IProcessable;
import com.scs.splitscreenfpsengine.entities.AbstractPhysicalEntity;
import com.scs.splitscreenfpsengine.entities.FloorOrCeiling;
import com.scs.splitscreenfpsengine.jme.JMEModelFunctions;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

import ssmith.util.RealtimeInterval;

public class FloorSelector extends AbstractPhysicalEntity implements IProcessable {

	private WizardAvatar wiz;
	private RealtimeInterval updateInt = new RealtimeInterval(200);

	public FloorSelector(SplitScreenFpsEngine _game, AbstractGameModule module, WizardAvatar _wiz) {
		super(_game, module, "FloorSelector");

		game =_game;
		wiz = _wiz;

		Box box1 = new Box(.5f, .01f, .5f);
		Geometry geometry = new Geometry("SquareIndicatorBox", box1);
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, WizardAvatar.getOrbColour(wiz.playerID));
		//geometry.setLocalTranslation(.5f, 0, .5f);
		this.getMainNode().attachChild(geometry);

		module.addEntity(this);

	}


	@Override
	public void process(float tpfSecs) {
		if (updateInt.hitInterval()) {
			if (wiz.ability[0] instanceof AbstractSummonSpell == false) {
				this.getMainNode().setCullHint(CullHint.Always);
				return;
			}

			Vector3f position = module.getPointWithRay(wiz, FloorOrCeiling.class, -1);
			if (position != null) {
				// Check range
				if (wiz.ability[0] instanceof AbstractSpell) {
					AbstractSpell spell = (AbstractSpell)wiz.ability[0];
					if (spell.getRange() > 0) {
						float dist = wiz.distance(position);
						if (dist > spell.getRange()) {
							this.getMainNode().setCullHint(CullHint.Always);
						}
					}
				}

				this.getMainNode().setCullHint(CullHint.Inherit);
				this.getMainNode().setLocalTranslation(position);
			} else {
				this.getMainNode().setCullHint(CullHint.Always);
			}
		}
	}


}
