package com.qzx.au.extras;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	public static int renderPass = 0;
	public static int infuserRenderType;
	public static int glassRenderType;
	public static int lampRenderType;

	@Override
	public void registerEvents(){}

	@Override
	public void registerRenderers(){
		infuserRenderType = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new RendererChromaInfuser());

		glassRenderType = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new RendererGlass());

		lampRenderType = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new RendererLamp());
	}

	@Override
	public void registerHandlers(){
		super.registerHandlers();
	}

	@Override
	public void postInit(){}
}
