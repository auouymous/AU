package com.qzx.au.extras;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemEnderMagnet extends Item {
	@SideOnly(Side.CLIENT)
	private Icon enabledIcon;

	public ItemEnderMagnet(int id, String name, String readableName){
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
		this.enabledIcon = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("item.au.", "")+"-enabled");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(ItemStack itemstack, int pass){
		return (itemstack != null && this.isMagnetEnabled(itemstack) ? this.enabledIcon : this.itemIcon);
	}
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconIndex(ItemStack itemstack){
		return this.getIcon(itemstack, 0);
	}

	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack itemstack){
		// add glint when enabled
		return this.isMagnetEnabled(itemstack);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player){
		if(world.isRemote) return itemstack;

		boolean enabled = this.isMagnetEnabled(itemstack);
		if(!enabled){
			// refill using eyes of ender in hotbar
			ItemEnderStar.consumeEyeOfEnder(itemstack, player);

			// stop functioning at 0% durability
			if(itemstack.getItemDamage() == ItemEnderStar.MAX_DAMAGE) return itemstack;
		}

		// toggle
		this.setMagnetEnabled(itemstack, !enabled);

// TODO: client tick handler
//	- if in hotbar and enabled
//		- scan 8 block radius around player for items
//		- if item will fit in player's inventory
//			- send server packet to teleport items to player's inventory
//				- verify range on server to avoid exploits
//				- check damage and disable if burned out
//				- check damage and item range before teleporting item
//			- send client packets to spawn a few portal particles where item was
//				- server option to disable particles
//			- decrease durability per item per block
//				- a stack of items 8 blocks away would cause 512 damage
//				- can teleport 1500-12000 items per full charge
//		- client should scan once per second and only request 1 stack per scan

// ENCHANCEMENT (ender star in hotbar)
//	- increase scan radius to 12-16 blocks
//	- all damage is applied to ender star, until it burns out
//		- increases damage by 50%

		return itemstack;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity){
		// stop functioning at 0% durability
		if(itemstack.getItemDamage() == ItemEnderStar.MAX_DAMAGE) return false;

// TODO: teleport entity upto 16 blocks in direction player is looking
//	- stops at the first obstruction
//	- decrease durability by 16 per block teleported

		return false;
	}

	private boolean isMagnetEnabled(ItemStack itemstack){
		NBTTagCompound nbt = itemstack.getTagCompound();
		return (nbt == null ? false : nbt.getBoolean("enabled"));
	}
	private void setMagnetEnabled(ItemStack itemstack, boolean enabled){
		NBTTagCompound nbt = itemstack.getTagCompound();
		if(nbt == null){
			nbt = new NBTTagCompound();
			itemstack.setTagCompound(nbt);
		}
		if(nbt != null) nbt.setBoolean("enabled", enabled);
	}
}
