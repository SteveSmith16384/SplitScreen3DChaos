package com.scs.splitscreenchaos;

import java.io.IOException;
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


	@Override
	public AbstractGameModule getGameModule() {
		try {
			return new ChaosGameModule(this, super.getNumPlayers(), NUM_AI);
		} catch (IOException e) {
			throw new RuntimeException("Unable to create game", e);
		}
	}


	@Override
	public AbstractStartModule getStartModule() {
		return new ChaosStartModule(this);
	}


}
