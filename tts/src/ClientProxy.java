package com.qzx.au.tts;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.qzx.au.core.BlockCoord;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	@Override
	public void registerEvents(){}

	@Override
	public void registerRenderers(){}

	@Override
	public void registerHandlers(){
		super.registerHandlers();
	}

	//////////

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
		if(id == Guis.TILE_GUI){
			// all TileEntityAUs that have a gui
			TileEntity tileEntity = BlockCoord.getTileEntity(world, x, y, z);
			if(tileEntity instanceof TileEntityTTS)
				return new GuiTTS(player.inventory, (TileEntityTTS)tileEntity);
		}
		return null;
	}
}
