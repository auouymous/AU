package com.qzx.au.extras;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockVine;
import net.minecraft.block.BlockWeb;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;

public class ItemDiamondShears extends ItemShears {
	private final EnumToolMaterial toolMaterial = EnumToolMaterial.EMERALD;

	public ItemDiamondShears(int id, String name){
		super(id);
		this.setUnlocalizedName(name);

		this.setMaxDamage(this.toolMaterial.getMaxUses());
		this.setFull3D();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(MC_ICON_REGISTER iconRegister){
		this.itemIcon = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("item.au.", ""));
	}

	@Override
	public float getStrVsBlock(ItemStack itemstack, Block block){
		// cuts vines 15 times faster than iron shears
		if(block instanceof BlockWeb || block instanceof BlockLeaves || block instanceof BlockVine) return 15.0F; // same speed as iron shears
		if(block.blockID == Block.cloth.blockID) return 10.0F; // cuts wool at twice the speed of iron shears
		return 1.0F;
	}

	@Override
	public int getItemEnchantability(){
		return this.toolMaterial.getEnchantability();
	}

	@Override
	public boolean getIsRepairable(ItemStack shears_stack, ItemStack diamond_stack){
		return (this.toolMaterial.getToolCraftingMaterial() == diamond_stack.itemID);
	}
}
