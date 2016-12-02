package com.qzx.au.core;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;

IMPORT_FORGE_DIRECTION

import java.io.DataInputStream;

@SideOnly(Side.CLIENT)
public class PacketHandlerClient implements IPacketHandler {
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player){
		DataInputStream data = PacketUtils.getPacketData(packet);
		int id = PacketUtils.getPacketID(data);
		if(id == -1){ Debug.error("CLIENT: Invalid packet ID "+id); return; }

		if(id == Packets.CLIENT_ACCESS_PLAYERS){
			// number of players accessing block
			Object[] values = PacketUtils.getPacketData(data, Integer.class, Integer.class, Integer.class, Short.class);
			if(values == null){ Debug.error("CLIENT: Invalid accessing players packet"); return; }

			TileEntity te = BlockCoord.getTileEntity(((EntityPlayer)player).worldObj, (Integer)values[0], (Integer)values[1], (Integer)values[2]);
			if(te instanceof TileEntityAU)
				((TileEntityAU)te).setAccessPlayers((Short)values[3]);
		} else if(id == Packets.CLIENT_DIRECTION){
			// block direction
			Object[] values = PacketUtils.getPacketData(data, Integer.class, Integer.class, Integer.class, Byte.class);
			if(values == null){ Debug.error("CLIENT: Invalid block direction packet"); return; }

			TileEntity te = BlockCoord.getTileEntity(((EntityPlayer)player).worldObj, (Integer)values[0], (Integer)values[1], (Integer)values[2]);
			if(te instanceof TileEntityAU)
				((TileEntityAU)te).setDirection(ForgeDirection.values()[(Byte)values[3]], false);
		} else if(id == Packets.CLIENT_BLOCK_CAMO){
			// block camo
			Object[] values = PacketUtils.getPacketData(data, Integer.class, Integer.class, Integer.class, Integer.class, Byte.class);
			if(values == null){ Debug.error("CLIENT: Invalid block camo packet"); return; }

			TileEntity te = BlockCoord.getTileEntity(((EntityPlayer)player).worldObj, (Integer)values[0], (Integer)values[1], (Integer)values[2]);
			if(te instanceof TileEntityAU)
				((TileEntityAU)te).setCamoBlock((Integer)values[3], (Byte)values[4]);
		} else if(id == Packets.CLIENT_SIDED_IO){
			// set sided I/O
			Object[] values = PacketUtils.getPacketData(data, Integer.class, Integer.class, Integer.class, Byte.class, Byte.class);
			if(values == null){ Debug.error("CLIENT: Invalid set sided IO packet"); return; }

			TileEntity te = BlockCoord.getTileEntity(((EntityPlayer)player).worldObj, (Integer)values[0], (Integer)values[1], (Integer)values[2]);
			if(te instanceof TileEntityAU)
				((TileEntityAU)te).setSide((Byte)values[3], (Byte)values[4]);
		}
	}
}
