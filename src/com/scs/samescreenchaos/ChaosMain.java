package com.scs.samescreenchaos;

import java.util.prefs.BackingStoreException;

import com.jme3.system.AppSettings;
import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldProperties;
import com.scs.multiplayervoxelworld.Settings;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;

public class ChaosMain extends MultiplayerVoxelWorldMain {

	private static final String PROPS_FILE = "chaos_settings.txt";
	
	public static void main(String[] args) {
		try {
			properties = new MultiplayerVoxelWorldProperties(PROPS_FILE);
			settings = new AppSettings(true);
			try {
				settings.load(Settings.NAME);
			} catch (BackingStoreException e) {
				e.printStackTrace();
			}
			//settings.setUseJoysticks(true);
			settings.setTitle(Settings.NAME + " (v" + Settings.VERSION + ")");
			if (Settings.RELEASE_MODE) {
				//todo settings.setSettingsDialogImage("Textures/text/multiplayerarena.png");
			} else {
				settings.setSettingsDialogImage(null);
			}

			MAX_TURN_SPEED = MultiplayerVoxelWorldMain.properties.GetMaxTurnSpeed();
			//BASE_SCORE_INC = MultiplayerVoxelWorldMain.properties.GetBaseScoreInc();
			
			ChaosMain app = new ChaosMain();
			app.setSettings(settings);

			try {
				settings.save(Settings.NAME);
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
		return new ChaosGameModule(this);
	}


}
