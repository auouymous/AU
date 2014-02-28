package com.qzx.au.extras;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;

public class CreativeTabAUExtras extends CreativeTabs {
	public CreativeTabAUExtras(){
		super("tab_au_extras");
	}

	@SideOnly(Side.CLIENT)
	public int getTabIconItemIndex(){
		return THIS_MOD.blockChromaInfuser.blockID;
	}
}
