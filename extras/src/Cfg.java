package com.qzx.au.extras;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import com.qzx.au.core.Config;

public class Cfg extends Config {
	// ENABLE BLOCKS
	public static boolean enableChromaInfuser;
	public static boolean rainResetsChromaInfuser;

	public static boolean enableCobble;
		public static boolean enableCobbleStairs;
		public static boolean enableCobbleSlabs;
	public static boolean enableStone;
		public static boolean enableStoneStairs;
		public static boolean enableStoneSlabs;
	public static boolean enableStoneBrick;
		public static boolean enableStoneBrickStairs;
		public static boolean enableStoneBrickSlabs;
	public static boolean enableChiseledBrick;
		// no chiseled brick stairs
		public static boolean enableChiseledBrickSlabs;
	public static boolean enableSmoothBrick;
		public static boolean enableSmoothBrickStairs;
		public static boolean enableSmoothBrickSlabs;
	public static boolean enableGravel;
		// no gravel stairs
		public static boolean enableGravelSlabs;
	public static boolean enableSand;
		public static boolean enableSandStairs;
		public static boolean enableSandSlabs;
	public static boolean enableArtificialGrass;
		public static boolean enableArtificialGrassStairs;
		public static boolean enableArtificialGrassSlabs;
	public static boolean enableArtificialVine;

	public static boolean enableGlass;
	public static boolean enableGlassTinted;
	public static boolean enableGlassTintedNoFrame;
	public static boolean enableGlassPane;
	public static boolean enableGlassPaneTinted;
	public static boolean enableGlassPaneTintedNoFrame;
	public static boolean enableIronBars;

	public static boolean enableLamps;

	public static boolean enableFlowers;
	public static boolean enableFlowerWorldGen;
	public static boolean enableFlowerSeed100;

	public static boolean enableEnderCube;
	public static int enderCubeDistance;

	// ENABLE ITEMS
	public static boolean enableFriedEgg;

	public static boolean enableCookedFlesh;
	public static boolean enableCookedFleshToLeather;
	public static int nrCookedFleshToLeather;

	public static boolean enableEnderStar;
	public static boolean enableEnderStarInDungeonChests;
	public static boolean enableEnderWand;
	public static boolean enableEnderMagnet;
	public static boolean enableEnderXT;

	// ENABLE Vanilla Recipes
	public static boolean enableChiseledBrickCrafting;
	public static boolean enableMossyBrickCrafting;
	public static boolean enableMossyCobbleCrafting;
	public static boolean enableCrackedBrickCrafting;
	public static boolean enableGrassBlockCrafting;
	public static boolean enableMyceliumCrafting;

	// ENABLE Lighting Hack
	public static boolean enableLightingHack;
	public static int[] additionalLightingHack;

	//////////

	// BLOCKS
	public static int blockChromaInfuser;

	public static int blockCobble;
		public static int blockCobbleStairs; // first of 16
		public static int blockCobbleSlab0;
		public static int blockCobbleSlab1;
	public static int blockStone;
		public static int blockStoneStairs; // first of 16
		public static int blockStoneSlab0;
		public static int blockStoneSlab1;
	public static int blockStoneBrick;
		public static int blockStoneBrickStairs; // first of 16
		public static int blockStoneBrickSlab0;
		public static int blockStoneBrickSlab1;
	public static int blockChiseledBrick;
		// no chiseled brick stairs
		public static int blockChiseledBrickSlab0;
		public static int blockChiseledBrickSlab1;
	public static int blockSmoothBrick;
		public static int blockSmoothBrickStairs; // first of 16
		public static int blockSmoothBrickSlab0;
		public static int blockSmoothBrickSlab1;
	public static int blockGravel;
		// no gravel stairs
		public static int blockGravelSlab0;
		public static int blockGravelSlab1;
	public static int blockSand;
		public static int blockSandStairs; // first of 16
		public static int blockSandSlab0;
		public static int blockSandSlab1;
	public static int blockArtificialGrass;
		public static int blockArtificialGrassStairs; // first of 16
		public static int blockArtificialGrassSlab0;
		public static int blockArtificialGrassSlab1;
	public static int blockArtificialVine; // first of 16

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

