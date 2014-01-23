package com.qzx.au.extras;

import net.minecraft.util.Icon;

public class ItemBlockGlassTinted extends ItemBlockColored {
	public ItemBlockGlassTinted(int id){
		super(id);
		this.setUnlocalizedName("au.colorGlassTinted");
	}

	@Override
	public Icon getIconFromDamage(int color){
		return ((BlockGlass)AUExtras.blockGlassTinted).getIcon(0, color);
	}
}
