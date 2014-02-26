package com.qzx.au.extras;

import net.minecraft.item.ItemStack;

public class ChromaRecipe {
	public final ChromaButton button;
	public final boolean colored_input;
	public final ItemStack input;
	public final int color;
	public final boolean colored_output;
	public final ItemStack output;
	private boolean reverse_colors;

	public String error = null;

	public ChromaRecipe(ChromaButton button, boolean colored_input, ItemStack input, int color, boolean colored_output, ItemStack output, boolean reverse_colors){
		if(input.stackSize > 1){
			input.stackSize = 1;
			Debug.error("clamping chroma infuser recipe input quantity to 1");
		}
		if(output.stackSize > 1){
			output.stackSize = 1;
			Debug.error("clamping chroma infuser recipe output quantity to 1");
		}

		if(color < -1 || color > 15)
			this.error = "ChromaRecipe: invalid dye color used in recipe";
		if(!colored_output && color == -1)
			this.error = "ChromaRecipe: can't use uncolored output without specifying dye color";
		if(colored_output && color != -1)
			this.error = "ChromaRecipe: can't use colored output when specifying dye color";

		this.button = button;
		this.colored_input = colored_input;
		this.input = input;
		this.color = color;
		this.colored_output = colored_output;
		this.output = output;
		this.reverse_colors = reverse_colors;
	}

	public int getOutputColor(int dyeColor){
		if(!this.colored_output) return this.output.getItemDamage();
		return (this.reverse_colors ? ~dyeColor & 15 : dyeColor);
	}
}

