package com.qzx.au.MOD_LOWER;

import cpw.mods.fml.common.FMLLog;

import net.minecraft.tileentity.TileEntity;

public class Debug {
	private static final String mod = "AU MOD_UPPER: ";

	public static void error(String text){
		FMLLog.severe(Debug.mod+text);
	}

	public static void print(String text){
		// use warning since these should only be used in DEBUG code
		FMLLog.warning(Debug.mod+text);
	}

	public static void print(TileEntity te, String text){
		// use warning since these should only be used in DEBUG code
		FMLLog.warning(Debug.mod+(te.worldObj == null ? "------ " : (te.worldObj.isRemote ? "client " : "server "))+text);
	}
}
