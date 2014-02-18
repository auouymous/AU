package com.qzx.au.tts;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

public class ModBlocks {
	public static void init(){
		THIS_MOD.blockTTS = new BlockTTS(Cfg.blockTTS, "au.ttsCube")
			.setHardness(0.8F)
			.setResistance(4.0F)
			.setStepSound(Block.soundMetalFootstep)
			.setCreativeTab(CreativeTabs.tabRedstone);
	}
}
