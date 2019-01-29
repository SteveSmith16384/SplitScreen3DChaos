package com.scs.splitscreenchaos;

import com.jme3.scene.Spatial;
import com.scs.splitscreenchaos.models.GoldenDragonModel2;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractStartModule;

public class ChaosStartModule extends AbstractStartModule {

	private Spatial dragon;

	public ChaosStartModule(SplitScreenFpsEngine _game) {
		super(_game);
	}

	
	@Override
	public void init() {
		super.init();
		
		dragon = new GoldenDragonModel2(this.game.getAssetManager()).getModel();
		game.getRootNode().attachChild(dragon);
/*		
		// Audio
		audioMusic = new AudioNode(game.getAssetManager(), "Sound/n-Dimensions (Main Theme - Retro Ver.ogg", true, false);
		//audioMusic.setLooping(true);  // activate continuous playing.  BROKEN!
		audioMusic.setPositional(false);
		audioMusic.setVolume(3);
		game.getRootNode().attachChild(audioMusic);
		audioMusic.play(); // play continuously!
*/


	}
	
	
	@Override
	public void update(float tpf) {
		super.update(tpf);
		
		dragon.getWorldTranslation();
		dragon.rotate(0, tpf, 0);
	}



}
