package com.qzx.au.core;

import net.minecraft.world.World;

public interface ISidedBlock {
	public abstract void toggleSide(World world, int x, int y, int z, byte side);
}
