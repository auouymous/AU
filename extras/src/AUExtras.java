package com.qzx.au.extras;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.qzx.au.core.Color;
import com.qzx.au.core.Light;


@Mod(modid="AUExtras", name="Altered Unification EXTRAS", version=AUExtras.modVersion)
@NetworkMod(clientSideRequired = true, serverSideRequired = true,
//	clientPacketHandlerSpec = @SidedPacketHandler(channels = { AUExtras.packetChannel }, packetHandler = PacketHandlerClient.class),
	serverPacketHandlerSpec = @SidedPacketHandler(channels = { AUExtras.packetChannel }, packetHandler = PacketHandlerServer.class))
public class AUExtras {
	@Instance("AUExtras")
	public static AUExtras instance;

	@SidedProxy(clientSide="com.qzx.au.extras.ClientProxy", serverSide="com.qzx.au.extras.CommonProxy")
	public static CommonProxy proxy;

	public static final String modVersion = "0.0.0";
	public static final String packetChannel = "AUExtras";

	#ifdef MC152
	public static final String texturePath = "/mods/au_extras/textures";
	#else
	public static final String texturePath = "textures";
	#endif

	public static CreativeTabs tabAU = new CreativeTabAUExtras();

	public static Block blockChromaInfuser;
	public static Block blockCobble;
	public static Block blockStone;
	public static Block blockStoneBrick;
	public static Block blockChiseledBrick;
	public static Block blockSmoothBrick;
	public static Block blockGravel;
	public static Block blockGlass;
	public static Block blockGlassTinted;
	public static Block blockGlassTintedNoFrame;
	public static Block blockLamp;
	public static Block blockInvertedLamp;
	public static Block blockLampPowered;
	public static Block blockInvertedLampPowered;
	public static Block[] blockCobbleStairs = new BlockStairsColored[16];
	public static Block[] blockStoneStairs = new BlockStairsColored[16];
	public static Block[] blockStoneBrickStairs = new BlockStairsColored[16];
	public static Block[] blockSmoothBrickStairs = new BlockStairsColored[16];
	public static Block blockFlower;
	public static Block blockFlowerSeed;
	public static Block blockEnderCube;
	public static Item itemFriedEgg;
	public static Item itemCookedFlesh;
	public static Item itemFlowerDye;
//	public static Item itemChromaSprayer;

	@PreInit
	public void preInit(FMLPreInitializationEvent event){
		Cfg.init(event);
		MinecraftForge.EVENT_BUS.register(new Events());
	}

	@Init
	public void load(FMLInitializationEvent event){
		proxy.registerRenderers();
		proxy.registerHandlers();
		proxy.registerEntities();

		//////////

		ItemStack grassBlock = new ItemStack(Block.grass);
		ItemStack cobblestone = new ItemStack(Block.cobblestone);
		ItemStack stone = new ItemStack(Block.stone);
		ItemStack stoneBrick = new ItemStack(Block.stoneBrick);
		ItemStack chiseledBrick = new ItemStack(Block.stoneBrick, 1, 3);
		ItemStack gravel = new ItemStack(Block.gravel);
		ItemStack glass = new ItemStack(Block.glass);
		ItemStack glassPane = new ItemStack(Block.thinGlass);
		ItemStack redstoneTorch = new ItemStack(Block.torchRedstoneActive);
		ItemStack redstoneDust = new ItemStack(Item.redstone);
		ItemStack glowstone = new ItemStack(Block.glowStone);
		#ifdef MC152
		ItemStack glowstoneDust = new ItemStack(Item.lightStoneDust);
		#else
		ItemStack glowstoneDust = new ItemStack(Item.glowstone);
		#endif
		ItemStack[] dyes = new ItemStack[16];
		for(int c = 0; c < 16; c++)
			dyes[c] = new ItemStack(Item.dyePowder, 1, c);

		//////////

		if(Cfg.enableChromaInfuser){
			this.blockChromaInfuser = new BlockChromaInfuser(Cfg.blockChromaInfuser, "au.chromaInfuser", "Chroma Infuser")
				.setHardness(2.0F)
				.setResistance(10.0F)
//				.setLightOpacity(191) // 25% transparent
				.setStepSound(Block.soundMetalFootstep)
				.setCreativeTab(AUExtras.tabAU);
			MinecraftForge.setBlockHarvestLevel(this.blockChromaInfuser, "pickaxe", 0); // wooden pickaxe

			// CRAFT cauldron + 4 glass -> 1 chroma infuser
			GameRegistry.addRecipe(new ItemStack(this.blockChromaInfuser), " g ", "gcg", " g ", 'g', glass, 'c', new ItemStack(Item.cauldron));
		}

		//////////

		if(Cfg.enableCobble){
			this.blockCobble = new BlockColored(Cfg.blockCobble, "au.colorCobble", " Cobblestone", ItemBlockCobble.class, Material.rock)
				.setHardness(2.0F)
				.setResistance(10.0F)
				.setStepSound(Block.soundStoneFootstep)
				.setCreativeTab(AUExtras.tabAU);
			MinecraftForge.setBlockHarvestLevel(this.blockCobble, "pickaxe", 0); // wooden pickaxe
			ItemStack coloredCobble = new ItemStack(this.blockCobble);

			// CHROMA INFUSER recipes
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, cobblestone, coloredCobble);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, new ItemStack(this.blockCobble, 1, c), coloredCobble);
			// SMELT <colored> cobble -> cobblestone
			GameRegistry.addSmelting(coloredCobble.itemID, cobblestone, 1.0f);

