package com.qzx.au.core;

IMPORT_FML
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;

@Mod(modid="AUCore", name="Altered Unification CORE", version=AUCore.modVersion)
#if defined MC147 || defined MC152
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
#else
@NetworkMod(clientSideRequired = true, serverSideRequired = true,
	clientPacketHandlerSpec = @SidedPacketHandler(channels = { AUCore.packetChannel }, packetHandler = PacketHandlerClient.class))
#endif
public class AUCore {
	@Instance("AUCore")
	public static AUCore instance;

	public static final String modVersion = "0.0.0";
	public static final String packetChannel = "AUCore";

	FML_PREINIT
	public void preInit(FMLPreInitializationEvent event){}

	FML_INIT
	public void load(FMLInitializationEvent event){}

	FML_POSTINIT
	public void postInit(FMLPostInitializationEvent event){}
}
