package com.scs.samescreenchaos.entities.nonphysical;

import java.util.ArrayList;
import java.util.List;

import com.jme3.math.Vector3f;
import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.components.IEntity;
import com.scs.multiplayervoxelworld.components.IProcessable;
import com.scs.multiplayervoxelworld.entities.AbstractEntity;
import com.scs.multiplayervoxelworld.entities.AbstractPhysicalEntity;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;
import com.scs.samescreenchaos.ChaosGameModule;

import ssmith.lang.NumberFunctions;
import ssmith.util.RealtimeInterval;

public class TurmoilEffect extends AbstractEntity implements IProcessable {

	private List<IEntity> entities = new ArrayList<IEntity>();
	private RealtimeInterval interval = new RealtimeInterval(2000);

	public TurmoilEffect(MultiplayerVoxelWorldMain _game, AbstractGameModule _module) {
		super(_game, _module, "Turmoil");
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
