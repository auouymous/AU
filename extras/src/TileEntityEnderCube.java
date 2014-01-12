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

import com.qzx.au.core.SidedBlockInfo;
import com.qzx.au.core.SidedSlotInfo;
import com.qzx.au.core.TileEntityAU;

public class TileEntityEnderCube extends TileEntityAU {
	public TileEntityEnderCube(){
		super();
	}

	//////////

/*
	@Override
	public ContainerEnderCube getContainer(InventoryPlayer inventory){
		return new ContainerEnderCube(inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiEnderCube getGuiContainer(InventoryPlayer inventory){
		return new GuiEnderCube(inventory, this);
	}
*/

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

	private static final int CLIENT_EVENT_TELEPORT = 100;

	@Override
	public boolean canUpdate(){
		return false;
	}

	@Override
	public boolean receiveClientEvent(int eventID, int value){
		if(super.receiveClientEvent(eventID, value)) return true;

		switch(eventID){
		case TileEntityEnderCube.CLIENT_EVENT_TELEPORT:
			Random random = new Random();
			TileEntityEnderCube.spawnParticles(this.worldObj, this.xCoord, this.yCoord + 1, this.zCoord, random, 1.0F, true);
			TileEntityEnderCube.spawnParticles(this.worldObj, this.xCoord, value + 1, this.zCoord, random, 1.0F, true);
			return true;
		default:
			return false;
		}
	}

	//////////

	public void teleportPlayer(EntityPlayer player, boolean teleport_up){
		// scan for nearby ender cube
		int delta = (teleport_up ? 1 : -1);
		int y = this.yCoord + delta;
		int limit = y + (20 * delta);
		if(limit < 1) limit = 1; else if(limit > 255) limit = 255;
		for(; y != limit; y += delta){
			if(this.worldObj.isAirBlock(this.xCoord, y, this.zCoord)) continue;

			TileEntity tileEntity = this.worldObj.getBlockTileEntity(this.xCoord, y, this.zCoord);
			if(tileEntity instanceof TileEntityEnderCube)
				if(this.worldObj.isAirBlock(this.xCoord, y+1, this.zCoord) && this.worldObj.isAirBlock(this.xCoord, y+2, this.zCoord)){
					this.worldObj.playSoundAtEntity(player, "mob.endermen.portal", 1.0F, 1.0F);
					player.setPositionAndUpdate(this.xCoord + 0.5F, y + 1.1F, this.zCoord + 0.5F);
					this.worldObj.playSoundAtEntity(player, "mob.endermen.portal", 1.0F, 1.0F);

					this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, TileEntityEnderCube.CLIENT_EVENT_TELEPORT, y);
				}
			break;
		}
	}

	public static void spawnParticles(World world, int x, int y, int z, Random random, float deviation, boolean stretch_up){
		short nr_particles = 128;
		for(int p = 0; p < nr_particles; p++){
			double h = (stretch_up ? (double)p / ((double)nr_particles - 1.0D) : 0.0D);
			double xx = (x + 0.5F) + (random.nextDouble()*deviation - (double)deviation/2.0D);
			double yy = (y + 0.5F) + h + (random.nextDouble()*deviation - (double)deviation/2.0D);
			double zz = (z + 0.5F) + (random.nextDouble()*deviation - (double)deviation/2.0D);
			world.spawnParticle("portal", xx, yy, zz, 0.0D, 0.0D, 0.0D);
		}
	}

	//////////

	@Override
	public String getInvName(){
		return "au.tileentity.EnderCube";
	}
}
