package com.qzx.au.extras;

import net.minecraft.util.Icon;

public class ItemBlockGlassPaneTintedNoFrame extends ItemBlockColored {
	public ItemBlockGlassPaneTintedNoFrame(int id){
		super(id);
		this.setUnlocalizedName("au.colorGlassPaneTintedNoFrame");
	}

	@Override
	public Icon getIconFromDamage(int color){
		return AUExtras.blockGlassPaneTintedNoFrame.getIcon(0, color);
	}
}
