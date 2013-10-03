package com.qzx.au.util;

// no support for 147
#ifndef MC147

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

import net.minecraftforge.common.ForgeDirection;

public class BlockCoord {
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

	public int getBlockID(){
		return this.access.getBlockId(this.x, this.y, this.z);
	}

	public int getBlockMetadata(){
		return this.access.getBlockMetadata(this.x, this.y, this.z);
	}

	public TileEntity getBlockTileEntity(){
		return this.access.getBlockTileEntity(this.x, this.y, this.z);
	}

	public Block getBlock(){
		return Block.blocksList[this.getBlockID()];
	}

	public static Block getBlock(IBlockAccess access, int x, int y, int z){
		return Block.blocksList[access.getBlockId(x, y, z)];
	}

	public static int UP	= 0;
	public static int DOWN	= 1;
	public static int LEFT	= 2;
	public static int RIGHT	= 3;
	private static int[][] directionMap = {
		{2, 3, 4, 5},
		{2, 3, 4, 5},
		{1, 0, 5, 4},
		{1, 0, 4, 5},
		{1, 0, 2, 3},
		{1, 0, 3, 2}
	};
	public static int getSideAtDirection(int side, int direction){
		return BlockCoord.directionMap[side][direction];
	}

	public BlockCoord translateToSide(int side){
		ForgeDirection d = (ForgeDirection)ForgeDirection.values()[side];
		this.x += d.offsetX;
		this.y += d.offsetY;
		this.z += d.offsetZ;
		return this;
	}
	public BlockCoord translateToSideAtDirection(int face, int direction){
		return this.translateToSide(BlockCoord.getSideAtDirection(face, direction));
	}

	public BlockCoord translateToDiagonal(int side1, int side2){
		ForgeDirection d1 = (ForgeDirection)ForgeDirection.values()[side1];
		ForgeDirection d2 = (ForgeDirection)ForgeDirection.values()[side2];
		this.x += d1.offsetX + d2.offsetX;
		this.y += d1.offsetY + d2.offsetY;
		this.z += d1.offsetZ + d2.offsetZ;
		return this;
	}
	public BlockCoord translateToDiagonalAtDirection(int face, int direction1, int direction2){
		return this.translateToDiagonal(BlockCoord.getSideAtDirection(face, direction1), BlockCoord.getSideAtDirection(face, direction2));
	}
}

#endif
// no support for 147
