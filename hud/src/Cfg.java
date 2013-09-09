package com.qzx.au.hud;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import com.qzx.au.util.Config;

public class Cfg extends Config {
	public static boolean enable_info_hud;
	public static int info_hud_x;
	public static int info_hud_y;

	public static boolean enable_armor_hud;
	public static boolean always_show_armor_hud;
	public static int armor_hud_x;
	public static int armor_hud_y;
	public static int armor_hud_corner;

	public static boolean enable_potion_hud;
	public static boolean always_show_potion_hud;
	public static int potion_hud_x;
	public static int potion_hud_y;
	public static int potion_hud_corner;

	public static boolean show_inspector = false; // always default to off

	private static final String CATEGORY_ELEMENTS = "show-elements";

	// info hud
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

	public static void init(FMLPreInitializationEvent event){
		Cfg.loadConfig(event);

		Cfg.enable_info_hud = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enable-info-hud", true, null);
		Cfg.info_hud_x = Cfg.getInt(Cfg.CATEGORY_GENERAL, "info-hud-x", 2, null);
		Cfg.info_hud_y = Cfg.getInt(Cfg.CATEGORY_GENERAL, "info-hud-y", 32, null);

		Cfg.enable_armor_hud = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enable-armor-hud", true, null);
		Cfg.always_show_armor_hud = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "always-show-armor-hud", false, null);
		Cfg.armor_hud_x = Cfg.getInt(Cfg.CATEGORY_GENERAL, "armor-hud-x", 2, null);
		Cfg.armor_hud_y = Cfg.getInt(Cfg.CATEGORY_GENERAL, "armor-hud-y", 2, null);
		Cfg.armor_hud_corner = Cfg.getCornerID(Cfg.getString(Cfg.CATEGORY_GENERAL, "armor-hud-corner", "BottomRight", null));

		Cfg.enable_potion_hud = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "enable-potion-hud", true, null);
		Cfg.always_show_potion_hud = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "always-show-potion-hud", false, null);
		Cfg.potion_hud_x = Cfg.getInt(Cfg.CATEGORY_GENERAL, "potion-hud-x", 2, null);
		Cfg.potion_hud_y = Cfg.getInt(Cfg.CATEGORY_GENERAL, "potion-hud-y", 2, null);
		Cfg.potion_hud_corner = Cfg.getCornerID(Cfg.getString(Cfg.CATEGORY_GENERAL, "potion-hud-corner", "BottomLeft", null));

		// info hud
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

		Cfg.clipPositions();

		Cfg.saveConfig();
	}

	public static void save(){
		Cfg.clipPositions();

		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "enable-info-hud", Cfg.enable_info_hud);
		Cfg.setInt(Cfg.CATEGORY_GENERAL, "info-hud-x", Cfg.info_hud_x);
		Cfg.setInt(Cfg.CATEGORY_GENERAL, "info-hud-y", Cfg.info_hud_y);

		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "enable-armor-hud", Cfg.enable_armor_hud);
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "always-show-armor-hud", Cfg.always_show_armor_hud);
		Cfg.setInt(Cfg.CATEGORY_GENERAL, "armor-hud-x", Cfg.armor_hud_x);
		Cfg.setInt(Cfg.CATEGORY_GENERAL, "armor-hud-y", Cfg.armor_hud_y);
		Cfg.setString(Cfg.CATEGORY_GENERAL, "armor-hud-corner", Cfg.getCornerName(Cfg.armor_hud_corner));

		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "enable-potion-hud", Cfg.enable_potion_hud);
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "always-show-potion-hud", Cfg.always_show_potion_hud);
		Cfg.setInt(Cfg.CATEGORY_GENERAL, "potion-hud-x", Cfg.potion_hud_x);
		Cfg.setInt(Cfg.CATEGORY_GENERAL, "potion-hud-y", Cfg.potion_hud_y);
		Cfg.setString(Cfg.CATEGORY_GENERAL, "potion-hud-corner", Cfg.getCornerName(Cfg.potion_hud_corner));

		// info hud
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

	public static String getAlwaysShowName(boolean always_show){
		return (always_show ? "Hide when chat open" : "Show when chat open");
	}

	public static String[] corners = { "TopLeft", "TopRight", "BottomLeft", "BottomRight" };
	public static int HUD_CORNER_TOPLEFT = 0;
	public static int HUD_CORNER_TOPRIGHT = 1;
	public static int HUD_CORNER_BOTTOMLEFT = 2;
	public static int HUD_CORNER_BOTTOMRIGHT = 3;
	public static int getCornerID(String s){
		if(s.equals(Cfg.corners[Cfg.HUD_CORNER_TOPRIGHT])) return Cfg.HUD_CORNER_TOPRIGHT;
		if(s.equals(Cfg.corners[Cfg.HUD_CORNER_BOTTOMLEFT])) return Cfg.HUD_CORNER_BOTTOMLEFT;
		if(s.equals(Cfg.corners[Cfg.HUD_CORNER_BOTTOMRIGHT])) return Cfg.HUD_CORNER_BOTTOMRIGHT;
		return Cfg.HUD_CORNER_TOPLEFT;
	}
	public static String getCornerName(int corner){
		if(corner < Cfg.corners.length) return Cfg.corners[corner];
		return Cfg.corners[0];
	}

	private static void clipPositions(){
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution screen = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		int width = screen.getScaledWidth();
		int height = screen.getScaledHeight();

		if(Cfg.info_hud_x < 0) Cfg.info_hud_x = 0;
		if(Cfg.info_hud_x > width) Cfg.info_hud_x = width;
		if(Cfg.info_hud_y < 0) Cfg.info_hud_y = 0;
		if(Cfg.info_hud_y > height) Cfg.info_hud_y = height;
		if(Cfg.armor_hud_x < 0) Cfg.armor_hud_x = 0;
		if(Cfg.armor_hud_x > width) Cfg.armor_hud_x = width;
		if(Cfg.armor_hud_y < 0) Cfg.armor_hud_y = 0;
		if(Cfg.armor_hud_y > height) Cfg.armor_hud_y = height;
		if(Cfg.potion_hud_x < 0) Cfg.potion_hud_x = 0;
		if(Cfg.potion_hud_x > width) Cfg.potion_hud_x = width;
		if(Cfg.potion_hud_y < 0) Cfg.potion_hud_y = 0;
		if(Cfg.potion_hud_y > height) Cfg.potion_hud_y = height;
	}
}
