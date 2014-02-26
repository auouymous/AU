package com.qzx.au.extras;

#ifdef WITH_API_NEI

import net.minecraft.client.Minecraft;
IMPORT_ITEMS
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import codechicken.nei.PositionedStack;
import codechicken.nei.forge.GuiContainerManager;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.IUsageHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.qzx.au.core.Color;
import com.qzx.au.core.UI;

public class NEIChromaRecipeHandler extends TemplateRecipeHandler {
	public String getRecipeName(){
		return "Chroma Infuser";
	}

	@Override
	public int recipiesPerPage(){
		return 2;
	}

	public String getGuiTexture(){
		return null;
	}

	//////////

	private final int SLOT_Y = 15;
	private final int SLOT_X_DYE	= 27 + 1;
	private final int SLOT_X_INPUT	= 27 + 21;
	private final int SLOT_X_BUTTON	= 27 + 40;
	private final int SLOT_X_ARROW	= 27 + 59;
	private final int SLOT_X_OUTPUT	= 27 + 81;

	@Override
	#ifdef MC152
	public void drawBackground(GuiContainerManager gui, int recipe){
	#else
	public void drawBackground(int recipe){
	#endif
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		// dye
		UI.drawBorder(SLOT_X_DYE-1, SLOT_Y-1, 18, 18, 0xff373737, 0xffffffff, 0xff8b8b8b);
		UI.drawRect(SLOT_X_DYE, SLOT_Y, 16, 16, 0xff8b8b8b); // background

		// input
		UI.drawBorder(SLOT_X_INPUT-1, SLOT_Y-1, 18, 18, 0xff373737, 0xffffffff, 0xff8b8b8b);
		UI.drawRect(SLOT_X_INPUT, SLOT_Y, 16, 16, 0xff8b8b8b); // background

		// output
		UI.drawBorder(SLOT_X_OUTPUT-3, SLOT_Y-3, 22, 22, 0xff373737, 0xffffffff, 0xff8b8b8b);
		UI.drawRect(SLOT_X_OUTPUT-2, SLOT_Y-2, 20, 20, 0xff8b8b8b); // background
	}

	@Override
	#ifdef MC152
	public void drawForeground(GuiContainerManager gui, int recipe){
	#else
	public void drawForeground(int recipe){
	#endif
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);

		CachedChromaRecipe cachedRecipe = (CachedChromaRecipe)this.arecipes.get(recipe);
		UI.bindTexture(Minecraft.getMinecraft(), "au_extras", THIS_MOD.texturePath+"/gui/container.png");

		// recipe button
		switch(cachedRecipe.button.ordinal()){
		case 0: UI.drawTexturedRect(SLOT_X_BUTTON, SLOT_Y+2, 20+1*12,0, 12,12, 0.0F); break;
		case 1: UI.drawTexturedRect(SLOT_X_BUTTON, SLOT_Y+2, 20+3*12,0, 12,12, 0.0F); break;
		case 2: UI.drawTexturedRect(SLOT_X_BUTTON, SLOT_Y+2, 20+5*12,0, 12,12, 0.0F); break;
		case 3: UI.drawTexturedRect(SLOT_X_BUTTON, SLOT_Y+2, 20+7*12,0, 12,12, 0.0F); break;
		}

		// animated arrow
		int arrow_x_offset = 12*NEIChromaRecipeHandler.cycle_color/15;
		if(arrow_x_offset > 0)
			UI.drawTexturedRect(SLOT_X_ARROW, SLOT_Y+3, 20+11*12,0, arrow_x_offset,10, 0.0F); // white
		if(arrow_x_offset < 12)
			UI.drawTexturedRect(SLOT_X_ARROW+arrow_x_offset, SLOT_Y+3, 20+10*12+arrow_x_offset,0, 12-arrow_x_offset,10, 0.0F); // gray
	}

	//////////

	private static int cycle_color = 0;
	@Override
	public void onUpdate(){
		super.onUpdate();
		NEIChromaRecipeHandler.cycle_color = (this.cycleticks%128)>>3;
	}

	public class CachedChromaRecipe extends CachedRecipe {
		public String group;
		public int color;
		public ItemStack dye;
		public ArrayList<ItemStack> oreDyes = null;
		public ItemStack input;
		public ItemStack[] inputs = null;
		public ChromaButton button;
		public ItemStack output;
		public ItemStack[] outputs = null;

		private int dye_cycle = 0;
		private int last_cycle_color = 0;
		private int merge_counter;

		public CachedChromaRecipe(String group, int color, ItemStack dye, String oreDye, ItemStack input, ChromaButton button, ItemStack output){
			this.group = group;
			this.color = color;
			this.dye = dye;
			if(oreDye != null){
				// add all dyes of oreDye color
				this.oreDyes = OreDictionary.getOres(oreDye);
			} else if(dye == null)
				this.addAllDyes();
			this.input = input;
			this.button = button;
			this.output = output;
		}

		public void addAllDyes(){
			// add all dyes, but not ore dict dyes
			this.oreDyes = new ArrayList<ItemStack>();
			for(int c = 0; c < 16; c++)
				this.oreDyes.add(new ItemStack(MC_ITEM.dyePowder, 1, c));
		}

		public void setChromaInputs(){
			this.inputs = new ItemStack[16];
			for(int c = 1; c < 16; c++)
				this.inputs[c] = new ItemStack(this.input.getItem(), 1, c);
		}
		public void setChromaOutputs(ChromaRecipe recipe){
			this.outputs = new ItemStack[16];
			for(int c = 1; c < 16; c++)
				this.outputs[c] = new ItemStack(recipe.output.getItem(), 1, recipe.getOutputMetadata(c));
		}


		public void mergeChromaInput(ItemStack input){
			if(this.inputs == null){
				this.merge_counter = 0;
				this.inputs = new ItemStack[16];
				this.inputs[this.merge_counter] = this.input;
			}
			this.merge_counter++;
			this.inputs[this.merge_counter] = input;
		}
		public void mergeChromaOutput(ItemStack output, int color){
			if(this.outputs == null){
				this.outputs = new ItemStack[16];
				this.outputs[this.color] = this.output;
				this.dye = null;
				this.addAllDyes();
			}
			this.outputs[color] = output;
		}


		public PositionedStack getResult(){
			ItemStack output = this.output;
			if(this.outputs != null){
				output = this.outputs[NEIChromaRecipeHandler.cycle_color];
				if(output == null) output = this.output;
			}
			PositionedStack outputStack = new PositionedStack(output, SLOT_X_OUTPUT, SLOT_Y);
			outputStack.setMaxSize(64);
			return outputStack;
		}
		public PositionedStack getIngredient(){
			ItemStack input = this.input;
			if(this.inputs != null){
				input = this.inputs[NEIChromaRecipeHandler.cycle_color];
				if(input == null) input = this.input;
			}
			PositionedStack inputStack = new PositionedStack(input, SLOT_X_INPUT, SLOT_Y);
			inputStack.setMaxSize(64);
			return inputStack;
		}
		public PositionedStack getOtherStack(){
			ItemStack dye = this.dye;
			if(dye == null){
				if(this.oreDyes.size() == 16){
					dye = this.oreDyes.get(NEIChromaRecipeHandler.cycle_color);
				} else {
					if(NEIChromaRecipeHandler.cycle_color != this.last_cycle_color){
						this.last_cycle_color = NEIChromaRecipeHandler.cycle_color;
						this.dye_cycle++;
						if(this.dye_cycle >= this.oreDyes.size()) this.dye_cycle = 0;
					}
					dye = this.oreDyes.get(this.dye_cycle);
				}
			}
			PositionedStack dyeStack = new PositionedStack(dye, SLOT_X_DYE, SLOT_Y);
			dyeStack.setMaxSize(64);
			return dyeStack;
		}
	}

	private CachedChromaRecipe addRecipe(String group, int color, ItemStack dye, String oreDye, ItemStack input, ChromaButton button, ItemStack output){
		CachedChromaRecipe cachedRecipe = new CachedChromaRecipe(group, color, dye, oreDye, input, button, output);
		this.arecipes.add(cachedRecipe);
		return cachedRecipe;
	}

	private boolean mergeInputs(ChromaRecipe recipe){
		if(recipe.color == -1) return false;

		// merge inputs with other recipes
		int nr_recipes = this.arecipes.size();
		for(int r = 0; r < nr_recipes; r++){
			CachedChromaRecipe cachedRecipe = (CachedChromaRecipe)this.arecipes.get(r);
			if(cachedRecipe != null){
				if(cachedRecipe.color == -1) continue;

				// different inputs but same output, button, color and group
				if(cachedRecipe.outputs == null
				&& cachedRecipe.output.itemID == recipe.output.itemID
				&& cachedRecipe.output.getItemDamage() == recipe.output.getItemDamage()
				&& cachedRecipe.button == recipe.button
				&& cachedRecipe.color == recipe.color
				&& cachedRecipe.group == recipe.group
				){
					cachedRecipe.mergeChromaInput(recipe.input);
					return true;
				}
			}
		}
		return false;
	}
	private boolean mergeOutputs(ChromaRecipe recipe){
		if(recipe.color == -1) return false;

		// merge outputs with other recipes
		int nr_recipes = this.arecipes.size();
		for(int r = 0; r < nr_recipes; r++){
			CachedChromaRecipe cachedRecipe = (CachedChromaRecipe)this.arecipes.get(r);
			if(cachedRecipe != null){
				if(cachedRecipe.color == -1) continue;

				// different outputs but same input, button and group
				if(cachedRecipe.inputs == null
				&& cachedRecipe.input.itemID == recipe.input.itemID
				&& cachedRecipe.input.getItemDamage() == recipe.input.getItemDamage()
				&& cachedRecipe.button == recipe.button
				&& cachedRecipe.group == recipe.group
				){
					cachedRecipe.mergeChromaOutput(recipe.output, recipe.color);
					return true;
				}
			}
		}
		return false;
	}

	private String getOreDye(ChromaRecipe recipe, int outputColor){
		return Color.oreDyes[recipe.getOutputColor(outputColor)];
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results){
		if(outputId.equals("item")){
			ItemStack result = (ItemStack)results[0];

			List<ChromaRecipe> recipes = ChromaRegistry.registry.recipes;
			int resultItemID = result.itemID;
			int resultDamage = result.getItemDamage();
			for(int i = 0; i < ChromaRegistry.registry.nr_recipes; i++){
				ChromaRecipe recipe = recipes.get(i);
				if(resultItemID == recipe.output.itemID && (recipe.colored_output || resultDamage == recipe.output.getItemDamage())){
					if(this.mergeInputs(recipe)) continue;
					CachedChromaRecipe r = this.addRecipe(recipe.group, recipe.color, null, getOreDye(recipe, resultDamage), recipe.input, recipe.button,
															new ItemStack(result.getItem(), 1, resultDamage));
					if(recipe.colored_input) r.setChromaInputs();
				}
			}
		}
	}

	@Override
	public void loadUsageRecipes(String inputId, Object... ingredients){
		if(inputId.equals("item")){
			ItemStack ingredient = (ItemStack)ingredients[0];

			List<ChromaRecipe> recipes = ChromaRegistry.registry.recipes;
			if(ingredient.getItem() instanceof ItemDye){
				// dye inputs
				int color = ingredient.getItemDamage();
				for(int i = 0; i < ChromaRegistry.registry.nr_recipes; i++){
					ChromaRecipe recipe = recipes.get(i);
					if(recipe.colored_output || color == recipe.color){
						if(this.mergeInputs(recipe)) continue;
						CachedChromaRecipe r = this.addRecipe(recipe.group, recipe.color, new ItemStack(ingredient.getItem(), 1, color), null, recipe.input, recipe.button,
																new ItemStack(recipe.output.getItem(), 1, recipe.getOutputMetadata(color)));
						if(recipe.colored_input) r.setChromaInputs();
					}
				}
			} else {
				// item inputs
				int ingredientItemID = ingredient.itemID;
				int ingredientDamage = ingredient.getItemDamage();
				for(int i = 0; i < ChromaRegistry.registry.nr_recipes; i++){
					ChromaRecipe recipe = recipes.get(i);
					ItemStack recipeInput = recipe.input;
					if(ingredientItemID == recipeInput.itemID && (recipe.colored_input || ingredientDamage == recipeInput.getItemDamage())){
						if(this.mergeOutputs(recipe)) continue;
						CachedChromaRecipe r = this.addRecipe(recipe.group, recipe.color, null, (recipe.color == -1 ? null : getOreDye(recipe, recipe.color)),
																new ItemStack(ingredient.getItem(), 1, ingredientDamage), recipe.button,
																new ItemStack(recipe.output.getItem(), 1, recipe.getOutputMetadata(0)));
						if(recipe.colored_output) r.setChromaOutputs(recipe);
					}
				}
			}
		}
	}
}

#endif
