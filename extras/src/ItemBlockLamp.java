package com.qzx.au.extras;

import net.minecraft.block.Block;
import net.minecraft.util.Icon;

public class ItemBlockLamp extends ItemBlockColored {
	private int lampBlockID;

	public ItemBlockLamp(int id){
		super(id);
		this.setUnlocalizedName("au.colorLamp");
		this.lampBlockID = id + 256;
	}

	@Override
	public Icon getIconFromDamage(int color){
		return ((BlockLamp)Block.blocksList[this.lampBlockID]).getIcon(0, color);
	}
}
