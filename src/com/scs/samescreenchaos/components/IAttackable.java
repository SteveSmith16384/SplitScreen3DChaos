package com.scs.samescreenchaos.components;

import com.jme3.math.Vector3f;
import com.scs.multiplayervoxelworld.components.IDamagable;

public interface IAttackable extends IDamagable { // todo - rename

	int getSide();
	
	boolean isAlive();
	
	Vector3f getLocation();
	
	float getDef();
	
}
