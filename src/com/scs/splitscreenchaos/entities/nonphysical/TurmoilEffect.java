package com.scs.splitscreenchaos.entities.nonphysical;

import java.util.ArrayList;
import java.util.List;

import com.jme3.math.Vector3f;
import com.scs.splitscreenchaos.ChaosGameModule;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.components.IEntity;
import com.scs.splitscreenfpsengine.components.IProcessable;
import com.scs.splitscreenfpsengine.entities.AbstractEntity;
import com.scs.splitscreenfpsengine.entities.AbstractPhysicalEntity;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

import ssmith.lang.NumberFunctions;
import ssmith.util.RealtimeInterval;

public class TurmoilEffect extends AbstractEntity implements IProcessable {

	private List<IEntity> entities = new ArrayList<IEntity>();
	private RealtimeInterval interval = new RealtimeInterval(2000);

	public TurmoilEffect(SplitScreenFpsEngine _game, AbstractGameModule _module) {
		super(_game, _module, "TurmoilEffect");
		
		this.module.addEntity(this);
	}


	@Override
	public void actuallyAdd() {
		this.entities.addAll(module.entities);

	}

	
	@Override
	public void process(float tpfSecs) {
		if (interval.hitInterval()) {
			AbstractPhysicalEntity ape = null;
			while (ape == null && !this.entities.isEmpty()) {
				IEntity e = this.entities.remove(0);
				if (e instanceof AbstractPhysicalEntity) {
					ape = (AbstractPhysicalEntity)e;
					break;
				}
			}
			if (ape != null) {
				float x = NumberFunctions.rndFloat(0,  ChaosGameModule.MAP_SIZE);
				float z = NumberFunctions.rndFloat(0,  ChaosGameModule.MAP_SIZE);
				Vector3f pos = new Vector3f(x, 0f, z);
				ape.setLocation(pos);
			}
		}

	}

}
