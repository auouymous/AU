package com.qzx.au.extras;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import net.minecraftforge.common.MinecraftForge;

import com.qzx.au.util.BlockColored;

@Mod(modid="AUExtras", name="Altered Unification EXTRAS", version="20130817-r1")
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class AUExtras {
	@Instance("AUExtras")
	public static AUExtras instance;

	@SidedProxy(clientSide="com.qzx.au.extras.ClientProxy", serverSide="com.qzx.au.extras.CommonProxy")
	public static CommonProxy proxy;

	public static Block blockCobble;
	public static Block blockStone;
	public static Block blockStoneBrick;
	public static Block blockChiseledBrick;
	public static Block blockSmoothBrick;
	public static Block blockGlass;
	public static Item itemFriedEgg;
	public static Item itemCookedFlesh;

	@PreInit
	public void preInit(FMLPreInitializationEvent event){
		Cfg.init(event);
	}

	@Init
	public void load(FMLInitializationEvent event){
		proxy.registerRenderers();
		proxy.registerHandlers();

		ItemStack cobblestone = new ItemStack(Block.cobblestone);
		ItemStack stone = new ItemStack(Block.stone);
		ItemStack stoneBrick = new ItemStack(Block.stoneBrick);
		ItemStack chiseledBrick = new ItemStack(Block.stoneBrick, 1, 3);
		ItemStack glass = new ItemStack(Block.glass);
//		ItemStack redstoneDust = new ItemStack(Item.redstone);
//		ItemStack glowstoneDust = new ItemStack(Item.lightStoneDust);
		ItemStack[] dyes = new ItemStack[16];
		for(int c = 0; c < 16; c++)
			dyes[c] = new ItemStack(Item.dyePowder, 1, c);

		//////////

		if(Cfg.enableCobble){
			this.blockCobble = new BlockColored("au_extras", Cfg.blockCobble, "au.blockCobble", " Cobblestone", ItemBlockCobble.class, Material.rock)
				.setHardness(2.0F)
				.setResistance(10.0F)
				.setStepSound(Block.soundStoneFootstep)
				.setCreativeTab(CreativeTabs.tabBlock);
			MinecraftForge.setBlockHarvestLevel(this.blockCobble, "pickaxe", 0); // wooden pickaxe
			ItemStack coloredCobble = new ItemStack(this.blockCobble);

			// CRAFT 8 cobblestone + dye -> 8 <colored> cobble
			for(int c = 0; c < 16; c++)
				GameRegistry.addRecipe(new ItemStack(this.blockCobble, 8, c), "sss", "s-s", "sss", 's', cobblestone, '-', dyes[c]);
			// CRAFT 8 <colored> cobble + dye -> 8 <colored> cobble
			for(int g = 0; g < 16; g++){
				ItemStack anyCobble = new ItemStack(this.blockCobble, 1, g);
				for(int c = 0; c < 16; c++)
					if(g != c)
						GameRegistry.addRecipe(new ItemStack(this.blockCobble, 8, c), "sss", "s-s", "sss", 's', anyCobble, '-', dyes[c]);
			}
			// SMELT <colored> cobble -> cobblestone
			GameRegistry.addSmelting(coloredCobble.itemID, cobblestone, 1.0f);
		}

		//////////

		if(Cfg.enableStone){
			this.blockStone = new BlockColored("au_extras", Cfg.blockStone, "au.blockStone", " Stone", ItemBlockStone.class, Material.rock)
				.setHardness(1.5F)
				.setResistance(10.0F)
				.setStepSound(Block.soundStoneFootstep)
				.setCreativeTab(CreativeTabs.tabBlock);
			MinecraftForge.setBlockHarvestLevel(this.blockStone, "pickaxe", 0); // wooden pickaxe
			ItemStack coloredStone = new ItemStack(this.blockStone);

			// CRAFT 8 stone + dye -> 8 <colored> stone
			for(int c = 0; c < 16; c++)
				GameRegistry.addRecipe(new ItemStack(this.blockStone, 8, c), "sss", "s-s", "sss", 's', stone, '-', dyes[c]);
			// CRAFT 8 <colored> stone + dye -> 8 <colored> stone
			for(int g = 0; g < 16; g++){
				ItemStack anyStone = new ItemStack(this.blockStone, 1, g);
				for(int c = 0; c < 16; c++)
					if(g != c)
						GameRegistry.addRecipe(new ItemStack(this.blockStone, 8, c), "sss", "s-s", "sss", 's', anyStone, '-', dyes[c]);
			}
			// SMELT <colored> stone -> stone
			GameRegistry.addSmelting(coloredStone.itemID, stone, 1.0f);
		}

		//////////

		if(Cfg.enableStoneBrick){
			this.blockStoneBrick = new BlockColored("au_extras", Cfg.blockStoneBrick, "au.blockStoneBrick", " Stone Brick", ItemBlockStoneBrick.class, Material.rock)
				.setHardness(2.0F)
				.setResistance(10.0F)
				.setStepSound(Block.soundStoneFootstep)
				.setCreativeTab(CreativeTabs.tabBlock);
			MinecraftForge.setBlockHarvestLevel(this.blockStoneBrick, "pickaxe", 0); // wooden pickaxe
			ItemStack coloredStoneBrick = new ItemStack(this.blockStoneBrick);

			// CRAFT 8 stoneBrick + dye -> 8 <colored> stoneBrick
			for(int c = 0; c < 16; c++)
				GameRegistry.addRecipe(new ItemStack(this.blockStoneBrick, 8, c), "bbb", "b-b", "bbb", 'b', stoneBrick, '-', dyes[c]);
			// CRAFT 8 <colored> stoneBrick + dye -> 8 <colored> stoneBrick
			for(int g = 0; g < 16; g++){
				ItemStack anyStoneBrick = new ItemStack(this.blockStoneBrick, 1, g);
				for(int c = 0; c < 16; c++)
					if(g != c)
						GameRegistry.addRecipe(new ItemStack(this.blockStoneBrick, 8, c), "bbb", "b-b", "bbb", 'b', anyStoneBrick, '-', dyes[c]);
			}
			// SMELT <colored> stoneBrick -> stoneBrick
			GameRegistry.addSmelting(coloredStoneBrick.itemID, stoneBrick, 1.0f);
		}

		//////////

		if(Cfg.enableChiseledBrick){
			this.blockChiseledBrick = new BlockColored("au_extras", Cfg.blockChiseledBrick, "au.blockChiseledBrick", " Chiseled Brick", ItemBlockChiseledBrick.class, Material.rock)
				.setHardness(2.0F)
				.setResistance(10.0F)
				.setStepSound(Block.soundStoneFootstep)
				.setCreativeTab(CreativeTabs.tabBlock);
			MinecraftForge.setBlockHarvestLevel(this.blockChiseledBrick, "pickaxe", 0); // wooden pickaxe
			ItemStack coloredChiseledBrick = new ItemStack(this.blockChiseledBrick);

			// CRAFT 4 stoneBrick -> 4 chiseledBrick
			GameRegistry.addRecipe(new ItemStack(Block.stoneBrick, 4, 3), "bb ", "bb ", "   ", 'b', stoneBrick);

			// CRAFT 8 chiseledBrick + dye -> 8 <colored> chiseledBrick
			for(int c = 0; c < 16; c++)
				GameRegistry.addRecipe(new ItemStack(this.blockChiseledBrick, 8, c), "bbb", "b-b", "bbb", 'b', chiseledBrick, '-', dyes[c]);
			// CRAFT 8 <colored> chiseledBrick + dye -> 8 <colored> chiseledBrick
			for(int g = 0; g < 16; g++){
				ItemStack anyChiseledBrick = new ItemStack(this.blockChiseledBrick, 1, g);
				for(int c = 0; c < 16; c++)
					if(g != c)
						GameRegistry.addRecipe(new ItemStack(this.blockChiseledBrick, 8, c), "bbb", "b-b", "bbb", 'b', anyChiseledBrick, '-', dyes[c]);
			}
			// SMELT <colored> chiseledBrick -> chiseledBrick
			GameRegistry.addSmelting(coloredChiseledBrick.itemID, chiseledBrick, 1.0f);
		}

		//////////

		if(Cfg.enableSmoothBrick){
			this.blockSmoothBrick = new BlockColored("au_extras", Cfg.blockSmoothBrick, "au.blockSmoothBrick", " Smooth Brick", ItemBlockSmoothBrick.class, Material.rock)
				.setHardness(2.0F)
				.setResistance(10.0F)
				.setStepSound(Block.soundStoneFootstep)
				.setCreativeTab(CreativeTabs.tabBlock);
			MinecraftForge.setBlockHarvestLevel(this.blockSmoothBrick, "pickaxe", 0); // wooden pickaxe
			ItemStack coloredSmoothBrick = new ItemStack(this.blockSmoothBrick);

			// CRAFT 4 stoneBrick + 4 stone + dye -> 8 <colored> smoothBrick
			for(int c = 0; c < 16; c++)
				GameRegistry.addRecipe(new ItemStack(this.blockSmoothBrick, 8, c), "bsb", "s-s", "bsb", 'b', stoneBrick, 's', stone, '-', dyes[c]);
			// CRAFT 8 <colored> smoothBrick + dye -> 8 <colored> smoothBrick
			for(int g = 0; g < 16; g++){
				ItemStack anySmoothBrick = new ItemStack(this.blockSmoothBrick, 1, g);
				for(int c = 0; c < 16; c++)
					if(g != c)
						GameRegistry.addRecipe(new ItemStack(this.blockSmoothBrick, 8, c), "bbb", "b-b", "bbb", 'b', anySmoothBrick, '-', dyes[c]);
			}
			// SMELT <colored> smoothBrick -> stone
			GameRegistry.addSmelting(coloredSmoothBrick.itemID, stone, 1.0f);
		}

		//////////

		if(Cfg.enableGlass){
			this.blockGlass = new BlockGlass(Cfg.blockGlass, "au.blockGlass", " Glass")
				.setHardness(0.3F)
				.setResistance(1.5F)
				.setStepSound(Block.soundGlassFootstep)
				.setCreativeTab(CreativeTabs.tabBlock);
			ItemStack coloredGlass = new ItemStack(this.blockGlass);

			// CRAFT 8 glass + dye -> 8 <colored> glass
			for(int c = 0; c < 16; c++)
				GameRegistry.addRecipe(new ItemStack(this.blockGlass, 8, c), "ggg", "g-g", "ggg", 'g', glass, '-', dyes[c]);
			// CRAFT 8 <colored> glass + dye -> 8 <colored> glass
			for(int g = 0; g < 16; g++){
				ItemStack anyGlass = new ItemStack(this.blockGlass, 1, g);
				for(int c = 0; c < 16; c++)
					if(g != c)
						GameRegistry.addRecipe(new ItemStack(this.blockGlass, 8, c), "ggg", "g-g", "ggg", 'g', anyGlass, '-', dyes[c]);
			}
		}

		//////////

		// SMELT egg -> fried egg
		if(Cfg.enableFriedEgg){
			this.itemFriedEgg = (new ItemFoodGeneric(Cfg.itemFriedEgg, 64, "au.friedEgg", "Fried Egg", 2, 0.4F, false));
			GameRegistry.addSmelting(Item.egg.itemID, new ItemStack(this.itemFriedEgg), 1.0f);
		}

		// SMELT rotten flesh -> cooked flesh
		ItemStack cookedFlesh = null;
		if(Cfg.enableCookedFlesh){
			this.itemCookedFlesh = (new ItemFoodGeneric(Cfg.itemCookedFlesh, 64, "au.cookedFlesh", "Cooked Flesh", 2, 0.2F, false));
			cookedFlesh = new ItemStack(this.itemCookedFlesh, 1);
			GameRegistry.addSmelting(Item.rottenFlesh.itemID, cookedFlesh, 1.0f);
		}

		// CRAFT cooked flesh -> leather
		if(Cfg.enableCookedFleshToLeather && Cfg.enableCookedFlesh){
			ItemStack leather = new ItemStack(Item.leather, 1);
			switch(Cfg.nrCookedFleshToLeather){
			case 1: GameRegistry.addShapelessRecipe(leather, cookedFlesh); break;
			case 2: GameRegistry.addShapelessRecipe(leather, cookedFlesh, cookedFlesh); break;
			case 3: GameRegistry.addShapelessRecipe(leather, cookedFlesh, cookedFlesh, cookedFlesh); break;
			case 4: GameRegistry.addShapelessRecipe(leather, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh); break;
			case 5: GameRegistry.addShapelessRecipe(leather, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh); break;
			case 6: GameRegistry.addShapelessRecipe(leather, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh); break;
			case 7: GameRegistry.addShapelessRecipe(leather, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh); break;
			case 8: GameRegistry.addShapelessRecipe(leather, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh); break;
			default: GameRegistry.addShapelessRecipe(leather, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh, cookedFlesh); break;
			}
		}
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent event){}
}
