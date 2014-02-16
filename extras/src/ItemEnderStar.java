package com.qzx.au.extras;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
IMPORT_ITEMS
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEnderStar extends Item {
	public static int CHARGE_PER_EYE = 500;
	public static int MAX_DAMAGE = 24*ItemEnderStar.CHARGE_PER_EYE;

	public ItemEnderStar(int id, String name){
		super(id);
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);

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

// TODO: only refill if shift-right clicking

// TODO: right click could teleport player upto 32 blocks in direction player is looking
//	- decrease durability by 8+ per block travelled
//	- stops at first obstruction
//	- teleportion is instant, not delayed like ender pearls
//		- but destination can place player far above the ground

		return itemstack;
	}

	public static void consumeEyeOfEnder(ItemStack itemstack, EntityPlayer player){
		int damage = itemstack.getItemDamage();
		if(damage < ItemEnderStar.CHARGE_PER_EYE) return;

		for(int i = 0; i < 9; i++){
			ItemStack item = player.inventory.mainInventory[i];
			if(item != null && item.getItem() == MC_ITEM.eyeOfEnder){
				item.stackSize--;
				if(item.stackSize == 0) player.inventory.mainInventory[i] = null;
				itemstack.setItemDamage(damage - ItemEnderStar.CHARGE_PER_EYE);
				return;
			}
		}
	}

	// apply damage to ender star
	public static boolean canApplyDamage(ItemStack itemstack, int damage){
		return (itemstack.getItemDamage() + damage <= ItemEnderStar.MAX_DAMAGE);
	}
	public static void applyDamage(ItemStack itemstack, int damage){
		itemstack.setItemDamage(itemstack.getItemDamage() + damage);
	}

	// apply damage to ender star or item
	public static boolean canApplyDamage(ItemStack itemstack, int damage, EntityPlayer player){
		for(int i = 0; i < 9; i++){
			ItemStack item = player.inventory.mainInventory[i];
			if(item != null && item.getItem() == AUExtras.itemEnderStar)
				if(item.getItemDamage() + damage <= ItemEnderStar.MAX_DAMAGE) return true;
		}
		return (itemstack.getItemDamage() + damage <= ItemEnderStar.MAX_DAMAGE);
	}
	public static void applyDamage(ItemStack itemstack, int damage, EntityPlayer player){
		for(int i = 0; i < 9; i++){
			ItemStack item = player.inventory.mainInventory[i];
			if(item != null && item.getItem() == AUExtras.itemEnderStar){
				item.setItemDamage(item.getItemDamage() + damage);
				return;
			}
		}
		itemstack.setItemDamage(itemstack.getItemDamage() + damage);
	}
}
