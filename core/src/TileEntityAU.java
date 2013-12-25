package com.qzx.au.core;

// no support for 147
#ifndef MC147

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeDirection;

import java.util.Random;

public abstract class TileEntityAU extends TileEntity implements ISidedInventory {
	// public World worldObj
	// public int xCoord, yCoord, zCoord
	// protected boolean tileEntityInvalid
	// public int blockMetadata = -1
	// public Block blockType
	protected int nrAccessPlayers;
	protected ForgeDirection direction; // NBT
	private Block camoBlock; // NBT
	private String owner; // NBT
	protected int nrUpgrades; // NBT
	protected ItemStack[] upgradeContents; // NBT
	protected int nrSlots; // NBT
	protected ItemStack[] slotContents; // NBT
	protected int firstValidSlot;

	protected TileEntityAU(){
		super();
		this.nrAccessPlayers = 0;
		this.direction = (this.canRotateVertical() ? ForgeDirection.UP : ForgeDirection.NORTH);
		this.camoBlock = null;
		this.owner = null;
		this.nrUpgrades = 0;
		this.upgradeContents = null;
		this.nrSlots = 0;
		this.slotContents = null;
		this.firstValidSlot = 0;
	}

	//////////

	protected void markChunkModified(){
		#ifdef MC152
		this.worldObj.updateTileEntityChunkAndDoNothing(this.xCoord, this.yCoord, this.zCoord, this);
		#else
		this.worldObj.markTileEntityChunkModified(this.xCoord, this.yCoord, this.zCoord, this);
		#endif
	}

	//////////

	public void dropContents(World world, int x, int y, int z, ItemStack item){
		Random random = new Random();

// TODO: if retention, store NBT from TileEntity into Item
//		ItemUtils.dropItemAsEntity() has code to do this

		// drop upgrades
//		for(int i = 0; i < this.upgradeContents.length; i++)
//			ItemUtils.dropItemAsEntity(world, x, y, z, this.upgradeContents[i], random);
		// drop inventory
		for(int i = this.firstValidSlot; i < this.slotContents.length; i++)
			ItemUtils.dropItemAsEntity(world, x, y, z, this.slotContents[i], random);
	}

