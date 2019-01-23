package com.scs.samescreenchaos.components;

import com.jme3.math.Vector3f;

public interface ITargetByAI { // todo - rename

	int getSide();
	
	boolean isAlive();
	
	Vector3f getLocation();
	
	float getDef();
	
	//void attackedBy(ITargetByAI other);

}
