package com.qzx.au.extras;

import net.minecraft.item.ItemStack;

public class ItemBlockArtificialGrassSlab extends ItemBlockColored {
	public ItemBlockArtificialGrassSlab(int id){
		super(id);
		this.setUnlocalizedName("tile.au.artificialGrassSlab");
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack){
		return this.getUnlocalizedName() + "." + itemstack.getItemDamage();
	}
}
