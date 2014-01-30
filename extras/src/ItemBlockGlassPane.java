package com.qzx.au.extras;

import net.minecraft.util.Icon;

public class ItemBlockGlassPane extends ItemBlockColored {
	public ItemBlockGlassPane(int id){
		super(id);
		this.setUnlocalizedName("au.colorGlassPane");
	}

	@Override
	public Icon getIconFromDamage(int color){
		return AUExtras.blockGlassPane.getIcon(0, color);
	}
}
