package com.scs.samescreenchaos.models;

import com.jme3.scene.Spatial;

public interface ICreatureModel {

	Spatial getModel();
	
	void setAnim(int code);
}
