package com.qzx.au.extras;

import net.minecraft.util.Icon;

public class ItemBlockGlassTintedNoFrame extends ItemBlockColored {
	public ItemBlockGlassTintedNoFrame(int id){
		super(id);
		this.setUnlocalizedName("au.colorGlassTintedNoFrame");
	}

	@Override
	public Icon getIconFromDamage(int color){
		return ((BlockGlass)AUExtras.blockGlassTintedNoFrame).getIcon(0, color);
	}
}
