package com.qzx.au.core;

// no support for 147
#ifndef MC147

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

// fake pattern block
// - ghosted item
// - accepts a single item, but does not decrement stack
// - item can not be removed
//		- clicking clears item
// - stone filter image
public class SlotBlockCamo extends SlotPattern {
	public SlotBlockCamo(IInventory inventory, int slot, int x, int y){
		super(inventory, slot, x, y, 1);
//		this.setFilterItemStack(new ItemStack(Block.dirt));
		this.setTooltip("block camo");
		if(slot != -1) Debug.error("SlotBlockCamo needs slotIndex == -1");
	}

	@Override
	public boolean isItemValid(ItemStack itemstack){
		Block block = Block.blocksList[itemstack.getItem().itemID];
		if(block == null) return false;
		return block.getRenderType() == 0 && block.getRenderBlockPass() == 0;
	}

	@Override
	public void onSlotChanged(){
		this.inventory.onInventoryChanged();
		TileEntityAU te = (TileEntityAU)this.inventory;
		if(!te.worldObj.isRemote && te.canCamo())

			te.setCamoBlock(this.getStack());
	}
}

#endif
// no support for 147
