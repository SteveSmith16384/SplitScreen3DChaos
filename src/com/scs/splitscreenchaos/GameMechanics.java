package com.scs.splitscreenchaos;

import ssmith.lang.NumberFunctions;

public class GameMechanics {

	public GameMechanics() {
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * Returns how much damage the defender incurs
	 * @param att
	 * @param def
	 * @return
	 */
	public static float combat(float att, float def) {
		att += NumberFunctions.rndFloat(1,  10);
		def += NumberFunctions.rndFloat(1,  10);

		if (att > def) {
			return att - def;
		} else {
			return 0;
		}
		
	}
}
