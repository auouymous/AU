package com.qzx.au.extras;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import com.qzx.au.core.ItemUtils;

public class ChromaRegistry {
	public static ChromaRegistry registry = new ChromaRegistry();

	public List<ChromaRecipe> recipes = new ArrayList<ChromaRecipe>();
	public int nr_recipes = 0;

	private ChromaRegistry(){}

	#define RECIPE_ERROR(msg) Debug.error(msg+" (button: "+button.ordinal()\
											+", colored_input: "+colored_input\
											+", input: "+ItemUtils.getDisplayName(input)\
											+", color: "+color\
											+", colored_output: "+colored_output\
											+", output: "+ItemUtils.getDisplayName(output)+")");

	public static void addRecipe(ChromaButton button, boolean colored_input, ItemStack input, int color, boolean colored_output, ItemStack output, boolean reverse_colors){
		// ignore duplicate recipes (same button and input/color)
		if(ChromaRegistry.hasRecipe(button, input, color, true, colored_input)){
			RECIPE_ERROR(">>> ignoring duplicate Chroma Infuser recipe")
			return;
		}
		ChromaRecipe recipe = new ChromaRecipe(button, colored_input, input, color, colored_output, output, reverse_colors);
		if(recipe.error != null){
			Debug.error(recipe.error);
			RECIPE_ERROR(">>> ignoring invalid Chroma Infuser recipe")
			return;
		}
		ChromaRegistry.registry.recipes.add(recipe);
		ChromaRegistry.registry.nr_recipes++;
	}

	public static void addRecipeColored(ChromaButton button, ItemStack input, ItemStack output){
		ChromaRegistry.addRecipe(button, true, input, -1, true, output, false); // colored input -> colored output
	}
	public static void addRecipe(ChromaButton button, ItemStack input, ItemStack output){
		ChromaRegistry.addRecipe(button, false, input, -1, true, output, false); //input -> colored output
	}

	public static void addRecipeColoredReversed(ChromaButton button, ItemStack input, ItemStack output){
		ChromaRegistry.addRecipe(button, true, input, -1, true, output, true); // colored input -> colored output (reverse output colors)
	}
	public static void addRecipeReversed(ChromaButton button, ItemStack input, ItemStack output){
		ChromaRegistry.addRecipe(button, false, input, -1, true, output, true); // input -> colored output (reverse output colors)
	}

	//////////

	public static boolean hasRecipe(ChromaButton findButton, ItemStack findInput, int findColor, boolean register, boolean colored_input){
		if(findInput == null) return false;

		List<ChromaRecipe> recipes = ChromaRegistry.registry.recipes;
		int findItemID = findInput.itemID;
		int findDamage = findInput.getItemDamage();
		for(int i = 0; i < ChromaRegistry.registry.nr_recipes; i++){
			ChromaRecipe recipe = recipes.get(i);
			ItemStack recipeInput = recipe.input;
			boolean ignore_meta = (register ? colored_input : false); // colored_input recipes will match against all uncolored input recipes to prevent registering both
			if(findItemID == recipeInput.itemID && (ignore_meta || recipe.colored_input || findDamage == recipeInput.getItemDamage())) // match input ID, match META unless colored_input
				if(findButton == null || findButton == recipe.button) // match button, unless no button requested
					if(recipe.color == -1 || findColor == recipe.color) // match color unless recipe doesn't specify a color
						return true;
		}
		return false;
	}
	public static boolean hasRecipe(ChromaButton findButton, ItemStack findInput, int findColor){
		return ChromaRegistry.hasRecipe(findButton, findInput, findColor, false, false);
	}
	public static boolean hasRecipe(ItemStack findInput, int findColor){
		return ChromaRegistry.hasRecipe(null, findInput, findColor, false, false);
	}

	//////////

	public static ChromaRecipe getRecipe(ChromaButton findButton, ItemStack findInput, int findColor){
		if(findButton == null || findInput == null) return null;

		List<ChromaRecipe> recipes = ChromaRegistry.registry.recipes;
		int findItemID = findInput.itemID;
		int findDamage = findInput.getItemDamage();
		for(int i = 0; i < ChromaRegistry.registry.nr_recipes; i++){
			ChromaRecipe recipe = recipes.get(i);
			ItemStack recipeInput = recipe.input;
			if(findItemID == recipeInput.itemID && (recipe.colored_input || findDamage == recipeInput.getItemDamage())) // match input ID, match META unless colored_input
				if(findButton == recipe.button) // match button
					if(recipe.color == -1 || findColor == recipe.color) // match color unless recipe doesn't specify a color
						return recipes.get(i);
		}
		return null;
	}
}
