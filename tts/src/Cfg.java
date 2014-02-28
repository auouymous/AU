package com.qzx.au.tts;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import com.qzx.au.core.Config;

public class Cfg extends Config {
	public static final Config instance = new Config();

	// IDs
	public static int blockTTS;

	public static void init(FMLPreInitializationEvent event){
		Cfg.instance.loadConfig(event);

		//////////

		Cfg.blockTTS = Cfg.instance.getBlock("block.TTS.id", 3799, null);

		//////////

		Cfg.instance.saveConfig();
	}
}
