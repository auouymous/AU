package com.qzx.au.extras;

import net.minecraft.util.Icon;

public class ItemBlockInvertedLamp extends ItemBlockColored {
	public ItemBlockInvertedLamp(int id){
		super(id);
		this.setUnlocalizedName("au.colorInvertedLamp");
	}

	@Override
	public Icon getIconFromDamage(int color){
		return THIS_MOD.blockInvertedLamp.getIcon(0, color);
	}
}
