package com.qzx.au.tts;

import cpw.mods.fml.common.registry.GameRegistry;

IMPORT_BLOCKS
IMPORT_ITEMS
import net.minecraft.item.ItemStack;

public class ModRecipes {
	public static void init(){
		// CRAFT noteblock + sign + 4 gold ingots + 3 redstone dust -> tts
		GameRegistry.addShapedRecipe(new ItemStack(THIS_MOD.blockTTS), "gng", "nsn", "gng",
										'n', new ItemStack(MC_BLOCK.music), 's', new ItemStack(MC_ITEM.sign),
										'g', new ItemStack(MC_ITEM.ingotGold), 'r', new ItemStack(MC_ITEM.redstone));
	}
}
