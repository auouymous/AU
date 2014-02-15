package com.qzx.au.extras;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import com.qzx.au.core.Config;

public class Cfg extends Config {
	public static boolean enableChromaInfuser;
	public static boolean rainResetsChromaInfuser;

	public static boolean enableCobble;
	public static boolean enableStone;
	public static boolean enableStoneBrick;
	public static boolean enableChiseledBrick;
	public static boolean enableSmoothBrick;
	public static boolean enableGravel;
	public static boolean enableGlass;
	public static boolean enableGlassTinted;
	public static boolean enableGlassTintedNoFrame;
	public static boolean enableGlassPane;
	public static boolean enableGlassPaneTinted;
	public static boolean enableGlassPaneTintedNoFrame;
	public static boolean enableIronBars;
	public static boolean enableLamps;
	public static boolean enableCobbleStairs;
	public static boolean enableStoneStairs;
	public static boolean enableStoneBrickStairs;
	public static boolean enableSmoothBrickStairs;
	public static boolean enableFlowers;
	public static boolean enableFlowerWorldGen;
	public static boolean enableFlowerSeed100;
	public static boolean enableEnderCube;
	public static int enderCubeDistance;
	public static boolean enableArtificialGrass;
	public static boolean enableEnderStar;
	public static boolean enableEnderStarInDungeonChests;
	public static boolean enableEnderWand;
	public static boolean enableEnderMagnet;
	public static boolean enableEnderXT;

	public static boolean enableChiseledBrickCrafting;
	public static boolean enableMossyBrickCrafting;
	public static boolean enableMossyCobbleCrafting;
	public static boolean enableCrackedBrickCrafting;
	public static boolean enableGrassBlockCrafting;
	public static boolean enableMyceliumCrafting;

	public static boolean enableLightingHack;
	public static int[] additionalLightingHack;

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
	public static int blockGlassPane;
	public static int blockGlassPaneTinted;
	public static int blockGlassPaneTintedNoFrame;
	public static int blockIronBars;
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
	public static int blockEnderCube;
	public static int blockArtificialGrass;

	public static int itemFriedEgg;
	public static int itemCookedFlesh;
	public static int itemFlowerDye;
	public static int itemEnderStar;
	public static int itemEnderWand;
	public static int itemEnderMagnet;
	public static int itemEnderXT;
//	public static int itemChromaSprayer;

