package com.qzx.au.core;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

// output slot
// - does not accept items
// - can remove items
public class SlotOutput extends SlotAU {
	public SlotOutput(IInventory inventory, int slot, int x, int y, int maxStackSize){
		super(inventory, slot, x, y, maxStackSize);
	}

	@Override
	public boolean isItemValid(ItemStack stack){
		return false;
	}
}
