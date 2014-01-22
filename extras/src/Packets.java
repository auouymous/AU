package com.qzx.au.extras;

public class Packets {
	// to SERVER
	public static int SERVER_CHROMA_SET_RECIPE					= 100;	// byte
	public static int SERVER_CHROMA_SET_LOCK					= 101;	// boolean
	public static int SERVER_ENDER_PLAYER_MOVEMENT				= 202;	// byte
	public static int SERVER_ENDER_SET_BUTTON					= 203;	// byte
	public static int SERVER_ENDER_SET_PCL						= 204;	// string

	// to CLIENT
	public static final int CLIENT_CHROMA_SET_RECIPE			= 100;	// byte
	public static final int CLIENT_CHROMA_SET_LOCK				= 101;	// boolean
	public static final int CLIENT_CHROMA_RESET_WATER			= 102;	// -
	public static final int CLIENT_CHROMA_SET_COLOR				= 103;	// byte
	public static final int CLIENT_CHROMA_UPDATE_OUTPUT			= 104;	// -

	public static final int CLIENT_ENDER_SPAWN_PARTICLES		= 110;	// integer
	public static final int CLIENT_ENDER_SET_PLAYER_DIRECTION	= 111;	// byte
	public static final int CLIENT_ENDER_SET_TELEPORT_DIRECTION	= 112;	// byte
	public static final int CLIENT_ENDER_SET_PLAYER_CONTROL		= 113;	// boolean
	public static final int CLIENT_ENDER_SET_PLAYER_RS_CONTROL	= 114;	// boolean
	public static final int CLIENT_ENDER_SET_REDSTONE_CONTROL	= 115;	// boolean
	public static final int CLIENT_ENDER_SET_PCL				= 116;	// string
}
