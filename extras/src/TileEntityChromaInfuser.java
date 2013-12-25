package com.qzx.au.extras;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.player.InventoryPlayer;
//import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.qzx.au.core.TileEntityAU;

public class TileEntityChromaInfuser extends TileEntityAU {
	public static int SLOT_ITEM_INPUT = 0;
	public static int SLOT_ITEM_OUTPUT = 1;
	public static int SLOT_DYE_INPUT = 2;
	public static int SLOT_WATER_BUCKET = 3;

	private ChromaButton recipeButton;
	private boolean hasWater;
	private int dyeColor;
	private int dyeVolume; // 0-8

	public TileEntityChromaInfuser(){
		super();
		this.nrSlots = 4; // item input(top), item output(bottom), dye inventory(side), water bucket inventory
		this.slotContents = new ItemStack[this.nrSlots];

		this.recipeButton = ChromaButton.BUTTON_BLANK;
		this.hasWater = false;
		this.dyeColor = 0;
		this.dyeVolume = 0;
	}

	//////////

	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);

		this.recipeButton = ChromaButton.values()[nbt.getByte("recipeButton")];
		this.hasWater = nbt.getBoolean("hasWater");
		this.dyeColor = nbt.getByte("dyeColor");
		this.dyeVolume = nbt.getByte("dyeVolume");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);

		nbt.setByte("recipeButton", (byte)this.recipeButton.ordinal());
		nbt.setBoolean("hasWater", this.hasWater);
		nbt.setByte("dyeColor", (byte)this.dyeColor);
		nbt.setByte("dyeVolume", (byte)this.dyeVolume);
	}

	//////////

	@Override
	public ContainerChromaInfuser getContainer(InventoryPlayer inventory){
		return new ContainerChromaInfuser(inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiChromaInfuser getGuiContainer(InventoryPlayer inventory){
		return new GuiChromaInfuser(inventory, this);
	}

	//////////

	@Override
	public boolean canRotate(){
		return false;
	}

	@Override
	public boolean canCamo(){
		return false;
	}

	@Override
	public boolean canOwn(){
		return false;
	}

	//////////

	public ChromaButton getRecipeButton(){
		return this.recipeButton;
	}
	public void setRecipeButton(ChromaButton button){
		this.recipeButton = button;

		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, 100, button.ordinal());
		this.markChunkModified();

		this.resetOutputSlot(false);
	}

	public boolean getWater(){
		return this.hasWater;
	}
	public void resetWater(){
		this.hasWater = true;
		this.dyeVolume = 0;

		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, 101, 0);
		this.markChunkModified();

		this.consumeDye();
	}

	public int getDyeColor(){
		return this.dyeColor;
	}
	public int getDyeVolume(){
		return this.dyeVolume;
	}
	public void consumeDye(){
		if(this.hasWater && this.dyeVolume == 0){
			ItemStack itemstack = this.slotContents[TileEntityChromaInfuser.SLOT_DYE_INPUT];
			if(itemstack != null){
//				Item item = itemstack.getItem();
				int color = itemstack.getItemDamage();
				this.dyeVolume = 8;
				this.dyeColor = color;

// TODO: does this work for other dyes? works for anything that extends ItemDye, should it support ore dictionary?

				itemstack.stackSize--;
				if(itemstack.stackSize == 0) this.slotContents[TileEntityChromaInfuser.SLOT_DYE_INPUT] = null;

				this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, 102, color);
				this.markChunkModified();
			}
		}
		this.resetOutputSlot(false);
	}

	public void resetOutputSlot(boolean sync){
		ChromaRecipe recipe = ChromaRegistry.getRecipe(this.recipeButton, this.slotContents[TileEntityChromaInfuser.SLOT_ITEM_INPUT]);
		this.slotContents[TileEntityChromaInfuser.SLOT_ITEM_OUTPUT]
			= (recipe == null || !this.hasWater || this.dyeVolume == 0
			? null
			: new ItemStack(recipe.output.getItem().itemID, recipe.output.stackSize, this.dyeColor));

		if(sync){
			this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, 103, 0);
			this.markChunkModified();
		}
	}

	//////////

	@Override
	public boolean receiveClientEvent(int eventID, int value){
		if(super.receiveClientEvent(eventID, value)) return true;

		switch(eventID){
		case 100:
			// set button
			this.recipeButton = ChromaButton.values()[value];
			this.resetOutputSlot(false);
			return true;
		case 101:
			// set water, reset dye
			this.hasWater = true;
			this.dyeVolume = 0;
			this.resetOutputSlot(false);
			return true;
		case 102:
			// set dye color
			this.dyeVolume = 8;
			this.dyeColor = value;
			this.resetOutputSlot(false);
			return true;
		case 103:
			// set output slot
			this.resetOutputSlot(false);
			return true;
		default:
			return false;
		}
	}

	//////////

	@Override
	public String getInvName(){
		return "au.tileentity.ChromaInfuser";
	}

	public int[] getAccessibleSlotsFromSide(int side){
		// returns slot indices accessible from side
		int[][] sidedSlots = {{TileEntityChromaInfuser.SLOT_ITEM_OUTPUT}, {TileEntityChromaInfuser.SLOT_ITEM_INPUT}, {TileEntityChromaInfuser.SLOT_DYE_INPUT}};
		if(side == 0) return sidedSlots[0];
		if(side == 1) return sidedSlots[1];
		return sidedSlots[2];
	}
}
