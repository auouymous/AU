package com.qzx.au.MOD_LOWER;

import net.minecraft.tileentity.TileEntity;

public class Debug {
	private static final String mod = "AU MOD_UPPER: ";

	public static void error(String text){
		System.err.println(Debug.mod+text);
	}

	public static void print(String text){
		System.out.println(Debug.mod+text);
	}

	public static void print(TileEntity te, String text){
		System.out.println(Debug.mod+(te.worldObj == null ? "------ " : (te.worldObj.isRemote ? "client " : "server "))+text);
	}
}
