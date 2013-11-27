package com.qzx.au.extras;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class CreativeTabAUExtras extends CreativeTabs {
	public CreativeTabAUExtras(){
		super("tabAUExtras");
		LanguageRegistry.instance().addStringLocalization("itemGroup.tabAUExtras", "en_US", "Altered Unification EXTRAS");
	}

	@SideOnly(Side.CLIENT)
	public int getTabIconItemIndex(){
		return AUExtras.blockChromaInfuser.blockID;
	}
}
