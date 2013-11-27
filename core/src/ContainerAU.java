package com.qzx.au.core;

// no support for 147
#ifndef MC147

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerAU extends Container {
	public static int borderThickness = 5; // thickness of border around upper and lower windows
	public static int borderPlayer = (ContainerAU.borderThickness<<1) + 1;
	public static int tabSize = 16; // width and height of tabs
	public static int lowerWidth = 9*18; // width of player inventory, excluding borders
	public static int lowerHeight = 4*18 + 4; // height of player inventory, excluding borders

	protected TileEntityAU tileEntity;
	protected int slotPlayerInventory;

	public int upperWidth = 0; // width of upper content, excluding borders
	public int upperHeight = 0; // height of upper content, excluding borders

	public int upperOffsetX = 0, upperOffsetY = 0;
	public int lowerOffsetX = 0, lowerOffsetY = 0;

	/*
	public ContainerAU(InventoryPlayer inventoryPlayer, TileEntityAU tileEntity){
		this.tileEntity = tileEntity;
		// subclass puts some code here...
		this.centerUpperLowerWindows(0,0);
		// most code goes here...
		this.addPlayerInventorySlotsToContainer(inventoryPlayer, 0);
	}
	*/

	public void centerUpperLowerWindows(int upperWidth, int upperHeight){
		this.upperWidth = upperWidth;
		this.upperHeight = upperHeight;

		this.upperOffsetX = ContainerAU.borderThickness + (upperWidth > ContainerAU.lowerWidth ? 0 : (ContainerAU.lowerWidth - upperWidth)/2);
		this.upperOffsetY = ContainerAU.borderThickness;
		this.lowerOffsetX = ContainerAU.borderThickness + (ContainerAU.lowerWidth > upperWidth ? 0 : (upperWidth - ContainerAU.lowerWidth)/2);
		this.lowerOffsetY = ContainerAU.borderThickness + upperHeight + ContainerAU.borderPlayer;
	}

	public void addPlayerInventorySlotsToContainer(InventoryPlayer inventoryPlayer, int y_offset){
		this.slotPlayerInventory = this.inventorySlots.size();
		// player's inventory
		int x_offset = this.lowerOffsetX + 1;
		y_offset += ContainerAU.borderPlayer;
		for(int v = 0; v < 3; v++, y_offset+=18)
			for(int h = 0; h < 9; h++)
				this.addSlotToContainer(new SlotInventory(inventoryPlayer, h+v*9+9, x_offset+h*18, y_offset));
		// player's hotbar
		y_offset += 4;
		for(int h = 0; h < 9; h++)
			this.addSlotToContainer(new SlotInventory(inventoryPlayer, h, x_offset+h*18, y_offset));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player){
		return this.tileEntity.isUseableByPlayer(player);
	}

/*
	@Override
	public List getInventory(){
		ArrayList list = new ArrayList();
		for(int i = this.tileEntity.firstValidSlot; i < this.inventorySlots.size(); i++)
			list.add(((Slot)this.inventorySlots.get(i)).getStack());
		return list;
	}
*/

	//////////

	@Override
	// shift-click slot in or out of container
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex){
		ItemStack originalStack = null;
		SlotAU slot = (slotIndex < 0 ? null : (SlotAU)this.inventorySlots.get(slotIndex));

		// null checks and checks if the item can be stacked (maxStackSize > 1)
		if(slot != null && slot.getHasStack()){
			int nr_slots = this.inventorySlots.size();
			ItemStack stackInSlot = slot.getStack();
			originalStack = stackInSlot.copy();

			// transfer stack from player inventory/hotbar to container
			if(slotIndex >= nr_slots - 36 && this.tryShiftItemStack(stackInSlot, nr_slots)){
				// do nothing
			// if container full, transfer stack from player inventory to hotbar
			} else if(slotIndex >= nr_slots - 36 && slotIndex < nr_slots - 9){
				if(!this.shiftItemStack(stackInSlot, nr_slots - 9, nr_slots))
					return null;
			// if container full, transfer stack from hotbar to player inventory
			} else if(slotIndex >= nr_slots - 9 && slotIndex < nr_slots){
				if(!this.shiftItemStack(stackInSlot, nr_slots - 36, nr_slots - 9))
					return null;
			// transfer stack from container to player inventory/hotbar
			} else if(!this.shiftItemStack(stackInSlot, nr_slots - 36, nr_slots))
				return null;

			slot.onSlotChange(stackInSlot, originalStack);
			if(stackInSlot.stackSize == 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();

			if(stackInSlot.stackSize == originalStack.stackSize)
				return null;
			slot.onPickupFromSlot(player, stackInSlot);
		}
		return originalStack;
	}

	// Copyright (c) SpaceToad, 2011 http://www.mod-buildcraft.com
	private boolean tryShiftItemStack(ItemStack stackToShift, int nr_slots){
		for(int i = 0; i < nr_slots - 36; i++){
			SlotAU slot = (SlotAU)inventorySlots.get(i);
			if(!slot.canShiftItemStack())
				continue;
			if(!slot.isItemValid(stackToShift))
				continue;
			if(this.shiftItemStack(stackToShift, i, i + 1))
				return true;
		}
		return false;
	}

	// Copyright (c) SpaceToad, 2011 http://www.mod-buildcraft.com
	private boolean shiftItemStack(ItemStack stackToShift, int start, int end){
		boolean changed = false;
		if(stackToShift.isStackable()){
			for(int slotIndex = start; stackToShift.stackSize > 0 && slotIndex < end; slotIndex++){
				SlotAU slot = (SlotAU)this.inventorySlots.get(slotIndex);
				ItemStack stackInSlot = slot.getStack();
				if(stackInSlot != null && this.canStacksMerge(stackInSlot, stackToShift)){
					int resultingStackSize = stackInSlot.stackSize + stackToShift.stackSize;
					int max = Math.min(stackToShift.getMaxStackSize(), slot.getSlotStackLimit());
					if(resultingStackSize <= max){
						stackToShift.stackSize = 0;
						stackInSlot.stackSize = resultingStackSize;
						slot.onSlotChanged();
						changed = true;
					} else if(stackInSlot.stackSize < max){
						stackToShift.stackSize -= max - stackInSlot.stackSize;
						stackInSlot.stackSize = max;
						slot.onSlotChanged();
						changed = true;
					}
				}
			}
		}
		if(stackToShift.stackSize > 0){
			for(int slotIndex = start; stackToShift.stackSize > 0 && slotIndex < end; slotIndex++){
				SlotAU slot = (SlotAU)this.inventorySlots.get(slotIndex);
				ItemStack stackInSlot = slot.getStack();
				if(stackInSlot == null){
					int max = Math.min(stackToShift.getMaxStackSize(), slot.getSlotStackLimit());
					stackInSlot = stackToShift.copy();
					stackInSlot.stackSize = Math.min(stackToShift.stackSize, max);
					stackToShift.stackSize -= stackInSlot.stackSize;
					slot.putStack(stackInSlot);
					slot.onSlotChanged();
					changed = true;
				}
			}
		}
		return changed;
	}

	// Copyright (c) SpaceToad, 2011 http://www.mod-buildcraft.com
	private boolean canStacksMerge(ItemStack stack1, ItemStack stack2) {
		if(stack1 == null || stack2 == null)
			return false;
		if(!stack1.isItemEqual(stack2))
			return false;
		if(!ItemStack.areItemStackTagsEqual(stack1, stack2))
			return false;
		return true;
	}

	//////////

	@Override
	public ItemStack slotClick(int slotIndex, int mouseButton, int modifier, EntityPlayer player){
		/*
			mouseButton		0		left
			mouseButton		1		right
			mouseButton		2		middle ?
			modifier		1		shift
		*/

		SlotAU slot = (slotIndex < 0 ? null : (SlotAU)this.inventorySlots.get(slotIndex));
		if(slot instanceof SlotPattern)
			return this.slotClickPattern((SlotPattern)slot, mouseButton, modifier, player);
		return super.slotClick(slotIndex, mouseButton, modifier, player);
	}

	// Copyright (c) SpaceToad, 2011 http://www.mod-buildcraft.com
	private ItemStack slotClickPattern(SlotPattern slot, int mouseButton, int modifier, EntityPlayer player){
		ItemStack stack = null;
		ItemStack stackSlot = slot.getStack();
		ItemStack stackHeld = player.inventory.getItemStack();

		if(stackSlot != null)
			stack = stackSlot.copy();

		if(stackSlot == null){
			// set slot with item in hand
			if(stackHeld != null && slot.isItemValid(stackHeld))
				this.fillPatternSlot(slot, stackHeld, mouseButton, modifier);
		} else if(stackHeld == null){
			// clear slot when hand is empty
			if(slot.canAdjust())
				slot.putStack(null);
		} else if(slot.isItemValid(stackHeld)){
			// adjust slot quantity
			if(this.canStacksMerge(stackSlot, stackHeld))
				this.adjustPatternSlot(slot, mouseButton, modifier);
			// overwrite slot with item in hand
			else
				this.fillPatternSlot(slot, stackHeld, mouseButton, modifier);
		}

		return stack;
	}

	// Copyright (c) SpaceToad, 2011 http://www.mod-buildcraft.com
	protected void adjustPatternSlot(SlotPattern slot, int mouseButton, int modifier){
		if(!slot.canAdjust()) return;
		ItemStack stackSlot = slot.getStack();
		int stackSize;
		if(modifier == 1)
			// halve or double quantity when shift clicking (left or right)
			stackSize = (mouseButton == 0 ? (stackSlot.stackSize + 1) / 2 : stackSlot.stackSize * 2);
		else
			// decr or incr quantity when clicking (left or right)
			stackSize = (mouseButton == 0 ? stackSlot.stackSize - 1 : stackSlot.stackSize + 1);
		if(stackSize > slot.getSlotStackLimit())
			stackSize = slot.getSlotStackLimit();
		stackSlot.stackSize = stackSize;
		if(stackSlot.stackSize <= 0)
			slot.putStack(null);
		else
			slot.onSlotChanged();
	}

	// Copyright (c) SpaceToad, 2011 http://www.mod-buildcraft.com
	protected void fillPatternSlot(SlotPattern slot, ItemStack stackHeld, int mouseButton, int modifier){
		if(!slot.canAdjust()) return;
		int stackSize = (mouseButton == 0 ? stackHeld.stackSize : 1); // left adds stack, right adds 1
		if(stackSize > slot.getSlotStackLimit())
			stackSize = slot.getSlotStackLimit();
		ItemStack patternStack = stackHeld.copy();
		patternStack.stackSize = stackSize;
		slot.putStack(patternStack);
	}
}

#endif
// no support for 147
