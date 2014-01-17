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

	private String tooltip;

	public SlotAU(IInventory inventory, int slot, int x, int y, int maxStackSize){
		super(inventory, slot, x, y);
		this.maxStackSize = maxStackSize;
		this.borderPadding = 0;
		this.shaded = false;
		this.filterItemStack = null;
		this.tooltip = null;
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

	public String getTooltip(){
		return this.tooltip;
	}
	public SlotAU setTooltip(String tooltip){
		this.tooltip = tooltip;
		return this;
	}

	public boolean isMouseOver(int x, int y){
		// x and y are relative to container
		if(x < this.xDisplayPosition - (this.borderPadding>>1) || y < this.yDisplayPosition - (this.borderPadding>>1)) return false;
		if(x < this.xDisplayPosition + (this.borderPadding>>1) + 16 && y < this.yDisplayPosition + (this.borderPadding>>1) + 16) return true;
		return false;
	}
}

#endif
// no support for 147
