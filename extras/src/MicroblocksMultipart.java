package com.qzx.au.extras;

#ifdef WITH_API_FMP

import codechicken.microblock.BlockMicroMaterial;

public class MicroblocksMultipart {
	public static void init(){
		for(int c = 0; c < 16; c++){
			if(Cfg.enableCobble) BlockMicroMaterial.createAndRegister(THIS_MOD.blockCobble, c);
			if(Cfg.enableStone) BlockMicroMaterial.createAndRegister(THIS_MOD.blockStone, c);
			if(Cfg.enableStoneBrick) BlockMicroMaterial.createAndRegister(THIS_MOD.blockStoneBrick, c);
			if(Cfg.enableChiseledBrick) BlockMicroMaterial.createAndRegister(THIS_MOD.blockChiseledBrick, c);
			if(Cfg.enableSmoothBrick) BlockMicroMaterial.createAndRegister(THIS_MOD.blockSmoothBrick, c);
			if(Cfg.enableGravel) BlockMicroMaterial.createAndRegister(THIS_MOD.blockGravel, c);
			if(Cfg.enableSand) BlockMicroMaterial.createAndRegister(THIS_MOD.blockSand, c);
//			if(Cfg.enableArtificialGrass) BlockMicroMaterial.createAndRegister(THIS_MOD.blockArtificialGrass, c); // they all have same shade
//			if(Cfg.enableGlass) BlockMicroMaterial.createAndRegister(THIS_MOD.blockGlass, c); // doesn't look good
//			if(Cfg.enableGlassTinted) BlockMicroMaterial.createAndRegister(THIS_MOD.blockGlassTinted, c); // doesn't look good
			if(Cfg.enableGlassTintedNoFrame) BlockMicroMaterial.createAndRegister(THIS_MOD.blockGlassTintedNoFrame, c);
		}

		if(Cfg.enableEnderCube) BlockMicroMaterial.createAndRegister(THIS_MOD.blockEnderCube);
	}
}
#endif
