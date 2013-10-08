package com.qzx.au.extras;

import net.minecraft.block.Block;
import net.minecraft.util.Icon;

public class ItemBlockGlassTinted extends ItemBlockColored {
	private int glassBlockID;

	public ItemBlockGlassTinted(int id){
		super(id);
		this.setUnlocalizedName("colorGlassTinted");
		this.glassBlockID = id + 256;
	}

	@Override
	public Icon getIconFromDamage(int color){
		return ((BlockGlass)Block.blocksList[this.glassBlockID]).getItemIcon(color);
	}
}
