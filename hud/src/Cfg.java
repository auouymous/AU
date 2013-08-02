package com.qzx.au.hud;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import com.qzx.au.util.Config;

public class Cfg extends Config {
	public static boolean enable_info_hud;
	public static int info_hud_x;
	public static int info_hud_y;

	private static final String CATEGORY_ELEMENTS = "show-elements";
	public static boolean show_world;
	public static boolean show_biome;
	public static boolean show_position;
	public static boolean show_position_eyes;
	public static boolean show_light;
	public static boolean show_time;
	public static boolean show_weather;
	public static boolean show_used_inventory;
	public static boolean show_fps;
	public static boolean show_chunk_updates;
	public static boolean show_entities;
	public static boolean show_particles;
	public static boolean show_block_name;
	public static boolean show_block_inspector = false; // always default to off

	public static void init(FMLPreInitializationEvent event){
		Cfg.loadConfig(event);

		Cfg.enable_info_hud = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enable-info-hud", true, null);
		Cfg.info_hud_x = Cfg.getInt(Cfg.CATEGORY_GENERAL, "info-hud-x", 2, null);
		Cfg.info_hud_y = Cfg.getInt(Cfg.CATEGORY_GENERAL, "info-hud-y", 32, null);

		Cfg.show_world = Cfg.getBoolean(Cfg.CATEGORY_ELEMENTS, "world", true, null);
		Cfg.show_biome = Cfg.getBoolean(Cfg.CATEGORY_ELEMENTS, "biome", true, null);
		Cfg.show_position = Cfg.getBoolean(Cfg.CATEGORY_ELEMENTS, "position", true, null);
		Cfg.show_position_eyes = Cfg.getBoolean(Cfg.CATEGORY_ELEMENTS, "position-eyes", true, null);
		Cfg.show_light = Cfg.getBoolean(Cfg.CATEGORY_ELEMENTS, "light", true, null);
		Cfg.show_time = Cfg.getBoolean(Cfg.CATEGORY_ELEMENTS, "time", true, null);
		Cfg.show_weather = Cfg.getBoolean(Cfg.CATEGORY_ELEMENTS, "weather", true, null);
		Cfg.show_used_inventory = Cfg.getBoolean(Cfg.CATEGORY_ELEMENTS, "used-inventory", true, null);
		Cfg.show_fps = Cfg.getBoolean(Cfg.CATEGORY_ELEMENTS, "fps", true, null);
		Cfg.show_chunk_updates = Cfg.getBoolean(Cfg.CATEGORY_ELEMENTS, "chunk-updates", true, null);
		Cfg.show_entities = Cfg.getBoolean(Cfg.CATEGORY_ELEMENTS, "entities", true, null);
		Cfg.show_particles = Cfg.getBoolean(Cfg.CATEGORY_ELEMENTS, "particles", true, null);
		Cfg.show_block_name = Cfg.getBoolean(Cfg.CATEGORY_ELEMENTS, "block-name", true, null);

		// fix dependent elements
		if(Cfg.show_position == false && Cfg.show_position_eyes == true)
			Cfg.show_position_eyes = false;

		Cfg.saveConfig();
	}

	public static void save(){
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "enable-info-hud", Cfg.enable_info_hud);
		Cfg.setInt(Cfg.CATEGORY_GENERAL, "info-hud-x", Cfg.info_hud_x);
		Cfg.setInt(Cfg.CATEGORY_GENERAL, "info-hud-y", Cfg.info_hud_y);

		Cfg.setBoolean(Cfg.CATEGORY_ELEMENTS, "world", Cfg.show_world);
		Cfg.setBoolean(Cfg.CATEGORY_ELEMENTS, "biome", Cfg.show_biome);
		Cfg.setBoolean(Cfg.CATEGORY_ELEMENTS, "position", Cfg.show_position);
		Cfg.setBoolean(Cfg.CATEGORY_ELEMENTS, "position-eyes", Cfg.show_position_eyes);
		Cfg.setBoolean(Cfg.CATEGORY_ELEMENTS, "light", Cfg.show_light);
		Cfg.setBoolean(Cfg.CATEGORY_ELEMENTS, "time", Cfg.show_time);
		Cfg.setBoolean(Cfg.CATEGORY_ELEMENTS, "weather", Cfg.show_weather);
		Cfg.setBoolean(Cfg.CATEGORY_ELEMENTS, "used-inventory", Cfg.show_used_inventory);
		Cfg.setBoolean(Cfg.CATEGORY_ELEMENTS, "fps", Cfg.show_fps);
		Cfg.setBoolean(Cfg.CATEGORY_ELEMENTS, "chunk-updates", Cfg.show_chunk_updates);
		Cfg.setBoolean(Cfg.CATEGORY_ELEMENTS, "entities", Cfg.show_entities);
		Cfg.setBoolean(Cfg.CATEGORY_ELEMENTS, "particles", Cfg.show_particles);
		Cfg.setBoolean(Cfg.CATEGORY_ELEMENTS, "block-name", Cfg.show_block_name);

		Cfg.saveConfig();
	}
}
