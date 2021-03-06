package com.qzx.au.core;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

IMPORT_FORGE_DIRECTION

public class BlockCoord {
	public static final float ADD_1_8  = 0.125F;
	public static final float ADD_1_16 = 0.0625F;
	public static final float ADD_1_32 = 0.03125F;
	public static final float ADD_1_64 = 0.015625F;

	public static final float SUB_1_8  = 1.0F - BlockCoord.ADD_1_8;
	public static final float SUB_1_16 = 1.0F - BlockCoord.ADD_1_16;
	public static final float SUB_1_32 = 1.0F - BlockCoord.ADD_1_32;
	public static final float SUB_1_64 = 1.0F - BlockCoord.ADD_1_64;

	public IBlockAccess access;
	public int x, y, z;

	/*
		coordinates are the bottom north west corner of block

		0	down	-y
		1	up		+y
		2	north	-z
		3	south	+z
		4	west	-x
		5	east	+x
	*/

	public BlockCoord(IBlockAccess access, int x, int y, int z){
		this.access = access;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public BlockCoord(BlockCoord coord){
		this.access = coord.access;
		this.x = coord.x;
		this.y = coord.y;
		this.z = coord.z;
	}

	public int getBlockMetadata(){
		return this.access.getBlockMetadata(this.x, this.y, this.z);
	}

	public Block getBlock(){
		#ifdef NO_IDS
		return this.access.getBlock(this.x, this.y, this.z);
		#else
		return Block.blocksList[this.access.getBlockId(this.x, this.y, this.z)];
		#endif
	}

	public static Block getBlock(IBlockAccess access, int x, int y, int z){
		#ifdef NO_IDS
		return access.getBlock(x, y, z);
		#else
		return Block.blocksList[access.getBlockId(x, y, z)];
		#endif
	}

	public TileEntity getTileEntity(){
		#ifdef NO_IDS
		return this.access.getTileEntity(this.x, this.y, this.z);
		#else
		return this.access.getBlockTileEntity(this.x, this.y, this.z);
		#endif
	}

	public static TileEntity getTileEntity(IBlockAccess access, int x, int y, int z){
		#ifdef NO_IDS
		return access.getTileEntity(x, y, z);
		#else
		return access.getBlockTileEntity(x, y, z);
		#endif
	}

	//////////

	public static final int UP		= 0;
	public static final int DOWN	= 1;
	public static final int LEFT	= 2;
	public static final int RIGHT	= 3;
	private static final int[][] directionMap = {
		{2, 3, 4, 5},
		{2, 3, 4, 5},
		{1, 0, 5, 4},
		{1, 0, 4, 5},
		{1, 0, 2, 3},
		{1, 0, 3, 2}
	};
	public static int getSideAtDirection(int side, int bc_direction){
		return BlockCoord.directionMap[side][bc_direction];
	}

	public BlockCoord translateToSide(int side){
		ForgeDirection d = (ForgeDirection)ForgeDirection.values()[side];
		this.x += d.offsetX;
		this.y += d.offsetY;
		this.z += d.offsetZ;
		return this;
	}
	public BlockCoord translateToSideAtDirection(int face, int bc_direction){
		return this.translateToSide(BlockCoord.getSideAtDirection(face, bc_direction));
	}

	public BlockCoord translateToDiagonal(int side1, int side2){
		ForgeDirection d1 = (ForgeDirection)ForgeDirection.values()[side1];
		ForgeDirection d2 = (ForgeDirection)ForgeDirection.values()[side2];
		this.x += d1.offsetX + d2.offsetX;
		this.y += d1.offsetY + d2.offsetY;
		this.z += d1.offsetZ + d2.offsetZ;
		return this;
	}
	public BlockCoord translateToDiagonalAtDirection(int face, int bc_direction1, int bc_direction2){
		return this.translateToDiagonal(BlockCoord.getSideAtDirection(face, bc_direction1), BlockCoord.getSideAtDirection(face, bc_direction2));
	}
}
