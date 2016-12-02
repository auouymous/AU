package com.qzx.au.core;

import net.minecraft.world.World;

//IMPORT_FORGE_DIRECTION

public interface IRotatableBlock {
	public abstract void rotateBlock(World world, int x, int y, int z);
//	public abstract void rotateBlock(World world, int x, int y, int z, ForgeDirection direction);
}
