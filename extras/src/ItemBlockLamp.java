package com.qzx.au.extras;

import net.minecraft.util.Icon;

public class ItemBlockLamp extends ItemBlockColored {
	public ItemBlockLamp(int id){
		super(id);
		this.setUnlocalizedName("au.colorLamp");
	}

	@Override
	public Icon getIconFromDamage(int color){
		return ((BlockLamp)AUExtras.blockLamp).getIcon(0, color);
	}
}
