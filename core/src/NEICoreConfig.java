package com.qzx.au.core;

#ifdef WITH_API_NEI

import codechicken.nei.api.IConfigureNEI;

public class NEICoreConfig implements IConfigureNEI {
	@Override
	public void loadConfig(){
		// disable NEI keys in text fields
		TextField.with_nei = true;
	}

	@Override
	public String getName(){
		return "AUCore";
	}

	@Override
	public String getVersion(){
		return THIS_MOD.modVersion;
	}
}

#endif
