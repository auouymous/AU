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

	public static void addRecipe(ChromaButton button, ItemStack input, ItemStack output, boolean reverse_colors){
		// ignore duplicate recipes (same button and input)
		if(ChromaRegistry.hasRecipe(button, input)){
			Debug.error("ignoring duplicate Chroma Infuser recipe (button: "+button.ordinal()
																+", input: "+ItemUtils.getDisplayName(input)
																+", output: "+ItemUtils.getDisplayName(output)+")");
			return;
		}
		ChromaRegistry.registry.recipes.add(new ChromaRecipe(button, input, output, reverse_colors));
		ChromaRegistry.registry.nr_recipes++;
	}
	public static void addRecipe(ChromaButton button, ItemStack input, ItemStack output){
		ChromaRegistry.addRecipe(button, input, output, false);
	}

	public static boolean hasRecipe(ChromaButton findButton, ItemStack findInput){
		if(findInput == null) return false;

		List<ChromaRecipe> recipes = ChromaRegistry.registry.recipes;
		int findItemID = findInput.getItem().itemID;
		int findDamage = findInput.getItemDamage();
		for(int i = 0; i < ChromaRegistry.registry.nr_recipes; i++){
			ChromaRecipe recipe = recipes.get(i);
			ItemStack recipeInput = recipe.input;
			if(findItemID == recipeInput.getItem().itemID && findDamage == recipeInput.getItemDamage()){
				if(findButton == null || findButton == recipe.button)
					return true;
			}
		}
		return false;
	}
	public static boolean hasRecipe(ItemStack findInput){
		return ChromaRegistry.hasRecipe(null, findInput);
	}

	public static ChromaRecipe getRecipe(ChromaButton findButton, ItemStack findInput){
		if(findButton == null || findInput == null) return null;

		List<ChromaRecipe> recipes = ChromaRegistry.registry.recipes;
		int findItemID = findInput.getItem().itemID;
		int findDamage = findInput.getItemDamage();
		for(int i = 0; i < ChromaRegistry.registry.nr_recipes; i++){
			ChromaRecipe recipe = recipes.get(i);
			ItemStack recipeInput = recipe.input;
			if(findItemID == recipeInput.getItem().itemID && findDamage == recipeInput.getItemDamage())
				if(findButton == recipe.button)
					return recipes.get(i);
		}
		return null;
	}
}
