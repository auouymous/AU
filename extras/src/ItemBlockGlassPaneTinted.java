package com.qzx.au.extras;

import net.minecraft.util.Icon;

public class ItemBlockGlassPaneTinted extends ItemBlockColored {
	public ItemBlockGlassPaneTinted(int id){
		super(id);
		this.setUnlocalizedName("au.colorGlassPaneTinted");
	}

	@Override
	public Icon getIconFromDamage(int color){
		return AUExtras.blockGlassPaneTinted.getIcon(0, color);
	}
}
