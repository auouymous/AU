package com.qzx.au.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

// fake pattern items
// - ghosted item
// - accepts a single item, but does not decrement stack
// - item can not be removed
//		- clicking clears item
public class SlotPattern extends SlotAU {
	public SlotPattern(IInventory inventory, int slot, int x, int y, int maxStackSize){
		super(inventory, slot, x, y, maxStackSize);
		this.shaded = true;
	}

	@Override
	public boolean canTakeStack(EntityPlayer player){
		return false;
	}

	@Override
	public boolean canShiftItemStack(){
		return false;
	}

	public boolean canAdjust(){
		return true;
	}
}
