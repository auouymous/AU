package com.qzx.au.extras;

IMPORT_FML
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.SidedProxy;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import net.minecraftforge.common.MinecraftForge;

@Mod(modid=THIS_MOD.modID, name="Altered Unification EXTRAS", version=THIS_MOD.modVersion)

#if defined MC164
@NetworkMod(clientSideRequired = true, serverSideRequired = true,
	clientPacketHandlerSpec = @SidedPacketHandler(channels = { THIS_MOD.packetChannel }, packetHandler = PacketHandlerClient.class),
	serverPacketHandlerSpec = @SidedPacketHandler(channels = { THIS_MOD.packetChannel }, packetHandler = PacketHandlerServer.class))
#endif

public class THIS_MOD {
	@Instance(THIS_MOD.modID)
	public static THIS_MOD instance;

	@SidedProxy(clientSide="com.qzx.au.extras.ClientProxy", serverSide="com.qzx.au.extras.CommonProxy")
	public static CommonProxy proxy;

	public static final String modID = "au_extras";
	public static final String modVersion = "0.0.0";
	public static final String packetChannel = "au_extras";

	public static final String texturePath = "textures";

	public static CreativeTabs tabAU = new CreativeTabAUExtras();

	//////////

	// BLOCKS
	public static Block blockChromaInfuser;

	public static Block blockStoneHalfSlabs;
		public static Block blockStoneHalfSlabsSmooth;
		public static Block[] blockStoneHalfSlab = new BlockColoredHalfSlab[2];
	public static Block blockCobble;
		public static Block[] blockCobbleStairs = new BlockStairsColored[16];
		public static Block[] blockCobbleSlab = new BlockColoredSlab[2];
	public static Block blockStone;
		public static Block[] blockStoneStairs = new BlockStairsColored[16];
		public static Block[] blockStoneSlab = new BlockColoredSlab[2];
	public static Block blockStoneBrick;
		public static Block[] blockStoneBrickStairs = new BlockStairsColored[16];
		public static Block[] blockStoneBrickSlab = new BlockColoredSlab[2];
	public static Block blockChiseledBrick;
		// no chiseled brick stairs
		public static Block[] blockChiseledBrickSlab = new BlockColoredSlab[2];
	public static Block blockSmoothBrick;
		public static Block[] blockSmoothBrickStairs = new BlockStairsColored[16];
		public static Block[] blockSmoothBrickSlab = new BlockColoredSlab[2];
	public static Block blockGravel;
		// no gravel stairs
		public static Block[] blockGravelSlab = new BlockColoredSlab[2];
	public static Block blockSand;
		public static Block[] blockSandStairs = new BlockStairsColored[16];
		public static Block[] blockSandSlab = new BlockColoredSlab[2];
	public static Block blockArtificialGrass;
		public static Block[] blockArtificialGrassStairs = new BlockStairsColored[16];
		public static Block[] blockArtificialGrassSlab = new BlockColoredSlab[2];
	public static Block[] blockArtificialVine = new BlockArtificialVine[16];

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

	public static Block blockFlower;
	public static Block blockFlowerSeed;

	public static Block blockEnderCube;

	public static Block[] blockPlank = new BlockColored[3];
		public static Block[] blockPlankStairs = new BlockStairsColored[48];
		public static Block[] blockPlankSlab = new BlockColoredSlab[6];

	// ITEMS
	public static Item itemFriedEgg;

	public static Item itemCookedFlesh;

	public static Item itemFlowerDye;

	public static Item itemEnderStar;
	public static Item itemEnderWand;
	public static Item itemEnderMagnet;
	public static Item itemEnderXT;

	public static Item itemDiamondShears;

	//////////

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
			MC_BLOCK.stairsCobblestone.setLightOpacity(0);
			MC_BLOCK.stairsBrick.setLightOpacity(0);
			MC_BLOCK.stairsStoneBrick.setLightOpacity(0);
			MC_BLOCK.stairsSandStone.setLightOpacity(0);
			MC_BLOCK.stairsNetherBrick.setLightOpacity(0);
			MC_BLOCK.stairsNetherQuartz.setLightOpacity(0);
			MC_BLOCK.stairsWoodBirch.setLightOpacity(0);
			MC_BLOCK.stairsWoodOak.setLightOpacity(0);
			MC_BLOCK.stairsWoodJungle.setLightOpacity(0);
			MC_BLOCK.stairsWoodSpruce.setLightOpacity(0);
			// slabs
			MC_BLOCK.stoneSingleSlab.setLightOpacity(0);
			MC_BLOCK.woodSingleSlab.setLightOpacity(0);
		}

		// additional lighting hack
		if(Cfg.additionalLightingHack != null){
			for(int i = 0; i < Cfg.additionalLightingHack.length; i++){
				Block block = GET_BLOCK_BY_ID(Cfg.additionalLightingHack[i]);
				if(block != null) block.setLightOpacity(0);
			}
		}

		//////////

		// Forge Multiparts
		#ifdef WITH_API_FMP
		if(Loader.isModLoaded("ForgeMultipart")) MicroblocksMultipart.init();
		#endif

		// BuildCraft Facades
		if(Loader.isModLoaded("BuildCraft|Transport")) MicroblocksBuildcraft.init();
	}

	FML_POSTINIT
	public void postInit(FMLPostInitializationEvent event){}
}
