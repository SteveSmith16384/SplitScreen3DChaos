package com.scs.splitscreenchaos.effects;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
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
		JMEModelFunctions.setTextureOnSpatial(game.getAssetManager(), geometry, "Textures/yellowsun.jpg"); // todo - diff colours
		//geometry.setLocalTranslation(.5f, 0, .5f);
		this.getMainNode().attachChild(geometry);

		module.addEntity(this);

	}


	@Override
	public void process(float tpfSecs) {
		if (wiz.ability[0] instanceof AbstractSummonSpell == false) {
			this.getMainNode().setCullHint(CullHint.Always);
			return;
		}
		
		if (updateInt.hitInterval()) {
			Vector3f ape = module.getPointWithRay(wiz, FloorOrCeiling.class, -1);
			if (ape != null) {
				this.getMainNode().setCullHint(CullHint.Inherit);
				this.getMainNode().setLocalTranslation(ape);
			} else {
				this.getMainNode().setCullHint(CullHint.Always);
			}
			/*Ray ray = new Ray(this.wiz.getCamera().getLocation(), this.wiz.getCamera().getDirection());

			CollisionResults results = new CollisionResults();
			game.getRootNode().collideWith(ray, results);

			CollisionResult result = results.getClosestCollision();
			this.getMainNode().setCullHint(CullHint.Always);
			if (result != null) {
				Geometry g = result.getGeometry();
				AbstractPhysicalEntity ape = (AbstractPhysicalEntity)AbstractGameModule.getEntityFromSpatial(g);
				if (ape instanceof FloorOrCeiling) { // || g instanceof FloorSelector) {
					this.getMainNode().setCullHint(CullHint.Inherit);
					this.getMainNode().setLocalTranslation(result.getContactPoint());
				}
			}*/
		}
	}


}
