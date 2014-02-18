package com.qzx.au.tts;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import com.qzx.au.core.Config;

public class Cfg extends Config {
	// IDs
	public static int blockTTS;

	public static void init(FMLPreInitializationEvent event){
		Cfg.loadConfig(event);

		//////////

		Cfg.blockTTS = Cfg.getBlock("block.TTS.id", 3799, null);

		//////////

		Cfg.saveConfig();
	}
}
