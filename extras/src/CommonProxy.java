package com.qzx.au.extras;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.qzx.au.core.TileEntityAU;

public class CommonProxy implements IGuiHandler {
	public void registerEvents(){}
	public void registerRenderers(){}
	public void registerHandlers(){
		// Gui Handler
		NetworkRegistry.instance().registerGuiHandler(AUExtras.instance, AUExtras.proxy);
	}
	public void registerEntities(){
		GameRegistry.registerTileEntity(TileEntityChromaInfuser.class, "AU-ChromaInfuser");
	}
	public void postInit(){}

	//////////

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
		if(id == Guis.TILE_GUI){
			// all TileEntityAUs that have a gui
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			if(tileEntity instanceof TileEntityAU)
				return ((TileEntityAU)tileEntity).getContainer(player.inventory);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
		if(id == Guis.TILE_GUI){
			// all TileEntityAUs that have a gui
			TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
			if(tileEntity instanceof TileEntityAU)
				return ((TileEntityAU)tileEntity).getGuiContainer(player.inventory);
		}
		return null;
	}
}
