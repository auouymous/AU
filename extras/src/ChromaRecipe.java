package com.qzx.au.extras;

import net.minecraft.item.ItemStack;

public class ChromaRecipe {
	public ChromaButton button;
	public int dyeConsumption;
	public ItemStack input;
	public ItemStack output;

	public ChromaRecipe(ChromaButton button, int dyeConsumption, ItemStack input, ItemStack output){
		this.button = button;
		this.dyeConsumption = dyeConsumption;
		this.input = input;
		this.output = output;
	}
}

