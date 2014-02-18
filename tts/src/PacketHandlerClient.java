package com.qzx.au.tts;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;

import java.io.DataInputStream;

import com.qzx.au.core.PacketUtils;

@SideOnly(Side.CLIENT)
public class PacketHandlerClient implements IPacketHandler {
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player){
		DataInputStream data = PacketUtils.getPacketData(packet);
		int id = PacketUtils.getPacketID(data);
		if(id == -1){ Debug.error("CLIENT: Invalid packet ID "+id); return; }


		if(id == Packets.CLIENT_TTS_PLAY_SOUND){
			// play sound on client
			Object[] values = PacketUtils.getPacketData(data, Integer.class, Integer.class, Integer.class);
			if(values == null){ Debug.error("CLIENT: Invalid TTS play sound packet"); return; }

			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)values[0], (Integer)values[1], (Integer)values[2]);
			if(te instanceof TileEntityTTS)
				((TileEntityTTS)te).playSoundOnClient((EntityPlayer)player);
		} else if(id == Packets.CLIENT_TTS_SET_TEXT){
			// set text
			Object[] values = PacketUtils.getPacketData(data, Integer.class, Integer.class, Integer.class, String.class);
			if(values == null){ Debug.error("CLIENT: Invalid TTS text packet"); return; }

			TileEntity te = ((EntityPlayer)player).worldObj.getBlockTileEntity((Integer)values[0], (Integer)values[1], (Integer)values[2]);
			if(te instanceof TileEntityTTS)
				((TileEntityTTS)te).setText((String)values[3], false);
		}
	}
}
