package com.qzx.au.extras;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.qzx.au.core.PacketUtils;
import com.qzx.au.core.SidedBlockInfo;
import com.qzx.au.core.SidedSlotInfo;
import com.qzx.au.core.TileEntityAU;

public class TileEntityChromaInfuser extends TileEntityAU {
	public static byte SLOT_ITEM_INPUT = 0;
	public static byte SLOT_ITEM_OUTPUT = 1;
	public static byte SLOT_DYE_INPUT = 2;
	public static byte SLOT_WATER_BUCKET = 3;

	private ChromaButton recipeButton;
	private boolean isLocked;
	private boolean hasWater;
	private byte dyeColor;
	private byte dyeVolume; // 0-8

	private byte outputTick;
	private ItemStack processingItem;
	private ChromaRecipe processingRecipe;

	public static final byte outputTickMax = 20;

	public TileEntityChromaInfuser(){
		super();
		this.nrSlots = 4; // item input(top), item output(bottom), dye inventory(side), water bucket inventory
		this.slotContents = new ItemStack[this.nrSlots];

		this.recipeButton = ChromaButton.BUTTON_BLANK;
		this.isLocked = true;
		this.hasWater = false;
		this.dyeColor = 0;
		this.dyeVolume = 0;
		this.outputTick = 0;
		this.processingItem = null;
		this.processingRecipe = null;

		this.sidedBlockInfo = new SidedBlockInfo(TileEntityChromaInfuser.sidedSlotInfo,
												TileEntityChromaInfuser.sidedSlots,
												TileEntityChromaInfuser.sidedCanToggle,
												TileEntityChromaInfuser.sidedIsVisible);
	}

	private static final SidedSlotInfo[] sidedSlotInfo = {
		(new SidedSlotInfo("Item Input",	SidedSlotInfo.BLUE_COLOR,	TileEntityChromaInfuser.SLOT_ITEM_INPUT,	1,	true, true){
				@Override
				public boolean isItemValid(ItemStack itemstack){
					return ChromaRegistry.hasRecipe(itemstack);
				}
			}),
		(new SidedSlotInfo("Dye Input",		SidedSlotInfo.PURPLE_COLOR,	TileEntityChromaInfuser.SLOT_DYE_INPUT,		1,	true, true){
				@Override
				public boolean isItemValid(ItemStack itemstack){
					return itemstack.getItem() instanceof ItemDye;
				}
			}),
		new SidedSlotInfo("Item Output",	SidedSlotInfo.ORANGE_COLOR,	TileEntityChromaInfuser.SLOT_ITEM_OUTPUT,	1,	false, true)
	};
	private static final byte[] sidedSlots = {2, 0, 1, 1, 1, 1}; // bottom:itemOutput, top:itemInput, sides:dyeInput
	private static final boolean[] sidedCanToggle = {false, false, false, false, false, false};
	private static final boolean[] sidedIsVisible = {false, false, false, false, false, false};

