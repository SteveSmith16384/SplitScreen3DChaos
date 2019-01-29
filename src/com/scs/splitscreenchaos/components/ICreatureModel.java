package com.scs.splitscreenchaos.components;

import com.jme3.scene.Spatial;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;

public interface ICreatureModel {

	Spatial getModel();
	
	void setCreatureAnim(AbstractCreature.Anim anim);
}
