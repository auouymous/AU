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
		this.setFilterItemStack(new ItemStack(Block.dirt));
	}

	@Override
	public boolean isItemValid(ItemStack itemstack){
		Block block = Block.blocksList[itemstack.getItem().itemID];
		if(block == null) return false;
		return block.getRenderType() == 0 && block.getRenderBlockPass() == 0;
	}

	public boolean canAdjust(){
		return false;
	}
}

#endif
// no support for 147
