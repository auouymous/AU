package com.qzx.au.extras;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemBlock;

import java.util.List;

public class BlockColored extends Block {
	@SideOnly(Side.CLIENT)
	private MC_ICON[] blockIcons;

	public BlockColored(int id, String name, Class<? extends ItemBlock> itemblockclass, Material material){
		super(id, material);
		this.setUnlocalizedName(name);
		if(itemblockclass != null)
			GameRegistry.registerBlock(this, itemblockclass, name);
		else
			GameRegistry.registerBlock(this, name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(MC_ICON_REGISTER iconRegister){
		this.blockIcons = new MC_ICON[16];
		for(int c = 0; c < 16; c++)
			this.blockIcons[c] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("tile.au.", "")+c);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public MC_ICON getIcon(int side, int color){
		return this.blockIcons[color];
	}

	@Override
	public int damageDropped(int color){
		return color;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int unknown, CreativeTabs tab, List subItems){
		for(int c = 0; c < 16; c++)
			subItems.add(new ItemStack(this, 1, c));
	}
}
