package com.scs.samescreenchaos.entities.nonphysical;

import java.util.ArrayList;
import java.util.List;

import com.jme3.math.Vector3f;
import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.components.IAffectedByPhysics;
import com.scs.multiplayervoxelworld.components.IEntity;
import com.scs.multiplayervoxelworld.components.IProcessable;
import com.scs.multiplayervoxelworld.entities.AbstractEntity;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;

import ssmith.util.RealtimeInterval;

public class WindEffect extends AbstractEntity implements IProcessable {

	private List<IEntity> entities = new ArrayList<IEntity>();
	private RealtimeInterval interval = new RealtimeInterval(2000);
	private Vector3f dir;

	public WindEffect(MultiplayerVoxelWorldMain _game, AbstractGameModule _module, Vector3f _dir) {
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
