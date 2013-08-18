package com.qzx.au.util;

// no support for 147
#ifndef MC147

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockColored extends ItemBlock {
	public ItemBlockColored(int id){
		super(id);
		this.setHasSubtypes(true);
		// extend and set unlocalized name
	}

	@Override
	public int getMetadata(int color){
		return color;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack){
		return this.getUnlocalizedName() + "." + Color.colors[itemstack.getItemDamage()];
	}
}

#endif
// no support for 147
