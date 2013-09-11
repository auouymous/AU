package com.qzx.au.extras;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.Icon;

import net.minecraftforge.common.MinecraftForge;

import java.util.List;

import com.qzx.au.util.Color;

public class BlockColored extends Block {
	@SideOnly(Side.CLIENT)
	private Icon[] blockIcons;

	protected String modPath;

	public BlockColored(String mod, int id, String name, String readableName, Class<? extends ItemBlock> itemclass, Material material){
		super(id, material);
		this.modPath = mod+":";
		this.setUnlocalizedName(name);
		GameRegistry.registerBlock(this, itemclass, name);
		for(int c = 0; c < 16; c++){
			ItemStack coloredStack = new ItemStack(this, 1, c);
			LanguageRegistry.addName(coloredStack, Color.readableColors[c]+readableName);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister){
		this.blockIcons = new Icon[16];
		for(int c = 0; c < 16; c++)
			this.blockIcons[c] = iconRegister.registerIcon(this.modPath+this.getUnlocalizedName().replace("tile.au.", "")+c);
	}

	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int color){
		return this.blockIcons[color];
	}

	@Override
	public int damageDropped(int color){
		return color;
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int unknown, CreativeTabs tab, List subItems){
		for(int c = 0; c < 16; c++)
			subItems.add(new ItemStack(this, 1, c));
	}

	public static int getBlockFromDye(int color){
		return ~color & 15;
	}
	public static int getDyeFromBlock(int color){
		return ~color & 15;
	}
}