	public static int blockFlower;
	public static int blockFlowerSeed;

	public static int blockEnderCube;

	// ITEMS
	public static int itemFriedEgg;

	public static int itemCookedFlesh;

	public static int itemFlowerDye;

	public static int itemEnderStar;
	public static int itemEnderWand;
	public static int itemEnderMagnet;
	public static int itemEnderXT;

	//////////

	public static void init(FMLPreInitializationEvent event){
		Cfg.loadConfig(event);

		//////////

		// ENABLE BLOCKS
		Cfg.enableChromaInfuser = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.chromaInfuser.enable", true, "enable chroma infuser (uses 1 block ID)");
		Cfg.rainResetsChromaInfuser = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.chromaInfuser.rainResets", true, "rain resets chroma infuser");

		Cfg.enableCobble = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.cobble.enable", true, "enable colored cobblestone (uses 1 block ID)");
			Cfg.enableCobbleStairs = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.cobble.stairs.enable", true, "enable colored cobblestone stairs (uses 16 block IDs)");
			Cfg.enableCobbleSlabs = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.cobble.slabs.enable", true, "enable colored cobblestone slabs (uses 2 block IDs)");
		Cfg.enableStone = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.stone.enable", true, "enable colored stone (uses 1 block ID)");
			Cfg.enableStoneStairs = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.stone.stairs.enable", true, "enable colored stone stairs (uses 16 block IDs)");
			Cfg.enableStoneSlabs = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.stone.slabs.enable", true, "enable colored stone slabs (uses 2 block IDs)");
		Cfg.enableStoneBrick = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.stoneBrick.enable", true, "enable colored stone brick (uses 1 block ID)");
			Cfg.enableStoneBrickStairs = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.stoneBrick.stairs.enable", true, "enable colored stone brick stairs (uses 16 block IDs)");
			Cfg.enableStoneBrickSlabs = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.stoneBrick.slabs.enable", true, "enable colored stone brick slabs (uses 2 block IDs)");
		Cfg.enableChiseledBrick = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.chiseledBrick.enable", true, "enable colored chiseled brick (uses 1 block ID)");
			// no chiseled brick stairs
			Cfg.enableChiseledBrickSlabs = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.chiseledBrick.slabs.enable", true, "enable colored chiseled brick slabs (uses 2 block IDs)");
		Cfg.enableSmoothBrick = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.smoothBrick.enable", true, "enable colored smooth brick (uses 1 block ID)");
			Cfg.enableSmoothBrickStairs = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.smoothBrick.stairs.enable", true, "enable colored smooth brick stairs (uses 16 block IDs)");
			Cfg.enableSmoothBrickSlabs = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.smoothBrick.slabs.enable", true, "enable colored smooth brick slabs (uses 2 block IDs)");
		Cfg.enableGravel = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.gravel.enable", true, "enable colored gravel (uses 1 block ID)");
			// no gravel stairs
			Cfg.enableGravelSlabs = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.gravel.slabs.enable", true, "enable colored gravel slabs (uses 2 block IDs)");
		Cfg.enableSand = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.sand.enable", true, "enable colored sand (uses 1 block ID)");
			Cfg.enableSandStairs = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.sand.stairs.enable", true, "enable colored sand stairs (uses 16 block IDs)");
			Cfg.enableSandSlabs = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.sand.slabs.enable", true, "enable colored sand slabs (uses 2 block IDs)");
		Cfg.enableArtificialGrass = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.artificialGrass.enable", true, "enable artificial grass (uses 1 block ID)");
			Cfg.enableArtificialGrassStairs = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.artificialGrass.stairs.enable", true, "enable artificial grass stairs (uses 16 block IDs)");
			Cfg.enableArtificialGrassSlabs = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.artificialGrass.slabs.enable", true, "enable artificial grass slabs (uses 2 block IDs)");
		Cfg.enableArtificialVine = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.artificialVine.enable", true, "enable artificial vine (uses 16 block IDs)");

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

		Cfg.enableFlowers = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.flowers.enable", true, "enable colored flowers (uses 2 block IDs and 1 item ID)");
		Cfg.enableFlowerWorldGen = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.flowers.worldgen", true, "generate colored flowers in the world");
		Cfg.enableFlowerSeed100 = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.colored.flowers.seed100", false,
												"flower seeds have a 100% chance to produce all colors (default: 25%)");

