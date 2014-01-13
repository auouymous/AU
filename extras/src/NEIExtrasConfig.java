package com.qzx.au.extras.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

import com.qzx.au.extras.Cfg;

public class NEIExtrasConfig implements IConfigureNEI {
	@Override
	public void loadConfig(){
		try {
			if(Cfg.enableLamps){
				// hide powered lamps
				API.hideItem(Cfg.blockLampPowered);
				API.hideItem(Cfg.blockInvertedLampPowered);
			}

			// chroma recipes
//			API.registerRecipeHandler(new ChromaRecipesNEI());
//			API.registerUsageHandler(new ChromaRecipesNEI());
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public String getName(){
		return "AUExtras";
	}

	@Override
	public String getVersion(){
		return "0.0.0";
	}
}
