package com.qzx.au.extras;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import java.util.List;

import com.qzx.au.core.Color;

public class ItemFlowerDye extends ItemDye {
	@SideOnly(Side.CLIENT)
	private Icon[] itemIcons;

	public ItemFlowerDye(int id, String name, String readableName){
		super(id);
		this.setUnlocalizedName(name);

		LanguageRegistry.addName(new ItemStack(this, 1, 0), Color.readableColors[0]+readableName);
		LanguageRegistry.addName(new ItemStack(this, 1, 2), Color.readableColors[2]+readableName);
		LanguageRegistry.addName(new ItemStack(this, 1, 3), Color.readableColors[3]+readableName);
		LanguageRegistry.addName(new ItemStack(this, 1, 4), Color.readableColors[4]+readableName);
		LanguageRegistry.addName(new ItemStack(this, 1, 15), Color.readableColors[15]+readableName);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister){
		this.itemIcons = new Icon[16];
		this.itemIcons[0] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("item.au.", "")+"0");
		this.itemIcons[2] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("item.au.", "")+"2");
		this.itemIcons[3] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("item.au.", "")+"3");
		this.itemIcons[4] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("item.au.", "")+"4");
		this.itemIcons[15] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("item.au.", "")+"15");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int color){
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
