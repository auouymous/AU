package com.qzx.au.extras;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEnderStar extends Item {
	public static int CHARGE_PER_EYE = 500;
	public static int MAX_DAMAGE = 24*ItemEnderStar.CHARGE_PER_EYE;

	public ItemEnderStar(int id, String name, String readableName){
		super(id);
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		LanguageRegistry.addName(this, readableName);

		this.setMaxDamage(ItemEnderStar.MAX_DAMAGE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister){
		this.itemIcon = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("item.au.", ""));
	}

	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack itemstack){
		// remove glint when burned out
		return itemstack.getItemDamage() < ItemEnderStar.MAX_DAMAGE;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player){
		if(world.isRemote) return itemstack;

		// refill using eyes of ender in hotbar
		ItemEnderStar.consumeEyeOfEnder(itemstack, player);

		return itemstack;
	}

	public static void consumeEyeOfEnder(ItemStack itemstack, EntityPlayer player){
		int damage = itemstack.getItemDamage();
		if(damage < ItemEnderStar.CHARGE_PER_EYE) return;

		for(int i = 0; i < 9; i++){
			ItemStack item = player.inventory.mainInventory[i];
			if(item != null && item.getItem() == Item.eyeOfEnder){
				item.stackSize--;
				if(item.stackSize == 0) player.inventory.mainInventory[i] = null;
				itemstack.setItemDamage(damage - ItemEnderStar.CHARGE_PER_EYE);
				return;
			}
		}
	}
}
