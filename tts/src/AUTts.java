package com.qzx.au.tts;

IMPORT_FML
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import net.minecraftforge.common.MinecraftForge;

@Mod(modid="AUTts", name="Altered Unification TTS", version=AUTts.modVersion)
@NetworkMod(clientSideRequired = true, serverSideRequired = true,
	clientPacketHandlerSpec = @SidedPacketHandler(channels = { AUTts.packetChannel }, packetHandler = PacketHandlerClient.class),
	serverPacketHandlerSpec = @SidedPacketHandler(channels = { AUTts.packetChannel }, packetHandler = PacketHandlerServer.class))
public class AUTts {
	@Instance("AUTts")
	public static AUTts instance;

	@SidedProxy(clientSide="com.qzx.au.tts.ClientProxy", serverSide="com.qzx.au.tts.CommonProxy")
	public static CommonProxy proxy;

	public static final String modVersion = "0.0.0";
	public static final String packetChannel = "AUTts";

	#ifdef MC152
	public static final String texturePath = "/mods/au_tts/textures";
	#else
	public static final String texturePath = "textures";
	#endif

	public static Block blockTTS;

	FML_PREINIT
	public void preInit(FMLPreInitializationEvent event){
		Cfg.init(event);

		#ifndef MC164
			ModBlocks.init();
		#endif
	}

	FML_INIT
	public void load(FMLInitializationEvent event){
		proxy.registerHandlers();
		proxy.registerEntities();

		#ifdef MC164
			ModBlocks.init();
		#endif

		ModRecipes.init();
	}

	FML_POSTINIT
	public void postInit(FMLPostInitializationEvent event){}
}
