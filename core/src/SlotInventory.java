package com.qzx.au.core;

// no support for 147
#ifndef MC147

import net.minecraft.inventory.IInventory;

public class SlotInventory extends SlotAU {
	public SlotInventory(IInventory inventory, int slot, int x, int y){
		super(inventory, slot, x, y, 64);
	}
}

#endif
// no support for 147
