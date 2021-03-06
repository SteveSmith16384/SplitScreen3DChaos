package com.scs.splitscreenchaos;

import java.io.IOException;

import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.scs.splitscreenchaos.entities.AIWizard;
import com.scs.splitscreenchaos.entities.BlackHole;
import com.scs.splitscreenchaos.entities.ChaosTerrainEntity;
import com.scs.splitscreenchaos.entities.MageTower;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.Beholder;
import com.scs.splitscreenchaos.entities.creatures.Cerberus;
import com.scs.splitscreenchaos.entities.creatures.GiantSpider;
import com.scs.splitscreenchaos.entities.creatures.GoldenDragon;
import com.scs.splitscreenchaos.entities.creatures.Golem;
import com.scs.splitscreenchaos.entities.creatures.Scorpion;
import com.scs.splitscreenchaos.entities.creatures.Skeleton;
import com.scs.splitscreenchaos.entities.creatures.Zombie;
import com.scs.splitscreenchaos.hud.ChaosHUD;
import com.scs.splitscreenfpsengine.Settings;
import com.scs.splitscreenfpsengine.SplitScreenFpsEngine;
import com.scs.splitscreenfpsengine.components.IEntity;
import com.scs.splitscreenfpsengine.entities.AbstractPlayersAvatar;
import com.scs.splitscreenfpsengine.entities.AbstractTerrainEntity;
import com.scs.splitscreenfpsengine.entities.FloorOrCeiling;
import com.scs.splitscreenfpsengine.hud.IHud;
import com.scs.splitscreenfpsengine.input.IInputDevice;
import com.scs.splitscreenfpsengine.jme.JMEModelFunctions;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

public class ChaosGameModule extends AbstractGameModule {

	public static final int MAP_SIZE = 32;

	public int totalWizards, totalHumans, totalAI;
	public CreatureStats stats;

	public ChaosGameModule(SplitScreenFpsEngine _game, int _numHumans, int _numAI) throws IOException {
		super(_game);

		totalHumans = _numHumans;
		totalAI = _numAI;
		totalWizards = totalHumans + totalAI;

		stats = new CreatureStats();
	}


	@Override
	public void init() {
		super.init();

		// Point wizards to centre
		Vector3f centre = new Vector3f(MAP_SIZE/2, 1, MAP_SIZE/2);
		for (IEntity e : entities) {
			if (e instanceof WizardAvatar) {
				WizardAvatar wiz = (WizardAvatar)e;
				wiz.getMainNode().lookAt(centre, Vector3f.UNIT_Y);
			}
		}
	}


	@Override
	public void setupLevel() {
		if (!Settings.USE_TERRAIN) {
			FloorOrCeiling floor = new FloorOrCeiling(game, this, 0, 0, 0, MAP_SIZE, 1f, MAP_SIZE, "Textures/blocks/lavarock.jpg");
			this.addEntity(floor);

			new MageTower(game, this, new Vector3f(0.5f, 0, 0.5f));
			new MageTower(game, this, new Vector3f(MAP_SIZE-1, 0, 0.5f));
			new MageTower(game, this, new Vector3f(0.5f, 0, MAP_SIZE-1));
			new MageTower(game, this, new Vector3f(MAP_SIZE-1, 0, MAP_SIZE-1));

		} else {
			AbstractTerrainEntity t = new ChaosTerrainEntity(game, this, MAP_SIZE);
			this.addEntity(t);

			Vector3f pos = JMEModelFunctions.getHeightAtPoint(0.5f, 0.5f, t.getMainNode());
			new MageTower(game, this, pos);
			pos = JMEModelFunctions.getHeightAtPoint(MAP_SIZE-1, 0.5f, t.getMainNode());
			new MageTower(game, this, pos);
			pos = JMEModelFunctions.getHeightAtPoint(0.5f, MAP_SIZE-1, t.getMainNode());
			new MageTower(game, this, pos);
			pos = JMEModelFunctions.getHeightAtPoint(MAP_SIZE-1, MAP_SIZE-1, t.getMainNode());
			new MageTower(game, this, pos);

		}

		// Create AI Wiz
		if (ChaosSettings.AI_WIZARDS) {
			int num = this.totalHumans;
			for (int i=0 ; i<totalAI ; i++) {
				AIWizard wiz = new AIWizard(game, this, this.getPlayerStartPos(num), num);
				this.addEntity(wiz);
				num++;
			}
		}

		// Create AI Monsters
		if (game.getNumPlayers() == 1) {
			{
				GoldenDragon gd = new GoldenDragon(game, this, new Vector3f(3, 2, 3), null);
				//this.addEntity(gd);
			}
			{
				Golem gd = new Golem(game, this, new Vector3f(6, 2, 3), null);
				this.addEntity(gd);
			}
			/*{
				Scorpion gd = new Scorpion(game, this, new Vector3f(18, 2, 3), null);
				this.addEntity(gd);
			}
			{
				Skeleton gd = new Skeleton(game, this, new Vector3f(21, 2, 3), null);
				this.addEntity(gd);
			}
			{
				Zombie gd = new Zombie(game, this, new Vector3f(24, 2, 3), null);
				this.addEntity(gd);
			}
			{
				Beholder gd = new Beholder(game, this, new Vector3f(9, 2, 3), null);
				this.addEntity(gd);
			}
			{
				GiantSpider gd = new GiantSpider(game, this, new Vector3f(15, 2, 3), null);
				this.addEntity(gd);
			}
			{
				Cerberus gd = new Cerberus(game, this, new Vector3f(12, 2, 3), null);
				this.addEntity(gd);
			}*/
			for (int i=0; i<10 ; i++) {
				Skeleton gd = new Skeleton(game, this, new Vector3f(5 + (i*2), 2, 3), null);
				//this.addEntity(gd);
			}
		}
	}


