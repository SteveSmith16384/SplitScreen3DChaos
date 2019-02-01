package com.scs.splitscreenchaos;

import java.util.prefs.BackingStoreException;

import com.jme3.system.AppSettings;
import com.scs.splitscreenfpsengine.MultiplayerVoxelWorldProperties;
import com.scs.splitscreenfpsengine.Settings;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;
import com.scs.splitscreenfpsengine.modules.AbstractStartModule;

public class ChaosMain extends SplitScreenFpsEngine {

	private static final String PROPS_FILE = "chaos_settings.txt";
	private static final int NUM_AI = 0;//1;

	public static void main(String[] args) {
		try {
			properties = new MultiplayerVoxelWorldProperties(PROPS_FILE);
			settings = new AppSettings(true);
			try {
				settings.load(ChaosSettings.NAME);
			} catch (BackingStoreException e) {
				e.printStackTrace();
			}
			//settings.setUseJoysticks(true);
			settings.setTitle(ChaosSettings.NAME + " (v" + ChaosSettings.VERSION + ")");
			if (Settings.RELEASE_MODE) {
				//todo settings.setSettingsDialogImage("Textures/text/multiplayerarena.png");
			} else {
				settings.setSettingsDialogImage(null);
			}

			MAX_TURN_SPEED = SplitScreenFpsEngine.properties.GetMaxTurnSpeed();
			//BASE_SCORE_INC = MultiplayerVoxelWorldMain.properties.GetBaseScoreInc();

			ChaosMain app = new ChaosMain();
			app.setSettings(settings);

			try {
				settings.save(ChaosSettings.NAME);
			} catch (BackingStoreException e) {
				e.printStackTrace();
			}

			app.start();

		} catch (Exception e) {
			Settings.p("Error: " + e);
			e.printStackTrace();
		}

	}


	public ChaosMain() {
		super();
	}

/*
	@Override
	public void simpleInitApp() {
		super.simpleInitApp();

		// Bloom
		BloomFilter bloom = new BloomFilter();
		bloom.setDownSamplingFactor(2);
		bloom.setBlurScale(1.37f);
		bloom.setExposurePower(3.30f);
		bloom.setExposureCutOff(0.2f);
		bloom.setBloomIntensity(2.45f);
		FilterPostProcessor fpp2 = new FilterPostProcessor(game.getAssetManager());
		fpp2.addFilter(bloom);
		view2.addProcessor(fpp2);
	}
*/

	@Override
	public AbstractGameModule getGameModule() {
		return new ChaosGameModule(this, super.getNumPlayers(), NUM_AI);
	}


	@Override
	public AbstractStartModule getStartModule() {
		return new ChaosStartModule(this);
	}


}
