package com.qzx.au.extras;

import net.minecraft.util.Icon;

public class ItemBlockIronBars extends ItemBlockColored {
	public ItemBlockIronBars(int id){
		super(id);
		this.setUnlocalizedName("au.colorIronBars");
	}

	@Override
	public Icon getIconFromDamage(int color){
		return THIS_MOD.blockIronBars.getIcon(0, color);
	}
}
