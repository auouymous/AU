package com.qzx.au.extras;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;

public class ItemFoodGeneric extends ItemFood {
	public ItemFoodGeneric(int id, int maxStackSize, String name, String readableName, int healAmount, float saturationModifier, boolean isWolfsFavoriteMeat){
		super(id, healAmount, saturationModifier, isWolfsFavoriteMeat);
		this.setMaxStackSize(maxStackSize);
		this.setUnlocalizedName(name);
		LanguageRegistry.addName(this, readableName);
		this.setCreativeTab(CreativeTabs.tabFood);
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister){
		this.itemIcon = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("item.au.", ""));
	}
}
