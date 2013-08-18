package com.qzx.au.extras;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	@Override
	public void registerEvents(){}

	@Override
	public void registerRenderers(){}

	@Override
	public void registerHandlers(){}

	@Override
	public void postInit(){}
}
