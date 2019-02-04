package com.scs.splitscreenchaos.entities.nonphysical;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.components.IAffectedByPhysics;
import com.scs.splitscreenfpsengine.components.IEntity;
import com.scs.splitscreenfpsengine.components.IExpiringEffect;
import com.scs.splitscreenfpsengine.components.IProcessable;
import com.scs.splitscreenfpsengine.entities.AbstractEntity;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

import ssmith.util.RealtimeInterval;

public class WindEffect extends AbstractEntity implements IProcessable, IExpiringEffect {

	//private List<IEntity> entities = new ArrayList<IEntity>();
	private RealtimeInterval interval = new RealtimeInterval(2000);
	private Vector3f dir;
	private WizardAvatar wiz;
	private float timeLeft = 10;
	
	public WindEffect(SplitScreenFpsEngine _game, AbstractGameModule _module, WizardAvatar _wiz, Vector3f _dir) {
		super(_game, _module, "WindEffect");

		wiz = _wiz;
		dir = _dir;
		this.module.addEntity(this);
	}


	@Override
	public void actuallyAdd() {
		//this.entities.addAll(module.entities);

	}

	@Override
	public void process(float tpfSecs) {
		if (interval.hitInterval()) {
			for (IEntity e : module.entities) {
				if (e instanceof AbstractCreature) {
					if (e instanceof IAffectedByPhysics) {
						AbstractCreature cre = (AbstractCreature)e;
						if (cre.getOwner() != this.wiz) {
							IAffectedByPhysics abp = (IAffectedByPhysics)e;
							abp.applyForce(dir);
						}
					}
				}
			}
		}

	}


	@Override
	public float getTimeRemaining() {
		return timeLeft;
	}


	@Override
	public void setTimeRemaining(float t) {
		timeLeft = t;
		
	}


	@Override
	public void effectFinished() {
		module.markEntityForRemoval(this);
		
	}

}
