package com.qzx.au.extras;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

class BlockStairsColored extends BlockStairs {
	private final Block _modelBlock;
	private final int _modelBlockMetadata;

	public BlockStairsColored(int id, String name, Block block, int blockMeta){
		super(id, block, blockMeta);
		this._modelBlock = block;
		this._modelBlockMetadata = blockMeta;
		this.setUnlocalizedName(name);
		GameRegistry.registerBlock(this, name);

		// hack to fix lighting glitch
		this.setLightOpacity(0); // stairs allow light sources to pass through them
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int shade){
		// artificial grass
		return _modelBlock.getRenderColor(this._modelBlockMetadata);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess access, int x, int y, int z){
		// artificial grass
		return _modelBlock.getRenderColor(this._modelBlockMetadata);
	}
}
