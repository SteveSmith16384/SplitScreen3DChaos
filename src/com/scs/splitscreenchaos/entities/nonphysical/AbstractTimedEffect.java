package com.scs.splitscreenchaos.entities.nonphysical;

import com.scs.splitscreenfpsengine.MultiplayerVoxelWorldMain;
import com.scs.splitscreenfpsengine.components.IProcessable;
import com.scs.splitscreenfpsengine.entities.AbstractEntity;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public abstract class AbstractTimedEffect extends AbstractEntity implements IProcessable {

	private float timeLeftSecs;
	
	public AbstractTimedEffect(MultiplayerVoxelWorldMain _game, AbstractGameModule _module, String name, float _durationSecs) {
		super(_game, _module, name);
		
		this.timeLeftSecs = _durationSecs;
		
		module.addEntity(this);
	}

	
	@Override
	public void process(float tpfSecs) {
		timeLeftSecs -= tpfSecs;
		if (timeLeftSecs <= 0) {
			this.effectFinished();
			this.markForRemoval();			
		}
	}
	
	protected abstract void effectFinished();
}
