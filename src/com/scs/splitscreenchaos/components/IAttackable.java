package com.scs.splitscreenchaos.components;

import com.jme3.math.Vector3f;
import com.scs.splitscreenfpsengine.components.IDamagable;

public interface IAttackable extends IDamagable {

	int getSide();
	
	boolean isAlive();
	
	Vector3f getLocation();
	
	float getDef();
	
	boolean isUndead();
	
}
