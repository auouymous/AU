package com.qzx.au.extras;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.MinecraftForge;

import com.qzx.au.core.Color;

public class ModBlocks {
	public static void init(){
		if(Cfg.enableChromaInfuser){
			THIS_MOD.blockChromaInfuser = new BlockChromaInfuser(Cfg.blockChromaInfuser, "au.chromaInfuser")
				.setHardness(2.0F)
				.setResistance(10.0F)
				.setStepSound(Block.soundMetalFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
			SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockChromaInfuser, "pickaxe", 0); // wooden pickaxe
		}

		//////////

		if(Cfg.enableStoneHalfSlabs){
			THIS_MOD.blockStoneHalfSlabs = new BlockColoredHalfSlab(Cfg.blockStoneHalfSlabs, "au.colorStoneHalfSlabs", ItemBlockColored.class, Material.rock)
				.setHardness(2.0F)
				.setResistance(10.0F)
				.setStepSound(Block.soundStoneFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
			SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockStoneHalfSlabs, "pickaxe", 0); // wooden pickaxe

			THIS_MOD.blockStoneHalfSlabsSmooth = new BlockColoredHalfSlab(Cfg.blockStoneHalfSlabsSmooth, "au.colorStoneHalfSlabsSmooth", ItemBlockColored.class, THIS_MOD.blockStoneHalfSlabs)
				.setCreativeTab(THIS_MOD.tabAU);
			SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockStoneHalfSlabsSmooth, "pickaxe", 0); // wooden pickaxe

			THIS_MOD.blockStoneHalfSlab[0] = new BlockColoredHalfSlab(Cfg.blockStoneHalfSlab0, "au.colorStoneHalfSlab", ItemBlockColoredHalfSlab.class, THIS_MOD.blockStoneHalfSlabs, false)
				.setCreativeTab(THIS_MOD.tabAU);
			SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockStoneHalfSlab[0], "pickaxe", 0); // wooden pickaxe

