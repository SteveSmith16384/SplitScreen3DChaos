package com.scs.splitscreenchaos;

import java.io.IOException;
import java.util.HashMap;

import ssmith.lang.Functions;

public class CreatureStats {

	private HashMap<String, Stats> data = new HashMap<>();

	public CreatureStats() throws IOException {
		String[] lines = Functions.readAllTextFileFromJar("Data/creaturestats.txt").split("\n");

		for (String s : lines) {
			if (s.length() > 0 && s.startsWith("#") == false) {
				String[] tmp = s.split(",");
				Stats stats = new Stats();
				stats.att = Integer.parseInt(tmp[1].trim());
				stats.def = Integer.parseInt(tmp[2].trim());
				stats.speed = Float.parseFloat(tmp[3].trim());
				stats.health = Integer.parseInt(tmp[4].trim());

				data.put(tmp[0].trim(), stats);
			}
		}
	}
	
	
	public Stats getStats(String name) {
		return data.get(name);
	}

}
