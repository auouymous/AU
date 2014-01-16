package com.qzx.au.extras;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeDirection;

import java.util.Random;

import com.qzx.au.core.RenderUtils;
import com.qzx.au.core.SidedBlockInfo;
import com.qzx.au.core.SidedSlotInfo;
import com.qzx.au.core.TileEntityAU;

public class TileEntityEnderCube extends TileEntityAU {
	private byte playerDirection; // NBT
	private byte teleportDirection; // NBT
	private boolean playerControl; // NBT
	private boolean contactControl; // NBT
	private boolean redstoneControl; // NBT

	public TileEntityEnderCube(){
		super();

		this.playerDirection = 0;
		this.teleportDirection = 0;
		this.playerControl = false;
		this.contactControl = false;
		this.redstoneControl = false;
	}

	//////////

	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);

		short buttons = nbt.getShort("settings");

		this.playerDirection = (byte)(buttons & 0x3);			// 0000 0011
		this.teleportDirection = (byte)((buttons>>2) & 0x7);	// 0001 1100
		this.playerControl = (buttons & 0x20) != 0;				// 0010 0000
		this.contactControl = (buttons & 0x40) != 0;			// 0100 0000
		this.redstoneControl = (buttons & 0x80) != 0;			// 1000 0000
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);

		short buttons = (short)(this.playerDirection | (this.teleportDirection<<2));
		if(this.playerControl) buttons |= 0x20;
		if(this.contactControl) buttons |= 0x40;
		if(this.redstoneControl) buttons |= 0x80;

		nbt.setShort("settings", buttons);
	}

	//////////

	@Override
	public ContainerEnderCube getContainer(InventoryPlayer inventory){
		return new ContainerEnderCube(inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiEnderCube getGuiContainer(InventoryPlayer inventory){
		return new GuiEnderCube(inventory, this);
	}

	//////////

	@Override
	public boolean canRotate(){
		return false;
	}

	@Override
	public boolean canCamo(){
		return true;
	}

	@Override
	public boolean canOwn(){
		return false;
	}

	//////////

	private static final int CLIENT_EVENT_SPAWN_PARTICLES		= 100;
	private static final int CLIENT_EVENT_PLAYER_DIRECTION		= 101;
	private static final int CLIENT_EVENT_TELEPORT_DIRECTION	= 102;
	private static final int CLIENT_EVENT_PLAYER_CONTROL		= 103;
	private static final int CLIENT_EVENT_CONTACT_CONTROL		= 104;
	private static final int CLIENT_EVENT_REDSTONE_CONTROL		= 105;

	public EnderButton getPlayerDirection(){
		return EnderButton.values()[EnderButton.BUTTON_PLAYER_UD.ordinal() + this.playerDirection];
	}
	public void setPlayerDirection(EnderButton button){
		this.playerDirection = (byte)(button.ordinal() - EnderButton.BUTTON_PLAYER_UD.ordinal());

		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, TileEntityEnderCube.CLIENT_EVENT_PLAYER_DIRECTION, this.playerDirection);
		this.markChunkModified();
	}

	public EnderButton getTeleportDirection(){
		return EnderButton.values()[EnderButton.BUTTON_DOWN.ordinal() + this.teleportDirection];
	}
	public void setTeleportDirection(EnderButton button){
		this.teleportDirection = (byte)(button.ordinal() - EnderButton.BUTTON_DOWN.ordinal());

		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, TileEntityEnderCube.CLIENT_EVENT_TELEPORT_DIRECTION, this.teleportDirection);
		this.markChunkModified();
	}

	public boolean getPlayerControl(){
		return this.playerControl;
	}
	public void togglePlayerControl(){
		this.playerControl = this.playerControl ? false : true;
		if(this.playerControl) this.contactControl = false; // can't have both

		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, TileEntityEnderCube.CLIENT_EVENT_PLAYER_CONTROL, this.playerControl ? 1 : 0);
		this.markChunkModified();
	}

	public boolean getContactControl(){
		return this.contactControl;
	}
	public void toggleContactControl(){
		this.contactControl = this.contactControl ? false : true;
		if(this.contactControl) this.playerControl = false; // can't have both

		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, TileEntityEnderCube.CLIENT_EVENT_CONTACT_CONTROL, this.contactControl ? 1 : 0);
		this.markChunkModified();
	}

	public boolean getRedstoneControl(){
		return this.redstoneControl;
	}
	public void toggleRedstoneControl(){
		this.redstoneControl = this.redstoneControl ? false : true;

		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, TileEntityEnderCube.CLIENT_EVENT_REDSTONE_CONTROL, this.redstoneControl ? 1 : 0);
		this.markChunkModified();
	}

	@Override
	public boolean canUpdate(){
		return false;
	}

	@Override
	public boolean receiveClientEvent(int eventID, int value){
		if(super.receiveClientEvent(eventID, value)) return true;

		switch(eventID){
		case TileEntityEnderCube.CLIENT_EVENT_SPAWN_PARTICLES:
			// display particles above both ender cubes
			Random random = new Random();
			RenderUtils.spawnParticles(this.worldObj, (float)this.xCoord + 0.5F, (float)this.yCoord + 2.0F, (float)this.zCoord + 0.5F,
										random, BlockEnderCube.nrPortalParticles, "portal", 1.0F, 2.0F, 1.0F);
			if(this.playerDirection == 0) // up/down
				RenderUtils.spawnParticles(this.worldObj, (float)this.xCoord + 0.5F, (float)value + 1.5F, (float)this.zCoord + 0.5F,
											random, BlockEnderCube.nrPortalParticles, "portal", 1.0F, 2.0F, 1.0F);
			else if(this.playerDirection == 1) // north/south
				RenderUtils.spawnParticles(this.worldObj, (float)this.xCoord + 0.5F, (float)this.yCoord + 1.5F, (float)value + 0.5F,
											random, BlockEnderCube.nrPortalParticles, "portal", 1.0F, 2.0F, 1.0F);
			else // east/west
				RenderUtils.spawnParticles(this.worldObj, (float)value + 0.5F, (float)this.yCoord + 1.5F, (float)this.zCoord + 0.5F,
											random, BlockEnderCube.nrPortalParticles, "portal", 1.0F, 2.0F, 1.0F);
			return true;
		case TileEntityEnderCube.CLIENT_EVENT_PLAYER_DIRECTION:
			// set player direction button
			this.playerDirection = (byte)value;
			return true;
		case TileEntityEnderCube.CLIENT_EVENT_TELEPORT_DIRECTION:
			// set direction button
			this.teleportDirection = (byte)value;
			return true;
		case TileEntityEnderCube.CLIENT_EVENT_PLAYER_CONTROL:
			// set player control button
			this.playerControl = (value == 1);
			if(this.playerControl) this.contactControl = false; // can't have both
			return true;
		case TileEntityEnderCube.CLIENT_EVENT_CONTACT_CONTROL:
			// set contact control button
			this.contactControl = (value == 1);
			if(this.contactControl) this.playerControl = false; // can't have both
			return true;
		case TileEntityEnderCube.CLIENT_EVENT_REDSTONE_CONTROL:
			// set redstone control button
			this.redstoneControl = (value == 1);
			return true;
		default:
			return false;
		}
	}

	//////////

	public void teleportPlayer(EntityPlayer player, boolean teleport_up){
		if(this.playerControl == false) return;

		// scan for nearby ender cube
		int delta = (teleport_up ? 1 : -1);
		if(this.playerDirection == 0){
			// scan up/down
			int y = this.yCoord + delta;
			int limit = y + (Cfg.enderCubeDistance * delta);
			if(limit < 1) limit = 1; else if(limit > 255) limit = 255;
			for(; y != limit; y += delta){
				TileEntity tileEntity = this.worldObj.getBlockTileEntity(this.xCoord, y, this.zCoord);
				if(tileEntity instanceof TileEntityEnderCube){
					this._teleportPlayer(this.worldObj, this.xCoord, y, this.zCoord, player, y);
					break;
				}
			}
		} else if(this.playerDirection == 1){
			// scan north/south
			int z = this.zCoord + delta;
			int limit = z + (Cfg.enderCubeDistance * delta);
			for(; z != limit; z += delta){
				TileEntity tileEntity = this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, z);
				if(tileEntity instanceof TileEntityEnderCube){
					this._teleportPlayer(this.worldObj, this.xCoord, this.yCoord, z, player, z);
					break;
				}
			}
		} else {
			// scan east/west
			int x = this.xCoord + delta;
			int limit = x + (Cfg.enderCubeDistance * delta);
			for(; x != limit; x += delta){
				TileEntity tileEntity = this.worldObj.getBlockTileEntity(x, this.yCoord, this.zCoord);
				if(tileEntity instanceof TileEntityEnderCube){
					this._teleportPlayer(this.worldObj, x, this.yCoord, this.zCoord, player, x);
					break;
				}
			}
		}
	}

	private void _teleportPlayer(World world, int x, int y, int z, EntityPlayer player, int direction_coord){
		if(world.isAirBlock(x, y+1, z) && world.isAirBlock(x, y+2, z)){ // 2 air blocks above destination cube
			world.playSoundAtEntity(player, "mob.endermen.portal", 1.0F, 1.0F);
			player.setPositionAndUpdate(x + 0.5F, y + 1.1F, z + 0.5F);
			world.playSoundAtEntity(player, "mob.endermen.portal", 1.0F, 1.0F);

			world.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, TileEntityEnderCube.CLIENT_EVENT_SPAWN_PARTICLES, direction_coord);
		}
	}

	//////////

	@Override
	public String getInvName(){
		return "au.tileentity.EnderCube";
	}
}