	//////////

	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);

		short cfg = nbt.getShort("_cfg");

		this.recipeButton = ChromaButton.values()[cfg & 0x3];	// 0000 0000 0011
		this.dyeColor = (byte)((cfg>>2) & 0xf);					// 0000 0011 1100
		this.dyeVolume = (byte)((cfg>>6) & 0xf);				// 0011 1100 0000
		this.hasWater = (cfg & 0x400) != 0;						// 0100 0000 0000
		this.isLocked = (cfg & 0x800) != 0;						// 1000 0000 0000

		this.processingItem = this.getInput();
		this.processingRecipe = ChromaRegistry.getRecipe(this.recipeButton, this.getInput());
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);

		short cfg = (short)(this.recipeButton.ordinal() | (this.dyeColor<<2) | ((short)this.dyeVolume<<6));
		if(this.hasWater) cfg |= 0x400;
		if(this.isLocked) cfg |= 0x800;

		nbt.setShort("_cfg", cfg);
	}

	//////////

	@Override
	public ContainerChromaInfuser getContainer(InventoryPlayer inventory){
		return new ContainerChromaInfuser(inventory, this);
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

	public ItemStack getInput(){
		return this.slotContents[TileEntityChromaInfuser.SLOT_ITEM_INPUT];
	}

	public ChromaButton getRecipeButton(){
		return this.recipeButton;
	}
	public void setRecipeButton(ChromaButton button, boolean server){
		this.recipeButton = button;
		this.updateInput(this.getInput());

		if(server){
			PacketUtils.sendToAllAround(this.worldObj, PacketUtils.MAX_RANGE, AUExtras.packetChannel, Packets.CLIENT_CHROMA_SET_RECIPE,
										this.xCoord, this.yCoord, this.zCoord, (byte)button.ordinal());
			this.markChunkModified();
		}
	}

	public boolean getLocked(){
		return this.isLocked;
	}
	public void setLocked(boolean locked, boolean server){
		this.isLocked = locked;
		this.outputTick = 0;

		if(server){
			PacketUtils.sendToAllAround(this.worldObj, PacketUtils.MAX_RANGE, AUExtras.packetChannel, Packets.CLIENT_CHROMA_SET_LOCK,
										this.xCoord, this.yCoord, this.zCoord, locked);
			this.markChunkModified();
		}
	}
	public float getOutputTick(){
		return (float)this.outputTick / (float)TileEntityChromaInfuser.outputTickMax;
	}

	public boolean getWater(){
		return this.hasWater;
	}
	public void resetWater(boolean server){
		this.hasWater = true;
		this.dyeVolume = 0;

		if(server){
			PacketUtils.sendToAllAround(this.worldObj, PacketUtils.MAX_RANGE, AUExtras.packetChannel, Packets.CLIENT_CHROMA_RESET_WATER,
										this.xCoord, this.yCoord, this.zCoord);
			this.markChunkModified();

			this.consumeDye();
		} else {
			this.outputTick = 0;
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
	}

	public int getDyeColor(){
		return (int)this.dyeColor;
	}
	public int getDyeVolume(){
		return (int)this.dyeVolume;
	}
	public void consumeDye(){
		this.outputTick = 0;

		if(this.hasWater && this.dyeVolume == 0){
			ItemStack itemstack = this.slotContents[TileEntityChromaInfuser.SLOT_DYE_INPUT];
			if(itemstack != null){
				int color = itemstack.getItemDamage();
				this.dyeVolume = 8;
				this.dyeColor = (byte)color;

				itemstack.stackSize--;
				if(itemstack.stackSize == 0) this.slotContents[TileEntityChromaInfuser.SLOT_DYE_INPUT] = null;

				PacketUtils.sendToAllAround(this.worldObj, PacketUtils.MAX_RANGE, AUExtras.packetChannel, Packets.CLIENT_CHROMA_SET_COLOR,
											this.xCoord, this.yCoord, this.zCoord, (byte)color);
				this.markChunkModified();
			}
		}
	}
	public void setDyeColor(byte color){
		// client
		this.dyeVolume = 8;
		this.dyeColor = color;
		this.outputTick = 0;

		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}

	//////////

	private void updateInput(ItemStack input){
		this.processingItem = input;
		this.processingRecipe = ChromaRegistry.getRecipe(this.recipeButton, input);
		this.outputTick = 0;
	}

	private boolean canOutput(ItemStack output){
		if(output == null) return true;
		if(output.stackSize == output.getMaxStackSize()) return false;
		return (this.processingRecipe.output.itemID == output.itemID && this.processingRecipe.getOutputColor(this.dyeColor) == output.getItemDamage());
	}

	private void updateOutput(ItemStack input, ItemStack output, ChromaRecipe recipe){
		// server
		int recipe_itemID = recipe.output.itemID;
		if(output != null){
			// output slot has an item in it, abort if not same item
			output.stackSize += recipe.output.stackSize;
		} else {
			// output slot is empty, create new item
			this.slotContents[TileEntityChromaInfuser.SLOT_ITEM_OUTPUT] = new ItemStack(recipe_itemID, recipe.output.stackSize, recipe.getOutputColor(this.dyeColor));
		}

		input.stackSize--;
		if(input.stackSize == 0) this.slotContents[TileEntityChromaInfuser.SLOT_ITEM_INPUT] = null;

		this.dyeVolume--;
		this.consumeDye();

		PacketUtils.sendToAllAround(this.worldObj, PacketUtils.MAX_RANGE, AUExtras.packetChannel, Packets.CLIENT_CHROMA_UPDATE_OUTPUT,
									this.xCoord, this.yCoord, this.zCoord, this.dyeVolume);
		this.markChunkModified();
	}
	public void updateOutput(byte dyeVolume){
		// client
		this.outputTick = 0;
		this.dyeVolume = dyeVolume;

// TODO: display particle effect on block?

	}

	//////////

	@Override
	public boolean canUpdate(){
		return true;
	}

	@Override
	public void updateEntity(){
		if(!this.isLocked && this.hasWater && this.dyeVolume > 0){
			ItemStack input = this.slotContents[TileEntityChromaInfuser.SLOT_ITEM_INPUT];
			if(input == null){
				// no input, reset progress
				if(this.outputTick > 0) this.updateInput(null);
			} else {
				if(this.processingItem == null){
					// new input, reset progress
					this.updateInput(input);
				} else if(input.itemID != this.processingItem.itemID || input.getItemDamage() != processingItem.getItemDamage()){
					// input item changed, reset progress
					this.updateInput(input);
				}

				if(this.processingRecipe != null){
					ItemStack output = this.slotContents[TileEntityChromaInfuser.SLOT_ITEM_OUTPUT];
					if(this.canOutput(output)){
						if(!this.worldObj.isRemote){
							// server
							this.outputTick++;
							if(this.outputTick < TileEntityChromaInfuser.outputTickMax) return;
							this.outputTick = 0;

							this.updateOutput(input, output, this.processingRecipe);
						} else {
							// client
							if(this.outputTick < TileEntityChromaInfuser.outputTickMax)
								this.outputTick++;
						}
					}
				}
			}
		}
	}

	//////////

	@Override
	public String getInvName(){
		return "au.tileentity.ChromaInfuser";
	}
}
