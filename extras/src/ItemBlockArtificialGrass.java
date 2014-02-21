package com.qzx.au.extras;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockArtificialGrass extends ItemBlock {
	public ItemBlockArtificialGrass(int id){
		super(id);
		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int shade){
		return shade;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack){
		return this.getUnlocalizedName() + "." + itemstack.getItemDamage();
	}
}
