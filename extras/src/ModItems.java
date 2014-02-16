package com.qzx.au.extras;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;

import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.oredict.OreDictionary;

public class ModItems {
	public static void init(){
		if(Cfg.enableFlowers){
			THIS_MOD.itemFlowerDye = new ItemFlowerDye(Cfg.itemFlowerDye, "au.colorFlowerDye")
				.setCreativeTab(THIS_MOD.tabAU);

			// ore dictionary
			OreDictionary.registerOre("dyeBlack", new ItemStack(THIS_MOD.itemFlowerDye, 1, 0));
			OreDictionary.registerOre("dyeGreen", new ItemStack(THIS_MOD.itemFlowerDye, 1, 2));
			OreDictionary.registerOre("dyeBrown", new ItemStack(THIS_MOD.itemFlowerDye, 1, 3));
			OreDictionary.registerOre("dyeBlue",  new ItemStack(THIS_MOD.itemFlowerDye, 1, 4));
			OreDictionary.registerOre("dyeWhite", new ItemStack(THIS_MOD.itemFlowerDye, 1, 15));
		}

		//////////

		if(Cfg.enableEnderCube){
			if(Cfg.enableEnderStar){
				THIS_MOD.itemEnderStar = new ItemEnderStar(Cfg.itemEnderStar, "au.enderStar")
					.setCreativeTab(THIS_MOD.tabAU);

				if(Cfg.enableEnderStarInDungeonChests){
					#define ADD_CHEST_LOOT(category, weight)\
						ChestGenHooks.getInfo(category).addItem(new WeightedRandomChestContent(new ItemStack(THIS_MOD.itemEnderStar, 1, ItemEnderStar.MAX_DAMAGE), 1, 1, weight));
					// add burned out stars to stronghold chests (1/12 chance)
					ADD_CHEST_LOOT(ChestGenHooks.STRONGHOLD_CORRIDOR, 10)
					ADD_CHEST_LOOT(ChestGenHooks.STRONGHOLD_LIBRARY, 10)
					ADD_CHEST_LOOT(ChestGenHooks.STRONGHOLD_CROSSING, 10)
					// add burned out stars to dungeon chests (1/60 chance)
					ADD_CHEST_LOOT(ChestGenHooks.DUNGEON_CHEST, 2)
					// add burned out stars to mineshaft chests (1/120 chance)
					ADD_CHEST_LOOT(ChestGenHooks.MINESHAFT_CORRIDOR, 1)
				}

				//////////

				if(Cfg.enableEnderWand){
					THIS_MOD.itemEnderWand = new ItemEnderWand(Cfg.itemEnderWand, "au.enderWand")
						.setCreativeTab(THIS_MOD.tabAU);
				}

				//////////

				if(Cfg.enableEnderMagnet){
					THIS_MOD.itemEnderMagnet = new ItemEnderMagnet(Cfg.itemEnderMagnet, "au.enderMagnet")
						.setCreativeTab(THIS_MOD.tabAU);

					//////////

					if(Cfg.enableEnderXT){
						THIS_MOD.itemEnderXT = new ItemEnderXT(Cfg.itemEnderXT, "au.enderXT")
							.setCreativeTab(THIS_MOD.tabAU);
					}
				}
			}
		}

		//////////

		// SMELT egg -> fried egg
		if(Cfg.enableFriedEgg){
			THIS_MOD.itemFriedEgg = new ItemFoodGeneric(Cfg.itemFriedEgg, 64, "au.friedEgg", 2, 0.4F, false)
				.setCreativeTab(THIS_MOD.tabAU);
		}

		//////////

		// SMELT rotten flesh -> cooked flesh
		if(Cfg.enableCookedFlesh){
			THIS_MOD.itemCookedFlesh = new ItemFoodGeneric(Cfg.itemCookedFlesh, 64, "au.cookedFlesh", 2, 0.2F, false)
				.setCreativeTab(THIS_MOD.tabAU);
		}
	}
}
