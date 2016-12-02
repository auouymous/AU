package com.qzx.au.tts;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.qzx.au.core.BlockCoord;
import com.qzx.au.core.TileEntityAU;

public class CommonProxy implements IGuiHandler {
	public void registerEvents(){}
	public void registerRenderers(){}
	public void registerHandlers(){
		// Gui Handler
		NETWORK_REGISTRY_INSTANCE.registerGuiHandler(THIS_MOD.instance, THIS_MOD.proxy);
	}
	public void registerEntities(){
		GameRegistry.registerTileEntity(TileEntityTTS.class, "AU-TTS");
	}

	//////////

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
		if(id == Guis.TILE_GUI){
			// all TileEntityAUs that have a gui
			TileEntity tileEntity = BlockCoord.getTileEntity(world, x, y, z);
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
