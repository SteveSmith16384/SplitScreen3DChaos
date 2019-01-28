package com.scs.splitscreenchaos.abilities;

import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.nonphysical.RunFastEffect;
import com.scs.splitscreenfpsengine.MultiplayerVoxelWorldMain;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class RunFast extends AbstractSpell {

	private static final float MAX_POWER = 10;
	
	private float power;
	private boolean isRunningFast;
	
	public RunFast(MultiplayerVoxelWorldMain _game, AbstractGameModule module, WizardAvatar _player) {
		super(_game, module, _player, "RunFast", 1);
	}

	
	@Override
	public boolean cast() {
		this.player.moveSpeed = this.player.moveSpeed * 2;
		RunFastEffect rfe = new RunFastEffect(game, module, player);
		return  true;
	}

	
	@Override
	public String getHudText() {
		return isRunningFast ? "RUNNING FAST!" : "[running normally]";
	}

}
