package com.qzx.au.core;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

public interface IConnectedTexture {
	// coordinates are the block at each side, not the block being rendered
	public boolean shouldSideBeRendered(IBlockAccess access, int x, int y, int z, int side);

	public boolean canConnectSideTextures(Block block, int metadata, int side, BlockCoord neighbor);
	public boolean canConnectCornerTextures(Block block, int metadata, int side, BlockCoord diagonal);

	public MC_ICON getBlockTexture(IBlockAccess access, int x, int y, int z, int side);

	public static int ctm[] = {
		85, 69, 21, 84, 81, 0, 1, 5, 4, 20, 16, 80,
		64, 65, 17, 68, 133, 22, 88, 97, 161, 33, 129, 134,
		6, 132, 26, 24, 18, 104, 72, 96, 170, 42, 168, 162,
		138, 10, 40, 160, 130, 34, 136, 2, 8, 32, 128
	};
	public static int ctm_icons = 171;
	public static int ctm_default = 85;
	public static int ctm_borderless = 0;
}
