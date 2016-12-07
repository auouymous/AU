package com.qzx.au.extras;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;

import java.io.DataInputStream;

import com.qzx.au.core.BlockCoord;
import com.qzx.au.core.PacketUtils;

@SideOnly(Side.CLIENT)
public class PacketHandlerClient implements IPacketHandler {
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player){
		DataInputStream data = PacketUtils.getPacketData(packet);
		int id = PacketUtils.getPacketID(data);
		if(id == -1){ Debug.error("CLIENT: Invalid packet ID "+id); return; }

		if(id == Packets.CLIENT_CHROMA_SET_RECIPE){
			// chroma recipe buttons
			Object[] values = PacketUtils.getPacketData(data, Integer.class, Integer.class, Integer.class, Byte.class);
			if(values == null){ Debug.error("CLIENT: Invalid chroma recipe button packet"); return; }

			TileEntity te = BlockCoord.getTileEntity(((EntityPlayer)player).worldObj, (Integer)values[0], (Integer)values[1], (Integer)values[2]);
			if(te instanceof TileEntityChromaInfuser)
				((TileEntityChromaInfuser)te).setRecipeButton(ChromaButton.values()[(Byte)values[3]], false);
		} else if(id == Packets.CLIENT_CHROMA_SET_LOCK){
			// set chroma lock
			Object[] values = PacketUtils.getPacketData(data, Integer.class, Integer.class, Integer.class, Boolean.class);
			if(values == null){ Debug.error("CLIENT: Invalid chroma lock packet"); return; }

			TileEntity te = BlockCoord.getTileEntity(((EntityPlayer)player).worldObj, (Integer)values[0], (Integer)values[1], (Integer)values[2]);
			if(te instanceof TileEntityChromaInfuser)
				((TileEntityChromaInfuser)te).setLocked((Boolean)values[3], false);
		} else if(id == Packets.CLIENT_CHROMA_RESET_WATER){
			// reset chroma water
			Object[] values = PacketUtils.getPacketData(data, Integer.class, Integer.class, Integer.class);
			if(values == null){ Debug.error("CLIENT: Invalid reset chroma water packet"); return; }

			TileEntity te = BlockCoord.getTileEntity(((EntityPlayer)player).worldObj, (Integer)values[0], (Integer)values[1], (Integer)values[2]);
			if(te instanceof TileEntityChromaInfuser)
				((TileEntityChromaInfuser)te).resetWater(false);
		} else if(id == Packets.CLIENT_CHROMA_SET_COLOR){
			// set chroma color and dye quantity
			Object[] values = PacketUtils.getPacketData(data, Integer.class, Integer.class, Integer.class, Byte.class, Byte.class);
			if(values == null){ Debug.error("CLIENT: Invalid chroma color packet"); return; }

			TileEntity te = BlockCoord.getTileEntity(((EntityPlayer)player).worldObj, (Integer)values[0], (Integer)values[1], (Integer)values[2]);
			if(te instanceof TileEntityChromaInfuser)
				((TileEntityChromaInfuser)te).setDyeColor((Byte)values[3], (Byte)values[4]);
		} else if(id == Packets.CLIENT_CHROMA_UPDATE_OUTPUT){
			// update chroma output
			Object[] values = PacketUtils.getPacketData(data, Integer.class, Integer.class, Integer.class, Byte.class);
			if(values == null){ Debug.error("CLIENT: Invalid update chroma output packet"); return; }

			TileEntity te = BlockCoord.getTileEntity(((EntityPlayer)player).worldObj, (Integer)values[0], (Integer)values[1], (Integer)values[2]);
			if(te instanceof TileEntityChromaInfuser)
				((TileEntityChromaInfuser)te).updateOutput((Byte)values[3]);

		//////////

		} else if(id == Packets.CLIENT_ENDER_SPAWN_PARTICLES){
			// spawn particles
			Object[] values = PacketUtils.getPacketData(data, Integer.class, Integer.class, Integer.class, Integer.class);
			if(values == null){ Debug.error("CLIENT: Invalid spawn particles packet"); return; }

			TileEntity te = BlockCoord.getTileEntity(((EntityPlayer)player).worldObj, (Integer)values[0], (Integer)values[1], (Integer)values[2]);
			if(te instanceof TileEntityEnderCube)
				((TileEntityEnderCube)te).spawnParticles((Integer)values[3]);
		} else if(id == Packets.CLIENT_ENDER_SET_PLAYER_DIRECTION){
			// set player direction
			Object[] values = PacketUtils.getPacketData(data, Integer.class, Integer.class, Integer.class, Byte.class);
			if(values == null){ Debug.error("CLIENT: Invalid player direction packet"); return; }

			TileEntity te = BlockCoord.getTileEntity(((EntityPlayer)player).worldObj, (Integer)values[0], (Integer)values[1], (Integer)values[2]);
			if(te instanceof TileEntityEnderCube)
				((TileEntityEnderCube)te).setPlayerDirection((Byte)values[3]);
		} else if(id == Packets.CLIENT_ENDER_SET_TELEPORT_DIRECTION){
			// set teleport direction
			Object[] values = PacketUtils.getPacketData(data, Integer.class, Integer.class, Integer.class, Byte.class);
			if(values == null){ Debug.error("CLIENT: Invalid teleport direction packet"); return; }

			TileEntity te = BlockCoord.getTileEntity(((EntityPlayer)player).worldObj, (Integer)values[0], (Integer)values[1], (Integer)values[2]);
			if(te instanceof TileEntityEnderCube)
				((TileEntityEnderCube)te).setTeleportDirection((Byte)values[3]);
		} else if(id == Packets.CLIENT_ENDER_SET_PLAYER_CONTROL){
			// set player control
			Object[] values = PacketUtils.getPacketData(data, Integer.class, Integer.class, Integer.class, Boolean.class);
			if(values == null){ Debug.error("CLIENT: Invalid player control packet"); return; }

			TileEntity te = BlockCoord.getTileEntity(((EntityPlayer)player).worldObj, (Integer)values[0], (Integer)values[1], (Integer)values[2]);
			if(te instanceof TileEntityEnderCube)
				((TileEntityEnderCube)te).setPlayerControl((Boolean)values[3]);
		} else if(id == Packets.CLIENT_ENDER_SET_PLAYER_RS_CONTROL){
			// set player redstone control
			Object[] values = PacketUtils.getPacketData(data, Integer.class, Integer.class, Integer.class, Boolean.class);
			if(values == null){ Debug.error("CLIENT: Invalid player redstone control packet"); return; }

			TileEntity te = BlockCoord.getTileEntity(((EntityPlayer)player).worldObj, (Integer)values[0], (Integer)values[1], (Integer)values[2]);
			if(te instanceof TileEntityEnderCube)
				((TileEntityEnderCube)te).setPlayerRedstoneControl((Boolean)values[3]);
		} else if(id == Packets.CLIENT_ENDER_SET_REDSTONE_CONTROL){
			// set redstone control
			Object[] values = PacketUtils.getPacketData(data, Integer.class, Integer.class, Integer.class, Boolean.class);
			if(values == null){ Debug.error("CLIENT: Invalid redstone control packet"); return; }

			TileEntity te = BlockCoord.getTileEntity(((EntityPlayer)player).worldObj, (Integer)values[0], (Integer)values[1], (Integer)values[2]);
			if(te instanceof TileEntityEnderCube)
				((TileEntityEnderCube)te).setRedstoneControl((Boolean)values[3]);
		} else if(id == Packets.CLIENT_ENDER_SET_PCL){
			// set player control whitelist
			Object[] values = PacketUtils.getPacketData(data, Integer.class, Integer.class, Integer.class, String.class);
			if(values == null){ Debug.error("CLIENT: Invalid player control whitelist packet"); return; }

			TileEntity te = BlockCoord.getTileEntity(((EntityPlayer)player).worldObj, (Integer)values[0], (Integer)values[1], (Integer)values[2]);
			if(te instanceof TileEntityEnderCube)
				((TileEntityEnderCube)te).setPlayerControlWhitelist((String)values[3], false);
		}
	}
}
