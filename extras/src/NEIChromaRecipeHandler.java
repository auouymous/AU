package com.qzx.au.extras;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
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
		UI.bindTexture(Minecraft.getMinecraft(), "au_extras", AUExtras.texturePath+"/gui/container.png");

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
		public ItemStack dye;
		public ArrayList<ItemStack> oreDyes = null;
		public ItemStack input;
		public ItemStack[] inputs = null;
		public ChromaButton button;
		public ItemStack output;
		public ItemStack[] outputs = null;

		private int dye_cycle = 0;
		private int last_cycle_color = 0;

		public CachedChromaRecipe(ItemStack dye, String oreDye, ItemStack input, ChromaButton button, ItemStack output){
			this.dye = dye;
			if(oreDye != null){
				// add all dyes of oreDye color
				this.oreDyes = OreDictionary.getOres(oreDye);
			} else if(dye == null){
				// add all dyes, but not ore dict dyes
				this.oreDyes = new ArrayList<ItemStack>();
				for(int c = 0; c < 16; c++)
					this.oreDyes.add(new ItemStack(Item.dyePowder, 1, c));
			}
			this.input = input;
			this.button = button;
			this.output = output;
		}

		public void addChromaInput(ItemStack input){
			if(this.inputs == null){
				this.inputs = new ItemStack[16];
				this.inputs[this.input.getItemDamage()] = this.input;
			}
			this.inputs[input.getItemDamage()] = input;
		}
		public void addChromaOutput(ItemStack output){
			if(this.outputs == null){
				this.outputs = new ItemStack[16];
				this.outputs[this.output.getItemDamage()] = this.output;
			}
			this.outputs[output.getItemDamage()] = output;
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

	private CachedChromaRecipe addRecipe(ItemStack dye, String oreDye, ItemStack input, ChromaButton button, ItemStack output){
		CachedChromaRecipe cachedRecipe = new CachedChromaRecipe(dye, oreDye, input, button, output);
		this.arecipes.add(cachedRecipe);
		return cachedRecipe;
	}

	private boolean mergeInputs(ChromaRecipe recipe){
		// merge inputs with other recipes
		int nr_recipes = this.arecipes.size();
		for(int r = 0; r < nr_recipes; r++){
			CachedChromaRecipe cachedRecipe = (CachedChromaRecipe)this.arecipes.get(r);
			if(cachedRecipe != null){
				if(cachedRecipe.input.itemID == recipe.input.itemID
				&& cachedRecipe.output.itemID == recipe.output.itemID
				&& cachedRecipe.button == recipe.button
				){
					cachedRecipe.addChromaInput(recipe.input);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void loadCraftingRecipes(String outputId, Object... results){
		if(outputId.equals("item")){
			ItemStack result = (ItemStack)results[0];

			List<ChromaRecipe> recipes = ChromaRegistry.registry.recipes;
			int resultItemID = result.itemID;
			int color = result.getItemDamage();
			for(int i = 0; i < ChromaRegistry.registry.nr_recipes; i++){
				ChromaRecipe recipe = recipes.get(i);
				ItemStack recipeOutput = recipe.output;
				if(resultItemID == recipeOutput.itemID)
					if(!this.mergeInputs(recipe))
						this.addRecipe(null, Color.oreDyes[color], recipe.input, recipe.button, result);
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
					if(!this.mergeInputs(recipe))
						this.addRecipe(ingredient, null, recipe.input, recipe.button, new ItemStack(recipe.output.getItem(), 1, recipe.getOutputColor(color)));
				}
			} else {
				// item inputs
				int ingredientItemID = ingredient.itemID;
				int ingredientDamage = ingredient.getItemDamage();
				for(int i = 0; i < ChromaRegistry.registry.nr_recipes; i++){
					ChromaRecipe recipe = recipes.get(i);
					ItemStack recipeInput = recipe.input;
					if(ingredientItemID == recipeInput.itemID && ingredientDamage == recipeInput.getItemDamage()){
						CachedChromaRecipe r = this.addRecipe(null, null, ingredient, recipe.button, new ItemStack(recipe.output.getItem(), 1, recipe.getOutputColor(0)));
						for(int c = 1; c < 16; c++)
							r.addChromaOutput(new ItemStack(recipe.output.getItem(), 1, recipe.getOutputColor(c)));
					}
				}
			}
		}
	}
}
