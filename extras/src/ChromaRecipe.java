package com.qzx.au.extras;

import net.minecraft.item.ItemStack;

public class ChromaRecipe {
	public ChromaButton button;
	public ItemStack input;
	public ItemStack output;
	private boolean reverse_colors;

	public ChromaRecipe(ChromaButton button, ItemStack input, ItemStack output, boolean reverse_colors){
		if(input.stackSize > 1){
			input.stackSize = 1;
			Debug.error("clamping chroma infuser recipe input quantity to 1");
		}
		if(output.stackSize > 1){
			output.stackSize = 1;
			Debug.error("clamping chroma infuser recipe output quantity to 1");
		}

		this.button = button;
		this.input = input;
		this.output = output;
		this.reverse_colors = reverse_colors;
	}

	public int getOutputColor(int dyeColor){
		if(this.reverse_colors) return ~dyeColor & 15;
		return dyeColor;
	}
}

