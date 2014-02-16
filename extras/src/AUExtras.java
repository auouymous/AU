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

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import net.minecraftforge.common.MinecraftForge;

@Mod(modid="AUExtras", name="Altered Unification EXTRAS", version=AUExtras.modVersion)
@NetworkMod(clientSideRequired = true, serverSideRequired = true,
	clientPacketHandlerSpec = @SidedPacketHandler(channels = { AUExtras.packetChannel }, packetHandler = PacketHandlerClient.class),
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
	public static Block blockGlassPane;
	public static Block blockGlassPaneTinted;
	public static Block blockGlassPaneTintedNoFrame;
	public static Block blockIronBars;
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
	public static Block blockArtificialGrass;
	public static Item itemFriedEgg;
	public static Item itemCookedFlesh;
	public static Item itemFlowerDye;
	public static Item itemEnderStar;
	public static Item itemEnderWand;
	public static Item itemEnderMagnet;
	public static Item itemEnderXT;
//	public static Item itemChromaSprayer;

	FML_PREINIT
	public void preInit(FMLPreInitializationEvent event){
		Cfg.init(event);

		MinecraftForge.EVENT_BUS.register(new Events());

		#ifndef MC164
			ModBlocks.init();
			ModItems.init();
		#endif
	}

	FML_INIT
	public void load(FMLInitializationEvent event){
		proxy.registerRenderers();
		proxy.registerHandlers();
		proxy.registerEntities();

		#ifdef MC164
			ModBlocks.init();
			ModItems.init();
		#endif

		ModRecipes.init();

		//////////

		if(Cfg.enableLightingHack){
			// stairs
			MC_BLOCK.stairsWoodOak.setLightOpacity(0);
			MC_BLOCK.stairsCobblestone.setLightOpacity(0);
			MC_BLOCK.stairsBrick.setLightOpacity(0);
			MC_BLOCK.stairsStoneBrick.setLightOpacity(0);
			MC_BLOCK.stairsNetherBrick.setLightOpacity(0);
			MC_BLOCK.stairsSandStone.setLightOpacity(0);
			MC_BLOCK.stairsWoodSpruce.setLightOpacity(0);
			MC_BLOCK.stairsWoodBirch.setLightOpacity(0);
			MC_BLOCK.stairsWoodJungle.setLightOpacity(0);
			MC_BLOCK.stairsNetherQuartz.setLightOpacity(0);
			// slabs
			MC_BLOCK.stoneSingleSlab.setLightOpacity(0);
			MC_BLOCK.woodSingleSlab.setLightOpacity(0);
		}

		// additional lighting hack
		if(Cfg.additionalLightingHack != null){
			for(int i = 0; i < Cfg.additionalLightingHack.length; i++){
				Block block = Block.blocksList[Cfg.additionalLightingHack[i]];
				if(block != null) block.setLightOpacity(0);
			}
		}
	}

	FML_POSTINIT
	public void postInit(FMLPostInitializationEvent event){}
}
