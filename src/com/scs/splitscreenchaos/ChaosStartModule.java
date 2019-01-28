package com.scs.splitscreenchaos;

import com.jme3.scene.Spatial;
import com.scs.splitscreenchaos.models.GoldenDragonModel;
import com.scs.splitscreenfpsengine.MultiplayerVoxelWorldMain;
import com.scs.splitscreenfpsengine.modules.AbstractStartModule;

public class ChaosStartModule extends AbstractStartModule {

	private Spatial dragon;

	public ChaosStartModule(MultiplayerVoxelWorldMain _game) {
		super(_game);
	}

	
	@Override
	public void init() {
		super.init();
		
		dragon = new GoldenDragonModel(this.game.getAssetManager()).getModel();
		game.getRootNode().attachChild(dragon);

	}
	
	
	@Override
	public void update(float tpf) {
		super.update(tpf);
		
		dragon.getWorldTranslation();
		dragon.rotate(0, tpf, 0);
	}



}
