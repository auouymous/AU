package com.qzx.au.extras;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.qzx.au.core.TileEntityAU;

public class CommonProxy implements IGuiHandler {
	public static TickHandlerServerPlayer tickHandlerServerPlayer = new TickHandlerServerPlayer();

	public void registerEvents(){}
	public void registerRenderers(){}
	public void registerHandlers(){
		// Gui Handler
		NetworkRegistry.instance().registerGuiHandler(THIS_MOD.instance, THIS_MOD.proxy);

		// Tick Handler (server player)
		TickRegistry.registerTickHandler(this.tickHandlerServerPlayer, Side.SERVER);
	}
	public void registerEntities(){
		GameRegistry.registerTileEntity(TileEntityChromaInfuser.class, "AU-ChromaInfuser");
		GameRegistry.registerTileEntity(TileEntityEnderCube.class, "AU-EnderCube");
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
		return null;
	}
}
