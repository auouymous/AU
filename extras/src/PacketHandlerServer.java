package com.qzx.au.extras;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

import net.minecraft.entity.EntityLiving;
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

		if(id == Packets.CHROMA_RECIPE_BUTTON){
			// chroma infuser recipe buttons
			Object[] values = PacketUtils.getPacketData(data, Integer.class, Integer.class, Integer.class, Byte.class);
			if(values == null){ Debug.error("Invalid recipe button packet"); return; }

			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)values[0], (Integer)values[1], (Integer)values[2]);
			if(te instanceof TileEntityChromaInfuser)
				((TileEntityChromaInfuser)te).setRecipeButton(ChromaButton.values()[(Byte)values[3]]);;
		} else if(id == Packets.CHROMA_LOCKED_BUTTON){
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
				((TileEntityEnderCube)te).teleportPlayer((EntityLiving)player, ((Byte)values[3] == 1));
		} else if(id == Packets.ENDER_BUTTON){
			// ender cube buttons
			Object[] values = PacketUtils.getPacketData(data, Integer.class, Integer.class, Integer.class, Byte.class);
			if(values == null){ Debug.error("Invalid ender cube button packet"); return; }

			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)values[0], (Integer)values[1], (Integer)values[2]);
			if(te instanceof TileEntityEnderCube){
				EnderButton button = EnderButton.values()[(Byte)values[3]];
				switch(button){
				case BUTTON_PLAYER_UD:
				case BUTTON_PLAYER_NS:
				case BUTTON_PLAYER_EW:
					((TileEntityEnderCube)te).setPlayerDirection(button);
					break;
				case BUTTON_DOWN:
				case BUTTON_UP:
				case BUTTON_NORTH:
				case BUTTON_SOUTH:
				case BUTTON_WEST:
				case BUTTON_EAST:
					((TileEntityEnderCube)te).setTeleportDirection(button);
					break;
				case BUTTON_CONTROL_PLAYER:
					((TileEntityEnderCube)te).togglePlayerControl();
					break;
				case BUTTON_CONTROL_PLAYER_RS:
					((TileEntityEnderCube)te).togglePlayerRedstoneControl();
					break;
				case BUTTON_CONTROL_REDSTONE:
					((TileEntityEnderCube)te).toggleRedstoneControl();
					break;
				}
			}
		} else if(id == Packets.UPDATE_PCL){
			// ender cube player control whitelist
			Object[] values = PacketUtils.getPacketData(data, Integer.class, Integer.class, Integer.class, String.class);
			if(values == null){ Debug.error("Invalid player control whitelist packet"); return; }

			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)values[0], (Integer)values[1], (Integer)values[2]);
			if(te instanceof TileEntityEnderCube)
				((TileEntityEnderCube)te).setPlayerControlWhitelist((String)values[3]);
		}
	}
}
