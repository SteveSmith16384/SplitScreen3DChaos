package com.scs.samescreenchaos.entities;

import com.jme3.scene.Node;
import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.components.ICanShoot;
import com.scs.multiplayervoxelworld.entities.AbstractBullet;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;

public class Fireball extends AbstractBullet {

	public Fireball(MultiplayerVoxelWorldMain _game, AbstractGameModule _module, ICanShoot _shooter) {
		super(_game, _module, "Fireball", _shooter);
	}

	@Override
	protected Node getBulletModel() {
		// TODO Auto-generated method stub
		return null;
	}

}
