package com.qzx.au.hud;

IMPORT_FML
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid="AUHud", name="Altered Unification HUD", version=AUHud.modVersion)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class AUHud {
	@Instance("AUHud")
	public static AUHud instance;

	public static boolean supportIC2 = false;

	@SidedProxy(clientSide="com.qzx.au.hud.ClientProxy", serverSide="com.qzx.au.hud.CommonProxy")
	public static CommonProxy proxy;

	public static final String modVersion = "0.0.0";

	FML_PREINIT
	public void preInit(FMLPreInitializationEvent event){
		Cfg.init(event);
	}

	FML_INIT
	public void load(FMLInitializationEvent event){
		proxy.registerRenderers();
		proxy.registerHandlers();
	}

	FML_POSTINIT
	public void postInit(FMLPostInitializationEvent event){
		AUHud.supportIC2 = Loader.isModLoaded("IC2");
	}
}
