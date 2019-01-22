package com.scs.samescreenchaos;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.scs.multiplayervoxelworld.MultiplayerVoxelWorldMain;
import com.scs.multiplayervoxelworld.entities.AbstractPlayersAvatar;
import com.scs.multiplayervoxelworld.entities.FloorOrCeiling;
import com.scs.multiplayervoxelworld.entities.VoxelTerrainEntity;
import com.scs.multiplayervoxelworld.hud.IHud;
import com.scs.multiplayervoxelworld.input.IInputDevice;
import com.scs.multiplayervoxelworld.modules.AbstractGameModule;
import com.scs.samescreenchaos.blocks.ChangingBlock;
import com.scs.samescreenchaos.blocks.LeavesBlock;
import com.scs.samescreenchaos.blocks.WoodBlock;
import com.scs.samescreenchaos.entities.WizardAvatar;
import com.scs.samescreenchaos.hud.ChaosHUD;

import mygame.util.Vector3Int;
import ssmith.lang.NumberFunctions;
import ssmith.util.RealtimeInterval;

public class ChaosGameModule extends AbstractGameModule {

	private static final float BLOCK_SIZE = .1f;
	private static final int MAP_SIZE = 20;
	private static final int MAP_SIZE_BLOCKS = (int)(MAP_SIZE/BLOCK_SIZE);

	private List<ChangingBlock> changingBlocks;
	public VoxelTerrainEntity vte;
	private RealtimeInterval addBlockInt = new RealtimeInterval(10);

	public ChaosGameModule(MultiplayerVoxelWorldMain _game) {
		super(_game);

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

		for (int i=0 ; i<2 ; i++) {
			Point p = this.getRandomBlockPos();
			this.createTree(vte, new Vector3f(p.x, 1, p.y));
		}

	}


	@Override
	public void update(float tpfSecs) {
		super.update(tpfSecs);

		if (!this.changingBlocks.isEmpty()) {
			if (addBlockInt.hitInterval()) {
				ChangingBlock block = this.changingBlocks.remove(0);
				vte.blocks.setBlock(block.pos, block.newClass);
				//Settings.p("adding block");
			}
		}
	}


	@Override
	public Vector3f getPlayerStartPos(int id) {
		return new Vector3f(MAP_SIZE/2-3, 2, MAP_SIZE/2-3); // todo
	}


	private Point getRandomBlockPos() {
		int x = NumberFunctions.rnd(1, MAP_SIZE_BLOCKS-2);
		int z = NumberFunctions.rnd(1, MAP_SIZE_BLOCKS-2);
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
					if (NumberFunctions.rnd(1, 3) == 1) {
						Vector3Int pos = new Vector3Int(x, treePos.y+y,z);
						ChangingBlock block = new ChangingBlock(LeavesBlock.class, pos);
						addChangingBlock(block);
						//vte.blocks.setBlock(new Vector3Int(x, treePos.y+y,z), LeavesBlock.class);
					}
				}
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
		return new ChaosHUD(_game, _module, _player, xBL, yBL, w, h, _cam);
	}



}
