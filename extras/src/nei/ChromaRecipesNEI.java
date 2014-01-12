package com.qzx.au.extras.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.forge.GuiContainerManager;
import codechicken.nei.recipe.ICraftingHandler;
import codechicken.nei.recipe.IUsageHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class ChromaRecipesNEI extends TemplateRecipeHandler {
	public class CachedRecipe {
		public PositionedStack getResult(){ // output
			return null;
		}
		public PositionedStack getIngredient(){ // input
			return null;
		}
		public PositionedStack getOtherStack(){ // dye
			return null;
		}
	}

	public String getGuiTexture(){
		return null;
	}

	public String getRecipeName(){
		return null;
	}

	#ifdef MC152
	@Override
	#endif
    public void drawExtras(GuiContainerManager gui, int recipe){
// foreground gui
	}
}
