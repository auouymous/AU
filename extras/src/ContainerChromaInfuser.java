package com.qzx.au.extras;

import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;

import com.qzx.au.core.ContainerAU;
import com.qzx.au.core.SlotAU;
import com.qzx.au.core.SlotInventory;

public class ContainerChromaInfuser extends ContainerAU {
	public ContainerChromaInfuser(InventoryPlayer inventoryPlayer, TileEntityChromaInfuser tileEntity){
		this.tileEntity = tileEntity;

		//	-----D-
		//	-IbP-W-
		//	bvbP-W-
		//	-ObP-W-
		//	-----B-

		// width = 7 slots
		// height = 5 slots
		this.centerUpperLowerWindows(7*18, 5*18 - 10 - 1);
		int x_offset = this.upperOffsetX + 1;
		int y_offset = this.upperOffsetY + 1 - 5;

		// input slot
		SlotInventory slotInput = (new SlotInventory(tileEntity, TileEntityChromaInfuser.SLOT_ITEM_INPUT, x_offset+1*18, y_offset+1*18){
				@Override
				public boolean isItemValid(ItemStack itemstack){
					return ChromaRegistry.hasRecipe(itemstack);
				}
			});
		slotInput.setTooltip("item input");
		this.addSlotToContainer(slotInput);

		// output slot
		SlotInventory slotOutput = (new SlotInventory(tileEntity, TileEntityChromaInfuser.SLOT_ITEM_OUTPUT, x_offset+1*18, y_offset+3*18){
				@Override
				public boolean isItemValid(ItemStack stack){
					return false;
				}
			});
		slotOutput.borderPadding = 4;
		slotOutput.setTooltip("item output");
		this.addSlotToContainer(slotOutput);

		// dye input slot
		SlotInventory slotDye = new SlotInventory(tileEntity, TileEntityChromaInfuser.SLOT_DYE_INPUT, x_offset+5*18, y_offset+0*18+9){
				@Override
				public void onSlotChanged(){
					this.inventory.onInventoryChanged();
					TileEntityChromaInfuser te = (TileEntityChromaInfuser)this.inventory;
					if(!te.worldObj.isRemote)
						te.consumeDye();
				}
				@Override
				public boolean isItemValid(ItemStack itemstack){
					return itemstack.getItem() instanceof ItemDye;
				}
			};
		slotDye.setFilterItemStack(new ItemStack(Item.dyePowder, 1, 8));
		slotDye.setTooltip("dye input");
		this.addSlotToContainer(slotDye);

		// water bucket slot
		SlotAU slotBucket = new SlotAU(tileEntity, TileEntityChromaInfuser.SLOT_WATER_BUCKET, x_offset+5*18, y_offset+4*18-9 -1, 1){ // limit 1 per stack
				@Override
				public void onSlotChanged(){
					ItemStack itemstack = this.getStack();
					if(itemstack != null){
						if(itemstack.itemID == Item.bucketWater.itemID){
							TileEntityChromaInfuser te = (TileEntityChromaInfuser)this.inventory;
							if(!te.worldObj.isRemote)
								te.resetWater(true);
							// replace water bucket with empty bucket
							this.putStack(new ItemStack(Item.bucketEmpty, 1));
//						} else if(itemstack.itemID == Block.waterMoving.blockID || itemstack.itemID == Block.waterStill.blockID){
//							((TileEntityChromaInfuser)this.inventory).resetWater(true);
//							// clear the slot
//							this.putStack(null);
						}
					}
					this.inventory.onInventoryChanged();
				}
				@Override
				public boolean isItemValid(ItemStack itemstack){
					return itemstack.itemID == Item.bucketWater.itemID; // || itemstack.itemID == Block.waterMoving.blockID || itemstack.itemID == Block.waterStill.blockID;
				}
			};
		slotBucket.setFilterItemStack(new ItemStack(Item.bucketWater));
		slotBucket.setTooltip("water bucket fills water or resets dye");
		this.addSlotToContainer(slotBucket);

		// player's inventory
		this.addPlayerInventorySlotsToContainer(inventoryPlayer, y_offset+5*18 -6);
	}
}
