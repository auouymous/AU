package com.qzx.au.tts;

IMPORT_FML
import cpw.mods.fml.common.SidedProxy;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import net.minecraftforge.common.MinecraftForge;

@Mod(modid=THIS_MOD.modID, name="Altered Unification TTS", version=THIS_MOD.modVersion)

#if defined MC164
@NetworkMod(clientSideRequired = true, serverSideRequired = true,
	clientPacketHandlerSpec = @SidedPacketHandler(channels = { THIS_MOD.packetChannel }, packetHandler = PacketHandlerClient.class),
	serverPacketHandlerSpec = @SidedPacketHandler(channels = { THIS_MOD.packetChannel }, packetHandler = PacketHandlerServer.class))
#endif

public class au_tts {
	@Instance(THIS_MOD.modID)
	public static THIS_MOD instance;

	@SidedProxy(clientSide="com.qzx.au.tts.ClientProxy", serverSide="com.qzx.au.tts.CommonProxy")
	public static CommonProxy proxy;

	public static final String modID = "au_tts";
	public static final String modVersion = "0.0.0";
	public static final String packetChannel = "au_tts";

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