		Cfg.enableEnderCube = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.enderCube.enable", true, "enable ender cube (uses 1 block ID)");
		Cfg.enderCubeDistance = Cfg.getInt(Cfg.CATEGORY_GENERAL, "block.enderCube.distance", 16, "maximum distance between ender cubes");

		// ENABLE ITEMS
		Cfg.enableFriedEgg = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "item.friedEgg.enable", true, "enable smelting eggs to edible food");

		Cfg.enableCookedFlesh = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "item.cookedFlesh.enable", true, "enable smelting rotten flesh to edible food");

		Cfg.enableCookedFleshToLeather = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "item.cookedFlesh.toLeather", true, "enable crafting cooked flesh to leather");
		Cfg.nrCookedFleshToLeather = Cfg.getInt(Cfg.CATEGORY_GENERAL, "item.cookedFlesh.toLeather.count", 4, "number of cooked flesh per leather (1-9)");

		Cfg.enableEnderStar = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.enderCube.enderStar.enable", true, "enable ender star (uses 1 item ID)");
		Cfg.enableEnderStarInDungeonChests = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.enderCube.enderStar.dungeonChests", true, "spawn burned out ender stars in dungeon chests");
		Cfg.enableEnderWand = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.enderCube.enderStar.enderWand.enable", true, "enable ender wand (uses 1 item ID)");
		Cfg.enableEnderMagnet = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.enderCube.enderStar.enderMagnet.enable", true, "enable ender magnet (uses 1 item ID)");
		Cfg.enableEnderXT = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "block.enderCube.enderStar.enderMagnet.enderXT.enable", true, "enable ender XT (uses 1 item ID)");

		// ENABLE Vanilla Recipes
		Cfg.enableChiseledBrickCrafting = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "vanilla.chiseledBrick.recipe", true, "enable chiseled brick crafting recipe");
		Cfg.enableMossyBrickCrafting = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "vanilla.mossyBrick.recipe", true, "enable mossy brick crafting recipe");
		Cfg.enableMossyCobbleCrafting = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "vanilla.mossyCobble.recipe", true, "enable mossy cobble crafting recipe");
		Cfg.enableCrackedBrickCrafting = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "vanilla.crackedBrick.recipe", true, "enable cracked brick crafting recipe");
		Cfg.enableGrassBlockCrafting = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "vanilla.grassBlock.recipe", true, "enable grass block crafting recipe");
		Cfg.enableMyceliumCrafting = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "vanilla.mycelium.recipe", true, "enable mycelium crafting recipe");

		// ENABLE Lighting Hack
		Cfg.enableLightingHack = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "lightingHack.enable", false,
								"set light opacity to 0 for vanilla stairs and slabs to prevent dark spots\nlight will however pass through");
		Cfg.additionalLightingHack = Cfg.getIntList(Cfg.CATEGORY_GENERAL, "lightingHack.additionalIDs", null,
								"additional block IDs to apply lighting hack, put each ID on its own line, between the <>\ndoes not require lighting hack to be enabled");

		//////////

		// IDs
		int startBlockID = Cfg.getInt(Cfg.CATEGORY_IDMAP, "startBlockID", 3800, "clear all block IDs and restart client to move all blocks, beginning here");
		int startItemID = Cfg.getInt(Cfg.CATEGORY_IDMAP, "startItemID", 31000, "clear all item IDs and restart client to move all items, beginning here");

		// BLOCK IDs
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
		Cfg.blockArtificialGrassStairs = Cfg.getBlock("block.artificialGrass.stairs.id", startBlockID, "First of 16 IDs for these stairs"); startBlockID+=16;

		Cfg.blockCobbleSlab0 = Cfg.getBlock("block.colored.cobble.slabs0.id", startBlockID, null); startBlockID++;
		Cfg.blockCobbleSlab1 = Cfg.getBlock("block.colored.cobble.slabs1.id", startBlockID, null); startBlockID++;
		Cfg.blockStoneSlab0 = Cfg.getBlock("block.colored.stone.slabs0.id", startBlockID, null); startBlockID++;
		Cfg.blockStoneSlab1 = Cfg.getBlock("block.colored.stone.slabs1.id", startBlockID, null); startBlockID++;
		Cfg.blockStoneBrickSlab0 = Cfg.getBlock("block.colored.stoneBrick.slabs0.id", startBlockID, null); startBlockID++;
		Cfg.blockStoneBrickSlab1 = Cfg.getBlock("block.colored.stoneBrick.slabs1.id", startBlockID, null); startBlockID++;
		Cfg.blockChiseledBrickSlab0 = Cfg.getBlock("block.colored.chiseledBrick.slabs0.id", startBlockID, null); startBlockID++;
		Cfg.blockChiseledBrickSlab1 = Cfg.getBlock("block.colored.chiseledBrick.slabs1.id", startBlockID, null); startBlockID++;
		Cfg.blockSmoothBrickSlab0 = Cfg.getBlock("block.colored.smoothBrick.slabs0.id", startBlockID, null); startBlockID++;
		Cfg.blockSmoothBrickSlab1 = Cfg.getBlock("block.colored.smoothBrick.slabs1.id", startBlockID, null); startBlockID++;
		Cfg.blockGravelSlab0 = Cfg.getBlock("block.colored.gravel.slabs0.id", startBlockID, null); startBlockID++;
		Cfg.blockGravelSlab1 = Cfg.getBlock("block.colored.gravel.slabs1.id", startBlockID, null); startBlockID++;
		Cfg.blockArtificialGrassSlab0 = Cfg.getBlock("block.artificialGrass.slabs0.id", startBlockID, null); startBlockID++;
		Cfg.blockArtificialGrassSlab1 = Cfg.getBlock("block.artificialGrass.slabs1.id", startBlockID, null); startBlockID++;

		Cfg.blockSand = Cfg.getBlock("block.colored.sand.id", startBlockID, null); startBlockID++;
		Cfg.blockSandStairs = Cfg.getBlock("block.colored.sand.stairs.id", startBlockID, "First of 16 IDs for these stairs"); startBlockID+=16;
		Cfg.blockSandSlab0 = Cfg.getBlock("block.colored.sand.slabs0.id", startBlockID, null); startBlockID++;
		Cfg.blockSandSlab1 = Cfg.getBlock("block.colored.sand.slabs1.id", startBlockID, null); startBlockID++;

		Cfg.blockArtificialVine = Cfg.getBlock("block.artificialVine.id", startBlockID, "First of 16 IDs"); startBlockID+=16;

		// ITEM IDs
		Cfg.itemFriedEgg = Cfg.getItem("item.friedEgg.id", startItemID, null); startItemID++;

		Cfg.itemCookedFlesh = Cfg.getItem("item.cookedFlesh.id", startItemID, null); startItemID++;

		Cfg.itemFlowerDye = Cfg.getItem("item.flowerDye.id", startItemID, null); startItemID++;

		Cfg.itemEnderStar = Cfg.getItem("item.enderStar.id", startItemID, null); startItemID++;
		Cfg.itemEnderWand = Cfg.getItem("item.enderWand.id", startItemID, null); startItemID++;
		Cfg.itemEnderMagnet = Cfg.getItem("item.enderMagnet.id", startItemID, null); startItemID++;
		Cfg.itemEnderXT = Cfg.getItem("item.enderXT.id", startItemID, null); startItemID++;

		//////////

		Cfg.saveConfig();
	}
}
