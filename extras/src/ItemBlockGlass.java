package com.qzx.au.extras;

import net.minecraft.util.Icon;

public class ItemBlockGlass extends ItemBlockColored {
	public ItemBlockGlass(int id){
		super(id);
		this.setUnlocalizedName("au.colorGlass");
	}

	@Override
	public Icon getIconFromDamage(int color){
		return THIS_MOD.blockGlass.getIcon(0, color);
	}
}
