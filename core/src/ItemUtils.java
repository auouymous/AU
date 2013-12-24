package com.qzx.au.core;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.Random;

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

	public static void dropItemAsEntity(World world, int x, int y, int z, ItemStack item, Random random){
		if(item != null && item.stackSize > 0){
			EntityItem entity = new EntityItem(world, (float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F, new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));

			if(item.hasTagCompound())
				entity.getEntityItem().setTagCompound((NBTTagCompound)item.getTagCompound().copy());

			entity.delayBeforeCanPickup = 10;
			entity.motionX = entity.motionY = entity.motionZ = 0.0F;
			world.spawnEntityInWorld(entity);
			item.stackSize = 0;
		}
	}
}