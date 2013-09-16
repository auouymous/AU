package com.qzx.au.extras;

import net.minecraft.block.Block;
import net.minecraft.util.Icon;

public class ItemBlockInvertedLamp extends ItemBlockColored {
	private int lampBlockID;

	public ItemBlockInvertedLamp(int id){
		super(id);
		this.setUnlocalizedName("au.colorInvertedLamp");
		this.lampBlockID = id + 256;
	}

	@Override
	public Icon getIconFromDamage(int color){
		return ((BlockLamp)Block.blocksList[this.lampBlockID]).getItemIcon(color);
	}
}
