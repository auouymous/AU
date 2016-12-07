package com.qzx.au.extras;

public class ItemBlockColoredIcon extends ItemBlockColored {
	public ItemBlockColoredIcon(int id){
		super(id);
	}

	@Override
	public MC_ICON getIconFromDamage(int color){
		return this.block.getIcon(0, color);
	}
}
