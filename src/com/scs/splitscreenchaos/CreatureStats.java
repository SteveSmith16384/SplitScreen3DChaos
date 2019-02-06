package com.scs.splitscreenchaos;

import java.io.IOException;
import java.util.HashMap;

import ssmith.lang.Functions;

public class CreatureStats {

	private HashMap<String, Stats> data = new HashMap<>();

	public CreatureStats() throws IOException {
		String[] lines = Functions.readAllTextFileFromJar("data/creaturestats.txt").split("\n");

		for (String s : lines) {
			if (s.length() > 0 && s.startsWith("#") == false) {
				String[] tmp = s.split(",");
				Stats stats = new Stats();
				stats.att = Integer.parseInt(tmp[1]);
				stats.def = Integer.parseInt(tmp[2]);
				stats.speed = Integer.parseInt(tmp[3]);
				stats.health = Integer.parseInt(tmp[4]);

				data.put(tmp[0], stats);
			}
		}
	}
	
	
	public Stats getStats(String name) {
		return data.get(name);
	}

}
