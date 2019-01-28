package com.scs.splitscreenchaos.abilities;

import com.jme3.scene.Spatial.CullHint;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenfpsengine.MultiplayerVoxelWorldMain;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class Invisibility extends AbstractSpell {

	private static final float MAX_POWER = 10;
	
	private float power;
	private boolean isInvisible;
	
	public Invisibility(MultiplayerVoxelWorldMain _game, AbstractGameModule module, WizardAvatar _player) {
		super(_game, module, _player, "Invisibility", 1);
	}

	
	@Override
	public boolean process(float interpol) {
		this.player.getMainNode().setCullHint(CullHint.Inherit); // Default
		isInvisible = false;
		power += interpol;
		power = Math.min(power, MAX_POWER);
		return true;
	}

	
	@Override
	public boolean cast() {
		power -= 4;//interpol*4;
		power = Math.max(power, 0);
		if (power > 0) {
			this.player.getMainNode().setCullHint(CullHint.Always);
			isInvisible = true;
			return true;
		}
		return false;
	}

	
	@Override
	public String getHudText() {
		return (isInvisible ? "INVISIBLE! " : "[not invisible] ") + ((int)(power*10));
	}

}
