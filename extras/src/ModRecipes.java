package com.qzx.au.extras;

import cpw.mods.fml.common.registry.GameRegistry;

IMPORT_BLOCKS
IMPORT_ITEMS
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.qzx.au.core.Color;

public class ModRecipes {
	public static void init(){
		ItemStack cobblestone = new ItemStack(MC_BLOCK.cobblestone);
		ItemStack stone = new ItemStack(MC_BLOCK.stone);
		ItemStack stoneBrick = new ItemStack(MC_BLOCK.stoneBrick);
		ItemStack chiseledBrick = new ItemStack(MC_BLOCK.stoneBrick, 1, 3);
		ItemStack gravel = new ItemStack(MC_BLOCK.gravel);
		ItemStack sand = new ItemStack(MC_BLOCK.sand);
		ItemStack grassBlock = new ItemStack(MC_BLOCK.grass);
		ItemStack vine = new ItemStack(MC_BLOCK.vine);
		ItemStack glass = new ItemStack(MC_BLOCK.glass);
		ItemStack glassPane = new ItemStack(MC_BLOCK.thinGlass);
		ItemStack ironBars = new ItemStack(MC_BLOCK.fenceIron);
		ItemStack redstoneTorch = new ItemStack(MC_BLOCK.torchRedstoneActive);
		ItemStack redstoneDust = new ItemStack(MC_ITEM.redstone);
		ItemStack glowstone = new ItemStack(MC_BLOCK.glowStone);
		#ifdef MC152
		ItemStack glowstoneDust = new ItemStack(MC_ITEM.lightStoneDust);
		#else
		ItemStack glowstoneDust = new ItemStack(MC_ITEM.glowstone);
		#endif
		ItemStack[] dyes = new ItemStack[16];
		for(int c = 0; c < 16; c++)
			dyes[c] = new ItemStack(MC_ITEM.dyePowder, 1, c);

		//////////

		if(Cfg.enableChromaInfuser){
			// CRAFT cauldron + red dye, green dye, blue dye -> chroma infuser
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(THIS_MOD.blockChromaInfuser), "rgb", " c ", "   ",
									'c', new ItemStack(MC_ITEM.cauldron), 'r', Color.oreDyes[1], 'g', Color.oreDyes[2], 'b', Color.oreDyes[4]));
		}

		//////////

		if(Cfg.enableCobble){
			ItemStack coloredCobble = new ItemStack(THIS_MOD.blockCobble);

			// CHROMA INFUSER recipes
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, cobblestone, coloredCobble);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, new ItemStack(THIS_MOD.blockCobble, 1, c), coloredCobble);
			// SMELT <colored> cobble -> cobblestone
			GameRegistry.addSmelting(coloredCobble.itemID, cobblestone, 1.0f);

			// stairs
			if(Cfg.enableCobbleStairs)
				for(int c = 0; c < 16; c++)
					GameRegistry.addRecipe(new ItemStack(THIS_MOD.blockCobbleStairs[c], 4), "b  ", "bb ", "bbb", 'b', new ItemStack(THIS_MOD.blockCobble, 1, c));

			// slabs
			if(Cfg.enableCobbleSlabs)
				for(int c = 0; c < 16; c++)
					GameRegistry.addRecipe(new ItemStack(THIS_MOD.blockCobbleSlab[0], 6, c), "bbb", 'b', new ItemStack(THIS_MOD.blockCobble, 1, c));
		}

		//////////

		if(Cfg.enableStone){
			ItemStack coloredStone = new ItemStack(THIS_MOD.blockStone);

			// CHROMA INFUSER recipes
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, stone, coloredStone);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, new ItemStack(THIS_MOD.blockStone, 1, c), coloredStone);
			// SMELT <colored> stone -> stone
			GameRegistry.addSmelting(coloredStone.itemID, stone, 1.0f);

			// stairs
			if(Cfg.enableStoneStairs)
				for(int c = 0; c < 16; c++)
					GameRegistry.addRecipe(new ItemStack(THIS_MOD.blockStoneStairs[c], 4), "b  ", "bb ", "bbb", 'b', new ItemStack(THIS_MOD.blockStone, 1, c));

			// slabs
			if(Cfg.enableStoneSlabs)
				for(int c = 0; c < 16; c++)
					GameRegistry.addRecipe(new ItemStack(THIS_MOD.blockStoneSlab[0], 6, c), "bbb", 'b', new ItemStack(THIS_MOD.blockStone, 1, c));
		}

		//////////

		if(Cfg.enableStoneBrick){
			ItemStack coloredStoneBrick = new ItemStack(THIS_MOD.blockStoneBrick);

			// CHROMA INFUSER recipes
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, stoneBrick, coloredStoneBrick);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, new ItemStack(THIS_MOD.blockStoneBrick, 1, c), coloredStoneBrick);
			// SMELT <colored> stoneBrick -> stoneBrick
			GameRegistry.addSmelting(coloredStoneBrick.itemID, stoneBrick, 1.0f);

			// stairs
			if(Cfg.enableStoneBrickStairs)
				for(int c = 0; c < 16; c++)
					GameRegistry.addRecipe(new ItemStack(THIS_MOD.blockStoneBrickStairs[c], 4), "b  ", "bb ", "bbb", 'b', new ItemStack(THIS_MOD.blockStoneBrick, 1, c));

			// slabs
			if(Cfg.enableStoneBrickSlabs)
				for(int c = 0; c < 16; c++)
					GameRegistry.addRecipe(new ItemStack(THIS_MOD.blockStoneBrickSlab[0], 6, c), "bbb", 'b', new ItemStack(THIS_MOD.blockStoneBrick, 1, c));
		}

		//////////

		if(Cfg.enableChiseledBrick){
			ItemStack coloredChiseledBrick = new ItemStack(THIS_MOD.blockChiseledBrick);

			// CHROMA INFUSER recipes
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, chiseledBrick, coloredChiseledBrick);
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE_DOT, stone, coloredChiseledBrick);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, new ItemStack(THIS_MOD.blockChiseledBrick, 1, c), coloredChiseledBrick);
			// SMELT <colored> chiseledBrick -> chiseledBrick
			GameRegistry.addSmelting(coloredChiseledBrick.itemID, chiseledBrick, 1.0f);

			// no stairs -- texture doesn't align correctly

			// slabs
			if(Cfg.enableChiseledBrickSlabs)
				for(int c = 0; c < 16; c++)
					GameRegistry.addRecipe(new ItemStack(THIS_MOD.blockChiseledBrickSlab[0], 6, c), "bbb", 'b', new ItemStack(THIS_MOD.blockChiseledBrick, 1, c));
		}

		//////////

		if(Cfg.enableSmoothBrick){
			ItemStack coloredSmoothBrick = new ItemStack(THIS_MOD.blockSmoothBrick);

			// CHROMA INFUSER recipes
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE, stone, coloredSmoothBrick);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, new ItemStack(THIS_MOD.blockSmoothBrick, 1, c), coloredSmoothBrick);
			// SMELT <colored> smoothBrick -> stone
			GameRegistry.addSmelting(coloredSmoothBrick.itemID, stone, 1.0f);

			// stairs
			if(Cfg.enableSmoothBrickStairs)
				for(int c = 0; c < 16; c++)
					GameRegistry.addRecipe(new ItemStack(THIS_MOD.blockSmoothBrickStairs[c], 4), "b  ", "bb ", "bbb", 'b', new ItemStack(THIS_MOD.blockSmoothBrick, 1, c));

			// slabs
			if(Cfg.enableSmoothBrickSlabs)
				for(int c = 0; c < 16; c++)
					GameRegistry.addRecipe(new ItemStack(THIS_MOD.blockSmoothBrickSlab[0], 6, c), "bbb", 'b', new ItemStack(THIS_MOD.blockSmoothBrick, 1, c));
		}

		//////////

		if(Cfg.enableGravel){
			ItemStack coloredGravel = new ItemStack(THIS_MOD.blockGravel);

			// CHROMA INFUSER recipes
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, gravel, coloredGravel);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, new ItemStack(THIS_MOD.blockGravel, 1, c), coloredGravel);
			// SMELT <colored> gravel -> gravel
			GameRegistry.addSmelting(coloredGravel.itemID, gravel, 1.0f);

			// no stairs -- texture doesn't align correctly
			// slabs
			if(Cfg.enableGravelSlabs)
				for(int c = 0; c < 16; c++)
					GameRegistry.addRecipe(new ItemStack(THIS_MOD.blockGravelSlab[0], 6, c), "bbb", 'b', new ItemStack(THIS_MOD.blockGravel, 1, c));

		}

		//////////

		if(Cfg.enableSand){
			ItemStack coloredSand = new ItemStack(THIS_MOD.blockSand);

			// CHROMA INFUSER recipes
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, sand, coloredSand);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, new ItemStack(THIS_MOD.blockSand, 1, c), coloredSand);
			// SMELT <colored> sand -> sand
			GameRegistry.addSmelting(coloredSand.itemID, sand, 1.0f);

			// stairs
			if(Cfg.enableSandStairs)
				for(int c = 0; c < 16; c++)
					GameRegistry.addRecipe(new ItemStack(THIS_MOD.blockSandStairs[c], 4), "b  ", "bb ", "bbb", 'b', new ItemStack(THIS_MOD.blockSand, 1, c));

			// slabs
			if(Cfg.enableSandSlabs)
				for(int c = 0; c < 16; c++)
					GameRegistry.addRecipe(new ItemStack(THIS_MOD.blockSandSlab[0], 6, c), "bbb", 'b', new ItemStack(THIS_MOD.blockSand, 1, c));
		}

		//////////

		if(Cfg.enableArtificialGrass){
			ItemStack artificialGrass = new ItemStack(THIS_MOD.blockArtificialGrass);

			// CHROMA INFUSER recipes
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, grassBlock, artificialGrass);
			for(int s = 0; s < 16; s++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, new ItemStack(THIS_MOD.blockArtificialGrass, 1, s), artificialGrass);

			// stairs
			if(Cfg.enableArtificialGrassStairs)
				for(int c = 0; c < 16; c++)
					GameRegistry.addRecipe(new ItemStack(THIS_MOD.blockArtificialGrassStairs[c], 4), "b  ", "bb ", "bbb", 'b', new ItemStack(THIS_MOD.blockArtificialGrass, 1, c));

			// slabs
			if(Cfg.enableArtificialGrassSlabs)
				for(int c = 0; c < 16; c++)
					GameRegistry.addRecipe(new ItemStack(THIS_MOD.blockArtificialGrassSlab[0], 6, c), "bbb", 'b', new ItemStack(THIS_MOD.blockArtificialGrass, 1, c));
		}

		//////////

		if(Cfg.enableArtificialVine){
//			// CHROMA INFUSER recipes
//			for(int c = 0; c < 16; c++)
//				ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, c, vine, new ItemStack(THIS_MOD.blockArtificialVine[c]));
//			for(int c = 0; c < 16; c++)
//				for(int s = 0; s < 16; s++)
//					ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, c, new ItemStack(THIS_MOD.blockArtificialVine[s]), new ItemStack(THIS_MOD.blockArtificialVine[c]));
// TODO: chroma infuser can't yet map colors to separate output IDs

			// CRAFT 8 vines + dye -> artificial vine
			for(int c = 0; c < 16; c++)
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(THIS_MOD.blockArtificialVine[c], 8), "vvv", "vcv", "vvv", 'v', vine, 'c', Color.oreDyes[c]));
		}

		//////////

		if(Cfg.enableGlass){
			ItemStack coloredGlass = new ItemStack(THIS_MOD.blockGlass);

			// CHROMA INFUSER recipes
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE, glass, coloredGlass);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE, new ItemStack(THIS_MOD.blockGlass, 1, c), coloredGlass);
			// SMELT <colored> glass -> glass
			GameRegistry.addSmelting(coloredGlass.itemID, glass, 1.0f);
		}

		if(Cfg.enableGlassTinted){
			ItemStack coloredGlassTinted = new ItemStack(THIS_MOD.blockGlassTinted);

			// CHROMA INFUSER recipes
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE_DOT, glass, coloredGlassTinted);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE_DOT, new ItemStack(THIS_MOD.blockGlassTinted, 1, c), coloredGlassTinted);
			// SMELT <colored tinted> glass -> glass
			GameRegistry.addSmelting(coloredGlassTinted.itemID, glass, 1.0f);
		}

		if(Cfg.enableGlassTintedNoFrame){
			ItemStack coloredGlassTintedNoFrame = new ItemStack(THIS_MOD.blockGlassTintedNoFrame);

			// CHROMA INFUSER recipes
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_DOT, glass, coloredGlassTintedNoFrame);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_DOT, new ItemStack(THIS_MOD.blockGlassTintedNoFrame, 1, c), coloredGlassTintedNoFrame);
			// SMELT <tinted frameless> glass -> glass
			GameRegistry.addSmelting(coloredGlassTintedNoFrame.itemID, glass, 1.0f);
		}

		//////////

		if(Cfg.enableGlassPane){
			ItemStack coloredGlassPane = new ItemStack(THIS_MOD.blockGlassPane);

			// CHROMA INFUSER recipes
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE, glassPane, coloredGlassPane);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE, new ItemStack(THIS_MOD.blockGlassPane, 1, c), coloredGlassPane);
			// SMELT <colored> glass pane -> glass pane
			GameRegistry.addSmelting(coloredGlassPane.itemID, glassPane, 1.0f);

			// CRAFT 6 <colored> glass -> 16 <colored> glass panes
			for(int c = 0; c < 16; c++)
				GameRegistry.addShapedRecipe(new ItemStack(THIS_MOD.blockGlassPane, 16, c), "ggg", "ggg", "   ", 'g', new ItemStack(THIS_MOD.blockGlass, 1, c));
		}

		if(Cfg.enableGlassPaneTinted){
			ItemStack coloredGlassPaneTinted = new ItemStack(THIS_MOD.blockGlassPaneTinted);

			// CHROMA INFUSER recipes
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE_DOT, glassPane, coloredGlassPaneTinted);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE_DOT, new ItemStack(THIS_MOD.blockGlassPaneTinted, 1, c), coloredGlassPaneTinted);
			// SMELT <colored tinted> glass pane -> glass pane
			GameRegistry.addSmelting(coloredGlassPaneTinted.itemID, glassPane, 1.0f);

			// CRAFT 6 <colored> glass -> 16 <colored> glass panes
			for(int c = 0; c < 16; c++)
				GameRegistry.addShapedRecipe(new ItemStack(THIS_MOD.blockGlassPaneTinted, 16, c), "ggg", "ggg", "   ", 'g', new ItemStack(THIS_MOD.blockGlassTinted, 1, c));
		}

		if(Cfg.enableGlassPaneTintedNoFrame){
			ItemStack coloredGlassPaneTintedNoFrame = new ItemStack(THIS_MOD.blockGlassPaneTintedNoFrame);

			// CHROMA INFUSER recipes
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_DOT, glassPane, coloredGlassPaneTintedNoFrame);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_DOT, new ItemStack(THIS_MOD.blockGlassPaneTintedNoFrame, 1, c), coloredGlassPaneTintedNoFrame);
			// SMELT <tinted frameless> glass pane -> glass pane
			GameRegistry.addSmelting(coloredGlassPaneTintedNoFrame.itemID, glassPane, 1.0f);

			// CRAFT 6 <colored> glass -> 16 <colored> glass panes
			for(int c = 0; c < 16; c++)
				GameRegistry.addShapedRecipe(new ItemStack(THIS_MOD.blockGlassPaneTintedNoFrame, 16, c), "ggg", "ggg", "   ", 'g', new ItemStack(THIS_MOD.blockGlassTintedNoFrame, 1, c));
		}

		//////////

		// convert any colored glass to any other colored glass
		if(Cfg.enableGlass && Cfg.enableGlassTinted){
			ItemStack coloredGlass = new ItemStack(THIS_MOD.blockGlass);
			ItemStack coloredGlassTinted = new ItemStack(THIS_MOD.blockGlassTinted);
			for(int c = 0; c < 16; c++){
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE, new ItemStack(THIS_MOD.blockGlassTinted, 1, c), coloredGlass);
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE_DOT, new ItemStack(THIS_MOD.blockGlass, 1, c), coloredGlassTinted);
			}
		}
		if(Cfg.enableGlass && Cfg.enableGlassTintedNoFrame){
			ItemStack coloredGlass = new ItemStack(THIS_MOD.blockGlass);
			ItemStack coloredGlassTintedNoFrame = new ItemStack(THIS_MOD.blockGlassTintedNoFrame);
			for(int c = 0; c < 16; c++){
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE, new ItemStack(THIS_MOD.blockGlassTintedNoFrame, 1, c), coloredGlass);
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_DOT, new ItemStack(THIS_MOD.blockGlass, 1, c), coloredGlassTintedNoFrame);
			}
		}
		if(Cfg.enableGlassTinted && Cfg.enableGlassTintedNoFrame){
			ItemStack coloredGlassTinted = new ItemStack(THIS_MOD.blockGlassTinted);
			ItemStack coloredGlassTintedNoFrame = new ItemStack(THIS_MOD.blockGlassTintedNoFrame);
			for(int c = 0; c < 16; c++){
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE_DOT, new ItemStack(THIS_MOD.blockGlassTintedNoFrame, 1, c), coloredGlassTinted);
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_DOT, new ItemStack(THIS_MOD.blockGlassTinted, 1, c), coloredGlassTintedNoFrame);
			}
		}

		// convert any colored glass pane to any other colored glass pane
		if(Cfg.enableGlassPane && Cfg.enableGlassPaneTinted){
			ItemStack coloredGlassPane = new ItemStack(THIS_MOD.blockGlassPane);
			ItemStack coloredGlassPaneTinted = new ItemStack(THIS_MOD.blockGlassPaneTinted);
			for(int c = 0; c < 16; c++){
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE, new ItemStack(THIS_MOD.blockGlassPaneTinted, 1, c), coloredGlassPane);
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE_DOT, new ItemStack(THIS_MOD.blockGlassPane, 1, c), coloredGlassPaneTinted);
			}
		}
		if(Cfg.enableGlassPane && Cfg.enableGlassPaneTintedNoFrame){
			ItemStack coloredGlassPane = new ItemStack(THIS_MOD.blockGlassPane);
			ItemStack coloredGlassPaneTintedNoFrame = new ItemStack(THIS_MOD.blockGlassPaneTintedNoFrame);
			for(int c = 0; c < 16; c++){
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE, new ItemStack(THIS_MOD.blockGlassPaneTintedNoFrame, 1, c), coloredGlassPane);
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_DOT, new ItemStack(THIS_MOD.blockGlassPane, 1, c), coloredGlassPaneTintedNoFrame);
			}
		}
		if(Cfg.enableGlassPaneTinted && Cfg.enableGlassPaneTintedNoFrame){
			ItemStack coloredGlassPaneTinted = new ItemStack(THIS_MOD.blockGlassPaneTinted);
			ItemStack coloredGlassPaneTintedNoFrame = new ItemStack(THIS_MOD.blockGlassPaneTintedNoFrame);
			for(int c = 0; c < 16; c++){
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE_DOT, new ItemStack(THIS_MOD.blockGlassPaneTintedNoFrame, 1, c), coloredGlassPaneTinted);
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_DOT, new ItemStack(THIS_MOD.blockGlassPaneTinted, 1, c), coloredGlassPaneTintedNoFrame);
			}
		}

		//////////

		if(Cfg.enableIronBars){
			ItemStack coloredIronBars = new ItemStack(THIS_MOD.blockIronBars);

			// CHROMA INFUSER recipes
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE, ironBars, coloredIronBars);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE, new ItemStack(THIS_MOD.blockIronBars, 1, c), coloredIronBars);
			// SMELT <colored> iron bars -> iron bars
			GameRegistry.addSmelting(coloredIronBars.itemID, ironBars, 1.0f);
		}

		//////////

		if(Cfg.enableLamps){
			ItemStack coloredLamp = new ItemStack(THIS_MOD.blockLamp);

			if(Cfg.enableGlassPaneTinted){
				// CRAFT 6 tinted glass panes + dye + glowstone + redstone -> <colored> lamp
				for(int c = 0; c < 16; c++)
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(THIS_MOD.blockLamp, 1, c), "pgp", "p-p", "prp",
											'p', new ItemStack(THIS_MOD.blockGlassPaneTinted, 1, c), 'g', glowstone, '-', Color.oreDyes[c], 'r', redstoneDust));
			} else {
				// CRAFT 6 glass panes + dye + glowstone + redstone -> <colored> lamp
				for(int c = 0; c < 16; c++)
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(THIS_MOD.blockLamp, 1, c), "pgp", "p-p", "prp",
											'p', glassPane, 'g', glowstone, '-', Color.oreDyes[c], 'r', redstoneDust));
			}
			// CRAFT <colored> lamp + dye -> <colored> lamp
			for(int g = 0; g < 16; g++){
				ItemStack anyLamp = new ItemStack(THIS_MOD.blockLamp, 1, g);
				for(int c = 0; c < 16; c++)
					if(g != c)
						GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(THIS_MOD.blockLamp, 1, c), anyLamp, Color.oreDyes[c]));
			}

			//////////

			ItemStack coloredInvertedLamp = new ItemStack(THIS_MOD.blockInvertedLamp);

			if(Cfg.enableGlassPaneTinted){
				// CRAFT 6 glass panes + dye + glowstone + redstone torch -> <colored> inverted lamp
				for(int c = 0; c < 16; c++)
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(THIS_MOD.blockInvertedLamp, 1, c), "pgp", "p-p", "prp",
											'p', new ItemStack(THIS_MOD.blockGlassPaneTinted, 1, c), 'g', glowstone, '-', Color.oreDyes[c], 'r', redstoneTorch));
			} else {
				// CRAFT 6 glass panes + dye + glowstone + redstone torch -> <colored> inverted lamp
				for(int c = 0; c < 16; c++)
					GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(THIS_MOD.blockInvertedLamp, 1, c), "pgp", "p-p", "prp",
											'p', glassPane, 'g', glowstone, '-', Color.oreDyes[c], 'r', redstoneTorch));
			}
			// CRAFT <colored> inverted lamp + dye -> <colored> inverted lamp
			for(int g = 0; g < 16; g++){
				ItemStack anyInvertedLamp = new ItemStack(THIS_MOD.blockInvertedLamp, 1, g);
				for(int c = 0; c < 16; c++)
					if(g != c)
						GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(THIS_MOD.blockInvertedLamp, 1, c), anyInvertedLamp, Color.oreDyes[c]));
			}

			//////////

			for(int c = 0; c < 16; c++){
				// CRAFT <colored> lamp + redstone torch -> <colored> inverted lamp
				// CRAFT <colored> inverted lamp + redstone dust -> <colored> lamp
				ItemStack anyLamp = new ItemStack(THIS_MOD.blockLamp, 1, c);
				ItemStack anyInvertedLamp = new ItemStack(THIS_MOD.blockInvertedLamp, 1, c);
				GameRegistry.addShapelessRecipe(anyInvertedLamp, anyLamp, redstoneTorch);
				GameRegistry.addShapelessRecipe(anyLamp, anyInvertedLamp, redstoneDust);
			}
		}

		//////////

		if(Cfg.enableFlowers){
			// flower to dye recipes
			GameRegistry.addShapelessRecipe(new ItemStack(THIS_MOD.itemFlowerDye, 1, 0), new ItemStack(THIS_MOD.blockFlower, 1, 0)); // black
			GameRegistry.addShapelessRecipe(dyes[1],  new ItemStack(THIS_MOD.blockFlower, 1, 1)); // red
			GameRegistry.addShapelessRecipe(new ItemStack(THIS_MOD.itemFlowerDye, 1, 2), new ItemStack(THIS_MOD.blockFlower, 1, 2)); // green
			GameRegistry.addShapelessRecipe(new ItemStack(THIS_MOD.itemFlowerDye, 1, 3), new ItemStack(THIS_MOD.blockFlower, 1, 3)); // brown
			GameRegistry.addShapelessRecipe(new ItemStack(THIS_MOD.itemFlowerDye, 1, 4), new ItemStack(THIS_MOD.blockFlower, 1, 4)); // blue
			GameRegistry.addShapelessRecipe(dyes[5],  new ItemStack(THIS_MOD.blockFlower, 1, 5)); // purple
			GameRegistry.addShapelessRecipe(dyes[6],  new ItemStack(THIS_MOD.blockFlower, 1, 6)); // cyan
			GameRegistry.addShapelessRecipe(dyes[7],  new ItemStack(THIS_MOD.blockFlower, 1, 7)); // light gray
			GameRegistry.addShapelessRecipe(dyes[8],  new ItemStack(THIS_MOD.blockFlower, 1, 8)); // gray
			GameRegistry.addShapelessRecipe(dyes[9],  new ItemStack(THIS_MOD.blockFlower, 1, 9)); // pink
			GameRegistry.addShapelessRecipe(dyes[10], new ItemStack(THIS_MOD.blockFlower, 1, 10)); // lime
			GameRegistry.addShapelessRecipe(dyes[11], new ItemStack(THIS_MOD.blockFlower, 1, 11)); // yellow
			GameRegistry.addShapelessRecipe(dyes[12], new ItemStack(THIS_MOD.blockFlower, 1, 12)); // light blue
			GameRegistry.addShapelessRecipe(dyes[13], new ItemStack(THIS_MOD.blockFlower, 1, 13)); // magenta
			GameRegistry.addShapelessRecipe(dyes[14], new ItemStack(THIS_MOD.blockFlower, 1, 14)); // orange
			GameRegistry.addShapelessRecipe(new ItemStack(THIS_MOD.itemFlowerDye, 1, 15), new ItemStack(THIS_MOD.blockFlower, 1, 15)); // white
		}

		//////////

		if(Cfg.enableEnderCube){
			ItemStack enderCube = new ItemStack(THIS_MOD.blockEnderCube);

			// CRAFT glass + 3 eye of ender + redstone dust + 4 gold ingots -> ender cube
			GameRegistry.addRecipe(enderCube, "xex", "eoe", "xrx",
									'o', new ItemStack(MC_BLOCK.obsidian), 'e', new ItemStack(MC_ITEM.eyeOfEnder), 'r', redstoneDust, 'x', new ItemStack(MC_ITEM.ingotGold));

			//////////

			if(Cfg.enableEnderStar){
				ItemStack enderStar = new ItemStack(THIS_MOD.itemEnderStar);

				// CRAFT 8 ender cubes + 1 nether star -> ender star
				GameRegistry.addRecipe(enderStar, "ccc", "csc", "ccc", 'c', enderCube, 's', new ItemStack(MC_ITEM.netherStar));

				//////////

				if(Cfg.enableEnderWand){
					ItemStack enderWand = new ItemStack(THIS_MOD.itemEnderWand);

					// CRAFT ender star + 2 diamonds + 2 gold ingots -> ender wand
					GameRegistry.addRecipe(enderWand, " ds", " gd", "g  ", 's', enderStar, 'd', new ItemStack(MC_ITEM.diamond), 'g', new ItemStack(MC_ITEM.ingotGold));
				}

				//////////

				if(Cfg.enableEnderMagnet){
					ItemStack enderMagnet = new ItemStack(THIS_MOD.itemEnderMagnet);

					// CRAFT 5 ender stars + 2 diamonds -> ender magnet
					GameRegistry.addRecipe(enderMagnet, "s s", "s s", "dsd", 's', enderStar, 'd', new ItemStack(MC_ITEM.diamond));

					//////////

					if(Cfg.enableEnderXT){
						ItemStack enderXT = new ItemStack(THIS_MOD.itemEnderXT);

						// CRAFT 4 ender stars + 3 ender magnet + 2 diamonds -> ender xt
						GameRegistry.addRecipe(enderXT, "sds", "mdm", "sms", 's', enderStar, 'm', enderMagnet, 'd', new ItemStack(MC_ITEM.diamond));
					}
				}
			}
		}

		//////////

		// SMELT egg -> fried egg
		if(Cfg.enableFriedEgg){
			GameRegistry.addSmelting(MC_ITEM.egg.itemID, new ItemStack(THIS_MOD.itemFriedEgg), 1.0f);
		}

		//////////

		// SMELT rotten flesh -> cooked flesh
		if(Cfg.enableCookedFlesh){
			ItemStack cookedFlesh = new ItemStack(THIS_MOD.itemCookedFlesh);
			GameRegistry.addSmelting(MC_ITEM.rottenFlesh.itemID, cookedFlesh, 1.0f);

			// CRAFT cooked flesh -> leather
			if(Cfg.enableCookedFleshToLeather){
				ItemStack leather = new ItemStack(MC_ITEM.leather);
				switch(Cfg.nrCookedFleshToLeather){
				case 1: GameRegistry.addShapelessRecipe(leather, cookedFlesh); break;
				case 2: GameRegistry.addShapelessRecipe(leather, cookedFlesh, cookedFlesh); break;
				case 3: GameRegistry.addShapelessRecipe(leather, cookedFlesh, cookedFlesh, cookedFlesh); break;
				case 4: GameRegistry.addShapelessRecipe(leather, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh); break;
				case 5: GameRegistry.addShapelessRecipe(leather, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh); break;
				case 6: GameRegistry.addShapelessRecipe(leather, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh); break;
				case 7: GameRegistry.addShapelessRecipe(leather, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh); break;
				case 8: GameRegistry.addShapelessRecipe(leather, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh); break;
				default: GameRegistry.addShapelessRecipe(leather, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh); break;
				}
			}
		}

		//////////

		if(Cfg.enableChiseledBrickCrafting){
			// CRAFT 2 stoneBrick slabs -> chiseledBrick (1.8 recipe)
			GameRegistry.addRecipe(new ItemStack(MC_BLOCK.stoneBrick, 1, 3), "b", "b", 'b', new ItemStack(MC_BLOCK.stoneSingleSlab, 1, 5));
		}
		if(Cfg.enableMossyBrickCrafting){
			// CRAFT stoneBrick + vine -> mossy stone brick (1.8 recipe)
			GameRegistry.addShapelessRecipe(new ItemStack(MC_BLOCK.stoneBrick, 1, 1), stoneBrick, vine);
		}
		if(Cfg.enableMossyCobbleCrafting){
			// CRAFT cobblestone + vine -> mossy cobble (1.8 recipe)
			GameRegistry.addShapelessRecipe(new ItemStack(MC_BLOCK.cobblestoneMossy), cobblestone, vine);
		}
		if(Cfg.enableCrackedBrickCrafting){
			// CRAFT stoneBrick + ice -> cracked stone brick
			GameRegistry.addShapelessRecipe(new ItemStack(MC_BLOCK.stoneBrick, 1, 2), stoneBrick, new ItemStack(MC_BLOCK.ice));
		}
		if(Cfg.enableGrassBlockCrafting){
			// CRAFT tall grass + dirt -> grass block
			GameRegistry.addShapelessRecipe(grassBlock, new ItemStack(MC_BLOCK.tallGrass, 1, 1), new ItemStack(MC_BLOCK.dirt));
		}
		if(Cfg.enableMyceliumCrafting){
			// CRAFT brown mushroom + red mushroom + grass block -> mycelium block
			GameRegistry.addShapelessRecipe(new ItemStack(MC_BLOCK.mycelium), new ItemStack(MC_BLOCK.mushroomBrown), new ItemStack(MC_BLOCK.mushroomRed), grassBlock);
		}

		//////////

		// vanilla wool
		for(int c = 0; c < 16; c++)
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, new ItemStack(MC_BLOCK.cloth, 1, c), new ItemStack(MC_BLOCK.cloth), true);

		#ifndef MC152
			// vanilla hardened clay (1.6)
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, new ItemStack(MC_BLOCK.hardenedClay), new ItemStack(MC_BLOCK.stainedClay), true);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, new ItemStack(MC_BLOCK.stainedClay, 1, c), new ItemStack(MC_BLOCK.stainedClay), true);
			// vanilla carpet (1.6)
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, new ItemStack(MC_BLOCK.carpet, 1, c), new ItemStack(MC_BLOCK.carpet), true);
		#endif

		#if !defined MC152 && !defined MC164
			// vanilla stained glass (1.7)
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, new ItemStack(glass), new ItemStack(MC_BLOCK.stained_glass), true);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, new ItemStack(MC_BLOCK.stained_glass, 1, c), new ItemStack(MC_BLOCK.stained_glass), true);
			// vanilla stained glass panes (1.7)
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, new ItemStack(glassPane), new ItemStack(MC_BLOCK.stained_glass_pane), true);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, new ItemStack(MC_BLOCK.stained_glass_pane, 1, c), new ItemStack(MC_BLOCK.stained_glass_pane), true);
		#endif
	}
}
