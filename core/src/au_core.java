package com.qzx.au.core;

IMPORT_FML

@Mod(modid=THIS_MOD.modID, name="Altered Unification CORE", version=THIS_MOD.modVersion)

#if defined MC147 || defined MC152
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
#elif defined MC164
@NetworkMod(clientSideRequired = true, serverSideRequired = true,
	clientPacketHandlerSpec = @SidedPacketHandler(channels = { THIS_MOD.packetChannel }, packetHandler = PacketHandlerClient.class))
#endif

public class THIS_MOD {
	@Instance(THIS_MOD.modID)
	public static THIS_MOD instance;

	public static final String modID = "au_core";
	public static final String modVersion = "0.0.0";
	public static final String packetChannel = "au_core";

	FML_PREINIT
	public void preInit(FMLPreInitializationEvent event){}

	FML_INIT
	public void load(FMLInitializationEvent event){}

	FML_POSTINIT
	public void postInit(FMLPostInitializationEvent event){}
}
