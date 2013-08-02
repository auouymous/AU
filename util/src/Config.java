package com.qzx.au.util;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class Config {
	private static Configuration config = null;

	public static final String CATEGORY_GENERAL = "general";
	public static final String CATEGORY_BLOCKS = "blocks";
	public static final String CATEGORY_ITEMS = "items";

	// called from MOD preInit()
	public static void loadConfig(FMLPreInitializationEvent event){
		Config.config = new Configuration(event.getSuggestedConfigurationFile());
		Config.config.load();
	}

	// save properties to config
	public static void saveConfig(){
		Config.config.save();
	}

	///////////////
	// READ-INIT //
	///////////////

	// get block or item ID
	public static int getBlock(String key, int defaultValue, String comment){
		return Config.config.get(Config.CATEGORY_BLOCKS, key, defaultValue, comment).getInt(defaultValue);
	}
	public static int getItem(String key, int defaultValue, String comment){
		return Config.config.get(Config.CATEGORY_ITEMS, key, defaultValue, comment).getInt(defaultValue);
	}

	// get value
	public static int getInt(String category, String key, int defaultValue, String comment){
		return Config.config.get(category, key, defaultValue, comment).getInt(defaultValue);
	}
	public static double getDouble(String category, String key, double defaultValue, String comment){
		return Config.config.get(category, key, defaultValue, comment).getDouble(defaultValue);
	}
	public static boolean getBoolean(String category, String key, boolean defaultValue, String comment){
		return Config.config.get(category, key, defaultValue, comment).getBoolean(defaultValue);
	}
	public static String getString(String category, String key, String defaultValue, String comment){
		#ifdef MC147
		return Config.config.get(category, key, defaultValue, comment).value;
		#else
		return Config.config.get(category, key, defaultValue, comment).getString();
		#endif
	}

	// get list of values
	public static int[] getIntList(String category, String key, int[] defaultValues, String comment){
		return Config.config.get(category, key, defaultValues, comment).getIntList();
	}
	public static double[] getDoubleList(String category, String key, double[] defaultValues, String comment){
		return Config.config.get(category, key, defaultValues, comment).getDoubleList();
	}
	public static boolean[] getBooleanList(String category, String key, boolean[] defaultValues, String comment){
		return Config.config.get(category, key, defaultValues, comment).getBooleanList();
	}
	public static String[] getStringList(String category, String key, String[] defaultValues, String comment){
		#ifdef MC147
		return Config.config.get(category, key, defaultValues, comment).valueList;
		#else
		return Config.config.get(category, key, defaultValues, comment).getStringList();
		#endif
	}

	///////////
	// WRITE //
	///////////

	// set value
	public static void setInt(String category, String key, int value){
		#ifdef MC147
		Config.config.get(category, key, value, null).value = Integer.toString(value);
		#else
		Config.config.get(category, key, value, null).set(Integer.toString(value));
		#endif
	}
	public static void setDouble(String category, String key, double value){
		#ifdef MC147
		Config.config.get(category, key, value, null).value = Double.toString(value);
		#else
		Config.config.get(category, key, value, null).set(Double.toString(value));
		#endif
	}
	public static void setBoolean(String category, String key, boolean value){
		#ifdef MC147
		Config.config.get(category, key, value, null).value = Boolean.toString(value);
		#else
		Config.config.get(category, key, value, null).set(Boolean.toString(value));
		#endif
	}
	public static void setString(String category, String key, String value){
		#ifdef MC147
		Config.config.get(category, key, value, null).value = value;
		#else
		Config.config.get(category, key, value, null).set(value);
		#endif
	}

	// set list of values
	public static void setIntList(String category, String key, int[] values){
		Property p = Config.config.get(category, key, values, null);
		String[] valueList = null;
		for(int i = 0; i < values.length; i++)
			valueList[i] = Integer.toString(values[i]);
		#ifdef MC147
		p.valueList = valueList;
		#else
		p.set(valueList);
		#endif
	}
	public static void setDoubleList(String category, String key, double[] values){
		Property p = Config.config.get(category, key, values, null);
		String[] valueList = null;
		for(int i = 0; i < values.length; i++)
			valueList[i] = Double.toString(values[i]);
		#ifdef MC147
		p.valueList = valueList;
		#else
		p.set(valueList);
		#endif
	}
	public static void setBooleanList(String category, String key, boolean[] values){
		Property p = Config.config.get(category, key, values, null);
		String[] valueList = null;
		for(int i = 0; i < values.length; i++)
			valueList[i] = Boolean.toString(values[i]);
		#ifdef MC147
		p.valueList = valueList;
		#else
		p.set(valueList);
		#endif
	}
	public static void setStringList(String category, String key, String[] values){
		Property p = Config.config.get(category, key, values, null);
		String[] valueList = null;
		for(int i = 0; i < values.length; i++)
			valueList[i] = values[i];
		#ifdef MC147
		p.valueList = valueList;
		#else
		p.set(valueList);
		#endif
	}
}
