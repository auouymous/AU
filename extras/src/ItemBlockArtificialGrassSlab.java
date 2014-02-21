package com.qzx.au.extras;

import net.minecraft.item.ItemStack;

public class ItemBlockArtificialGrassSlab extends ItemBlockColoredSlab {
	public ItemBlockArtificialGrassSlab(int id){
		super(id);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack){
		return this.getUnlocalizedName() + "." + itemstack.getItemDamage();
	}
}
