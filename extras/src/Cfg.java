package com.qzx.au.extras;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import com.qzx.au.core.Config;

public class Cfg extends Config {
	public static boolean enableCobble;
	public static boolean enableStone;
	public static boolean enableStoneBrick;
	public static boolean enableChiseledBrick;
	public static boolean enableSmoothBrick;
	public static boolean enableGravel;
	public static boolean enableGlass;
	public static boolean enableGlassTinted;
	public static boolean enableGlassTintedNoFrame;
	public static boolean enableLamps;
	public static boolean enableCobbleStairs;
	public static boolean enableStoneStairs;
	public static boolean enableStoneBrickStairs;
	public static boolean enableSmoothBrickStairs;
	public static boolean enableFlowers;

	public static boolean enableChiseledBrickCrafting;
	public static boolean enableGrassBlockCrafting;
	public static boolean enableMyceliumCrafting;

	public static boolean enableLightingHack;

	public static boolean enableFriedEgg;
	public static boolean enableCookedFlesh;

	public static boolean enableCookedFleshToLeather;
	public static int nrCookedFleshToLeather;

	// IDs
	public static int blockChromaInfuser;

	public static int blockCobble;
	public static int blockStone;
	public static int blockStoneBrick;
	public static int blockChiseledBrick;
	public static int blockSmoothBrick;
	public static int blockGravel;
	public static int blockGlass;
	public static int blockGlassTinted;
	public static int blockGlassTintedNoFrame;
	public static int blockLamp;
	public static int blockInvertedLamp;
	public static int blockLampPowered;
	public static int blockInvertedLampPowered;
	public static int blockCobbleStairs;
	public static int blockStoneStairs;
	public static int blockStoneBrickStairs;
	public static int blockSmoothBrickStairs;
	public static int blockFlower;
	public static int blockFlowerSeed;

	public static int itemFriedEgg;
	public static int itemCookedFlesh;
	public static int itemChromaSprayer;
	public static int itemFlowerDye;

	public static void init(FMLPreInitializationEvent event){
		Cfg.loadConfig(event);

		//////////

		Cfg.enableCobble = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableCobble", true, "enable colored cobblestone (uses 1 block ID)");
		Cfg.enableStone = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableStone", true, "enable colored stone (uses 1 block ID)");
		Cfg.enableStoneBrick = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableStoneBrick", true, "enable colored stone brick (uses 1 block ID)");
		Cfg.enableChiseledBrick = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableChiseledBrick", true, "enable colored chiseled brick (uses 1 block ID)");
		Cfg.enableSmoothBrick = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableSmoothBrick", true, "enable colored smooth brick (uses 1 block ID)");
		Cfg.enableGravel = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableGravel", true, "enable colored gravel (uses 1 block ID)");
		Cfg.enableGlass = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableGlass", true, "enable colored glass (uses 1 block ID)");
		Cfg.enableGlassTinted = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableGlassTinted", true, "enable colored glass w/ tint (uses 1 block ID)");
		Cfg.enableGlassTintedNoFrame = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableGlassTintedNoFrame", true, "enable frameless glass w/ tint (uses 1 block ID)");
		Cfg.enableLamps = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableLamps", true, "enable colored lamps (uses 4 block IDs)");

		Cfg.enableCobbleStairs = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableCobbleStairs", true, "enable colored cobblestone stairs (uses 16 block IDs)");
		Cfg.enableStoneStairs = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableStoneStairs", true, "enable colored stone stairs (uses 16 block IDs)");
		Cfg.enableStoneBrickStairs = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableStoneBrickStairs", true, "enable colored stone brick stairs (uses 16 block IDs)");
		Cfg.enableSmoothBrickStairs = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableSmoothBrickStairs", true, "enable colored smooth brick stairs (uses 16 block IDs)");

		Cfg.enableFlowers = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableFlowers", true, "enable colored flowers (uses 2 block IDs and 1 item ID)");

		Cfg.enableChiseledBrickCrafting = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableChiseledBrickCrafting", true, "enable chiseled brick crafting recipe");
		Cfg.enableGrassBlockCrafting = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableGrassBlockCrafting", true, "enable grass block crafting recipe");
		Cfg.enableMyceliumCrafting = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableMyceliumCrafting", true, "enable mycelium crafting recipe");

		Cfg.enableLightingHack = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableLightingHack", true,
								"add minimal light to vanilla stairs and slabs to prevent dark spots\nwill absorb and continue to emit external light sources, after sources are removed");

