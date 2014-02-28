package com.qzx.au.hud;

IMPORT_FML
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid=THIS_MOD.modID, name="Altered Unification HUD", version=THIS_MOD.modVersion)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class THIS_MOD {
	@Instance(THIS_MOD.modID)
	public static THIS_MOD instance;

	public static boolean supportIC2 = false;

	@SidedProxy(clientSide="com.qzx.au.hud.ClientProxy", serverSide="com.qzx.au.hud.CommonProxy")
	public static CommonProxy proxy;

	public static final String modID = "au_hud";
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
		THIS_MOD.supportIC2 = Loader.isModLoaded("IC2");
	}
}
