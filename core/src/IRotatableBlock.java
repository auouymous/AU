package com.qzx.au.core;

import net.minecraft.world.World;

import net.minecraftforge.common.ForgeDirection;

public interface IRotatableBlock {
	public abstract void rotateBlock(World world, int x, int y, int z);
//	public abstract void rotateBlock(World world, int x, int y, int z, ForgeDirection direction);
}
