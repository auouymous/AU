package com.qzx.au.extras;

import net.minecraft.entity.player.InventoryPlayer;

import com.qzx.au.core.ContainerAU;
import com.qzx.au.core.SlotBlockCamo;

public class ContainerEnderCube extends ContainerAU {
	public ContainerEnderCube(InventoryPlayer inventoryPlayer, TileEntityEnderCube tileEntity){
		this.tileEntity = tileEntity;

		this.centerUpperLowerWindows(7*18, 5*18 + 4);
		int x_offset = this.upperOffsetX + 1;
		int y_offset = this.upperOffsetY + 1;

		// block camo
		this.addSlotToContainer(new SlotBlockCamo(tileEntity, 0, x_offset+6*18-10, y_offset+4*18));

		// player's inventory
		this.addPlayerInventorySlotsToContainer(inventoryPlayer, y_offset+5*18 + 4);
	}
}
