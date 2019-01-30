package com.scs.splitscreenchaos;

import ssmith.lang.NumberFunctions;

public class GameMechanics {

	public GameMechanics() {
	}

	
	/**
	 * Returns how much damage the defender incurs
	 * @param att
	 * @param def
	 * @return
	 */
	public static float combat(float att, float def) {
		att += NumberFunctions.rndFloat(0,  9);
		def += NumberFunctions.rndFloat(0,  9);

		if (att > def) {
			return att - def;
		} else {
			return 0;
		}
		
	}
}
