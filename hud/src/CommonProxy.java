package com.qzx.au.hud;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CommonProxy implements IGuiHandler {
	public void registerEvents(){}
	public void registerRenderers(){}
	public void registerHandlers(){
		// Gui Handler
		NETWORK_REGISTRY_INSTANCE.registerGuiHandler(THIS_MOD.instance, THIS_MOD.proxy);
	}

	//////////

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
		if(id == Guis.HUD_OPTIONS)
			return new GuiOptions(player);
		return null;
	}
}