			// stairs
			if(Cfg.enableCobbleStairs)
				for(int c = 0; c < 16; c++)
					this.blockCobbleStairs[c] = new BlockStairsColored(Cfg.blockCobbleStairs+c, "au.colorCobbleStairs."+Color.colors[c],
															Color.readableColors[c]+" Cobblestone Stairs",
															this.blockCobble, c);
		}

		//////////

		if(Cfg.enableStone){
			this.blockStone = new BlockColored(Cfg.blockStone, "au.colorStone", " Stone", ItemBlockStone.class, Material.rock)
				.setHardness(1.5F)
				.setResistance(10.0F)
				.setStepSound(Block.soundStoneFootstep)
				.setCreativeTab(AUExtras.tabAU);
			MinecraftForge.setBlockHarvestLevel(this.blockStone, "pickaxe", 0); // wooden pickaxe
			ItemStack coloredStone = new ItemStack(this.blockStone);

			// CHROMA INFUSER recipes
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, stone, coloredStone);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, new ItemStack(this.blockStone, 1, c), coloredStone);
			// SMELT <colored> stone -> stone
			GameRegistry.addSmelting(coloredStone.itemID, stone, 1.0f);

			// stairs
			if(Cfg.enableStoneStairs)
				for(int c = 0; c < 16; c++)
					this.blockStoneStairs[c] = new BlockStairsColored(Cfg.blockStoneStairs+c, "au.colorStoneStairs."+Color.colors[c],
															Color.readableColors[c]+" Stone Stairs",
															this.blockStone, c);
		}

		//////////

		if(Cfg.enableStoneBrick){
			this.blockStoneBrick = new BlockColored(Cfg.blockStoneBrick, "au.colorStoneBrick", " Stone Brick", ItemBlockStoneBrick.class, Material.rock)
				.setHardness(1.5F)
				.setResistance(10.0F)
				.setStepSound(Block.soundStoneFootstep)
				.setCreativeTab(AUExtras.tabAU);
			MinecraftForge.setBlockHarvestLevel(this.blockStoneBrick, "pickaxe", 0); // wooden pickaxe
			ItemStack coloredStoneBrick = new ItemStack(this.blockStoneBrick);

			// CHROMA INFUSER recipes
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, stoneBrick, coloredStoneBrick);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, new ItemStack(this.blockStoneBrick, 1, c), coloredStoneBrick);
			// SMELT <colored> stoneBrick -> stoneBrick
			GameRegistry.addSmelting(coloredStoneBrick.itemID, stoneBrick, 1.0f);

			// stairs
			if(Cfg.enableStoneBrickStairs)
				for(int c = 0; c < 16; c++)
					this.blockStoneBrickStairs[c] = new BlockStairsColored(Cfg.blockStoneBrickStairs+c, "au.colorStoneBrickStairs."+Color.colors[c],
															Color.readableColors[c]+" Stone Brick Stairs",
															this.blockStoneBrick, c);
		}

		//////////

		if(Cfg.enableChiseledBrick){
			this.blockChiseledBrick = new BlockColored(Cfg.blockChiseledBrick, "au.colorChiseledBrick", " Chiseled Brick", ItemBlockChiseledBrick.class, Material.rock)
				.setHardness(1.5F)
				.setResistance(10.0F)
				.setStepSound(Block.soundStoneFootstep)
				.setCreativeTab(AUExtras.tabAU);
			MinecraftForge.setBlockHarvestLevel(this.blockChiseledBrick, "pickaxe", 0); // wooden pickaxe
			ItemStack coloredChiseledBrick = new ItemStack(this.blockChiseledBrick);

			// CHROMA INFUSER recipes
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, chiseledBrick, coloredChiseledBrick);
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE_DOT, stone, coloredChiseledBrick);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, new ItemStack(this.blockChiseledBrick, 1, c), coloredChiseledBrick);
			// SMELT <colored> chiseledBrick -> chiseledBrick
			GameRegistry.addSmelting(coloredChiseledBrick.itemID, chiseledBrick, 1.0f);

			// no stairs -- texture doesn't align correctly
		}

		//////////

		if(Cfg.enableSmoothBrick){
			this.blockSmoothBrick = new BlockColored(Cfg.blockSmoothBrick, "au.colorSmoothBrick", " Smooth Brick", ItemBlockSmoothBrick.class, Material.rock)
				.setHardness(1.5F)
				.setResistance(10.0F)
				.setStepSound(Block.soundStoneFootstep)
				.setCreativeTab(AUExtras.tabAU);
			MinecraftForge.setBlockHarvestLevel(this.blockSmoothBrick, "pickaxe", 0); // wooden pickaxe
			ItemStack coloredSmoothBrick = new ItemStack(this.blockSmoothBrick);

			// CHROMA INFUSER recipes
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE, stone, coloredSmoothBrick);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, new ItemStack(this.blockSmoothBrick, 1, c), coloredSmoothBrick);
			// SMELT <colored> smoothBrick -> stone
			GameRegistry.addSmelting(coloredSmoothBrick.itemID, stone, 1.0f);

			// stairs
			if(Cfg.enableSmoothBrickStairs)
				for(int c = 0; c < 16; c++)
					this.blockSmoothBrickStairs[c] = new BlockStairsColored(Cfg.blockSmoothBrickStairs+c, "au.colorSmoothBrickStairs."+Color.colors[c],
															Color.readableColors[c]+" Smooth Brick Stairs",
															this.blockSmoothBrick, c);
		}

		//////////

		if(Cfg.enableGravel){
			this.blockGravel = new BlockColored(Cfg.blockGravel, "au.colorGravel", " Gravel", ItemBlockGravel.class, Material.sand)
				.setHardness(0.6F)
				.setResistance(3.0F)
				.setStepSound(Block.soundGravelFootstep)
				.setCreativeTab(AUExtras.tabAU);
			MinecraftForge.setBlockHarvestLevel(this.blockGravel, "shovel", 0); // wooden shovel
			ItemStack coloredGravel = new ItemStack(this.blockGravel);

			// CHROMA INFUSER recipes
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, gravel, coloredGravel);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, new ItemStack(this.blockGravel, 1, c), coloredGravel);
			// SMELT <colored> gravel -> gravel
			GameRegistry.addSmelting(coloredGravel.itemID, gravel, 1.0f);
		}

		//////////

		if(Cfg.enableGlass){
			this.blockGlass = new BlockGlass(Cfg.blockGlass, "au.colorGlass", " Glass", ItemBlockGlass.class, 0)
				.setHardness(0.3F)
				.setResistance(1.5F)
				.setStepSound(Block.soundGlassFootstep)
				.setCreativeTab(AUExtras.tabAU);
			ItemStack coloredGlass = new ItemStack(this.blockGlass);

			// CHROMA INFUSER recipes
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE, glass, coloredGlass);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE, new ItemStack(this.blockGlass, 1, c), coloredGlass);
			// SMELT <colored> glass -> glass
			GameRegistry.addSmelting(coloredGlass.itemID, glass, 1.0f);
		}

		//////////

		if(Cfg.enableGlassTinted){
			this.blockGlassTinted = new BlockGlass(Cfg.blockGlassTinted, "au.colorGlassTinted", " Tinted Glass", ItemBlockGlassTinted.class, 1)
				.setHardness(0.3F)
				.setResistance(1.5F)
				.setStepSound(Block.soundGlassFootstep)
				.setCreativeTab(AUExtras.tabAU);
			ItemStack coloredGlassTinted = new ItemStack(this.blockGlassTinted);

			// CHROMA INFUSER recipes
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE_DOT, glass, coloredGlassTinted);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE_DOT, new ItemStack(this.blockGlassTinted, 1, c), coloredGlassTinted);
			// SMELT <colored tinted> glass -> glass
			GameRegistry.addSmelting(coloredGlassTinted.itemID, glass, 1.0f);
		}

		//////////

		if(Cfg.enableGlassTintedNoFrame){
			this.blockGlassTintedNoFrame = new BlockGlass(Cfg.blockGlassTintedNoFrame, "au.colorGlassTintedNoFrame", " Tinted Glass (Frameless)", ItemBlockGlassTintedNoFrame.class, 2)
				.setHardness(0.3F)
				.setResistance(1.5F)
				.setStepSound(Block.soundGlassFootstep)
				.setCreativeTab(AUExtras.tabAU);
			ItemStack coloredGlassTintedNoFrame = new ItemStack(this.blockGlassTintedNoFrame);

			// CHROMA INFUSER recipes
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_DOT, glass, coloredGlassTintedNoFrame);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_DOT, new ItemStack(this.blockGlassTintedNoFrame, 1, c), coloredGlassTintedNoFrame);
			// SMELT <tinted frameless> glass -> glass
			GameRegistry.addSmelting(coloredGlassTintedNoFrame.itemID, glass, 1.0f);
		}

		//////////

		// convert any colored glass to any other colored glass
		if(Cfg.enableGlass && Cfg.enableGlassTinted){
			ItemStack coloredGlass = new ItemStack(this.blockGlass);
			ItemStack coloredGlassTinted = new ItemStack(this.blockGlassTinted);
			for(int c = 0; c < 16; c++){
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE, new ItemStack(this.blockGlassTinted, 1, c), coloredGlass);
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE_DOT, new ItemStack(this.blockGlass, 1, c), coloredGlassTinted);
			}
		}
		if(Cfg.enableGlass && Cfg.enableGlassTintedNoFrame){
			ItemStack coloredGlass = new ItemStack(this.blockGlass);
			ItemStack coloredGlassTintedNoFrame = new ItemStack(this.blockGlassTintedNoFrame);
			for(int c = 0; c < 16; c++){
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE, new ItemStack(this.blockGlassTintedNoFrame, 1, c), coloredGlass);
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_DOT, new ItemStack(this.blockGlass, 1, c), coloredGlassTintedNoFrame);
			}
		}
		if(Cfg.enableGlassTinted && Cfg.enableGlassTintedNoFrame){
			ItemStack coloredGlassTinted = new ItemStack(this.blockGlassTinted);
			ItemStack coloredGlassTintedNoFrame = new ItemStack(this.blockGlassTintedNoFrame);
			for(int c = 0; c < 16; c++){
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_SQUARE_DOT, new ItemStack(this.blockGlassTintedNoFrame, 1, c), coloredGlassTinted);
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_DOT, new ItemStack(this.blockGlassTinted, 1, c), coloredGlassTintedNoFrame);
			}
		}

		//////////

		// vanilla wool
		for(int c = 0; c < 16; c++)
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, new ItemStack(Block.cloth, 1, c), new ItemStack(Block.cloth), true);

		#ifndef MC152
			// vanilla hardened clay (1.6)
			ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, new ItemStack(Block.hardenedClay), new ItemStack(Block.stainedClay), true);
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, new ItemStack(Block.stainedClay, 1, c), new ItemStack(Block.stainedClay), true);
			// vanilla carpet (1.6)
			for(int c = 0; c < 16; c++)
				ChromaRegistry.addRecipe(ChromaButton.BUTTON_BLANK, new ItemStack(Block.carpet, 1, c), new ItemStack(Block.carpet), true);
		#endif

		#if !defined MC152 && !defined MC162 && !defined MC164
			// vanilla stained glass (1.7)
			
			// vanilla stained glass panes (1.7)
			
		#endif

		//////////

		if(Cfg.enableLamps){
			this.blockLamp = new BlockLamp(Cfg.blockLamp, "au.colorLamp", " Lamp", false, false)
				.setHardness(0.6F)
				.setResistance(3.0F)
				.setStepSound(Block.soundGlassFootstep)
				.setCreativeTab(AUExtras.tabAU);
			this.blockLampPowered = new BlockLamp(Cfg.blockLampPowered, "au.colorLampPowered", " Lamp", false, true)
				.setHardness(0.6F)
				.setResistance(3.0F)
				.setStepSound(Block.soundGlassFootstep);
			ItemStack coloredLamp = new ItemStack(this.blockLamp);

			// CRAFT 6 glass panes + dye + glowstone + redstone -> <colored> lamp
			for(int c = 0; c < 16; c++)
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this.blockLamp, 1, c), "pgp", "p-p", "prp", 'p', glassPane, 'g', glowstone, '-', Color.oreDyes[c], 'r', redstoneDust));
			// CRAFT <colored> lamp + dye -> <colored> lamp
			for(int g = 0; g < 16; g++){
				ItemStack anyLamp = new ItemStack(this.blockLamp, 1, g);
				for(int c = 0; c < 16; c++)
					if(g != c)
						GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(this.blockLamp, 1, c), anyLamp, Color.oreDyes[c]));
			}

			//////////

			this.blockInvertedLamp = new BlockLamp(Cfg.blockInvertedLamp, "au.colorInvertedLamp", " Inverted Lamp", true, false)
				.setHardness(0.6F)
				.setResistance(3.0F)
				.setStepSound(Block.soundGlassFootstep)
				.setCreativeTab(AUExtras.tabAU);
			this.blockInvertedLampPowered = new BlockLamp(Cfg.blockInvertedLampPowered, "au.colorInvertedLampPowered", " Inverted Lamp", true, true)
				.setHardness(0.6F)
				.setResistance(3.0F)
				.setStepSound(Block.soundGlassFootstep);
			ItemStack coloredInvertedLamp = new ItemStack(this.blockInvertedLamp);

			// CRAFT 6 glass panes + dye + glowstone + redstone torch -> <colored> inverted lamp
			for(int c = 0; c < 16; c++)
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this.blockInvertedLamp, 1, c), "pgp", "p-p", "prp", 'p', glassPane, 'g', glowstone, '-', Color.oreDyes[c], 'r', redstoneTorch));
			// CRAFT <colored> inverted lamp + dye -> <colored> inverted lamp
			for(int g = 0; g < 16; g++){
				ItemStack anyInvertedLamp = new ItemStack(this.blockInvertedLamp, 1, g);
				for(int c = 0; c < 16; c++)
					if(g != c)
						GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(this.blockInvertedLamp, 1, c), anyInvertedLamp, Color.oreDyes[c]));
			}

			//////////

			for(int c = 0; c < 16; c++){
				// CRAFT <colored> lamp + redstone torch -> <colored> inverted lamp
				// CRAFT <colored> inverted lamp + redstone dust -> <colored> lamp
				ItemStack anyLamp = new ItemStack(this.blockLamp, 1, c);
				ItemStack anyInvertedLamp = new ItemStack(this.blockInvertedLamp, 1, c);
				GameRegistry.addShapelessRecipe(anyInvertedLamp, anyLamp, redstoneTorch);
				GameRegistry.addShapelessRecipe(anyLamp, anyInvertedLamp, redstoneDust);
			}
		}

		//////////

		if(Cfg.enableChiseledBrickCrafting){
			// CRAFT 2 stoneBrick slabs -> chiseledBrick (1.8 recipe)
			GameRegistry.addRecipe(new ItemStack(Block.stoneBrick, 1, 3), "b", "b", 'b', new ItemStack(Block.stoneSingleSlab, 1, 5));
		}
		if(Cfg.enableMossyBrickCrafting){
			// CRAFT stoneBrick + vine -> mossy stone brick (1.8 recipe)
			GameRegistry.addShapelessRecipe(new ItemStack(Block.stoneBrick, 1, 1), stoneBrick, new ItemStack(Block.vine));
		}
		if(Cfg.enableMossyCobbleCrafting){
			// CRAFT cobblestone + vine -> mossy cobble
			GameRegistry.addShapelessRecipe(new ItemStack(Block.cobblestoneMossy), cobblestone, new ItemStack(Block.vine));
		}
		if(Cfg.enableGrassBlockCrafting){
			// CRAFT tall grass + dirt -> grass block
			GameRegistry.addShapelessRecipe(grassBlock, new ItemStack(Block.tallGrass, 1, 1), new ItemStack(Block.dirt));
		}
		if(Cfg.enableMyceliumCrafting){
			// CRAFT brown mushroom + red mushroom + grass block -> mycelium block
			GameRegistry.addShapelessRecipe(new ItemStack(Block.mycelium), new ItemStack(Block.mushroomBrown), new ItemStack(Block.mushroomRed), grassBlock);
		}

		//////////

		if(Cfg.enableLightingHack){
			// stairs
			Block.stairsWoodOak.setLightValue(Light.level[1]);
			Block.stairsCobblestone.setLightValue(Light.level[1]);
			Block.stairsBrick.setLightValue(Light.level[1]);
			Block.stairsStoneBrick.setLightValue(Light.level[1]);
			Block.stairsNetherBrick.setLightValue(Light.level[1]);
			Block.stairsSandStone.setLightValue(Light.level[1]);
			Block.stairsWoodSpruce.setLightValue(Light.level[1]);
			Block.stairsWoodBirch.setLightValue(Light.level[1]);
			Block.stairsWoodJungle.setLightValue(Light.level[1]);
			Block.stairsNetherQuartz.setLightValue(Light.level[1]);
			// slabs
			Block.stoneSingleSlab.setLightValue(Light.level[1]);
			Block.woodSingleSlab.setLightValue(Light.level[1]);
			// additional
			if(Cfg.additionalLightingHack != null)
				for(int i = 0; i < Cfg.additionalLightingHack.length; i++){
					Block block = Block.blocksList[Cfg.additionalLightingHack[i]];
					if(block != null) block.setLightValue(Light.level[1]);
				}
		}

		//////////

		if(Cfg.enableFlowers){
			this.blockFlower = new BlockFlower(Cfg.blockFlower, "au.colorFlower", " Flower")
				.setHardness(0.0F)
				.setResistance(0.0F)
				.setStepSound(Block.soundGrassFootstep)
				.setCreativeTab(AUExtras.tabAU);
			this.blockFlowerSeed = new BlockFlowerSeed(Cfg.blockFlowerSeed, "au.colorFlowerSeed", "Flower Seed")
				.setHardness(0.0F)
				.setResistance(0.0F)
				.setStepSound(Block.soundGrassFootstep)
				.setCreativeTab(AUExtras.tabAU);
			this.itemFlowerDye = new ItemFlowerDye(Cfg.itemFlowerDye, "au.colorFlowerDye", " Dye")
				.setCreativeTab(AUExtras.tabAU);

			// bonemeal grass grows flowers
			for(int c = 0; c < 16; c++)
				MinecraftForge.addGrassPlant(this.blockFlower, c, 1);
			// breaking grass drops flower seed
			MinecraftForge.addGrassSeed(new ItemStack(this.blockFlowerSeed), 1);

			// ore dictionary
			OreDictionary.registerOre("dyeBlack", new ItemStack(this.itemFlowerDye, 1, 0));
			OreDictionary.registerOre("dyeGreen", new ItemStack(this.itemFlowerDye, 1, 2));
			OreDictionary.registerOre("dyeBrown", new ItemStack(this.itemFlowerDye, 1, 3));
			OreDictionary.registerOre("dyeBlue",  new ItemStack(this.itemFlowerDye, 1, 4));
			OreDictionary.registerOre("dyeWhite", new ItemStack(this.itemFlowerDye, 1, 15));

			// flower to dye recipes
			GameRegistry.addShapelessRecipe(new ItemStack(this.itemFlowerDye, 1, 0), new ItemStack(this.blockFlower, 1, 0)); // black
			GameRegistry.addShapelessRecipe(dyes[1],  new ItemStack(this.blockFlower, 1, 1)); // red
			GameRegistry.addShapelessRecipe(new ItemStack(this.itemFlowerDye, 1, 2), new ItemStack(this.blockFlower, 1, 2)); // green
			GameRegistry.addShapelessRecipe(new ItemStack(this.itemFlowerDye, 1, 3), new ItemStack(this.blockFlower, 1, 3)); // brown
			GameRegistry.addShapelessRecipe(new ItemStack(this.itemFlowerDye, 1, 4), new ItemStack(this.blockFlower, 1, 4)); // blue
			GameRegistry.addShapelessRecipe(dyes[5],  new ItemStack(this.blockFlower, 1, 5)); // purple
			GameRegistry.addShapelessRecipe(dyes[6],  new ItemStack(this.blockFlower, 1, 6)); // cyan
			GameRegistry.addShapelessRecipe(dyes[7],  new ItemStack(this.blockFlower, 1, 7)); // light gray
			GameRegistry.addShapelessRecipe(dyes[8],  new ItemStack(this.blockFlower, 1, 8)); // gray
			GameRegistry.addShapelessRecipe(dyes[9],  new ItemStack(this.blockFlower, 1, 9)); // pink
			GameRegistry.addShapelessRecipe(dyes[10], new ItemStack(this.blockFlower, 1, 10)); // lime
			GameRegistry.addShapelessRecipe(dyes[11], new ItemStack(this.blockFlower, 1, 11)); // yellow
			GameRegistry.addShapelessRecipe(dyes[12], new ItemStack(this.blockFlower, 1, 12)); // light blue
			GameRegistry.addShapelessRecipe(dyes[13], new ItemStack(this.blockFlower, 1, 13)); // magenta
			GameRegistry.addShapelessRecipe(dyes[14], new ItemStack(this.blockFlower, 1, 14)); // orange
			GameRegistry.addShapelessRecipe(new ItemStack(this.itemFlowerDye, 1, 15), new ItemStack(this.blockFlower, 1, 15)); // white
		}

		//////////

		if(Cfg.enableEnderCube){
			this.blockEnderCube = new BlockEnderCube(Cfg.blockEnderCube, "au.enderCube", "Ender Cube")
				.setHardness(5.0F)
				.setResistance(25.0F)
				.setLightValue(Light.level[15])
				.setStepSound(Block.soundStoneFootstep)
				.setCreativeTab(AUExtras.tabAU);
			MinecraftForge.setBlockHarvestLevel(this.blockEnderCube, "pickaxe", 3); // diamond pickaxe

			// CRAFT glass + 3 eye of ender + redstone dust + 4 gold ingots -> ender cube
			GameRegistry.addRecipe(new ItemStack(this.blockEnderCube),
									"xex", "eoe", "xrx",
									'o', new ItemStack(Block.obsidian), 'e', new ItemStack(Item.eyeOfEnder), 'r', redstoneDust, 'x', new ItemStack(Item.ingotGold));
		}

		//////////

		// SMELT egg -> fried egg
		if(Cfg.enableFriedEgg){
			this.itemFriedEgg = new ItemFoodGeneric(Cfg.itemFriedEgg, 64, "au.friedEgg", "Fried Egg", 2, 0.4F, false)
				.setCreativeTab(AUExtras.tabAU);
			GameRegistry.addSmelting(Item.egg.itemID, new ItemStack(this.itemFriedEgg), 1.0f);
		}

		// SMELT rotten flesh -> cooked flesh
		ItemStack cookedFlesh = null;
		if(Cfg.enableCookedFlesh){
			this.itemCookedFlesh = new ItemFoodGeneric(Cfg.itemCookedFlesh, 64, "au.cookedFlesh", "Cooked Flesh", 2, 0.2F, false)
				.setCreativeTab(AUExtras.tabAU);
			cookedFlesh = new ItemStack(this.itemCookedFlesh);
			GameRegistry.addSmelting(Item.rottenFlesh.itemID, cookedFlesh, 1.0f);
		}

		// CRAFT cooked flesh -> leather
		if(Cfg.enableCookedFleshToLeather && Cfg.enableCookedFlesh){
			ItemStack leather = new ItemStack(Item.leather);
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

	@PostInit
	public void postInit(FMLPostInitializationEvent event){}
}
