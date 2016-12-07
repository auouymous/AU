package com.qzx.au.extras;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

import com.qzx.au.core.Color;

public class ItemFlowerDye extends ItemDye {
	@SideOnly(Side.CLIENT)
	private MC_ICON[] itemIcons;

	public ItemFlowerDye(int id, String name){
		super(id);
		this.setUnlocalizedName(name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(MC_ICON_REGISTER iconRegister){
		this.itemIcons = new MC_ICON[16];
		this.itemIcons[0] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("item.au.", "")+"0");
		this.itemIcons[2] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("item.au.", "")+"2");
		this.itemIcons[3] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("item.au.", "")+"3");
		this.itemIcons[4] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("item.au.", "")+"4");
		this.itemIcons[15] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("item.au.", "")+"15");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public MC_ICON getIconFromDamage(int color){
		return this.itemIcons[color];
	}

	@Override
	public int getMetadata(int color){
		return color;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack){
		return this.getUnlocalizedName() + "." + Color.colors[itemstack.getItemDamage()];
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ){
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs creativeTabs, List list){
		list.add(new ItemStack(id, 1, 0));
		list.add(new ItemStack(id, 1, 2));
		list.add(new ItemStack(id, 1, 3));
		list.add(new ItemStack(id, 1, 4));
		list.add(new ItemStack(id, 1, 15));
	}
}
