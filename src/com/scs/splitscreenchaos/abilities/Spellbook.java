package com.scs.splitscreenchaos.abilities;

import java.util.ArrayList;
import java.util.List;

import com.scs.splitscreenfpsengine.MultiplayerVoxelWorldMain;
import com.scs.splitscreenfpsengine.abilities.AbstractAbility;
import com.scs.splitscreenfpsengine.entities.AbstractPlayersAvatar;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class Spellbook extends AbstractAbility { // todo - use this?

	private static final float SPELL_INTERVAL = 5;
	
	private float timeSinceLastCast = SPELL_INTERVAL;
	private AbstractSpell currentSpell;
	private List<AbstractSpell> spells = new ArrayList<>();
	
	public Spellbook(MultiplayerVoxelWorldMain _game, AbstractGameModule module, AbstractPlayersAvatar _player) {
		super(_game, module, _player);
		
		//currentSpell = new WallSpell(module, _player);
	}

	
	@Override
	public boolean process(float tpfSecs) {
		timeSinceLastCast += tpfSecs;
		//Settings.p("Too soon: " + timeSinceLastCast);
		return false;
	}

	
	@Override
	public boolean activate(float tpfSecs) {
		if (timeSinceLastCast > SPELL_INTERVAL) {
			if (currentSpell.activate(tpfSecs)) {
				timeSinceLastCast = 0;
				return true;
			}
		} else {
		}
		return false;
	}
	

	@Override
	public String getHudText() {
		return currentSpell.getHudText();
	}

}
