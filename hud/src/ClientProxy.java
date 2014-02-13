package com.qzx.au.hud;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
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
		KeyBindingRegistry.registerKeyBinding(this.keyHandler);

		// Tick Handler (client render)
		TickRegistry.registerTickHandler(this.tickHandler, Side.CLIENT);
	}

	@Override
	public void postInit(){}
}