	/////////
	// NBT //
	/////////

	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);

		// direction
		if(this.canRotate())
			this.direction = ForgeDirection.values()[nbt.getByte("face")];

		// block camo
		if(this.canCamo())
			this.camoBlock = Block.blocksList[nbt.getInteger("camo")];

		// placed by
		if(this.canOwn())
			this.owner = nbt.getString("owner");

		// upgrade contents
		if(this.nrUpgrades > 0){
			NBTTagList taglist = nbt.getTagList("upgrades");
			this.upgradeContents = new ItemStack[this.getSizeInventory()];
			for(int i = 0; i < taglist.tagCount(); i++){
				NBTTagCompound nbt_slot = (NBTTagCompound)taglist.tagAt(i);
				int slot = nbt_slot.getByte("slot") & 0xff;
				if(slot >= 0 && slot < this.upgradeContents.length)
					this.upgradeContents[slot] = ItemStack.loadItemStackFromNBT(nbt_slot);
			}
		}

		// slot contents (inventory, etc)
		if(this.nrSlots > 0){
			NBTTagList taglist = nbt.getTagList("contents");
			this.slotContents = new ItemStack[this.getSizeInventory()];
			for(int i = 0; i < taglist.tagCount(); i++){
				NBTTagCompound nbt_slot = (NBTTagCompound)taglist.tagAt(i);
				int slot = nbt_slot.getByte("slot") & 0xff;
				if(slot >= 0 && slot < this.slotContents.length)
					this.slotContents[slot] = ItemStack.loadItemStackFromNBT(nbt_slot);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);

		// direction
		if(this.canRotate())
			nbt.setByte("face", (byte)this.direction.ordinal());

		// block camo
		if(this.canCamo())
			nbt.setInteger("camo", this.camoBlock != null ? this.camoBlock.blockID : 0);

		// placed by
		if(this.owner != null)
			nbt.setString("owner", this.owner);

		// upgrade contents
		if(this.nrUpgrades > 0){
			NBTTagList taglist = new NBTTagList();
			for(int i = 0; i < this.upgradeContents.length; i++){
				if(this.upgradeContents[i] != null){
					NBTTagCompound nbt_slot = new NBTTagCompound();
					nbt_slot.setByte("slot", (byte)i);
					this.upgradeContents[i].writeToNBT(nbt_slot);
					taglist.appendTag(nbt_slot);
				}
			}
			nbt.setTag("upgrades", taglist);
		}

		// slot contents (inventory, etc)
		if(this.nrSlots > 0){
			NBTTagList taglist = new NBTTagList();
			for(int i = 0; i < this.slotContents.length; i++){
				if(this.slotContents[i] != null){
					NBTTagCompound nbt_slot = new NBTTagCompound();
					nbt_slot.setByte("slot", (byte)i);
					this.slotContents[i].writeToNBT(nbt_slot);
					taglist.appendTag(nbt_slot);
				}
			}
			nbt.setTag("contents", taglist);
		}
	}

	////////
	// UI //
	////////

	public ContainerAU getContainer(InventoryPlayer inventory){
		// override for custom GUI
		// return new ContainerAU(inventory, this);
		return null;
	}

	@SideOnly(Side.CLIENT)
	public GuiContainerAU getGuiContainer(InventoryPlayer inventory){
		// override for custom GUI
		// return new GuiContainerAU(inventory, this);
		return null;
	}

	/////////////////
	// directional //
	/////////////////

	public boolean canRotate(){
		return true;
	}
	public boolean canRotateVertical(){
		return true;
	}

	public boolean placeOppositeDirection(){
		return false;
	}

	public void setDirection(ForgeDirection direction){
		if(direction == this.direction || !this.canRotate()) return;
		this.direction = direction;
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, 2, direction.ordinal());
		this.markChunkModified();
	}

	public ForgeDirection getDirection(){
		return this.direction;
	}

	////////////////
	// block camo //
	////////////////

	public boolean canCamo(){
		return true;
	}

	public void setCamoBlock(Block block){
		if(block == this.camoBlock || !this.canCamo()) return;
		this.camoBlock = block;
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, 3, block.blockID);
		this.markChunkModified();
	}

	public Block getCamoBlock(){
		return this.camoBlock;
	}

	///////////
	// owner //
	///////////

	public boolean canOwn(){
		return false;
	}

	public String getOwner(){
		return this.owner;
	}

	public void setOwner(String owner){
		if(!this.canOwn()) return;
		this.owner = owner;
		this.markChunkModified();
	}

	////////////////
	// TileEntity //
	////////////////

	@Override
	public boolean canUpdate(){
		return false; // return true if updateEntity() should be called
	}
	// @Override public void updateEntity();

	@Override
	public Packet getDescriptionPacket(){
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, tag);
	}
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData packet){
		#ifdef MC152
		this.readFromNBT(packet.customParam1);
		#else
		this.readFromNBT(packet.data);
		#endif
	}

	@Override
	public boolean receiveClientEvent(int eventID, int value){
		switch(eventID){
		case 1:
			this.nrAccessPlayers = value;
			return true;
		case 2:
			this.direction = ForgeDirection.values()[value];
			return true;
		case 3:
			this.camoBlock = Block.blocksList[value];
			return true;
		default:
			return false;
		}
	}

	// @Override public void onInventoryChanged(){

	// public void onChunkUnload();

	////////////////
	// IInventory //
	////////////////

	public int getSizeInventory(){
		return this.nrSlots;
	}

	public boolean isInvNameLocalized(){
		return false;
	}
	public String getInvName(){
		// override and return name
		return "au.tileentity.AU";
	}

	public ItemStack getStackInSlot(int slot){
		return slot >= 0 && slot < this.nrSlots ? this.slotContents[slot] : null;
	}
	public ItemStack decrStackSize(int slot, int amount){
		ItemStack itemstack = this.getStackInSlot(slot);
		if(itemstack == null) return null;

// TODO: if upgrade slot, call method in ItemUpgrade to verify it can be removed

// TODO: return null if fake slot (SlotPattern, SlotResult, etc)

		if(itemstack.stackSize <= amount){
			this.slotContents[slot] = null;
			this.onInventoryChanged();
			return itemstack;
		}
		ItemStack ret_itemstack = this.slotContents[slot].splitStack(amount);
		if(this.slotContents[slot].stackSize == 0)
			this.slotContents[slot] = null;
		this.onInventoryChanged();
		return ret_itemstack;
	}
	public ItemStack getStackInSlotOnClosing(int slot){
		return null;
	}
	#ifdef MC152
	public boolean isStackValidForSlot(int slot, ItemStack itemstack){
	#else
	public boolean isItemValidForSlot(int slot, ItemStack itemstack){
	#endif
		// override and return true is slot accepts itemstack type (ignore itemstack size)
		return false;
	}
	public void setInventorySlotContents(int slot, ItemStack itemstack){
		if(slot < 0 || slot >= this.nrSlots) return;

// TODO: return if fake slot? (SlotPattern, SlotResult, etc)

// TODO: if upgrade slot, call method in ItemUpgrade to verify it can be removed

// TODO: set maxStackSize per slot
		int maxStackSize = 64;

		this.slotContents[slot] = itemstack;
		if(itemstack != null && itemstack.stackSize > maxStackSize)
			itemstack.stackSize = maxStackSize;
		this.onInventoryChanged();
	}

	public int getInventoryStackLimit(){
		return 64;
	}

	public boolean isUseableByPlayer(EntityPlayer player){
		if(this.worldObj == null) return true;
		if(this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this) return false;
		return player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	public void openChest(){
		if(this.worldObj == null) return;
		this.nrAccessPlayers++;
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, 1, this.nrAccessPlayers);
	}

	public void closeChest(){
		if(this.worldObj == null) return;
		this.nrAccessPlayers--;
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, 1, this.nrAccessPlayers);
	}

	/////////////////////
	// ISidedInventory //
	/////////////////////

	public int[] getAccessibleSlotsFromSide(int side){
		// returns slot indices accessible from side

// TODO: add support for 6 colored inventories

		return null;
	}
	public boolean canInsertItem(int slotIndex, ItemStack itemstack, int side){
		if(slotIndex < this.firstValidSlot) return false;
		return true;
	}
	public boolean canExtractItem(int slotIndex, ItemStack itemstack, int side){
		if(slotIndex < this.firstValidSlot) return false;
		return true;
	}
}

#endif
// no support for 147
