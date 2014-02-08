package com.qzx.au.hud;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import com.qzx.au.core.Config;

public class Cfg extends Config {
	public static boolean enable_info_hud;
	public static boolean always_show_info_hud;
	public static int info_hud_x;
	public static int info_hud_y;
	public static float info_hud_scale;
	public static boolean enable_advanced_inspector;
	public static boolean hide_silverfish_blocks;

	public static boolean enable_armor_hud;
	public static boolean always_show_armor_hud;
	public static int armor_hud_x;
	public static int armor_hud_y;
	public static float armor_hud_scale;
	public static int armor_hud_corner;
	public static int armor_hud_durability;

	public static boolean enable_potion_hud;
	public static boolean always_show_potion_hud;
	public static int potion_hud_x;
	public static int potion_hud_y;
	public static float potion_hud_scale;
	public static int potion_hud_corner;

	public static boolean enable_shop_signs_hud;
	public static boolean always_show_shop_signs_hud;
	public static float shop_signs_hud_scale;

	public static boolean show_inspector = false; // always default to off

	// info hud
	public static boolean show_world;
	public static boolean show_biome;
	public static boolean show_heading;
	public static boolean show_position;
	public static boolean show_position_eyes;
	public static boolean show_light;
	public static boolean show_time;
	public static boolean show_weather;
	public static boolean show_used_inventory;
	public static boolean animate_used_inventory;
	public static boolean show_fps;
	public static boolean show_chunk_updates;
	public static boolean show_entities;
	public static boolean show_particles;
	public static boolean show_memory;
	public static boolean show_tps;
	public static boolean show_block_name;

	public static void init(FMLPreInitializationEvent event){
		Cfg.loadConfig(event);

		// info
		Cfg.enable_info_hud = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.info.enable", true, null);
		Cfg.always_show_info_hud = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.info.alwaysShow", false, null);
		Cfg.info_hud_x = Cfg.getInt(Cfg.CATEGORY_GENERAL, "hud.info.x", 2, null);
		Cfg.info_hud_y = Cfg.getInt(Cfg.CATEGORY_GENERAL, "hud.info.y", 32, null);
		Cfg.info_hud_scale = (float)Cfg.getDouble(Cfg.CATEGORY_GENERAL, "hud.info.scale", 1.0F, null);
		Cfg.enable_advanced_inspector = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.info.advancedInspector.show", false, null);
		Cfg.hide_silverfish_blocks = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.info.silverfishBlocks.hide", true, "set to false to see silverfish blocks");
		// info elements
		Cfg.show_world = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.world", true, null);
		Cfg.show_biome = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.biome", true, null);
		Cfg.show_heading = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.heading", true, null);
		Cfg.show_position = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.position", true, null);
		Cfg.show_position_eyes = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.position.eyes", true, null);
		Cfg.show_light = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.light", true, null);
		Cfg.show_time = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.time", true, null);
		Cfg.show_weather = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.weather", true, null);
		Cfg.show_used_inventory = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.usedInventory", true, null);
		Cfg.animate_used_inventory = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.usedInventory.animate", true, null);
		Cfg.show_fps = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.fps", true, null);
		Cfg.show_chunk_updates = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.chunkUpdates", true, null);
		Cfg.show_entities = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.entities", true, null);
		Cfg.show_particles = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.particles", true, null);
		Cfg.show_memory = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.memory", false, null);
		Cfg.show_tps = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.tps", true, null);
		Cfg.show_block_name = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.blockName", true, null);

		// armor
		Cfg.enable_armor_hud = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.armor.enable", true, null);
		Cfg.always_show_armor_hud = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.armor.alwaysShow", false, null);
		Cfg.armor_hud_x = Cfg.getInt(Cfg.CATEGORY_GENERAL, "hud.armor.x", 2, null);
		Cfg.armor_hud_y = Cfg.getInt(Cfg.CATEGORY_GENERAL, "hud.armor.y", 2, null);
		Cfg.armor_hud_scale = (float)Cfg.getDouble(Cfg.CATEGORY_GENERAL, "hud.armor.scale", 1.0F, null);
		Cfg.armor_hud_corner = Cfg.getCornerID(Cfg.getString(Cfg.CATEGORY_GENERAL, "hud.armor.corner", "BottomRight", null));
		Cfg.armor_hud_durability = Cfg.clipDurabilityID(Cfg.getInt(Cfg.CATEGORY_GENERAL, "hud.armor.durability", Cfg.HUD_DURABILITY_PERCENT, null));

		// potion
		Cfg.enable_potion_hud = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.potion.enable", true, null);
		Cfg.always_show_potion_hud = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.potion.alwaysShow", false, null);
		Cfg.potion_hud_x = Cfg.getInt(Cfg.CATEGORY_GENERAL, "hud.potion.x", 2, null);
		Cfg.potion_hud_y = Cfg.getInt(Cfg.CATEGORY_GENERAL, "hud.potion.y", 2, null);
		Cfg.potion_hud_scale = (float)Cfg.getDouble(Cfg.CATEGORY_GENERAL, "hud.potion.scale", 1.0F, null);
		Cfg.potion_hud_corner = Cfg.getCornerID(Cfg.getString(Cfg.CATEGORY_GENERAL, "hud.potion.corner", "BottomLeft", null));

		// shop signs
		Cfg.enable_shop_signs_hud = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.shopSigns.enable", true, null);
		Cfg.always_show_shop_signs_hud = Cfg.getBoolean(Cfg.CATEGORY_GENERAL, "hud.shopSigns.alwaysShow", false, null);
		Cfg.shop_signs_hud_scale = (float)Cfg.getDouble(Cfg.CATEGORY_GENERAL, "hud.shopSigns.scale", 1.0F, null);

		// fix dependent elements
		if(Cfg.show_position == false && Cfg.show_position_eyes == true)
			Cfg.show_position_eyes = false;

		Cfg.clipPositions();
		Cfg.clipScale();

		Cfg.saveConfig();
	}

