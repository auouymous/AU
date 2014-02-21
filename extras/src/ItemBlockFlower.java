package com.qzx.au.extras;

import net.minecraft.block.Block;
import net.minecraft.util.Icon;

public class ItemBlockFlower extends ItemBlockColored {
	public ItemBlockFlower(int id){
		super(id);
	}

	@Override
	public Icon getIconFromDamage(int color){
		return ((BlockFlower)THIS_MOD.blockFlower).getItemIcon(color);
	}
}
