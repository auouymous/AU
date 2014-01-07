package com.qzx.au.core;

// no support for 147
#ifndef MC147

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotAU extends Slot {
	private int maxStackSize;
	public int borderPadding; // must be an even value
	public boolean shaded;

	private ItemStack filterItemStack;

	public SlotAU(IInventory inventory, int slot, int x, int y, int maxStackSize){
		super(inventory, slot, x, y);
		this.maxStackSize = maxStackSize;
		this.borderPadding = 0;
		this.shaded = false;
		this.filterItemStack = null;
	}

	public void setFilterItemStack(ItemStack itemstack){
		this.filterItemStack = itemstack;
	}
	public ItemStack getFilterItemStack(){
		return this.filterItemStack;
	}

	@Override
	public int getSlotStackLimit(){
		return this.maxStackSize;
	}

	public boolean canShiftItemStack(){
		return true;
	}
}

#endif
// no support for 147
