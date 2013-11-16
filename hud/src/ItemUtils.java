package com.qzx.au.hud;

import net.minecraft.item.ItemStack;

public class ItemUtils {
	public static String getDisplayName(ItemStack itemstack){
		if(itemstack == null) return null;
		try {
			return itemstack.getDisplayName();
		} catch(Exception e){
			// this can happen
			return null;
		}
	}
}
