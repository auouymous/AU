package com.qzx.au.core;

#if defined MC147 || defined MC152 || defined MC164
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
#else
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
#endif
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class Config {
	private Configuration config = null;

	public Config(){}

	public static final String CATEGORY_GENERAL = "general";
	public static final String CATEGORY_BLOCKS = "blocks";
	public static final String CATEGORY_ITEMS = "items";
	public static final String CATEGORY_IDMAP = "idmap";

	// called from MOD preInit()
	public void loadConfig(FMLPreInitializationEvent event){
		this.config = new Configuration(event.getSuggestedConfigurationFile());
		this.config.load();
	}

	// save properties to config
	public void saveConfig(){
		this.config.save();
	}

	///////////////
	// READ-INIT //
	///////////////

	// get block or item ID
	public int getBlock(String key, int defaultValue, String comment){
		return this.config.get(Config.CATEGORY_BLOCKS, key, defaultValue, comment).getInt(defaultValue);
	}
	public int getItem(String key, int defaultValue, String comment){
		return this.config.get(Config.CATEGORY_ITEMS, key, defaultValue, comment).getInt(defaultValue);
	}

	// get value
	public int getInt(String category, String key, int defaultValue, String comment){
		return this.config.get(category, key, defaultValue, comment).getInt(defaultValue);
	}
	public double getDouble(String category, String key, double defaultValue, String comment){
		return this.config.get(category, key, defaultValue, comment).getDouble(defaultValue);
	}
	public boolean getBoolean(String category, String key, boolean defaultValue, String comment){
		return this.config.get(category, key, defaultValue, comment).getBoolean(defaultValue);
	}
	public String getString(String category, String key, String defaultValue, String comment){
		#ifdef MC147
		return this.config.get(category, key, defaultValue, comment).value;
		#else
		return this.config.get(category, key, defaultValue, comment).getString();
		#endif
	}

	// get list of values
	public int[] getIntList(String category, String key, int[] defaultValues, String comment){
		return this.config.get(category, key, (defaultValues == null ? new int[0] : defaultValues), comment).getIntList();
	}
	public double[] getDoubleList(String category, String key, double[] defaultValues, String comment){
		return this.config.get(category, key, (defaultValues == null ? new double[0] : defaultValues), comment).getDoubleList();
	}
	public boolean[] getBooleanList(String category, String key, boolean[] defaultValues, String comment){
		return this.config.get(category, key, (defaultValues == null ? new boolean[0] : defaultValues), comment).getBooleanList();
	}
	public String[] getStringList(String category, String key, String[] defaultValues, String comment){
		#ifdef MC147
		return this.config.get(category, key, (defaultValues == null ? new String[0] : defaultValues), comment).valueList;
		#else
		return this.config.get(category, key, (defaultValues == null ? new String[0] : defaultValues), comment).getStringList();
		#endif
	}

	///////////
	// WRITE //
	///////////

	// set value
	public void setInt(String category, String key, int value, String comment){
		#ifdef MC147
		this.config.get(category, key, value, comment).value = Integer.toString(value);
		#else
		this.config.get(category, key, value, comment).set(Integer.toString(value));
		#endif
	}
	public void setDouble(String category, String key, double value, String comment){
		#ifdef MC147
		this.config.get(category, key, value, comment).value = Double.toString(value);
		#else
		this.config.get(category, key, value, comment).set(Double.toString(value));
		#endif
	}
	public void setBoolean(String category, String key, boolean value, String comment){
		#ifdef MC147
		this.config.get(category, key, value, comment).value = Boolean.toString(value);
		#else
		this.config.get(category, key, value, comment).set(Boolean.toString(value));
		#endif
	}
	public void setString(String category, String key, String value, String comment){
		#ifdef MC147
		this.config.get(category, key, value, comment).value = value;
		#else
		this.config.get(category, key, value, comment).set(value);
		#endif
	}

	// set list of values
	public void setIntList(String category, String key, int[] values, String comment){
		Property p = this.config.get(category, key, (values == null ? new int[0] : values), comment);
		String[] valueList = new String[values.length];
		for(int i = 0; i < values.length; i++)
			valueList[i] = Integer.toString(values[i]);
		#ifdef MC147
		p.valueList = valueList;
		#else
		p.set(valueList);
		#endif
	}
	public void setDoubleList(String category, String key, double[] values, String comment){
		Property p = this.config.get(category, key, (values == null ? new double[0] : values), comment);
		String[] valueList = new String[values.length];
		for(int i = 0; i < values.length; i++)
			valueList[i] = Double.toString(values[i]);
		#ifdef MC147
		p.valueList = valueList;
		#else
		p.set(valueList);
		#endif
	}
	public void setBooleanList(String category, String key, boolean[] values, String comment){
		Property p = this.config.get(category, key, (values == null ? new boolean[0] : values), comment);
		String[] valueList = new String[values.length];
		for(int i = 0; i < values.length; i++)
			valueList[i] = Boolean.toString(values[i]);
		#ifdef MC147
		p.valueList = valueList;
		#else
		p.set(valueList);
		#endif
	}
	public void setStringList(String category, String key, String[] values, String comment){
		Property p = this.config.get(category, key, (values == null ? new String[0] : values), comment);
		String[] valueList = new String[values.length];
		for(int i = 0; i < values.length; i++)
			valueList[i] = values[i];
		#ifdef MC147
		p.valueList = valueList;
		#else
		p.set(valueList);
		#endif
	}
}