	public static void init(FMLPreInitializationEvent event){
		Cfg.loadConfig(event);

		//////////

		Cfg.enableChromaInfuser = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.chromaInfuser.enable", true, "enable chroma infuser (uses 1 block ID)");
		Cfg.rainResetsChromaInfuser = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.chromaInfuser.rainResets", true, "rain resets chroma infuser");

		Cfg.enableCobble = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.cobble.enable", true, "enable colored cobblestone (uses 1 block ID)");
		Cfg.enableStone = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.stone.enable", true, "enable colored stone (uses 1 block ID)");
		Cfg.enableStoneBrick = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.stoneBrick.enable", true, "enable colored stone brick (uses 1 block ID)");
		Cfg.enableChiseledBrick = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.chiseledBrick.enable", true, "enable colored chiseled brick (uses 1 block ID)");
		Cfg.enableSmoothBrick = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.smoothBrick.enable", true, "enable colored smooth brick (uses 1 block ID)");
		Cfg.enableGravel = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.gravel.enable", true, "enable colored gravel (uses 1 block ID)");

		Cfg.enableGlass = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.glass.enable", true, "enable colored glass (uses 1 block ID)");
		Cfg.enableGlassTinted = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.glassTinted.enable", true,
												"enable colored glass w/ tint (uses 1 block ID)\nNEEDS colored.glass");
		Cfg.enableGlassTintedNoFrame = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.glassTintedNoFrame.enable", true, "enable frameless glass w/ tint (uses 1 block ID)");
		Cfg.enableGlassPane = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.glass.pane.enable", true,
												"enable colored glass pane (uses 1 block ID)\nNEEDS colored.glass");
		Cfg.enableGlassPaneTinted = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.glassTinted.pane.enable", true,
												"enable colored glass pane w/ tint (uses 1 block ID)\nNEEDS colored.glassTinted AND colored.glass");
		Cfg.enableGlassPaneTintedNoFrame = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.glassTintedNoFrame.pane.enable", true,
												"enable frameless glass pane w/ tint (uses 1 block ID)\nNEEDS colored.glassTintedNoFrame");
		if(!Cfg.enableGlass){ Cfg.enableGlassTinted = false; Cfg.enableGlassPaneTinted = false; }
		if(!Cfg.enableGlassTinted) Cfg.enableGlassPaneTinted = false;
		if(!Cfg.enableGlassTintedNoFrame) Cfg.enableGlassPaneTintedNoFrame = false;
		Cfg.enableIronBars = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.ironBars.enable", true, "enable colored iron bars (uses 1 block ID)");

		Cfg.enableLamps = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.lamps.enable", true, "enable colored lamps (uses 4 block IDs)");

		Cfg.enableCobbleStairs = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.cobble.stairs.enable", true, "enable colored cobblestone stairs (uses 16 block IDs)");
		Cfg.enableStoneStairs = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.stone.stairs.enable", true, "enable colored stone stairs (uses 16 block IDs)");
		Cfg.enableStoneBrickStairs = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.stoneBrick.stairs.enable", true, "enable colored stone brick stairs (uses 16 block IDs)");
		Cfg.enableSmoothBrickStairs = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.smoothBrick.stairs.enable", true, "enable colored smooth brick stairs (uses 16 block IDs)");

		Cfg.enableFlowers = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.flowers.enable", true, "enable colored flowers (uses 2 block IDs and 1 item ID)");
		Cfg.enableFlowerWorldGen = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.flowers.worldgen", true, "generate colored flowers in the world");
		Cfg.enableFlowerSeed100 = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.flowers.seed100", false,
												"flower seeds have a 100% chance to produce all colors (default: 25%)");

		Cfg.enableEnderCube = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.enderCube.enable", true, "enable ender cube (uses 1 block ID)");
		Cfg.enderCubeDistance = Cfg.getInt(Cfg.CATEGORY_GENERAL, "block.enderCube.distance", 16, "maximum distance between ender cubes");
		Cfg.enableEnderStar = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.enderCube.enderStar.enable", true, "enable ender star (uses 1 item ID)");
		Cfg.enableEnderStarInDungeonChests = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.enderCube.enderStar.dungeonChests", true, "spawn burned out ender stars in dungeon chests");
		Cfg.enableEnderWand = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.enderCube.enderStar.enderWand.enable", true, "enable ender wand (uses 1 item ID)");
		Cfg.enableEnderMagnet = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.enderCube.enderStar.enderMagnet.enable", true, "enable ender magnet (uses 1 item ID)");
		Cfg.enableEnderXT = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.enderCube.enderStar.enderMagnet.enderXT.enable", true, "enable ender XT (uses 1 item ID)");

		Cfg.enableArtificialGrass = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.artificialGrass.enable", true, "enable artificial grass (uses 1 block ID)");

		Cfg.enableChiseledBrickCrafting = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "vanilla.chiseledBrick.recipe", true, "enable chiseled brick crafting recipe");
		Cfg.enableMossyBrickCrafting = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "vanilla.mossyBrick.recipe", true, "enable mossy brick crafting recipe");
		Cfg.enableMossyCobbleCrafting = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "vanilla.mossyCobble.recipe", true, "enable mossy cobble crafting recipe");
		Cfg.enableCrackedBrickCrafting = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "vanilla.crackedBrick.recipe", true, "enable cracked brick crafting recipe");
		Cfg.enableGrassBlockCrafting = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "vanilla.grassBlock.recipe", true, "enable grass block crafting recipe");
		Cfg.enableMyceliumCrafting = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "vanilla.mycelium.recipe", true, "enable mycelium crafting recipe");

		Cfg.enableLightingHack = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "lightingHack.enable", false,
								"set light opacity to 0 for vanilla stairs and slabs to prevent dark spots\nlight will however pass through");
		Cfg.additionalLightingHack = Cfg.getIntList(Cfg.CATEGORY_GENERAL, "lightingHack.additionalIDs", null,
								"additional block IDs to apply lighting hack, put each ID on its own line, between the <>\ndoes not require lighting hack to be enabled");

		Cfg.enableFriedEgg = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "item.friedEgg.enable", true, "enable smelting eggs to edible food");
		Cfg.enableCookedFlesh = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "item.cookedFlesh.enable", true, "enable smelting rotten flesh to edible food");

		Cfg.enableCookedFleshToLeather = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "item.cookedFlesh.toLeather", true, "enable crafting cooked flesh to leather");
		Cfg.nrCookedFleshToLeather = Cfg.getInt(Cfg.CATEGORY_GENERAL, "item.cookedFlesh.toLeather.count", 4, "number of cooked flesh per leather (1-9)");

		//////////

		// IDs
		int startBlockID = Cfg.getInt(Cfg.CATEGORY_IDMAP, "startBlockID", 3800, "clear all block IDs and restart client to move all blocks, beginning here");
		int startItemID = Cfg.getInt(Cfg.CATEGORY_IDMAP, "startItemID", 31000, "clear all item IDs and restart client to move all items, beginning here");

		// BLOCKS
		Cfg.blockCobble = Cfg.getBlock("block.colored.cobble.id", startBlockID, null); startBlockID++;
		Cfg.blockStone = Cfg.getBlock("block.colored.stone.id", startBlockID, null); startBlockID++;
		Cfg.blockStoneBrick = Cfg.getBlock("block.colored.stoneBrick.id", startBlockID, null); startBlockID++;
		Cfg.blockGlass = Cfg.getBlock("block.colored.glass.id", startBlockID, null); startBlockID++;
		Cfg.blockChiseledBrick = Cfg.getBlock("block.colored.chiseledBrick.id", startBlockID, null); startBlockID++;
		Cfg.blockSmoothBrick = Cfg.getBlock("block.colored.smoothBrick.id", startBlockID, null); startBlockID++;

		Cfg.blockCobbleStairs = Cfg.getBlock("block.colored.cobble.stairs.id", startBlockID, "First of 16 IDs for these stairs"); startBlockID+=16;
		Cfg.blockStoneStairs = Cfg.getBlock("block.colored.stone.stairs.id", startBlockID, "First of 16 IDs for these stairs"); startBlockID+=16;
		Cfg.blockStoneBrickStairs = Cfg.getBlock("block.colored.stoneBrick.stairs.id", startBlockID, "First of 16 IDs for these stairs"); startBlockID+=16;
		Cfg.blockSmoothBrickStairs = Cfg.getBlock("block.colored.smoothBrick.stairs.id", startBlockID, "First of 16 IDs for these stairs"); startBlockID+=16;

		Cfg.blockLamp = Cfg.getBlock("block.colored.lamp.id", startBlockID, null); startBlockID++;
		Cfg.blockInvertedLamp = Cfg.getBlock("block.colored.lamp.inverted.id", startBlockID, null); startBlockID++;
		Cfg.blockLampPowered = Cfg.getBlock("block.colored.lamp.powered.id", startBlockID, null); startBlockID++;
		Cfg.blockInvertedLampPowered = Cfg.getBlock("block.colored.lamp.invertedPowered.id", startBlockID, null); startBlockID++;

		Cfg.blockGlassTinted = Cfg.getBlock("block.colored.glassTinted.id", startBlockID, null); startBlockID++;
		Cfg.blockGlassTintedNoFrame = Cfg.getBlock("block.colored.glassTintedNoFrame.id", startBlockID, null); startBlockID++;

		Cfg.blockChromaInfuser = Cfg.getBlock("block.chromaInfuser.id", startBlockID, null); startBlockID++;

		Cfg.blockGravel = Cfg.getBlock("block.colored.gravel.id", startBlockID, null); startBlockID++;

		Cfg.blockFlower = Cfg.getBlock("block.colored.flower.id", startBlockID, null); startBlockID++;
		Cfg.blockFlowerSeed = Cfg.getBlock("block.colored.flowerSeed.id", startBlockID, null); startBlockID++;

		Cfg.blockEnderCube = Cfg.getBlock("block.enderCube.id", startBlockID, null); startBlockID++;

		Cfg.blockGlassPane = Cfg.getBlock("block.colored.glass.pane.id", startBlockID, null); startBlockID++;
		Cfg.blockGlassPaneTinted = Cfg.getBlock("block.colored.glassTinted.pane.id", startBlockID, null); startBlockID++;
		Cfg.blockGlassPaneTintedNoFrame = Cfg.getBlock("block.colored.glassTintedNoFrame.pane.id", startBlockID, null); startBlockID++;
		Cfg.blockIronBars = Cfg.getBlock("block.colored.ironBars.id", startBlockID, null); startBlockID++;

		Cfg.blockArtificialGrass = Cfg.getBlock("block.artificialGrass.id", startBlockID, null); startBlockID++;

		// ITEMS
		Cfg.itemFriedEgg = Cfg.getItem("item.friedEgg.id", startItemID, null); startItemID++;

		Cfg.itemCookedFlesh = Cfg.getItem("item.cookedFlesh.id", startItemID, null); startItemID++;

		Cfg.itemFlowerDye = Cfg.getItem("item.flowerDye.id", startItemID, null); startItemID++;

		Cfg.itemEnderStar = Cfg.getItem("item.enderStar.id", startItemID, null); startItemID++;
		Cfg.itemEnderWand = Cfg.getItem("item.enderWand.id", startItemID, null); startItemID++;
		Cfg.itemEnderMagnet = Cfg.getItem("item.enderMagnet.id", startItemID, null); startItemID++;
		Cfg.itemEnderXT = Cfg.getItem("item.enderXT.id", startItemID, null); startItemID++;

//		Cfg.itemChromaSprayer = Cfg.getItem("item.chromaSprayer.id", startItemID, null); startItemID++;

		//////////

		Cfg.saveConfig();
	}
}
