package com.qzx.au.hud;

#if defined MC147 || defined MC152 || defined MC164
import cpw.mods.fml.client.registry.KeyBindingRegistry;
#define USE_KEY_REGISTRY
import cpw.mods.fml.common.registry.TickRegistry;
#define USE_TICK_REGISTRY
#else
import cpw.mods.fml.common.FMLCommonHandler;
#endif

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	public static KeyHandlerHUD keyHandler = new KeyHandlerHUD();
	public static TickHandlerHUD tickHandler = new TickHandlerHUD();

	@Override
	public void registerEvents(){}

	@Override
	public void registerRenderers(){}

	@Override
	public void registerHandlers(){
		super.registerHandlers();

		// Key Handler
		#ifdef USE_KEY_REGISTRY
		KeyBindingRegistry.registerKeyBinding(this.keyHandler);
		#else
		FMLCommonHandler.instance().bus().register(this.keyHandler);
		#endif

		// Tick Handler (client render)
		#ifdef USE_TICK_REGISTRY
		TickRegistry.registerTickHandler(this.tickHandler, Side.CLIENT);
		#else
		FMLCommonHandler.instance().bus().register(this.tickHandler);
		#endif
	}
}
