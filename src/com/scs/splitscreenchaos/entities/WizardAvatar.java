package com.scs.splitscreenchaos.entities;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial.CullHint;
import com.scs.splitscreenchaos.abilities.CycleThroughAbilitiesAbility;
import com.scs.splitscreenchaos.abilities.FireballSpell2;
import com.scs.splitscreenchaos.components.IAttackable;
import com.scs.splitscreenchaos.effects.FloorSelector;
import com.scs.splitscreenchaos.entities.creatures.AbstractCreature;
import com.scs.splitscreenchaos.hud.ChaosHUD;
import com.scs.splitscreenchaos.models.WizardModel;
import com.scs.splitscreenfpsengine.Settings;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.components.IAvatarModel;
import com.scs.splitscreenfpsengine.components.IDamagable;
import com.scs.splitscreenfpsengine.components.IEntity;
import com.scs.splitscreenfpsengine.components.IShowTextOnHud;
import com.scs.splitscreenfpsengine.entities.AbstractPhysicalEntity;
import com.scs.splitscreenfpsengine.entities.AbstractPlayersAvatar;
import com.scs.splitscreenfpsengine.input.IInputDevice;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class WizardAvatar extends AbstractPlayersAvatar implements IWizard, IAttackable, IDamagable {

	public AbstractPhysicalEntity selectedEntity; //e.g. selected creature
	public float mana;
	protected float health;
	public boolean killed = false;

	public WizardAvatar(SplitScreenFpsEngine _game, AbstractGameModule _module, int _playerID, Camera _cam, IInputDevice _input, int _side) {
		super(_game, _module, _playerID, _cam, _input, _side, 1f);

		mana = 50;
		health = 100;
		
		this.ability[1] = new CycleThroughAbilitiesAbility(game, _module, this);
		this.ability[0] = new FireballSpell2(game, _module, this); // todo - default to first spell

		new FloorSelector(game, module, this);
	}


	public static String getOrbColour(int id) {
		switch (id) {
		case 0:
			return "Textures/orb_yellow.png";
		case 1:
			return "Textures/orb_red.png";
		case 2:
			return "Textures/orb_blue.png";
		case 3:
			return "Textures/orb_purple.png";
		default:
			throw new RuntimeException("Unknown colour for id " + id);
		}
	}


	@Override
	protected IAvatarModel getPlayersModel(SplitScreenFpsEngine game, int pid) {
		IAvatarModel wiz = new WizardModel(game.getAssetManager(), pid);
		return wiz;
	}


	@Override
	public void process(float tpfSecs) {
		if (killed) {
			return;
		}

		if (this.input.isCycleAbilityPressed(true)) {
			//Settings.p("Cycling fwd");
			CycleThroughAbilitiesAbility c = (CycleThroughAbilitiesAbility)this.ability[1];
			c.selectNext();
			this.hud.refresh();
		} else if (this.input.isCycleAbilityPressed(false)) {
			//Settings.p("Cycling bwd");
			CycleThroughAbilitiesAbility c = (CycleThroughAbilitiesAbility)this.ability[1];
			c.selectPrev();
			this.hud.refresh();
		}

		this.mana += tpfSecs;
		if (mana > 200) {
			mana = 200;
		}
		super.process(tpfSecs);

		//Show stats of creature we're pointing at
		IShowTextOnHud creature = (IShowTextOnHud)module.getWithRay(this, IShowTextOnHud.class, -1);
		ChaosHUD hud = (ChaosHUD)this.hud;
		if (creature != null) {
			hud.creatureStats.setText(creature.getHudText());//.getStatsForHud());
			Vector3f pos = this.cam.getScreenCoordinates(creature.getLocation());
			hud.creatureStats.setLocalTranslation(pos);
		} else {
			hud.creatureStats.setText("");
		}
		
		// Fallen off edge?
		if (this.playerControl.getPhysicsRigidBody().getPhysicsLocation().y < -10f) {
			killed("Too low");
			return;
		}

	}


	@Override
	public boolean isAlive() {
		return !killed;
	}


	@Override
	public float getDef() {
		return 1;
	}


	@Override
	public void damaged(float amt, String reason) {
		if (!killed) { // check not already dead
			Settings.p(this.getName() + " wounded " + (int)amt + " by " + reason);
			this.health -= amt;
			this.hud.showDamageBox();
			this.hud.appendToLog("Wounded " + (int)amt + " by " + reason);
			if (this.health <= 0) {
				killed(reason);
			}
		}
	}


	private void killed(String reason) {
		hud.appendToLog("You have been killed by " + reason);
		killed = true;
		Settings.p("Player died: " + reason);
		//todo - die anim - this.pl

		// Remove all owned creatures
		for (IEntity e : module.entities) {
			if (e instanceof AbstractCreature) {
				AbstractCreature ac = (AbstractCreature)e;
				if (ac.getOwner() == this) {
					ac.killed("Owner killed", true);
				}
			}
		}
		
		this.markForRemoval();

	}


	@Override
	public boolean isUndead() {
		return false;
	}


	@Override
	public boolean canBeSeen() {
		return this.getMainNode().getCullHint() != CullHint.Always;
	}


	@Override
	public void setLockedInCombat(IAttackable other) {
		// Do nothing

	}

	
	public float getHealth() {
		return health;
	}

}
