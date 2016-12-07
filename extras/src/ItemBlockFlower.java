package com.qzx.au.extras;

import net.minecraft.block.Block;

public class ItemBlockFlower extends ItemBlockColored {
	public ItemBlockFlower(int id){
		super(id);
	}

	@Override
	public MC_ICON getIconFromDamage(int color){
		return ((BlockFlower)THIS_MOD.blockFlower).getItemIcon(color);
	}
}
