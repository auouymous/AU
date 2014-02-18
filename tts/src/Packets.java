package com.qzx.au.tts;

public class Packets {
	// to SERVER
	public static int SERVER_TTS_SET_BUTTON				= 100;	// byte
	public static int SERVER_TTS_SET_TEXT				= 101;	// string
	public static int SERVER_TTS_SET_MIN_BLOCKS			= 102;	// byte
	public static int SERVER_TTS_SET_MAX_BLOCKS			= 103;	// byte

	// to CLIENT
	public static final int CLIENT_TTS_PLAY_SOUND			= 100;	// -
	public static final int CLIENT_TTS_SET_TEXT				= 101;	// string
	public static final int CLIENT_TTS_SET_MIN_BLOCKS		= 102;	// byte
	public static final int CLIENT_TTS_SET_MAX_BLOCKS		= 103;	// byte
}
