package com.qzx.au.extras;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemEnderXT extends Item {
	private static int CHARGE_PER_LEFT_CLICK = 8;
	private static float SWORD_DAMAGE = 7.0F;

	@SideOnly(Side.CLIENT)
	private Icon enabledIcon;

	public ItemEnderXT(int id, String name){
		super(id);
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);

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
		return (itemstack != null && this.isXTEnabled(itemstack) ? this.enabledIcon : this.itemIcon);
	}
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconIndex(ItemStack itemstack){
		return this.getIcon(itemstack, 0);
	}

	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack itemstack){
		// add glint when enabled
		return this.isXTEnabled(itemstack);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player){
		if(world.isRemote) return itemstack;

		boolean enabled = this.isXTEnabled(itemstack);
		if(!enabled){
			// refill using eyes of ender in hotbar
			ItemEnderStar.consumeEyeOfEnder(itemstack, player);

			// stop functioning at 0% durability
			if(itemstack.getItemDamage() == ItemEnderStar.MAX_DAMAGE) return itemstack;
		}

		// toggle
		this.setXTEnabled(itemstack, !enabled);

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

	///////////
	// SWORD //
	///////////

	@Override
	public boolean onLeftClickEntity(ItemStack itemstack, EntityPlayer player, Entity entity){
		// stop functioning at 0% durability
		if(!ItemEnderStar.canApplyDamage(itemstack, ItemEnderXT.CHARGE_PER_LEFT_CLICK)) return false;

		if(entity.attackEntityFrom(DamageSourceEnderXT.damageSource, ItemEnderXT.SWORD_DAMAGE))
			ItemEnderStar.applyDamage(itemstack, ItemEnderXT.CHARGE_PER_LEFT_CLICK);

		return false;
	}

//	@Override
//	public boolean hitEntity(ItemStack itemstack, EntityLivingBase entity1, EntityLivingBase entity2){
//		return false;
//	}

	@Override
	public float getDamageVsEntity(Entity entity, ItemStack itemstack){
		// stop functioning at 0% durability
		return (ItemEnderStar.canApplyDamage(itemstack, ItemEnderXT.CHARGE_PER_LEFT_CLICK) ? ItemEnderXT.SWORD_DAMAGE : 1.0F);
	}

	///////////
	// TOOLS // pickaxe, axe, shovel
	///////////

// TODO: pick, axe, shovel

	@Override
	public boolean canHarvestBlock(Block block){
		// harvest all blocks
		return true;
	}

	@Override
	public float getStrVsBlock(ItemStack itemstack, Block block){
		// stop functioning at 0% durability
		if(!ItemEnderStar.canApplyDamage(itemstack, ItemEnderXT.CHARGE_PER_LEFT_CLICK)) return 1.0F;

		return 4.0F;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, World world, int id, int x, int y, int z, EntityLivingBase entity){
		float hardness = Block.blocksList[id].getBlockHardness(world, x, y, z) / 3.0F; // ores and harder apply max damage

		ItemEnderStar.applyDamage(itemstack, Math.min(ItemEnderXT.CHARGE_PER_LEFT_CLICK, Math.round((float)ItemEnderXT.CHARGE_PER_LEFT_CLICK * hardness)));

		return true;
	}

	//////////

	private boolean isXTEnabled(ItemStack itemstack){
		NBTTagCompound nbt = itemstack.getTagCompound();
		return (nbt == null ? false : nbt.getBoolean("enabled"));
	}
	private void setXTEnabled(ItemStack itemstack, boolean enabled){
		NBTTagCompound nbt = itemstack.getTagCompound();
		if(nbt == null){
			nbt = new NBTTagCompound();
			itemstack.setTagCompound(nbt);
		}
		if(nbt != null) nbt.setBoolean("enabled", enabled);
	}
}
