package com.qzx.au.core;

// no support for 147
#ifndef MC147

import net.minecraft.network.packet.Packet250CustomPayload;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class PacketUtils {
	public static DataInputStream getPacketData(Packet250CustomPayload packet){
		return new DataInputStream(new ByteArrayInputStream(packet.data));
	}

	public static int getPacketID(DataInputStream in){
		if(in == null) return 0;
		try {
			return (int)in.readInt();
		} catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	public static Object[] getPacketData(DataInputStream in, Class... types){
		Object[] objects = new Object[types.length];
		try {
			for(int i = 0; i < types.length; i++){
				Class t = types[i];
				     if(t == Boolean.class)		objects[i] = (Boolean)in.readBoolean();
				else if(t == Byte.class)		objects[i] = (Byte)in.readByte();
				else if(t == Integer.class)		objects[i] = (Integer)in.readInt();
				else if(t == String.class)		objects[i] = (String)in.readUTF();
				else {
					System.out.println("AU CORE: Invalid packet class");
					return null;
				}
				// float, double, long, short
			}
		} catch(Exception e){
			e.printStackTrace();
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
				else System.out.println("AU CORE: Invalid packet object");
				// float, double, long, short
			}
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = channel;
		packet.data = baos.toByteArray();
		packet.length = baos.size();
		return packet;
	}
}

#endif
// no support for 147