			THIS_MOD.blockStoneHalfSlab[1] = new BlockColoredHalfSlab(Cfg.blockStoneHalfSlab1, "au.colorStoneHalfSlabUpper", null, THIS_MOD.blockStoneHalfSlabs, false);
			SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockStoneHalfSlab[1], "pickaxe", 0); // wooden pickaxe

			((BlockColoredHalfSlab)THIS_MOD.blockStoneHalfSlabsSmooth).setSmoothBlock(THIS_MOD.blockStoneHalfSlabs);
			((BlockColoredHalfSlab)THIS_MOD.blockStoneHalfSlabs).setFullBlock(THIS_MOD.blockStoneHalfSlab[0]);
			((BlockColoredHalfSlab)THIS_MOD.blockStoneHalfSlab[0]).setUpperBlock(THIS_MOD.blockStoneHalfSlabs, THIS_MOD.blockStoneHalfSlab[1]);
			((BlockColoredHalfSlab)THIS_MOD.blockStoneHalfSlab[1]).setLowerBlock(THIS_MOD.blockStoneHalfSlabs, THIS_MOD.blockStoneHalfSlab[0]);
		}

		//////////

		if(Cfg.enableCobble){
			THIS_MOD.blockCobble = new BlockColored(Cfg.blockCobble, "au.colorCobble", ItemBlockColored.class, Material.rock)
				.setHardness(2.0F)
				.setResistance(10.0F)
				.setStepSound(Block.soundStoneFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
			SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockCobble, "pickaxe", 0); // wooden pickaxe

			// stairs
			if(Cfg.enableCobbleStairs)
				for(int c = 0; c < 16; c++){
					THIS_MOD.blockCobbleStairs[c] = new BlockStairsColored(Cfg.blockCobbleStairs+c, "au.colorCobbleStairs."+Color.colors[c], THIS_MOD.blockCobble, c)
						.setCreativeTab(THIS_MOD.tabAU);
					SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockCobbleStairs[c], "pickaxe", 0); // wooden pickaxe
				}

			// slabs
			if(Cfg.enableCobbleSlabs){
				THIS_MOD.blockCobbleSlab[0] = new BlockColoredSlab(Cfg.blockCobbleSlab0, "au.colorCobbleSlab", ItemBlockColoredSlab.class, THIS_MOD.blockCobble, false)
					.setCreativeTab(THIS_MOD.tabAU);
				SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockCobbleSlab[0], "pickaxe", 0); // wooden pickaxe

				THIS_MOD.blockCobbleSlab[1] = new BlockColoredSlab(Cfg.blockCobbleSlab1, "au.colorCobbleSlabUpper", null, THIS_MOD.blockCobble, false);
				SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockCobbleSlab[1], "pickaxe", 0); // wooden pickaxe

				((BlockColoredSlab)THIS_MOD.blockCobbleSlab[0]).setUpperBlock(THIS_MOD.blockCobbleSlab[1]);
				((BlockColoredSlab)THIS_MOD.blockCobbleSlab[1]).setLowerBlock(THIS_MOD.blockCobbleSlab[0]);
			}
		}

		//////////

		if(Cfg.enableStone){
			THIS_MOD.blockStone = new BlockColored(Cfg.blockStone, "au.colorStone", ItemBlockColored.class, Material.rock)
				.setHardness(1.5F)
				.setResistance(10.0F)
				.setStepSound(Block.soundStoneFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
			SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockStone, "pickaxe", 0); // wooden pickaxe

			// stairs
			if(Cfg.enableStoneStairs)
				for(int c = 0; c < 16; c++){
					THIS_MOD.blockStoneStairs[c] = new BlockStairsColored(Cfg.blockStoneStairs+c, "au.colorStoneStairs."+Color.colors[c], THIS_MOD.blockStone, c)
						.setCreativeTab(THIS_MOD.tabAU);
					SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockStoneStairs[c], "pickaxe", 0); // wooden pickaxe
				}

			// slabs
			if(Cfg.enableStoneSlabs){
				THIS_MOD.blockStoneSlab[0] = new BlockColoredSlab(Cfg.blockStoneSlab0, "au.colorStoneSlab", ItemBlockColoredSlab.class, THIS_MOD.blockStone, false)
					.setCreativeTab(THIS_MOD.tabAU);
				SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockStoneSlab[0], "pickaxe", 0); // wooden pickaxe

				THIS_MOD.blockStoneSlab[1] = new BlockColoredSlab(Cfg.blockStoneSlab1, "au.colorStoneSlabUpper", null, THIS_MOD.blockStone, false);
				SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockStoneSlab[1], "pickaxe", 0); // wooden pickaxe

				((BlockColoredSlab)THIS_MOD.blockStoneSlab[0]).setUpperBlock(THIS_MOD.blockStoneSlab[1]);
				((BlockColoredSlab)THIS_MOD.blockStoneSlab[1]).setLowerBlock(THIS_MOD.blockStoneSlab[0]);
			}
		}

		//////////

		if(Cfg.enableStoneBrick){
			THIS_MOD.blockStoneBrick = new BlockColored(Cfg.blockStoneBrick, "au.colorStoneBrick", ItemBlockColored.class, Material.rock)
				.setHardness(1.5F)
				.setResistance(10.0F)
				.setStepSound(Block.soundStoneFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
			SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockStoneBrick, "pickaxe", 0); // wooden pickaxe

			// stairs
			if(Cfg.enableStoneBrickStairs)
				for(int c = 0; c < 16; c++){
					THIS_MOD.blockStoneBrickStairs[c] = new BlockStairsColored(Cfg.blockStoneBrickStairs+c, "au.colorStoneBrickStairs."+Color.colors[c], THIS_MOD.blockStoneBrick, c)
						.setCreativeTab(THIS_MOD.tabAU);
					SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockStoneBrickStairs[c], "pickaxe", 0); // wooden pickaxe
				}

			// slabs
			if(Cfg.enableStoneBrickSlabs){
				THIS_MOD.blockStoneBrickSlab[0] = new BlockColoredSlab(Cfg.blockStoneBrickSlab0, "au.colorStoneBrickSlab", ItemBlockColoredSlab.class, THIS_MOD.blockStoneBrick, false)
					.setCreativeTab(THIS_MOD.tabAU);
				SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockStoneBrickSlab[0], "pickaxe", 0); // wooden pickaxe

				THIS_MOD.blockStoneBrickSlab[1] = new BlockColoredSlab(Cfg.blockStoneBrickSlab1, "au.colorStoneBrickSlabUpper", null, THIS_MOD.blockStoneBrick, false);
				SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockStoneBrickSlab[1], "pickaxe", 0); // wooden pickaxe

				((BlockColoredSlab)THIS_MOD.blockStoneBrickSlab[0]).setUpperBlock(THIS_MOD.blockStoneBrickSlab[1]);
				((BlockColoredSlab)THIS_MOD.blockStoneBrickSlab[1]).setLowerBlock(THIS_MOD.blockStoneBrickSlab[0]);
			}
		}

		//////////

		if(Cfg.enableChiseledBrick){
			THIS_MOD.blockChiseledBrick = new BlockColored(Cfg.blockChiseledBrick, "au.colorChiseledBrick", ItemBlockColored.class, Material.rock)
				.setHardness(1.5F)
				.setResistance(10.0F)
				.setStepSound(Block.soundStoneFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
			SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockChiseledBrick, "pickaxe", 0); // wooden pickaxe

			// no stairs -- texture doesn't align correctly

			// slabs
			if(Cfg.enableChiseledBrickSlabs){
				THIS_MOD.blockChiseledBrickSlab[0] = new BlockColoredSlab(Cfg.blockChiseledBrickSlab0, "au.colorChiseledBrickSlab", ItemBlockColoredSlab.class,
					THIS_MOD.blockChiseledBrick, true)
					.setCreativeTab(THIS_MOD.tabAU);
				SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockChiseledBrickSlab[0], "pickaxe", 0); // wooden pickaxe

				THIS_MOD.blockChiseledBrickSlab[1] = new BlockColoredSlab(Cfg.blockChiseledBrickSlab1, "au.colorChiseledBrickSlabUpper", null, THIS_MOD.blockChiseledBrick, true);
				SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockChiseledBrickSlab[1], "pickaxe", 0); // wooden pickaxe

				((BlockColoredSlab)THIS_MOD.blockChiseledBrickSlab[0]).setUpperBlock(THIS_MOD.blockChiseledBrickSlab[1]);
				((BlockColoredSlab)THIS_MOD.blockChiseledBrickSlab[1]).setLowerBlock(THIS_MOD.blockChiseledBrickSlab[0]);
			}
		}

		//////////

		if(Cfg.enableSmoothBrick){
			THIS_MOD.blockSmoothBrick = new BlockColored(Cfg.blockSmoothBrick, "au.colorSmoothBrick", ItemBlockColored.class, Material.rock)
				.setHardness(1.5F)
				.setResistance(10.0F)
				.setStepSound(Block.soundStoneFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
			SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockSmoothBrick, "pickaxe", 0); // wooden pickaxe

			// stairs
			if(Cfg.enableSmoothBrickStairs)
				for(int c = 0; c < 16; c++){
					THIS_MOD.blockSmoothBrickStairs[c] = new BlockStairsColored(Cfg.blockSmoothBrickStairs+c, "au.colorSmoothBrickStairs."+Color.colors[c], THIS_MOD.blockSmoothBrick, c)
						.setCreativeTab(THIS_MOD.tabAU);
					SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockSmoothBrickStairs[c], "pickaxe", 0); // wooden pickaxe
				}

			// slabs
			if(Cfg.enableSmoothBrickSlabs){
				THIS_MOD.blockSmoothBrickSlab[0] = new BlockColoredSlab(Cfg.blockSmoothBrickSlab0, "au.colorSmoothBrickSlab", ItemBlockColoredSlab.class, THIS_MOD.blockSmoothBrick, true)
					.setCreativeTab(THIS_MOD.tabAU);
				SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockSmoothBrickSlab[0], "pickaxe", 0); // wooden pickaxe

				THIS_MOD.blockSmoothBrickSlab[1] = new BlockColoredSlab(Cfg.blockSmoothBrickSlab1, "au.colorSmoothBrickSlabUpper", null, THIS_MOD.blockSmoothBrick, true);
				SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockSmoothBrickSlab[1], "pickaxe", 0); // wooden pickaxe

				((BlockColoredSlab)THIS_MOD.blockSmoothBrickSlab[0]).setUpperBlock(THIS_MOD.blockSmoothBrickSlab[1]);
				((BlockColoredSlab)THIS_MOD.blockSmoothBrickSlab[1]).setLowerBlock(THIS_MOD.blockSmoothBrickSlab[0]);
			}
		}

		//////////

		if(Cfg.enableGravel){
			THIS_MOD.blockGravel = new BlockColored(Cfg.blockGravel, "au.colorGravel", ItemBlockColored.class, Material.sand)
				.setHardness(0.6F)
				.setResistance(3.0F)
				.setStepSound(Block.soundGravelFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
			SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockGravel, "shovel", 0); // wooden shovel

			// no stairs -- texture doesn't align correctly

			// slabs
			if(Cfg.enableGravelSlabs){
				THIS_MOD.blockGravelSlab[0] = new BlockColoredSlab(Cfg.blockGravelSlab0, "au.colorGravelSlab", ItemBlockColoredSlab.class, THIS_MOD.blockGravel, false)
					.setCreativeTab(THIS_MOD.tabAU);
				SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockGravelSlab[0], "pickaxe", 0); // wooden pickaxe

				THIS_MOD.blockGravelSlab[1] = new BlockColoredSlab(Cfg.blockGravelSlab1, "au.colorGravelSlabUpper", null, THIS_MOD.blockGravel, false);
				SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockGravelSlab[1], "pickaxe", 0); // wooden pickaxe

				((BlockColoredSlab)THIS_MOD.blockGravelSlab[0]).setUpperBlock(THIS_MOD.blockGravelSlab[1]);
				((BlockColoredSlab)THIS_MOD.blockGravelSlab[1]).setLowerBlock(THIS_MOD.blockGravelSlab[0]);
			}
		}

		//////////

		if(Cfg.enableSand){
			THIS_MOD.blockSand = new BlockColored(Cfg.blockSand, "au.colorSand", ItemBlockColored.class, Material.sand)
				.setHardness(0.5F)
				.setResistance(2.5F)
				.setStepSound(Block.soundSandFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
			SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockSand, "shovel", 0); // wooden shovel

			// stairs
			if(Cfg.enableSandStairs)
				for(int c = 0; c < 16; c++){
					THIS_MOD.blockSandStairs[c] = new BlockStairsColored(Cfg.blockSandStairs+c, "au.colorSandStairs."+Color.colors[c], THIS_MOD.blockSand, c)
						.setCreativeTab(THIS_MOD.tabAU);
					SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockSandStairs[c], "pickaxe", 0); // wooden pickaxe
				}

			// slabs
			if(Cfg.enableSandSlabs){
				THIS_MOD.blockSandSlab[0] = new BlockColoredSlab(Cfg.blockSandSlab0, "au.colorSandSlab", ItemBlockColoredSlab.class, THIS_MOD.blockSand, true)
					.setCreativeTab(THIS_MOD.tabAU);
				SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockSandSlab[0], "pickaxe", 0); // wooden pickaxe

				THIS_MOD.blockSandSlab[1] = new BlockColoredSlab(Cfg.blockSandSlab1, "au.colorSandSlabUpper", null, THIS_MOD.blockSand, true);
				SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockSandSlab[1], "pickaxe", 0); // wooden pickaxe

				((BlockColoredSlab)THIS_MOD.blockSandSlab[0]).setUpperBlock(THIS_MOD.blockSandSlab[1]);
				((BlockColoredSlab)THIS_MOD.blockSandSlab[1]).setLowerBlock(THIS_MOD.blockSandSlab[0]);
			}
		}

		//////////

		if(Cfg.enableArtificialGrass){
			THIS_MOD.blockArtificialGrass = new BlockArtificialGrass(Cfg.blockArtificialGrass, "au.artificialGrass")
				.setHardness(0.6F)
				.setResistance(3.0F)
				.setStepSound(Block.soundGrassFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
			SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockArtificialGrass, "shovel", 0); // wooden shovel

			// stairs
			if(Cfg.enableArtificialGrassStairs)
				for(int c = 0; c < 16; c++){
					THIS_MOD.blockArtificialGrassStairs[c] = new BlockStairsColored(Cfg.blockArtificialGrassStairs+c, "au.artificialGrassStairs."+c, THIS_MOD.blockArtificialGrass, c)
						.setCreativeTab(THIS_MOD.tabAU);
					SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockArtificialGrassStairs[c], "pickaxe", 0); // wooden pickaxe
				}

			// slabs
			if(Cfg.enableArtificialGrassSlabs){
				THIS_MOD.blockArtificialGrassSlab[0] = new BlockColoredSlab(Cfg.blockArtificialGrassSlab0, "au.artificialGrassSlab", ItemBlockArtificialGrassSlab.class,
					THIS_MOD.blockArtificialGrass, false)
					.setCreativeTab(THIS_MOD.tabAU);
				SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockArtificialGrassSlab[0], "shovel", 0); // wooden shovel

				THIS_MOD.blockArtificialGrassSlab[1] = new BlockColoredSlab(Cfg.blockArtificialGrassSlab1, "au.artificialGrassSlabUpper", null, THIS_MOD.blockArtificialGrass, false);
				SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockArtificialGrassSlab[1], "shovel", 0); // wooden shovel

				((BlockColoredSlab)THIS_MOD.blockArtificialGrassSlab[0]).setUpperBlock(THIS_MOD.blockArtificialGrassSlab[1]);
				((BlockColoredSlab)THIS_MOD.blockArtificialGrassSlab[1]).setLowerBlock(THIS_MOD.blockArtificialGrassSlab[0]);
			}
		}

		//////////

		if(Cfg.enableArtificialVine){
			for(int c = 0; c < 16; c++){
				THIS_MOD.blockArtificialVine[c] = new BlockArtificialVine(Cfg.blockArtificialVine+c, "au.artificialVine."+c, c)
					.setHardness(0.2F)
					.setResistance(1.0F)
					.setStepSound(Block.soundGrassFootstep)
					.setCreativeTab(THIS_MOD.tabAU);
				Block.setBurnProperties(THIS_MOD.blockArtificialVine[c].blockID, 15, 100); // flammable
			}
		}

		//////////

		if(Cfg.enableGlass){
			THIS_MOD.blockGlass = new BlockGlass(Cfg.blockGlass, "au.colorGlass", ItemBlockColoredIcon.class, 0)
				.setHardness(0.3F)
				.setResistance(1.5F)
				.setStepSound(Block.soundGlassFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
		}

		if(Cfg.enableGlassTinted){
			THIS_MOD.blockGlassTinted = new BlockGlass(Cfg.blockGlassTinted, "au.colorGlassTinted", ItemBlockColoredIcon.class, 1)
				.setHardness(0.3F)
				.setResistance(1.5F)
				.setStepSound(Block.soundGlassFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
		}

		if(Cfg.enableGlassTintedNoFrame){
			THIS_MOD.blockGlassTintedNoFrame = new BlockGlass(Cfg.blockGlassTintedNoFrame, "au.colorGlassTintedNoFrame", ItemBlockColoredIcon.class, 2)
				.setHardness(0.3F)
				.setResistance(1.5F)
				.setStepSound(Block.soundGlassFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
		}

		//////////

		if(Cfg.enableGlassPane){
			THIS_MOD.blockGlassPane = new BlockColoredPane(Cfg.blockGlassPane, "au.colorGlassPane", ItemBlockColoredIcon.class, Material.glass, THIS_MOD.blockGlass, true, 0)
				.setHardness(0.3F)
				.setResistance(1.5F)
				.setStepSound(Block.soundGlassFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
		}

		if(Cfg.enableGlassPaneTinted){
			THIS_MOD.blockGlassPaneTinted = new BlockColoredPane(Cfg.blockGlassPaneTinted, "au.colorGlassPaneTinted",
																	ItemBlockColoredIcon.class, Material.glass, THIS_MOD.blockGlassTinted, true, 1)
				.setHardness(0.3F)
				.setResistance(1.5F)
				.setStepSound(Block.soundGlassFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
		}

		if(Cfg.enableGlassPaneTintedNoFrame){
			THIS_MOD.blockGlassPaneTintedNoFrame = new BlockColoredPane(Cfg.blockGlassPaneTintedNoFrame, "au.colorGlassPaneTintedNoFrame",
																	ItemBlockColoredIcon.class, Material.glass, THIS_MOD.blockGlassTintedNoFrame, false, 1)
				.setHardness(0.3F)
				.setResistance(1.5F)
				.setStepSound(Block.soundGlassFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
		}

		//////////

		if(Cfg.enableIronBars){
			THIS_MOD.blockIronBars = new BlockColoredPane(Cfg.blockIronBars, "au.colorIronBars", ItemBlockColoredIcon.class, Material.iron, null, false, 0)
				.setHardness(5.0F)
				.setResistance(10.0F)
				.setStepSound(Block.soundMetalFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
		}

		//////////

		if(Cfg.enableLamps){
			THIS_MOD.blockLamp = new BlockLamp(Cfg.blockLamp, "au.colorLamp", false, false)
				.setHardness(0.6F)
				.setResistance(3.0F)
				.setStepSound(Block.soundGlassFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
			THIS_MOD.blockLampPowered = new BlockLamp(Cfg.blockLampPowered, "au.colorLampPowered", false, true)
				.setHardness(0.6F)
				.setResistance(3.0F)
				.setStepSound(Block.soundGlassFootstep);

			//////////

			THIS_MOD.blockInvertedLamp = new BlockLamp(Cfg.blockInvertedLamp, "au.colorInvertedLamp", true, false)
				.setHardness(0.6F)
				.setResistance(3.0F)
				.setStepSound(Block.soundGlassFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
			THIS_MOD.blockInvertedLampPowered = new BlockLamp(Cfg.blockInvertedLampPowered, "au.colorInvertedLampPowered", true, true)
				.setHardness(0.6F)
				.setResistance(3.0F)
				.setStepSound(Block.soundGlassFootstep);
		}

		//////////

		if(Cfg.enableFlowers){
			THIS_MOD.blockFlower = new BlockFlower(Cfg.blockFlower, "au.colorFlower")
				.setHardness(0.0F)
				.setResistance(0.0F)
				.setStepSound(Block.soundGrassFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
			THIS_MOD.blockFlowerSeed = new BlockFlowerSeed(Cfg.blockFlowerSeed, "au.colorFlowerSeed")
				.setHardness(0.0F)
				.setResistance(0.0F)
				.setStepSound(Block.soundGrassFootstep)
				.setCreativeTab(THIS_MOD.tabAU);

			// bonemeal grass grows flowers
			for(int c = 0; c < 16; c++)
				MinecraftForge.addGrassPlant(THIS_MOD.blockFlower, c, 1);
			// breaking grass drops flower seed
			MinecraftForge.addGrassSeed(new ItemStack(THIS_MOD.blockFlowerSeed), 1);

			// generate flowers in world
			if(Cfg.enableFlowerWorldGen)
				GameRegistry.registerWorldGenerator(new WorldGenFlowers(THIS_MOD.blockFlower.blockID));
		}

		//////////

		if(Cfg.enableEnderCube){
			THIS_MOD.blockEnderCube = new BlockEnderCube(Cfg.blockEnderCube, "au.enderCube")
				.setHardness(5.0F)
				.setResistance(25.0F)
				.setStepSound(Block.soundStoneFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
			SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockEnderCube, "pickaxe", 3); // diamond pickaxe
		}

		//////////

		if(Cfg.enablePlanks){
			String[] names = new String[]{"Birch", "Oak", "Spruce"};
			for(int i = 0; i < 3; i++){
				String name = names[i];

				THIS_MOD.blockPlank[i] = new BlockColored(Cfg.blockPlank[i], String.format("au.colorPlank%s", name), ItemBlockColored.class, Material.wood)
					.setHardness(2.0F)
					.setResistance(5.0F)
					.setStepSound(Block.soundWoodFootstep)
					.setCreativeTab(THIS_MOD.tabAU);
				SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockPlank[i], "axe", 0); // wooden axe
				Block.setBurnProperties(THIS_MOD.blockPlank[i].blockID, 5, 20); // flammable

				// stairs
				if(Cfg.enablePlankStairs)
					for(int c = 0; c < 16; c++){
						int ii = (i<<4);
						THIS_MOD.blockPlankStairs[ii+c]
							= new BlockStairsColored(Cfg.blockPlankStairs[i]+c, String.format("au.colorPlank%sStairs.%s", name, Color.colors[c]), THIS_MOD.blockPlank[i], c)
							.setCreativeTab(THIS_MOD.tabAU);
						SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockPlankStairs[ii+c], "pickaxe", 0); // wooden pickaxe
						Block.setBurnProperties(THIS_MOD.blockPlankStairs[ii+c].blockID, 5, 20); // flammable
					}

				// slabs
				if(Cfg.enablePlankSlabs){
					int ii = (i<<1);
					THIS_MOD.blockPlankSlab[ii+0] = new BlockColoredSlab(Cfg.blockPlankSlab0[i], String.format("au.colorPlank%sSlab", name), ItemBlockColoredSlab.class, THIS_MOD.blockPlank[i], true)
						.setCreativeTab(THIS_MOD.tabAU);
					SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockPlankSlab[ii+0], "pickaxe", 0); // wooden pickaxe
					Block.setBurnProperties(THIS_MOD.blockPlankSlab[ii+0].blockID, 5, 20); // flammable

					THIS_MOD.blockPlankSlab[ii+1] = new BlockColoredSlab(Cfg.blockPlankSlab1[i], String.format("au.colorPlank%sSlabUpper", name), null, THIS_MOD.blockPlank[i], true);
					SET_BLOCK_HARVEST_TOOL(THIS_MOD.blockPlankSlab[ii+1], "pickaxe", 0); // wooden pickaxe
					Block.setBurnProperties(THIS_MOD.blockPlankSlab[ii+1].blockID, 5, 20); // flammable

					((BlockColoredSlab)THIS_MOD.blockPlankSlab[ii+0]).setUpperBlock(THIS_MOD.blockPlankSlab[ii+1]);
					((BlockColoredSlab)THIS_MOD.blockPlankSlab[ii+1]).setLowerBlock(THIS_MOD.blockPlankSlab[ii+0]);
				}
			}
		}
	}
}