	@Override
	public void update(float tpfSecs) {
		super.update(tpfSecs);
	}


	@Override
	public Vector3f getPlayerStartPos(int id) {
		float DEF_HEIGHT = 15;
		switch (this.totalWizards) {
		case 1:
			return new Vector3f(MAP_SIZE/2, DEF_HEIGHT, MAP_SIZE/2);
		case 2:
			switch (id) {
			case 0:
				return new Vector3f(2, DEF_HEIGHT, 2);
			case 1:
				return new Vector3f(MAP_SIZE-3, DEF_HEIGHT, MAP_SIZE-3);
			default:
				throw new RuntimeException("No start value for player " + id + " in a " + totalWizards + " player game");
			}
		case 3:
		case 4:
			switch (id) {
			case 0:
				return new Vector3f(2, DEF_HEIGHT, 2);
			case 1:
				return new Vector3f(MAP_SIZE-3, DEF_HEIGHT, MAP_SIZE-3);
			case 2:
				return new Vector3f(MAP_SIZE-3, DEF_HEIGHT, 2);
			case 3:
				return new Vector3f(2, DEF_HEIGHT, MAP_SIZE-3);
			default:
				throw new RuntimeException("No start value for player " + id + " in a " + totalWizards + " player game");
			}
		default:
			throw new RuntimeException("No start value for player " + id + " in a " + totalWizards + " player game");

		}
	}

	/*
	private Point getRandomBlockPos(int inset) {
		int x = NumberFunctions.rnd(inset, MAP_SIZE_BLOCKS-inset-1);
		int z = NumberFunctions.rnd(inset, MAP_SIZE_BLOCKS-inset-1);
		return new Point(x, z) ;
	}


	private void createTree(VoxelTerrainEntity vte, Vector3f treePos) {
		int height = NumberFunctions.rnd(10,  20);
		int leavesStartHeight = NumberFunctions.rnd(5,  height-2);
		int maxRad = NumberFunctions.rnd(1,  8);

		// Trunk
		for (int y=0 ; y<height ; y++) {
			Vector3Int pos = new Vector3Int(treePos.x, treePos.y+y, treePos.z);
			ChangingBlock block = new ChangingBlock(WoodBlock.class, pos);
			addChangingBlock(block);
		}

		for (int y=leavesStartHeight ; y<height ; y++) {
			for (int x=(int)treePos.x-maxRad ; x<=treePos.x+maxRad ; x++) {
				for (int z=(int)treePos.z-maxRad ; z<=treePos.z+maxRad ; z++) {
					if (NumberFunctions.rnd(1, 4) == 1) {
						Vector3Int pos = new Vector3Int(x, treePos.y+y,z);
						ChangingBlock block = new ChangingBlock(GrassBlock.class, pos);
						addChangingBlock(block);
					}
				}
			}
		}
	}

/*
	private void createWall_Horiz(VoxelTerrainEntity vte, Vector3f startPos) {
		int height = 8;
		for (int x=0 ; x<20 ; x++) {
			height += NumberFunctions.rnd(-1,  1);
			if (height < 0) {
				height = 1;
			}
			for (int y=0 ; y<height; y++) {
				Vector3Int pos = new Vector3Int(startPos.x+x, startPos.y+y, startPos.z);
				ChangingBlock block = new ChangingBlock(BrickBlock.class, pos);
				addChangingBlock(block);
			}
		}
	}


	private void createWall_Vert(VoxelTerrainEntity vte, Vector3f startPos) {
		int height = 8;
		for (int z=0 ; z<20 ; z++) {
			height += NumberFunctions.rnd(-1,  1);
			if (height < 0) {
				height = 1;
			}
			for (int y=0 ; y<height; y++) {
				Vector3Int pos = new Vector3Int(startPos.x, startPos.y+y, startPos.z+z);
				ChangingBlock block = new ChangingBlock(BrickBlock.class, pos);
				addChangingBlock(block);
			}
		}
	}


	private void addChangingBlock(ChangingBlock block) {
		vte.blocks.setBlock(block.pos, block.getClass());
		this.changingBlocks.add(block);
	}

	 */
	@Override
	protected AbstractPlayersAvatar getPlayersAvatar(SplitScreenFpsEngine _game, AbstractGameModule _module, int _playerID,
			Camera _cam, IInputDevice _input, int _side) {
		return new WizardAvatar(_game, this, _playerID, _cam, _input, _side);
	}


	@Override
	protected IHud generateHUD(SplitScreenFpsEngine _game, AbstractGameModule _module, AbstractPlayersAvatar _player, float xBL, float yBL, float w, float h, Camera _cam) {
		return new ChaosHUD(_game, _module, (WizardAvatar)_player, xBL, yBL, w, h, _cam);
	}


	@Override
	protected void onViewportCreated(ViewPort viewport) {
		if (ChaosSettings.BLOOM) {
			// Bloom
			BloomFilter bloom = new BloomFilter();
			bloom.setDownSamplingFactor(2);
			bloom.setBlurScale(1.37f);
			bloom.setExposurePower(3.30f);
			bloom.setExposureCutOff(0.2f);
			bloom.setBloomIntensity(1.5f);//2.45f);
			FilterPostProcessor fpp2 = new FilterPostProcessor(game.getAssetManager());
			fpp2.addFilter(bloom);
			viewport.addProcessor(fpp2);
		}
	}


	@Override
	protected void testPressed() {
		//new WindEffect(game, this, this.playerDebug, new Vector3f(1, 0, 0));
		Vector3f pos = playerDebug.getLocation();
		pos.y += 2f;
		//new ParticleShockwave(game, this, pos);
		new BlackHole(game, this, (WizardAvatar)playerDebug, pos);
	}

}
