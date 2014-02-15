package com.qzx.au.extras;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemEnderFlight extends Item {
	@SideOnly(Side.CLIENT)
	private Icon enabledIcon;

	public ItemEnderFlight(int id, String name, String readableName){
		super(id);
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		LanguageRegistry.addName(this, readableName);

		this.setMaxDamage(ItemEnderStar.MAX_DAMAGE);
		this.setFull3D();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister){
		this.itemIcon = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("item.au.", ""));
		this.enabledIcon = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("item.au.", "")+"-enabled");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(ItemStack itemstack, int pass){
		return (itemstack != null && this.isFlightEnabled(itemstack) ? this.enabledIcon : this.itemIcon);
	}
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconIndex(ItemStack itemstack){
		return this.getIcon(itemstack, 0);
	}

	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack itemstack){
		// add glint when enabled
		return this.isFlightEnabled(itemstack);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player){
		if(world.isRemote) return itemstack;

		boolean enabled = this.isFlightEnabled(itemstack);
		if(!enabled){
			// refill using eyes of ender in hotbar
			ItemEnderStar.consumeEyeOfEnder(itemstack, player);

			// stop functioning at 0% durability
			if(itemstack.getItemDamage() == ItemEnderStar.MAX_DAMAGE) return itemstack;
		}

		// toggle
		this.setFlightEnabled(itemstack, !enabled);

// TODO: server tick handler?
//	- if in hotbar and enabled
//		- allow player to use creative flight
//			- double jump to toggle flight
//		- give player hunger effect?
//		- give player weakness V effect
//		- give player slowness effect
//			- will this slow down creative flight? should higher levels be used?
//		- decrease durability every tick (10 minute duration)
//		- check damage and disable item and creative flight if burned out

// ENCHANCEMENT (ender star in hotbar)
//	- remove hunger effect
//	- remove a slowness level if more than one
//	- remove a couple weakness levels
//	- all damage is applied to ender star, until it burns out
//		- increases damage by 50%

		return itemstack;
	}

	private boolean isFlightEnabled(ItemStack itemstack){
		NBTTagCompound nbt = itemstack.getTagCompound();
		return (nbt == null ? false : nbt.getBoolean("enabled"));
	}
	private void setFlightEnabled(ItemStack itemstack, boolean enabled){
		NBTTagCompound nbt = itemstack.getTagCompound();
		if(nbt == null){
			nbt = new NBTTagCompound();
			itemstack.setTagCompound(nbt);
		}
		if(nbt != null) nbt.setBoolean("enabled", enabled);
	}
}
