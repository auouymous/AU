package com.qzx.au.extras;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockFlowerSeed extends ItemBlock {
	public ItemBlockFlowerSeed(int id){
		super(id);
	}

	@Override
	public MC_ICON getIconFromDamage(int stage){
		return ((BlockFlowerSeed)THIS_MOD.blockFlowerSeed).getItemIcon(stage);
	}
}
