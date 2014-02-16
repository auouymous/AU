package com.qzx.au.extras;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemBlockFlowerSeed extends ItemBlock {
	public ItemBlockFlowerSeed(int id){
		super(id);
		this.setUnlocalizedName("au.colorFlowerSeed");
	}

	@Override
	public Icon getIconFromDamage(int stage){
		return ((BlockFlowerSeed)THIS_MOD.blockFlowerSeed).getItemIcon(stage);
	}
}
