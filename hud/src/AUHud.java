package com.qzx.au.hud;

import cpw.mods.fml.common.Loader;
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

@Mod(modid="AUHud", name="Altered Unification HUD", version="20130817-r1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class AUHud {
	@Instance("AUHud")
	public static AUHud instance;

	public static boolean supportIC2 = false;

	@SidedProxy(clientSide="com.qzx.au.hud.ClientProxy", serverSide="com.qzx.au.hud.CommonProxy")
	public static CommonProxy proxy;

	@PreInit
	public void preInit(FMLPreInitializationEvent event){
		Cfg.init(event);
	}

	@Init
	public void load(FMLInitializationEvent event){
		proxy.registerRenderers();
		proxy.registerHandlers();
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent event){
		AUHud.supportIC2 = Loader.isModLoaded("IC2");
	}
}
