package com.qzx.au.core;

// no support for 147
#ifndef MC147

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.Icon;

public class SlotAU extends Slot {
	private int maxStackSize;
	public int borderPadding; // must be an even value
	public boolean shaded;

	private String filterMod;
	private String filterTexture;
	private Icon filterIcon;

	public SlotAU(IInventory inventory, int slot, int x, int y, int maxStackSize){
		super(inventory, slot, x, y);
		this.maxStackSize = maxStackSize;
		this.borderPadding = 0;
		this.shaded = false;
		this.filterMod = null;
		this.filterTexture = null;
		this.filterIcon = null;
	}

	public void setFilterIcon(String mod, String texture, Icon icon){
		this.filterMod = mod;
		this.filterTexture = texture;
		this.filterIcon = icon;
	}
	public void setFilterIcon(Icon icon){
		this.setFilterIcon(null, null, icon);
	}

	public String getFilterMod(){
		return this.filterMod;
	}
	public String getFilterTexture(){
		return this.filterTexture;
	}
	public Icon getFilterIcon(){
		return this.filterIcon;
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
