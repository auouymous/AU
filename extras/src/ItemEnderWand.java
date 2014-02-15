package com.qzx.au.extras;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemEnderWand extends Item {
	@SideOnly(Side.CLIENT)
	private Icon[] itemIcons;
	private Icon[] ghostIcons;

	private static int MAX_MODE = 2;

	public ItemEnderWand(int id, String name, String readableName){
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
		this.itemIcons = new Icon[ItemEnderWand.MAX_MODE+1];
		this.ghostIcons = new Icon[ItemEnderWand.MAX_MODE];
		this.itemIcons[0] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("item.au.", "")+"0");
		this.itemIcons[1] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("item.au.", "")+"1");
		this.itemIcons[2] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("item.au.", "")+"2");
		this.ghostIcons[0] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("item.au.", "")+"1-ghost");
		this.ghostIcons[1] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("item.au.", "")+"2-ghost");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(ItemStack itemstack, int pass){
		if(itemstack != null){
			int mode = this.getWandMode(itemstack);
			int upgrades = this.getWandUpgrades(itemstack);
			if(mode == 1) return ((upgrades&1) == 1 ? this.itemIcons[1] : this.ghostIcons[0]);
			if(mode == 2) return ((upgrades&2) == 2 ? this.itemIcons[2] : this.ghostIcons[1]);
		}
		return this.itemIcons[0];
	}
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconIndex(ItemStack itemstack){
		return this.getIcon(itemstack, 0);
	}

	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack itemstack){
		// remove glint when burned out
		return itemstack.getItemDamage() < ItemEnderStar.MAX_DAMAGE;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ){
		if(world.isRemote) return true;

		// stop functioning at 0% durability
		if(itemstack.getItemDamage() == ItemEnderStar.MAX_DAMAGE) return false;

		int mode = this.getWandMode(itemstack);
		int upgrades = this.getWandUpgrades(itemstack);

// TODO: get target block

// place
//	- will place a max of 9 blocks, upto 4 on each side of center block

// replace
//	- if shift right click, select block and store in NBT, this block must be present in inventory
//	- will replace a max of 9 blocks, center plus 8 blocks around center
//		- blocks must be same as center
//	- dont replace unbreakable blocks or blocks with TEs
//	- does not silk touch blocks, or should it?

// cube
//	- right clicking an ender cube in this mode will insta break the cube and place in inventory
//		- add message about this in tickhandler
//		- only if playere is allowed to use ender cube
//	- ender cubes must be in hotbar
//	- place ender cube
//		- if sneaking, place cube horizontally, 1-16 blocks away and below player's feet
//			- use pitch angle for range, down is 1 block, flat/above is 16
//		- if looking down, place block 1-16 below floor(player feet)-2
//			- flat is 1
//		- if looking up, place block 2-16 above floor(player feet)
//			- flat is 2
//	- display messages and block outline in TickHandler

// TODO: decrease durability per block placed or replaced
//	- hardness of replaced blocks amplifies damage
//	- builder: 12000/9 = number of blocks placed
//	- cube: 16-256 damage per cube placed

// TODO: spawn portal particles around center block

// ENCHANCEMENT (ender star in hotbar)
//	- increase block placing from 9 to 17, upto 8 on each side of center block
//	- increase block replacing from 9 to 25, 2 rows around center block
//  - all damage is applied to ender star, until it burns out
//		- increases damage by 50%

		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player){
		if(world.isRemote) return itemstack;

		int mode = getWandMode(itemstack);
		int upgrades = getWandUpgrades(itemstack);
		int damage = itemstack.getItemDamage();

		// add block replacing upgrade
		if(mode == 1 && (upgrades&1) == 0 && damage + ItemEnderStar.CHARGE_PER_EYE <= ItemEnderStar.MAX_DAMAGE){
			// find another wand in hotbar without upgrades and merge with this wand
			for(int i = 0; i < 9; i++){
				ItemStack item = player.inventory.mainInventory[i];
				if(item != null && item.getItem() == AUExtras.itemEnderWand && item != itemstack){
					if(this.getWandUpgrades(item) == 0){
						// current itemstack must be damaged or ender wand itemstack won't visually update its quantity
						player.inventory.mainInventory[i] = null;
						this.setWandUpgrades(itemstack, upgrades|1);
						itemstack.setItemDamage(damage + ItemEnderStar.CHARGE_PER_EYE);
						return itemstack;
					}
					break;
				}
			}
		}

		// add ender cube upgrade
		if(mode == 2 && (upgrades&2) == 0 && damage + ItemEnderStar.CHARGE_PER_EYE <= ItemEnderStar.MAX_DAMAGE){
			// find an ender cube in hotbar and merge with this wand
			for(int i = 0; i < 9; i++){
				ItemStack item = player.inventory.mainInventory[i];
				if(item != null && item.getItem() instanceof ItemBlock && item.getItem().itemID == AUExtras.blockEnderCube.blockID){
					// current itemstack must be damaged or ender cube itemstack won't visually update its quantity
					item.stackSize--;
					if(item.stackSize == 0) player.inventory.mainInventory[i] = null;
					this.setWandUpgrades(itemstack, upgrades|2);
					itemstack.setItemDamage(damage + ItemEnderStar.CHARGE_PER_EYE);
					return itemstack;
				}
			}
		}

		// cycle modes
		mode++;
		if(mode > ItemEnderWand.MAX_MODE) mode = 0;
		this.setWandMode(itemstack, mode);

		// refill using eyes of ender in hotbar
		ItemEnderStar.consumeEyeOfEnder(itemstack, player);

		return itemstack;
	}

	private int getWandMode(ItemStack itemstack){
		NBTTagCompound nbt = itemstack.getTagCompound();
		return (nbt == null ? 0 : nbt.getInteger("mode"));
	}
	private void setWandMode(ItemStack itemstack, int mode){
		NBTTagCompound nbt = itemstack.getTagCompound();
		if(nbt == null){
			nbt = new NBTTagCompound();
			itemstack.setTagCompound(nbt);
		}
		if(nbt != null) nbt.setInteger("mode", mode);
	}

	private int getWandUpgrades(ItemStack itemstack){
		NBTTagCompound nbt = itemstack.getTagCompound();
		return (nbt == null ? 0 : nbt.getInteger("upgrades"));
	}
	private void setWandUpgrades(ItemStack itemstack, int upgrades){
		NBTTagCompound nbt = itemstack.getTagCompound();
		if(nbt == null){
			nbt = new NBTTagCompound();
			itemstack.setTagCompound(nbt);
		}
		if(nbt != null) nbt.setInteger("upgrades", upgrades);
	}
}
