package com.qzx.au.extras;

import net.minecraft.util.Icon;

public class ItemBlockColoredIcon extends ItemBlockColored {
	public ItemBlockColoredIcon(int id){
		super(id);
	}

	@Override
	public Icon getIconFromDamage(int color){
		return this.block.getIcon(0, color);
	}
}
