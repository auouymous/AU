package com.qzx.au.extras;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import com.qzx.au.util.Config;

public class Cfg extends Config {
	public static boolean enableCobble;
	public static boolean enableStone;
	public static boolean enableStoneBrick;
	public static boolean enableGlass;

	public static boolean enableFriedEgg;
	public static boolean enableCookedFlesh;

	public static boolean enableCookedFleshToLeather;
	public static int nrCookedFleshToLeather;

	public static int blockCobble;
	public static int blockStone;
	public static int blockStoneBrick;
	public static int blockGlass;
	public static int itemFriedEgg;
	public static int itemCookedFlesh;

	public static void init(FMLPreInitializationEvent event){
		Cfg.loadConfig(event);

		Cfg.enableCobble = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableCobble", true, null);
		Cfg.enableStone = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableStone", true, null);
		Cfg.enableStoneBrick = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableStoneBrick", true, null);
		Cfg.enableGlass = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableGlass", true, null);

		Cfg.enableFriedEgg = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableFriedEgg", true, null);
		Cfg.enableCookedFlesh = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableCookedFlesh", true, null);

		Cfg.enableCookedFleshToLeather = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableCookedFleshToLeather", true, null);
		Cfg.nrCookedFleshToLeather = Cfg.getInt(Cfg.CATEGORY_GENERAL, "nrCookedFleshToLeather", 4, "number of cooked flesh per leather (1-9)");

		int startBlockID = 3800, startItemID = 31000;
		Cfg.blockCobble = Cfg.getBlock("blockCobble", startBlockID, null);
		Cfg.blockStone = Cfg.getBlock("blockStone", startBlockID+1, null);
		Cfg.blockStoneBrick = Cfg.getBlock("blockStoneBrick", startBlockID+2, null);
		Cfg.blockGlass = Cfg.getBlock("blockGlass", startBlockID+3, null);
		Cfg.itemFriedEgg = Cfg.getItem("itemFriedEgg", startItemID, null);
		Cfg.itemCookedFlesh = Cfg.getItem("itemCookedFlesh", startItemID+1, null);

		Cfg.saveConfig();
	}
}
