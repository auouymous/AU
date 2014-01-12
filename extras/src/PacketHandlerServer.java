package com.qzx.au.extras;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeDirection;

import java.io.DataInputStream;

import com.qzx.au.core.PacketUtils;

public class PacketHandlerServer implements IPacketHandler {
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player){
		DataInputStream data = PacketUtils.getPacketData(packet);
		int id = PacketUtils.getPacketID(data);
		if(id == -1){ Debug.error("Invalid packet ID"); return; }

		if(id == Packets.RECIPE_BUTTON){
			// chroma infuser recipe buttons
			Object[] values = PacketUtils.getPacketData(data, Integer.class, Integer.class, Integer.class, Byte.class);
			if(values == null){ Debug.error("Invalid recipe button packet"); return; }

			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)values[0], (Integer)values[1], (Integer)values[2]);
			if(te instanceof TileEntityChromaInfuser)
				((TileEntityChromaInfuser)te).setRecipeButton(ChromaButton.values()[(Byte)values[3]]);;
		} else if(id == Packets.LOCKED_BUTTON){
			// chroma infuser locked button
			Object[] values = PacketUtils.getPacketData(data, Integer.class, Integer.class, Integer.class, Boolean.class);
			if(values == null){ Debug.error("Invalid locked button packet"); return; }

			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)values[0], (Integer)values[1], (Integer)values[2]);
			if(te instanceof TileEntityChromaInfuser)
				((TileEntityChromaInfuser)te).setLocked(!(Boolean)values[3]);;
		} else if(id == Packets.PLAYER_MOVEMENT){
			// ender cube player movement
			Object[] values = PacketUtils.getPacketData(data, Integer.class, Integer.class, Integer.class, Byte.class);
			if(values == null){ Debug.error("Invalid player movement packet"); return; }

			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)values[0], (Integer)values[1], (Integer)values[2]);
			if(te instanceof TileEntityEnderCube)
				((TileEntityEnderCube)te).teleportPlayer((EntityPlayer)player, ((Byte)values[3] == 1));
		}
	}
}