	public static void save(){
		Cfg.clipPositions();
		Cfg.clipScale();

		// info
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.info.enable", Cfg.enable_info_hud, null);
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.info.alwaysShow", Cfg.always_show_info_hud, null);
		Cfg.setInt(Cfg.CATEGORY_GENERAL, "hud.info.x", Cfg.info_hud_x, null);
		Cfg.setInt(Cfg.CATEGORY_GENERAL, "hud.info.y", Cfg.info_hud_y, null);
		Cfg.setDouble(Cfg.CATEGORY_GENERAL, "hud.info.scale", Cfg.info_hud_scale, null);
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.info.advancedInspector.show", Cfg.enable_advanced_inspector, null);
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.info.silverfishBlocks.hide", Cfg.hide_silverfish_blocks, "set to false to see silverfish blocks");
		// info elements
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.world", Cfg.show_world, null);
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.biome", Cfg.show_biome, null);
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.heading", Cfg.show_heading, null);
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.position", Cfg.show_position, null);
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.position.eyes", Cfg.show_position_eyes, null);
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.light", Cfg.show_light, null);
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.time", Cfg.show_time, null);
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.weather", Cfg.show_weather, null);
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.usedInventory", Cfg.show_used_inventory, null);
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.usedInventory.animate", Cfg.animate_used_inventory, null);
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.fps", Cfg.show_fps, null);
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.chunkUpdates", Cfg.show_chunk_updates, null);
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.entities", Cfg.show_entities, null);
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.particles", Cfg.show_particles, null);
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.memory", Cfg.show_memory, null);
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.tps", Cfg.show_tps, null);
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.info.elements.blockName", Cfg.show_block_name, null);

		// armor
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.armor.enable", Cfg.enable_armor_hud, null);
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.armor.alwaysShow", Cfg.always_show_armor_hud, null);
		Cfg.setInt(Cfg.CATEGORY_GENERAL, "hud.armor.x", Cfg.armor_hud_x, null);
		Cfg.setInt(Cfg.CATEGORY_GENERAL, "hud.armor.y", Cfg.armor_hud_y, null);
		Cfg.setDouble(Cfg.CATEGORY_GENERAL, "hud.armor.scale", Cfg.armor_hud_scale, null);
		Cfg.setString(Cfg.CATEGORY_GENERAL, "hud.armor.corner", Cfg.getCornerName(Cfg.armor_hud_corner), null);
		Cfg.setInt(Cfg.CATEGORY_GENERAL, "hud.armor.durability", Cfg.armor_hud_durability, null);

		// potion
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.potion.enable", Cfg.enable_potion_hud, null);
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.potion.alwaysShow", Cfg.always_show_potion_hud, null);
		Cfg.setInt(Cfg.CATEGORY_GENERAL, "hud.potion.x", Cfg.potion_hud_x, null);
		Cfg.setInt(Cfg.CATEGORY_GENERAL, "hud.potion.y", Cfg.potion_hud_y, null);
		Cfg.setDouble(Cfg.CATEGORY_GENERAL, "hud.potion.scale", Cfg.potion_hud_scale, null);
		Cfg.setString(Cfg.CATEGORY_GENERAL, "hud.potion.corner", Cfg.getCornerName(Cfg.potion_hud_corner), null);

		// shop signs
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.shopSigns.enable", Cfg.enable_shop_signs_hud, null);
		Cfg.setBoolean(Cfg.CATEGORY_GENERAL, "hud.shopSigns.alwaysShow", Cfg.always_show_shop_signs_hud, null);
		Cfg.setDouble(Cfg.CATEGORY_GENERAL, "hud.shopSigns.scale", Cfg.shop_signs_hud_scale, null);

		Cfg.saveConfig();
	}

