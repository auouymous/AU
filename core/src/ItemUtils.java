package com.qzx.au.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemUtils {
	public static String getDisplayName(ItemStack itemstack){
		if(itemstack == null) return null;
		String s = null;
		try {
			s = itemstack.getDisplayName();
		} catch(Exception e){
			// this can happen
		}
		try {
			if(s == null || s.equals(""))
				#ifdef MC147
				s = itemstack.getItem().getItemNameIS(itemstack);
				#else
				s = itemstack.getItem().getUnlocalizedName(itemstack);
				#endif
		} catch(Exception e){
			s = "<Unknown>";
		}
		return s;
	}

	public static void dropItemAsEntity(World world, int x, int y, int z, ItemStack itemstack){
		if(itemstack != null && itemstack.stackSize > 0){
			EntityItem entity = new EntityItem(world, (float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F, new ItemStack(itemstack.getItem(), itemstack.stackSize, itemstack.getItemDamage()));

			if(itemstack.hasTagCompound())
				entity.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());

			entity.delayBeforeCanPickup = 10;
			entity.motionX = entity.motionY = entity.motionZ = 0.0F;
			world.spawnEntityInWorld(entity);
			itemstack.stackSize = 0;
		}
	}

	public static ItemBlock getItemBlock(Block block){
		if(block == null) return null;
		try {
			return (ItemBlock)(new ItemStack(block, 1, 0)).getItem();
		} catch(Exception e){
			return null;
		}
	}
	public static ItemBlock getItemBlock(Block block, int metadata){
		if(block == null) return null;
		try {
			return (ItemBlock)(new ItemStack(block, 1, metadata)).getItem();
		} catch(Exception e){
			return null;
		}
	}

	#ifndef NO_IDS
	public static ItemStack getItemStack(int id, int quantity, int metadata){
		if(id <= 0) return null;
		try {
			return new ItemStack(id, quantity, metadata);
		} catch(Exception e){
			return null;
		}
	}
	#endif
	public static ItemStack getItemStack(Block block, int quantity, int metadata){
		if(block == null) return null;
		try {
			return new ItemStack(block, quantity, metadata);
		} catch(Exception e){
			return null;
		}
	}
	public static ItemStack getItemStack(Item item, int quantity, int metadata){
		if(item == null) return null;
		try {
			return new ItemStack(item, quantity, metadata);
		} catch(Exception e){
			return null;
		}
	}
}
