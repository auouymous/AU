package com.qzx.au.extras;

import net.minecraft.block.Block;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIExtrasConfig implements IConfigureNEI {
	@Override
	public void loadConfig(){
		try {
			if(Cfg.enableLamps){
				// hide powered lamps
				API.hideItem(Cfg.blockLampPowered);
				API.hideItem(Cfg.blockInvertedLampPowered);
			}

			// hide vanilla comparators
			API.hideItem(Block.redstoneComparatorIdle.blockID);
			API.hideItem(Block.redstoneComparatorActive.blockID);

			// chroma recipes
			API.registerRecipeHandler(new NEIChromaRecipeHandler());
			API.registerUsageHandler(new NEIChromaRecipeHandler());
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public String getName(){
		return "AUExtras";
	}

	@Override
	public String getVersion(){
		return AUExtras.modVersion;
	}
}
