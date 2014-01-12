package com.qzx.au.extras;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	public static TickHandlerExtras tickHandler = new TickHandlerExtras();

	public static int renderPass = 0;
	public static int infuserRenderType;
	public static int glassRenderType;
	public static int lampRenderType;
	public static int flowerRenderType;

	@Override
	public void registerEvents(){}

	@Override
	public void registerRenderers(){
		if(Cfg.enableChromaInfuser){
			infuserRenderType = RenderingRegistry.getNextAvailableRenderId();
			RenderingRegistry.registerBlockHandler(new RendererChromaInfuser());
		}
		if(Cfg.enableGlass || Cfg.enableGlassTinted || Cfg.enableGlassTintedNoFrame){
			glassRenderType = RenderingRegistry.getNextAvailableRenderId();
			RenderingRegistry.registerBlockHandler(new RendererGlass());
		}
		if(Cfg.enableLamps){
			lampRenderType = RenderingRegistry.getNextAvailableRenderId();
			RenderingRegistry.registerBlockHandler(new RendererLamp());
		}
		if(Cfg.enableFlowers){
			flowerRenderType = RenderingRegistry.getNextAvailableRenderId();
			RenderingRegistry.registerBlockHandler(new RendererFlower());
		}
	}

	@Override
	public void registerHandlers(){
		super.registerHandlers();

		// Tick Handler
		TickRegistry.registerTickHandler(this.tickHandler, Side.CLIENT);
	}

	@Override
	public void postInit(){}
}
