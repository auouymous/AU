package com.qzx.au.extras;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import com.qzx.au.util.Config;

public class Cfg extends Config {
	public static boolean enableCobble;
	public static boolean enableStone;
	public static boolean enableStoneBrick;
	public static boolean enableChiseledBrick;
	public static boolean enableSmoothBrick;
	public static boolean enableGlass;
	public static boolean enableLamps;

	public static boolean enableCobbleStairs;
	public static boolean enableStoneStairs;
	public static boolean enableStoneBrickStairs;
	public static boolean enableSmoothBrickStairs;

	public static boolean enableLightingHack;

	public static boolean enableFriedEgg;
	public static boolean enableCookedFlesh;

	public static boolean enableCookedFleshToLeather;
	public static int nrCookedFleshToLeather;

	public static int blockCobble;
	public static int blockStone;
	public static int blockStoneBrick;
	public static int blockChiseledBrick;
	public static int blockSmoothBrick;
	public static int blockGlass;
	public static int blockLamp;
	public static int blockInvertedLamp;
	public static int blockLampPowered;
	public static int blockInvertedLampPowered;

	public static int blockCobbleStairs;
	public static int blockStoneStairs;
	public static int blockStoneBrickStairs;
	public static int blockSmoothBrickStairs;

	public static int itemFriedEgg;
	public static int itemCookedFlesh;

	public static void init(FMLPreInitializationEvent event){
		Cfg.loadConfig(event);

		Cfg.enableCobble = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableCobble", true, null);
		Cfg.enableStone = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableStone", true, null);
		Cfg.enableStoneBrick = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableStoneBrick", true, null);
		Cfg.enableChiseledBrick = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableChiseledBrick", true, null);
		Cfg.enableSmoothBrick = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableSmoothBrick", true, null);
		Cfg.enableGlass = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableGlass", true, null);
		Cfg.enableLamps = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableLamps", true, null);

		Cfg.enableCobbleStairs = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableCobbleStairs", true, null);
		Cfg.enableStoneStairs = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableStoneStairs", true, null);
		Cfg.enableStoneBrickStairs = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableStoneBrickStairs", true, null);
		Cfg.enableSmoothBrickStairs = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableSmoothBrickStairs", true, null);

		Cfg.enableLightingHack = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableLightingHack", true, "adds minimal light to vanilla stairs and slabs to prevent dark spots");

		Cfg.enableFriedEgg = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableFriedEgg", true, null);
		Cfg.enableCookedFlesh = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableCookedFlesh", true, null);

		Cfg.enableCookedFleshToLeather = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableCookedFleshToLeather", true, null);
		Cfg.nrCookedFleshToLeather = Cfg.getInt(Cfg.CATEGORY_GENERAL, "nrCookedFleshToLeather", 4, "number of cooked flesh per leather (1-9)");

		// IDs
		int startBlockID = 3800, startItemID = 31000;
		Cfg.blockCobble = Cfg.getBlock("blockCobble", startBlockID, null);
		Cfg.blockStone = Cfg.getBlock("blockStone", startBlockID+1, null);
		Cfg.blockStoneBrick = Cfg.getBlock("blockStoneBrick", startBlockID+2, null);
		Cfg.blockGlass = Cfg.getBlock("blockGlass", startBlockID+3, null);
		Cfg.blockChiseledBrick = Cfg.getBlock("blockChiseledBrick", startBlockID+4, null);
		Cfg.blockSmoothBrick = Cfg.getBlock("blockSmoothBrick", startBlockID+5, null);

		Cfg.blockCobbleStairs = Cfg.getBlock("blockCobbleStairs", startBlockID+6, "First of 16 IDs for these stairs");
		Cfg.blockStoneStairs = Cfg.getBlock("blockStoneStairs", startBlockID+6+16, "First of 16 IDs for these stairs");
		Cfg.blockStoneBrickStairs = Cfg.getBlock("blockStoneBrickStairs", startBlockID+6+32, "First of 16 IDs for these stairs");
		Cfg.blockSmoothBrickStairs = Cfg.getBlock("blockSmoothBrickStairs", startBlockID+6+48, "First of 16 IDs for these stairs");

		Cfg.blockLamp = Cfg.getBlock("blockLamp", startBlockID+6+64, null);
		Cfg.blockInvertedLamp = Cfg.getBlock("blockInvertedLamp", startBlockID+7+64, null);
		Cfg.blockLampPowered = Cfg.getBlock("blockLampPowered", startBlockID+8+64, null);
		Cfg.blockInvertedLampPowered = Cfg.getBlock("blockInvertedLampPowered", startBlockID+9+64, null);

		Cfg.itemFriedEgg = Cfg.getItem("itemFriedEgg", startItemID, null);
		Cfg.itemCookedFlesh = Cfg.getItem("itemCookedFlesh", startItemID+1, null);

		Cfg.saveConfig();
	}
}