	//////////

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

	//////////

	private static float clipScale(float scale){
		for(int i = 0; i < Cfg.scale_values.length; i++)
			if(scale == Cfg.scale_values[i]) return scale;
		return Cfg.scale_values[DEFAULT_SCALE_VALUE];
	}
	private static void clipScale(){
		Cfg.info_hud_scale = Cfg.clipScale(Cfg.info_hud_scale);
		Cfg.armor_hud_scale = Cfg.clipScale(Cfg.armor_hud_scale);
		Cfg.potion_hud_scale = Cfg.clipScale(Cfg.potion_hud_scale);
		Cfg.shop_signs_hud_scale = Cfg.clipScale(Cfg.shop_signs_hud_scale);
	}

	private static int DEFAULT_SCALE_VALUE = 1;
	private static float[] scale_values = { 0.5F, 1.0F }; // 2.0F
	private static String[] scale_names = { "Small - 50%", "Normal - 100%" }; // "Large"
	public static String getScaleName(float scale){
		for(int i = 0; i < Cfg.scale_values.length; i++)
			if(scale == Cfg.scale_values[i]) return Cfg.scale_names[i];
		return Cfg.scale_names[Cfg.DEFAULT_SCALE_VALUE];
	}

	public static float toggleScale(float scale){
		int n = Cfg.DEFAULT_SCALE_VALUE;
		for(int i = 0; i < Cfg.scale_values.length; i++)
			if(scale == Cfg.scale_values[i]) n = i;
		n++;
		if(n >= Cfg.scale_values.length) n = 0;
		return Cfg.scale_values[n];
	}

	//////////

	public static String[] durability_values = { "Durability Bar", "Durability / Max", "Durability %" };
	public static int HUD_DURABILITY_BAR = 0;
	public static int HUD_DURABILITY_VALUE = 1;
	public static int HUD_DURABILITY_PERCENT = 2;

	public static int clipDurabilityID(int value){
		if(value >= 0 && value < Cfg.durability_values.length) return value;
		return Cfg.HUD_DURABILITY_BAR;
	}
	public static String getDurabilityName(int value){
		if(value < Cfg.durability_values.length) return Cfg.durability_values[value];
		return Cfg.durability_values[0];
	}

}
