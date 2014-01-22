package com.qzx.au.core;

import cpw.mods.fml.common.network.PacketDispatcher;

import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class PacketUtils {
	public static DataInputStream getPacketData(Packet250CustomPayload packet){
		return new DataInputStream(new ByteArrayInputStream(packet.data));
	}

	public static int getPacketID(DataInputStream in){
		if(in == null) return -1;
		try {
			return (int)in.readInt();
		} catch(Exception e){
			Debug.error("Unable to read packet ID");
			return -1;
		}
	}

	public static Object[] getPacketData(DataInputStream in, Class... types){
		Object[] objects = new Object[types.length];
		try {
			for(int i = 0; i < types.length; i++){
				Class t = types[i];
				     if(t == Boolean.class)		objects[i] = (Boolean)in.readBoolean();
				else if(t == Byte.class)		objects[i] = (Byte)in.readByte();
//				else if(t == Short.class)		objects[i] = (Short)in.readShort();
				else if(t == Integer.class)		objects[i] = (Integer)in.readInt();
//				else if(t == Long.class)		objects[i] = (Long)in.readLong();
				else if(t == String.class)		objects[i] = (String)in.readUTF();
//				else if(t == Float.class)		objects[i] = (Float)in.readFloat();
//				else if(t == Double.class)		objects[i] = (Double)in.readDouble();
				else {
					Debug.error("Unsupported packet data class");
					return null;
				}
			}
		} catch(Exception e){
			Debug.error("Unable to read packet data");
			return null;
		}
		return objects;
	}

	//////////

	public static Packet250CustomPayload createPacket(String channel, int id, Object... data){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);
		try {
			out.writeInt(id);
			for(Object o : data){
				     if(o instanceof Boolean)	out.writeBoolean((Boolean)o);
				else if(o instanceof Byte)		out.writeByte((Byte)o);
				else if(o instanceof Integer)	out.writeInt((Integer)o);
				else if(o instanceof String)	out.writeUTF((String)o);
				else Debug.error("Invalid packet object");
				// float, double, long, short
			}
		} catch(Exception e){
			Debug.error("Unable to write packet ID/data");
			return null;
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = channel;
		packet.data = baos.toByteArray();
		packet.length = baos.size();
		return packet;
	}

	public static void sendToServer(String channel, int id, Object... data){
		PacketDispatcher.sendPacketToServer(PacketUtils.createPacket(channel, id, data));
	}

	public static int MAX_RANGE = 160; // 10 chunks
//	public static int ACCESS_RANGE = 9; // 9 blocks

	public static void sendToAllAround(World world, int range, String channel, int id, Object... data){
		// first three data values must be coordinates
		if(data.length >= 3){
			int x = (Integer)data[0];
			int y = (Integer)data[1];
			int z = (Integer)data[2];
			PacketDispatcher.sendPacketToAllAround(x, y, z, range, world.provider.dimensionId, PacketUtils.createPacket(channel, id, data));
		}
	}
	public static void sendToAllAround(World world, int range, int x, int y, int z, String channel, int id, Object... data){
		// send packet without tileEntity coordinates in data
		PacketDispatcher.sendPacketToAllAround(x, y, z, range, world.provider.dimensionId, PacketUtils.createPacket(channel, id, data));
	}

	public static void sendToAllInDimension(int dimensionID, String channel, int id, Object... data){
		PacketDispatcher.sendPacketToAllInDimension(PacketUtils.createPacket(channel, id, data), dimensionID);
	}

	public static void sendToAllPlayers(String channel, int id, Object... data){
		PacketDispatcher.sendPacketToAllPlayers(PacketUtils.createPacket(channel, id, data));
	}
}
