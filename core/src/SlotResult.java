package com.qzx.au.core;

// no support for 147
#ifndef MC147

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

// fake output slot
// - ghosted item, unghost if valid
// - does not accept items
// - can remove if valid
//		- dragging only takes one
//		- shift click takes upto a stack
public class SlotResult extends SlotAU {
	public SlotResult(IInventory inventory, int slot, int x, int y){
		super(inventory, slot, x, y, 64);
		this.shaded = true;
	}

	@Override
	public boolean isItemValid(ItemStack stack){
		return false;
	}

	@Override
	public ItemStack decrStackSize(int amount){

// TODO: verify amount

		return super.decrStackSize(amount);
	}
}

#endif
// no support for 147
