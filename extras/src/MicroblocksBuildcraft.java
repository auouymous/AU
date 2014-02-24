package com.qzx.au.extras;

import cpw.mods.fml.common.event.FMLInterModComms;

public class MicroblocksBuildcraft {
	public static void init(){
		#define REGISTER_FACADE(id, c) FMLInterModComms.sendMessage("BuildCraft|Transport", "add-facade", String.format("%d@%d", id, c))

		for(int c = 0; c < 16; c++){
			if(Cfg.enableStoneHalfSlabs) REGISTER_FACADE(THIS_MOD.blockStoneHalfSlabsSmooth.blockID, c);
			if(Cfg.enableStoneHalfSlabs) REGISTER_FACADE(THIS_MOD.blockStoneHalfSlabs.blockID, c);
			if(Cfg.enableCobble) REGISTER_FACADE(THIS_MOD.blockCobble.blockID, c);
			if(Cfg.enableStone) REGISTER_FACADE(THIS_MOD.blockStone.blockID, c);
			if(Cfg.enableStoneBrick) REGISTER_FACADE(THIS_MOD.blockStoneBrick.blockID, c);
			if(Cfg.enableChiseledBrick) REGISTER_FACADE(THIS_MOD.blockChiseledBrick.blockID, c);
			if(Cfg.enableSmoothBrick) REGISTER_FACADE(THIS_MOD.blockSmoothBrick.blockID, c);
			if(Cfg.enableGravel) REGISTER_FACADE(THIS_MOD.blockGravel.blockID, c);
			if(Cfg.enableSand) REGISTER_FACADE(THIS_MOD.blockSand.blockID, c);
//			if(Cfg.enableArtificialGrass) REGISTER_FACADE(THIS_MOD.blockArtificialGrass.blockID, c); // no shading at all
//			if(Cfg.enableGlass) REGISTER_FACADE(THIS_MOD.blockGlass.blockID, c); // doesn't look good, no transparency
//			if(Cfg.enableGlassTinted) REGISTER_FACADE(THIS_MOD.blockGlassTinted.blockID, c); // doesn't look good, no transparency
//			if(Cfg.enableGlassTintedNoFrame) REGISTER_FACADE(THIS_MOD.blockGlassTintedNoFrame.blockID, c); // no transparency
		}

		if(Cfg.enableEnderCube) REGISTER_FACADE(THIS_MOD.blockEnderCube.blockID, 0);
	}
}
