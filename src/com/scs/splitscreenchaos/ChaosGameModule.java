package com.scs.splitscreenchaos;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.scs.splitscreenchaos.blocks.ChangingBlock;
import com.scs.splitscreenchaos.blocks.GrassBlock;
import com.scs.splitscreenchaos.blocks.StoneBlock;
import com.scs.splitscreenchaos.blocks.WoodBlock;
import com.scs.splitscreenchaos.entities.WizardAvatar;
import com.scs.splitscreenchaos.entities.creatures.GoldenDragon;
import com.scs.splitscreenchaos.hud.ChaosHUD;
import com.scs.splitscreenfpsengine.MultiplayerVoxelWorldMain;
import com.scs.splitscreenfpsengine.entities.AbstractPlayersAvatar;
import com.scs.splitscreenfpsengine.entities.FloorOrCeiling;
import com.scs.splitscreenfpsengine.entities.VoxelTerrainEntity;
import com.scs.splitscreenfpsengine.hud.IHud;
import com.scs.splitscreenfpsengine.input.IInputDevice;
import com.scs.splitscreenfpsengine.modules.AbstractGameModule;

import mygame.util.Vector3Int;
import ssmith.lang.NumberFunctions;
import ssmith.util.RealtimeInterval;

public class ChaosGameModule extends AbstractGameModule {

	private static final float BLOCK_SIZE = .1f;
	public static final int MAP_SIZE = 30;
	private static final int MAP_SIZE_BLOCKS = (int)(MAP_SIZE/BLOCK_SIZE);

	private List<ChangingBlock> changingBlocks;
	public VoxelTerrainEntity vte;
	private RealtimeInterval addBlockInt = new RealtimeInterval(10);

	public int totalWizards, totalHumans, totalAI;

	public ChaosGameModule(MultiplayerVoxelWorldMain _game, int _numHumans, int _numAI) {
		super(_game);

		totalHumans = _numHumans;
		totalAI = _numAI;
		totalWizards = totalHumans + totalAI;

		changingBlocks = new LinkedList<>();
	}


	@Override
	public void setupLevel() {
		FloorOrCeiling floor = new FloorOrCeiling(game, this, 0, 0, 0, MAP_SIZE, 1f, MAP_SIZE, "Textures/blocks/lavarock.jpg");
		this.addEntity(floor);

		vte = new VoxelTerrainEntity(game, this, 0, 0, 0, new Vector3Int(MAP_SIZE_BLOCKS, (int)(20/BLOCK_SIZE), MAP_SIZE_BLOCKS), 50, BLOCK_SIZE, 1);
		this.addEntity(vte);

		//vte.addRectRange_Blocks(new Vector3Int(0, 0, 0), new Vector3Int(MAP_SIZE_BLOCKS, 1, MAP_SIZE_BLOCKS), LavaBlock.class);
		//vte.addRectRange_Blocks(BlockCodes.SAND, new Vector3Int(10, 1, 10), new Vector3Int(1, 1, 1));

		for (int i=0 ; i<5 ; i++) {
			Point p = this.getRandomBlockPos(1);
			this.createTree(vte, new Vector3f(p.x, 1, p.y));
		}

		for (int i=0 ; i<5 ; i++) {
			Point p = this.getRandomBlockPos(20);
			this.createWall_Horiz(vte, new Vector3f(p.x, 1, p.y));
			p = this.getRandomBlockPos(20);
			this.createWall_Vert(vte, new Vector3f(p.x, 1, p.y));
		}

		// Create AI Wiz
		for (int i=0 ; i<totalAI ; i++) {
			// todo
		}

		// Create AI Monsters
		GoldenDragon gd = new GoldenDragon(game, this, new Vector3f(1, 2, 1), null);
		this.addEntity(gd);
	}


	@Override
	public void update(float tpfSecs) {
		super.update(tpfSecs);

		if (!this.changingBlocks.isEmpty()) {
			if (addBlockInt.hitInterval()) {
				ChangingBlock block = this.changingBlocks.remove(0);
				vte.blocks.setBlock(block.pos, block.newClass);
			}
		}
	}


	@Override
	public Vector3f getPlayerStartPos(int id) {
		switch (this.totalWizards) {
		case 1:
			return new Vector3f(MAP_SIZE/2, 2, MAP_SIZE/2);
		case 2:
			switch (id) {
			case 0:
				return new Vector3f(1, 2, 1);
			case 1:
				return new Vector3f(MAP_SIZE-2, 2, MAP_SIZE-2);
			default:
				throw new RuntimeException("No start value for player " + id + " in a " + totalWizards + " player game");
			}
		case 3:
		case 4:
			switch (id) {
			case 0:
				return new Vector3f(1, 2, 1);
			case 1:
				return new Vector3f(MAP_SIZE-2, 2, MAP_SIZE-2);
			case 2:
				return new Vector3f(MAP_SIZE-2, 2, 1);
			case 3:
				return new Vector3f(1, 2, MAP_SIZE-2);
			default:
				throw new RuntimeException("No start value for player " + id + " in a " + totalWizards + " player game");
			}
		default:
			throw new RuntimeException("No start value for player " + id + " in a " + totalWizards + " player game");

		}
	}


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


	private void createWall_Horiz(VoxelTerrainEntity vte, Vector3f startPos) {
		int height = 4;
		for (int x=0 ; x<20 ; x++) {
			Vector3Int pos = new Vector3Int(startPos.x+x, startPos.y+height, startPos.z);
			ChangingBlock block = new ChangingBlock(StoneBlock.class, pos);
			addChangingBlock(block);
			height += NumberFunctions.rnd(-1,  1);
			if (height < 0) {
				height = 0;
			}
		}
	}


	private void createWall_Vert(VoxelTerrainEntity vte, Vector3f startPos) {
		int height = 4;
		for (int z=0 ; z<20 ; z++) {
			Vector3Int pos = new Vector3Int(startPos.x, startPos.y+height, startPos.z+z);
			ChangingBlock block = new ChangingBlock(StoneBlock.class, pos);
			addChangingBlock(block);
			height += NumberFunctions.rnd(-1,  1);
			if (height < 0) {
				height = 0;
			}
		}
	}


	private void addChangingBlock(ChangingBlock block) {
		vte.blocks.setBlock(block.pos, block.getClass());
		this.changingBlocks.add(block);
	}


	@Override
	protected AbstractPlayersAvatar getPlayersAvatar(MultiplayerVoxelWorldMain _game, AbstractGameModule _module, int _playerID,
			Camera _cam, IInputDevice _input, int _side) {
		return new WizardAvatar(_game, this, _playerID, _cam, _input, _side);
	}


	@Override
	protected IHud generateHUD(MultiplayerVoxelWorldMain _game, AbstractGameModule _module, AbstractPlayersAvatar _player, float xBL, float yBL, float w, float h, Camera _cam) {
		return new ChaosHUD(_game, _module, (WizardAvatar)_player, xBL, yBL, w, h, _cam);
	}


}
