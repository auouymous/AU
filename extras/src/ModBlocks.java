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
			MinecraftForge.setBlockHarvestLevel(THIS_MOD.blockChromaInfuser, "pickaxe", 0); // wooden pickaxe
		}

		//////////

		if(Cfg.enableCobble){
			THIS_MOD.blockCobble = new BlockColored(Cfg.blockCobble, "au.colorCobble", ItemBlockCobble.class, Material.rock)
				.setHardness(2.0F)
				.setResistance(10.0F)
				.setStepSound(Block.soundStoneFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
			MinecraftForge.setBlockHarvestLevel(THIS_MOD.blockCobble, "pickaxe", 0); // wooden pickaxe

			// stairs
			if(Cfg.enableCobbleStairs)
				for(int c = 0; c < 16; c++){
					THIS_MOD.blockCobbleStairs[c] = new BlockStairsColored(Cfg.blockCobbleStairs+c, "au.colorCobbleStairs."+Color.colors[c], THIS_MOD.blockCobble, c);
					MinecraftForge.setBlockHarvestLevel(THIS_MOD.blockCobbleStairs[c], "pickaxe", 0); // wooden pickaxe
				}
		}

		//////////

		if(Cfg.enableStone){
			THIS_MOD.blockStone = new BlockColored(Cfg.blockStone, "au.colorStone", ItemBlockStone.class, Material.rock)
				.setHardness(1.5F)
				.setResistance(10.0F)
				.setStepSound(Block.soundStoneFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
			MinecraftForge.setBlockHarvestLevel(THIS_MOD.blockStone, "pickaxe", 0); // wooden pickaxe

			// stairs
			if(Cfg.enableStoneStairs)
				for(int c = 0; c < 16; c++){
					THIS_MOD.blockStoneStairs[c] = new BlockStairsColored(Cfg.blockStoneStairs+c, "au.colorStoneStairs."+Color.colors[c], THIS_MOD.blockStone, c);
					MinecraftForge.setBlockHarvestLevel(THIS_MOD.blockStoneStairs[c], "pickaxe", 0); // wooden pickaxe
				}
		}

		//////////

		if(Cfg.enableStoneBrick){
			THIS_MOD.blockStoneBrick = new BlockColored(Cfg.blockStoneBrick, "au.colorStoneBrick", ItemBlockStoneBrick.class, Material.rock)
				.setHardness(1.5F)
				.setResistance(10.0F)
				.setStepSound(Block.soundStoneFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
			MinecraftForge.setBlockHarvestLevel(THIS_MOD.blockStoneBrick, "pickaxe", 0); // wooden pickaxe

			// stairs
			if(Cfg.enableStoneBrickStairs)
				for(int c = 0; c < 16; c++){
					THIS_MOD.blockStoneBrickStairs[c] = new BlockStairsColored(Cfg.blockStoneBrickStairs+c, "au.colorStoneBrickStairs."+Color.colors[c], THIS_MOD.blockStoneBrick, c);
					MinecraftForge.setBlockHarvestLevel(THIS_MOD.blockStoneBrickStairs[c], "pickaxe", 0); // wooden pickaxe
				}
		}

		//////////

		if(Cfg.enableChiseledBrick){
			THIS_MOD.blockChiseledBrick = new BlockColored(Cfg.blockChiseledBrick, "au.colorChiseledBrick", ItemBlockChiseledBrick.class, Material.rock)
				.setHardness(1.5F)
				.setResistance(10.0F)
				.setStepSound(Block.soundStoneFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
			MinecraftForge.setBlockHarvestLevel(THIS_MOD.blockChiseledBrick, "pickaxe", 0); // wooden pickaxe

			// no stairs -- texture doesn't align correctly
		}

		//////////

		if(Cfg.enableSmoothBrick){
			THIS_MOD.blockSmoothBrick = new BlockColored(Cfg.blockSmoothBrick, "au.colorSmoothBrick", ItemBlockSmoothBrick.class, Material.rock)
				.setHardness(1.5F)
				.setResistance(10.0F)
				.setStepSound(Block.soundStoneFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
			MinecraftForge.setBlockHarvestLevel(THIS_MOD.blockSmoothBrick, "pickaxe", 0); // wooden pickaxe

			// stairs
			if(Cfg.enableSmoothBrickStairs)
				for(int c = 0; c < 16; c++){
					THIS_MOD.blockSmoothBrickStairs[c] = new BlockStairsColored(Cfg.blockSmoothBrickStairs+c, "au.colorSmoothBrickStairs."+Color.colors[c], THIS_MOD.blockSmoothBrick, c);
					MinecraftForge.setBlockHarvestLevel(THIS_MOD.blockSmoothBrickStairs[c], "pickaxe", 0); // wooden pickaxe
				}
		}

		//////////

		if(Cfg.enableGravel){
			THIS_MOD.blockGravel = new BlockColored(Cfg.blockGravel, "au.colorGravel", ItemBlockGravel.class, Material.sand)
				.setHardness(0.6F)
				.setResistance(3.0F)
				.setStepSound(Block.soundGravelFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
			MinecraftForge.setBlockHarvestLevel(THIS_MOD.blockGravel, "shovel", 0); // wooden shovel
		}

		//////////

		if(Cfg.enableGlass){
			THIS_MOD.blockGlass = new BlockGlass(Cfg.blockGlass, "au.colorGlass", ItemBlockGlass.class, 0)
				.setHardness(0.3F)
				.setResistance(1.5F)
				.setStepSound(Block.soundGlassFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
		}

		//////////

		if(Cfg.enableGlassTinted){
			THIS_MOD.blockGlassTinted = new BlockGlass(Cfg.blockGlassTinted, "au.colorGlassTinted", ItemBlockGlassTinted.class, 1)
				.setHardness(0.3F)
				.setResistance(1.5F)
				.setStepSound(Block.soundGlassFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
		}

		//////////

		if(Cfg.enableGlassTintedNoFrame){
			THIS_MOD.blockGlassTintedNoFrame = new BlockGlass(Cfg.blockGlassTintedNoFrame, "au.colorGlassTintedNoFrame", ItemBlockGlassTintedNoFrame.class, 2)
				.setHardness(0.3F)
				.setResistance(1.5F)
				.setStepSound(Block.soundGlassFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
		}

		//////////

		if(Cfg.enableGlassPane){
			THIS_MOD.blockGlassPane = new BlockColoredPane(Cfg.blockGlassPane, "au.colorGlassPane", ItemBlockGlassPane.class, Material.glass, THIS_MOD.blockGlass, true, 0)
				.setHardness(0.3F)
				.setResistance(1.5F)
				.setStepSound(Block.soundGlassFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
		}

		//////////

		if(Cfg.enableGlassPaneTinted){
			THIS_MOD.blockGlassPaneTinted = new BlockColoredPane(Cfg.blockGlassPaneTinted, "au.colorGlassPaneTinted",
																	ItemBlockGlassPaneTinted.class, Material.glass, THIS_MOD.blockGlassTinted, true, 1)
				.setHardness(0.3F)
				.setResistance(1.5F)
				.setStepSound(Block.soundGlassFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
		}

		//////////

		if(Cfg.enableGlassPaneTintedNoFrame){
			THIS_MOD.blockGlassPaneTintedNoFrame = new BlockColoredPane(Cfg.blockGlassPaneTintedNoFrame, "au.colorGlassPaneTintedNoFrame",
																	ItemBlockGlassPaneTintedNoFrame.class, Material.glass, THIS_MOD.blockGlassTintedNoFrame, false, 1)
				.setHardness(0.3F)
				.setResistance(1.5F)
				.setStepSound(Block.soundGlassFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
		}

		//////////

		if(Cfg.enableIronBars){
			THIS_MOD.blockIronBars = new BlockColoredPane(Cfg.blockIronBars, "au.colorIronBars", ItemBlockIronBars.class, Material.iron, null, false, 0)
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
			MinecraftForge.setBlockHarvestLevel(THIS_MOD.blockEnderCube, "pickaxe", 3); // diamond pickaxe
		}

		//////////

		if(Cfg.enableArtificialGrass){
			THIS_MOD.blockArtificialGrass = new BlockArtificialGrass(Cfg.blockArtificialGrass, "au.artificialGrass")
				.setHardness(0.6F)
				.setResistance(3.0F)
				.setStepSound(Block.soundGrassFootstep)
				.setCreativeTab(THIS_MOD.tabAU);
			MinecraftForge.setBlockHarvestLevel(THIS_MOD.blockArtificialGrass, "shovel", 0); // wooden shovel
		}
	}
}
