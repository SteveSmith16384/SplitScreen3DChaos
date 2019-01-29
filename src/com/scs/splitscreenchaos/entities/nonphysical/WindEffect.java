package com.scs.splitscreenchaos.entities.nonphysical;

import java.util.ArrayList;
import java.util.List;

import com.jme3.math.Vector3f;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.components.IAffectedByPhysics;
import com.scs.splitscreenfpsengine.components.IEntity;
import com.scs.splitscreenfpsengine.components.IProcessable;
import com.scs.splitscreenfpsengine.entities.AbstractEntity;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

import ssmith.util.RealtimeInterval;

public class WindEffect extends AbstractEntity implements IProcessable {

	private List<IEntity> entities = new ArrayList<IEntity>();
	private RealtimeInterval interval = new RealtimeInterval(2000);
	private Vector3f dir;

	public WindEffect(SplitScreenFpsEngine _game, AbstractGameModule _module, Vector3f _dir) {
		super(_game, _module, "WindEffect");

		dir = _dir;
		this.module.addEntity(this);
	}


	@Override
	public void actuallyAdd() {
		this.entities.addAll(module.entities);

	}

	@Override
	public void process(float tpfSecs) {
		if (interval.hitInterval()) {
			for (IEntity e : this.entities) {
				// todo - only blow enemies
				if (e instanceof IAffectedByPhysics) {
					IAffectedByPhysics abp = (IAffectedByPhysics)e;
					abp.applyForce(dir);
				}
			}
		}

	}

}
