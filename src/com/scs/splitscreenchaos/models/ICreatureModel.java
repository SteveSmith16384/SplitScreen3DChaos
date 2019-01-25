package com.scs.splitscreenchaos.models;

import com.jme3.scene.Spatial;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;

public interface ICreatureModel {

	Spatial getModel();
	
	void setAnim(AbstractCreature.Anim anim);
}
