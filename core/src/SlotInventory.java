package com.qzx.au.core;

import net.minecraft.inventory.IInventory;

public class SlotInventory extends SlotAU {
	public SlotInventory(IInventory inventory, int slot, int x, int y){
		super(inventory, slot, x, y, 64);
	}
}
