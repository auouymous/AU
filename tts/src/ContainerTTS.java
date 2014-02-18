package com.qzx.au.tts;

import net.minecraft.entity.player.InventoryPlayer;

import com.qzx.au.core.ContainerAU;
import com.qzx.au.core.SlotBlockCamo;

public class ContainerTTS extends ContainerAU {
	public ContainerTTS(InventoryPlayer inventoryPlayer, TileEntityTTS tileEntity){
		this.tileEntity = tileEntity;

		//	buttons
		//	[text] camo

		this.centerUpperLowerWindows(9*18, 4*18+10);
		int x_offset = this.upperOffsetX + 1;
		int y_offset = this.upperOffsetY + 1;

		// block camo
		this.addSlotToContainer(new SlotBlockCamo(tileEntity, -1, x_offset+8*18-4, y_offset+3*18+10-4));

		// player's inventory
		this.addPlayerInventorySlotsToContainer(inventoryPlayer, y_offset+4*18+10);
	}
}