		Cfg.enableFriedEgg = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableFriedEgg", true, "enable smelting eggs to edible food");
		Cfg.enableCookedFlesh = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableCookedFlesh", true, "enable smelting rotten flesh to edible food");

		Cfg.enableCookedFleshToLeather = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enableCookedFleshToLeather", true, "enable crafting cooked flesh to leather");
		Cfg.nrCookedFleshToLeather = Cfg.getInt(Cfg.CATEGORY_GENERAL, "nrCookedFleshToLeather", 4, "number of cooked flesh per leather (1-9)");

		//////////

		// IDs
		int startBlockID = Cfg.getInt(Cfg.CATEGORY_IDMAP, "startBlockID", 3800, "clear all block IDs and restart client to move all blocks, beginning here");
		int startItemID = Cfg.getInt(Cfg.CATEGORY_IDMAP, "startItemID", 31000, "clear all item IDs and restart client to move all items, beginning here");

		// BLOCKS
		Cfg.blockCobble = Cfg.getBlock("blockCobble", startBlockID, null); startBlockID++;
		Cfg.blockStone = Cfg.getBlock("blockStone", startBlockID, null); startBlockID++;
		Cfg.blockStoneBrick = Cfg.getBlock("blockStoneBrick", startBlockID, null); startBlockID++;
		Cfg.blockGlass = Cfg.getBlock("blockGlass", startBlockID, null); startBlockID++;
		Cfg.blockChiseledBrick = Cfg.getBlock("blockChiseledBrick", startBlockID, null); startBlockID++;
		Cfg.blockSmoothBrick = Cfg.getBlock("blockSmoothBrick", startBlockID, null); startBlockID++;

		Cfg.blockCobbleStairs = Cfg.getBlock("startCobbleStairs", startBlockID, "First of 16 IDs for these stairs"); startBlockID+=16;
		Cfg.blockStoneStairs = Cfg.getBlock("startStoneStairs", startBlockID, "First of 16 IDs for these stairs"); startBlockID+=16;
		Cfg.blockStoneBrickStairs = Cfg.getBlock("startStoneBrickStairs", startBlockID, "First of 16 IDs for these stairs"); startBlockID+=16;
		Cfg.blockSmoothBrickStairs = Cfg.getBlock("startSmoothBrickStairs", startBlockID, "First of 16 IDs for these stairs"); startBlockID+=16;

		Cfg.blockLamp = Cfg.getBlock("blockLamp", startBlockID, null); startBlockID++;
		Cfg.blockInvertedLamp = Cfg.getBlock("blockInvertedLamp", startBlockID, null); startBlockID++;
		Cfg.blockLampPowered = Cfg.getBlock("blockLampPowered", startBlockID, null); startBlockID++;
		Cfg.blockInvertedLampPowered = Cfg.getBlock("blockInvertedLampPowered", startBlockID, null); startBlockID++;

		Cfg.blockGlassTinted = Cfg.getBlock("blockGlassTinted", startBlockID, null); startBlockID++;
		Cfg.blockGlassTintedNoFrame = Cfg.getBlock("blockGlassTintedNoFrame", startBlockID, null); startBlockID++;

		Cfg.blockChromaInfuser = Cfg.getBlock("blockChromaInfuser", startBlockID, null); startBlockID++;

		Cfg.blockGravel = Cfg.getBlock("blockGravel", startBlockID, null); startBlockID++;

		Cfg.blockFlower = Cfg.getBlock("blockFlower", startBlockID, null); startBlockID++;
		Cfg.blockFlowerSeed = Cfg.getBlock("blockFlowerSeed", startBlockID, null); startBlockID++;

		// ITEMS
		Cfg.itemFriedEgg = Cfg.getItem("itemFriedEgg", startItemID, null); startItemID++;
		Cfg.itemCookedFlesh = Cfg.getItem("itemCookedFlesh", startItemID, null); startItemID++;

		Cfg.itemChromaSprayer = Cfg.getItem("itemChromaSprayer", startItemID, null); startItemID++;

		Cfg.itemFlowerDye = Cfg.getItem("itemFlowerDye", startItemID, null); startItemID++;

		//////////

		Cfg.saveConfig();
	}
}
