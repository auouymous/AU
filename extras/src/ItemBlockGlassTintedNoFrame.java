package com.qzx.au.extras;

import net.minecraft.block.Block;
import net.minecraft.util.Icon;

public class ItemBlockGlassTintedNoFrame extends ItemBlockColored {
	private int glassBlockID;

	public ItemBlockGlassTintedNoFrame(int id){
		super(id);
		this.setUnlocalizedName("colorGlassTintedNoFrame");
		this.glassBlockID = id + 256;
	}

	@Override
	public Icon getIconFromDamage(int color){
		return ((BlockGlass)Block.blocksList[this.glassBlockID]).getItemIcon(color);
	}
}
