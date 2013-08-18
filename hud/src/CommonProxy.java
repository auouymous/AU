package com.qzx.au.hud;

import cpw.mods.fml.common.network.NetworkRegistry;

public class CommonProxy {
	public void registerEvents(){}
	public void registerRenderers(){}
	public void registerHandlers(){
		// Gui Handler
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
	}
	public void postInit(){}
}
