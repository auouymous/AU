package com.qzx.au.extras;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.MinecraftForge;

class BlockStairsColored extends BlockStairs {
	public BlockStairsColored(int id, String name, Block block, int blockMeta){
		super(id, block, blockMeta);
		this.setUnlocalizedName(name);
		GameRegistry.registerBlock(this, name);

		// hack to fix lighting glitch
		this.setLightOpacity(0); // stairs allow light sources to pass through them

		MinecraftForge.setBlockHarvestLevel(this, "pickaxe", 0); // wooden pickaxe

		GameRegistry.addRecipe(new ItemStack(this, 4), "b  ", "bb ", "bbb", 'b', new ItemStack(block, 1, blockMeta));
	}
}
