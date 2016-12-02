package com.qzx.au.tts;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;

import java.io.DataInputStream;

import com.qzx.au.core.BlockCoord;
import com.qzx.au.core.PacketUtils;

public class PacketHandlerServer implements IPacketHandler {
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player){
		DataInputStream data = PacketUtils.getPacketData(packet);
		int id = PacketUtils.getPacketID(data);
		if(id == -1){ Debug.error("SERVER: Invalid packet ID "+id); return; }

		if(id == Packets.SERVER_TTS_SET_BUTTON){
/*
			// 
			Object[] values = PacketUtils.getPacketData(data, Integer.class, Integer.class, Integer.class, Byte.class);
			if(values == null){ Debug.error("SERVER: Invalid *** packet"); return; }		

			TileEntity te = BlockCoord.getTileEntity(((EntityPlayer)player).worldObj, (Integer)values[0], (Integer)values[1], (Integer)values[2]);
			if(te instanceof TileEntityTTS){
				EnderButton button = TTSButton.values()[(Byte)values[3]];
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
*/
		} else if(id == Packets.SERVER_TTS_SET_TEXT){
			// text
			Object[] values = PacketUtils.getPacketData(data, Integer.class, Integer.class, Integer.class, String.class);
			if(values == null){ Debug.error("SERVER: Invalid TTS text packet"); return; }

			TileEntity te = BlockCoord.getTileEntity(((EntityPlayer)player).worldObj, (Integer)values[0], (Integer)values[1], (Integer)values[2]);
			if(te instanceof TileEntityTTS)
				((TileEntityTTS)te).setText((String)values[3], true);
		}
	}
}
