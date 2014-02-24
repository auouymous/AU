package com.qzx.au.extras;

#ifdef WITH_API_NEI

IMPORT_BLOCKS

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

			// hide upper slabs
			if(Cfg.enableStoneHalfSlabs) API.hideItem(Cfg.blockStoneHalfSlab1);
			if(Cfg.enableCobble && Cfg.enableCobbleSlabs) API.hideItem(Cfg.blockCobbleSlab1);
			if(Cfg.enableStone && Cfg.enableStoneSlabs) API.hideItem(Cfg.blockStoneSlab1);
			if(Cfg.enableStoneBrick && Cfg.enableStoneBrickSlabs) API.hideItem(Cfg.blockStoneBrickSlab1);
			if(Cfg.enableChiseledBrick && Cfg.enableChiseledBrickSlabs) API.hideItem(Cfg.blockChiseledBrickSlab1);
			if(Cfg.enableSmoothBrick && Cfg.enableSmoothBrickSlabs) API.hideItem(Cfg.blockSmoothBrickSlab1);
			if(Cfg.enableGravel && Cfg.enableGravelSlabs) API.hideItem(Cfg.blockGravelSlab1);
			if(Cfg.enableSand && Cfg.enableSandSlabs) API.hideItem(Cfg.blockSandSlab1);
			if(Cfg.enableArtificialGrass && Cfg.enableArtificialGrassSlabs) API.hideItem(Cfg.blockArtificialGrassSlab1);

			// hide vanilla comparators
			API.hideItem(MC_BLOCK.redstoneComparatorIdle.blockID);
			API.hideItem(MC_BLOCK.redstoneComparatorActive.blockID);

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
		return THIS_MOD.modVersion;
	}
}

#endif
