package com.scs.samescreenchaos.models;

import com.jme3.scene.Spatial;
import com.scs.samescreenchaos.entities.creatures.AbstractCreature;

public interface ICreatureModel {

	Spatial getModel();
	
	void setAnim(AbstractCreature.Anim anim);
}
