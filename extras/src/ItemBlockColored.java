package com.qzx.au.extras;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import com.qzx.au.core.Color;

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
