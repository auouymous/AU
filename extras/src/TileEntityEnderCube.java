package com.qzx.au.extras;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBasePressurePlate;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockTripWire;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeDirection;

import java.util.List;
import java.util.Random;

import com.qzx.au.core.RenderUtils;
import com.qzx.au.core.SidedBlockInfo;
import com.qzx.au.core.SidedSlotInfo;
import com.qzx.au.core.TileEntityAU;

public class TileEntityEnderCube extends TileEntityAU {
	private byte playerDirection; // NBT
	private byte teleportDirection; // NBT
	private boolean playerControl; // NBT
	private boolean playerRedstoneControl; // NBT
	private boolean redstoneControl; // NBT
	private boolean isPowered; // NBT
	private String playerControlWhitelist; // NBT

	public TileEntityEnderCube(){
		super();

		this.playerDirection = 0;
		this.teleportDirection = 0;
		this.playerControl = false;
		this.playerRedstoneControl = false;
		this.redstoneControl = false;
		this.isPowered = false;
		this.playerControlWhitelist = null;
	}

	//////////

	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);

		short cfg = nbt.getShort("_cfg");

		this.playerDirection = (byte)(cfg & 0x3);			// 0000 0000 0011
		this.teleportDirection = (byte)((cfg>>2) & 0x7);	// 0000 0001 1100
		this.playerControl = (cfg & 0x20) != 0;				// 0000 0010 0000
		this.playerRedstoneControl = (cfg & 0x40) != 0;		// 0000 0100 0000
		this.redstoneControl = (cfg & 0x80) != 0;			// 0000 1000 0000
		this.isPowered = (cfg & 0x100) != 0;				// 0001 0000 0000

		if(this.redstoneControl) this.playerControl = false; // can't use both
		if(!this.playerControl) this.playerRedstoneControl = false;

		String pcl = nbt.getString("_pcl");
		if(pcl != null && !pcl.trim().equals(""))
			this.playerControlWhitelist = pcl;
		else
			this.playerControlWhitelist = null;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);

		short cfg = (short)(this.playerDirection | (this.teleportDirection<<2));
		if(this.playerControl) cfg |= 0x20;
		if(this.playerRedstoneControl) cfg |= 0x40;
		if(this.redstoneControl) cfg |= 0x80;
		if(this.isPowered) cfg |= 0x100;

		nbt.setShort("_cfg", cfg);

		if(this.playerControlWhitelist != null)
			nbt.setString("_pcl", this.playerControlWhitelist);
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
	private static final int CLIENT_EVENT_PLAYER_RS_CONTROL		= 104;
	private static final int CLIENT_EVENT_REDSTONE_CONTROL		= 105;
	private static final int CLIENT_EVENT_IS_POWERED			= 106;
	private static final int CLIENT_EVENT_UPDATE_PCL			= 107;

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
		if(this.playerControl) this.redstoneControl = false; // can't use both
		else this.playerRedstoneControl = false;

		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, TileEntityEnderCube.CLIENT_EVENT_PLAYER_CONTROL, this.playerControl ? 1 : 0);
		this.markChunkModified();
	}

	public boolean getPlayerRedstoneControl(){
		return this.playerRedstoneControl;
	}
	public void togglePlayerRedstoneControl(){
		this.playerRedstoneControl = this.playerRedstoneControl ? false : true;
		if(!this.playerControl) this.playerRedstoneControl = false;

		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, TileEntityEnderCube.CLIENT_EVENT_PLAYER_RS_CONTROL, this.playerRedstoneControl ? 1 : 0);
		this.markChunkModified();
	}

	public boolean getRedstoneControl(){
		return this.redstoneControl;
	}
	public void toggleRedstoneControl(){
		this.redstoneControl = this.redstoneControl ? false : true;
		if(this.redstoneControl){
			this.playerControl = false; // can't use both
			this.playerRedstoneControl = false;
		}

		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, TileEntityEnderCube.CLIENT_EVENT_REDSTONE_CONTROL, this.redstoneControl ? 1 : 0);
		this.markChunkModified();
	}

	public boolean isPowered(){
		return this.isPowered;
	}
	public void setPowered(boolean isPowered){
		this.isPowered = isPowered;

		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, TileEntityEnderCube.CLIENT_EVENT_IS_POWERED, this.isPowered ? 1 : 0);
		this.markChunkModified();
	}

	public String getPlayerControlWhitelist(){
		return this.playerControlWhitelist == null ? "" : this.playerControlWhitelist;
	}
	public void setPlayerControlWhitelist(String pcl){
		if(pcl != null && pcl.trim().equals("")) pcl = null;
		if(pcl == null && this.playerControlWhitelist == null) return;
		this.playerControlWhitelist = pcl;

		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, TileEntityEnderCube.CLIENT_EVENT_UPDATE_PCL, 0);
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
			if((this.playerControl && this.playerDirection == 0) || (this.redstoneControl && (this.teleportDirection == 0 || this.teleportDirection == 1)))
				// up/down
				RenderUtils.spawnParticles(this.worldObj, (float)this.xCoord + 0.5F, (float)value + 1.5F, (float)this.zCoord + 0.5F,
											random, BlockEnderCube.nrPortalParticles, "portal", 1.0F, 2.0F, 1.0F);
			else if((this.playerControl && this.playerDirection == 1) || (this.redstoneControl && (this.teleportDirection == 2 || this.teleportDirection == 3)))
				// north/south
				RenderUtils.spawnParticles(this.worldObj, (float)this.xCoord + 0.5F, (float)this.yCoord + 1.5F, (float)value + 0.5F,
											random, BlockEnderCube.nrPortalParticles, "portal", 1.0F, 2.0F, 1.0F);
			else
				// east/west
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
			if(this.playerControl) this.redstoneControl = false; // can't use both
			return true;
		case TileEntityEnderCube.CLIENT_EVENT_PLAYER_RS_CONTROL:
			// set player redstone control button
			this.playerRedstoneControl = (value == 1);
			if(!this.playerControl) this.playerRedstoneControl = false;
			return true;
		case TileEntityEnderCube.CLIENT_EVENT_REDSTONE_CONTROL:
			// set redstone control button
			this.redstoneControl = (value == 1);
			if(this.redstoneControl){
				this.playerControl = false; // can't use both
				this.playerRedstoneControl = false;
			}
			return true;
		case TileEntityEnderCube.CLIENT_EVENT_IS_POWERED:
			// set redstone power state
			this.isPowered = (value == 1);
			return true;
		case TileEntityEnderCube.CLIENT_EVENT_UPDATE_PCL:
			// update player control whitelist
			GuiEnderCube.update_pcl = true;
			return true;
		default:
			return false;
		}
	}

	//////////

	private class AABB extends AxisAlignedBB {
		public AABB(double minX, double minY, double minZ, double maxX, double maxY, double maxZ){
			super(minX, minY, minZ, maxX, maxY, maxZ);
		}
	}

	public void teleportAll(){
		if(this.redstoneControl == false) return;

		int x = this.xCoord, y = this.yCoord, z = this.zCoord, direction_coord = 0;
		boolean found_match = false;

		// scan for nearby ender cube
		int delta = (this.teleportDirection % 2 == 1 ? 1 : -1);
		if(this.teleportDirection == 0 || this.teleportDirection == 1){
			// scan up/down
			y += delta;
			int limit = y + (Cfg.enderCubeDistance * delta);
			if(limit < 1) limit = 1; else if(limit > 255) limit = 255;
			for(; y != limit; y += delta){
				if(this.worldObj.getBlockId(x, y, z) == AUExtras.blockEnderCube.blockID){
					direction_coord = y;
					found_match = true;
					break;
				}
			}
		} else if(this.teleportDirection == 2 || this.teleportDirection == 3){
			// scan north/south
			z += delta;
			int limit = z + (Cfg.enderCubeDistance * delta);
			for(; z != limit; z += delta){
				if(this.worldObj.getBlockId(x, y, z) == AUExtras.blockEnderCube.blockID){
					direction_coord = z;
					found_match = true;
					break;
				}
			}
		} else {
			// scan east/west
			x += delta;
			int limit = x + (Cfg.enderCubeDistance * delta);
			for(; x != limit; x += delta){
				if(this.worldObj.getBlockId(x, y, z) == AUExtras.blockEnderCube.blockID){
					direction_coord = x;
					found_match = true;
					break;
				}
			}
		}

		if(found_match && !this.isObstructed(this.worldObj, x, y, z)){
			// get all entities above source cube
			List entities = this.worldObj.getEntitiesWithinAABB(EntityLiving.class,
					(AxisAlignedBB)(new AABB((double)this.xCoord, (double)this.yCoord + 1.0D, (double)this.zCoord,
									(double)this.xCoord + 1.0D, (double)this.yCoord + 3.0D, (double)this.zCoord + 1.0D)));

			// teleport all entities to destination cube
			int nr_entities = entities.size();
			for(int i = 0; i < nr_entities; i++)
				this._teleportEntity(this.worldObj, x, y, z, (EntityLiving)entities.get(i), direction_coord, (i == 0));
		}
	}

	public boolean canPlayerControl(String playerName){
		if(this.playerControlWhitelist == null) return false;

		String[] players = this.playerControlWhitelist.split(",");
		for(int i = 0; i < players.length; i++)
			if(players[i].trim().equals(playerName)) return true;
		return false;
	}

	public void teleportPlayer(EntityLiving player, boolean teleport_up){
		if(this.playerControl == false || (this.playerRedstoneControl == true && this.isPowered == false)) return;

		if(!canPlayerControl(player.getEntityName())) return;

		// scan for nearby ender cube
		int delta = (teleport_up ? 1 : -1);
		if(this.playerDirection == 0){
			// scan up/down
			int y = this.yCoord + delta;
			int limit = y + (Cfg.enderCubeDistance * delta);
			if(limit < 1) limit = 1; else if(limit > 255) limit = 255;
			for(; y != limit; y += delta){
				if(this.worldObj.getBlockId(this.xCoord, y, this.zCoord) == AUExtras.blockEnderCube.blockID){
					if(!this.isObstructed(this.worldObj, this.xCoord, y, this.zCoord))
						this._teleportEntity(this.worldObj, this.xCoord, y, this.zCoord, player, y, true);
					break;
				}
			}
		} else if(this.playerDirection == 1){
			// scan north/south
			int z = this.zCoord + delta;
			int limit = z + (Cfg.enderCubeDistance * delta);
			for(; z != limit; z += delta){
				if(this.worldObj.getBlockId(this.xCoord, this.yCoord, z) == AUExtras.blockEnderCube.blockID){
					if(!this.isObstructed(this.worldObj, this.xCoord, this.yCoord, z))
						this._teleportEntity(this.worldObj, this.xCoord, this.yCoord, z, player, z, true);
					break;
				}
			}
		} else {
			// scan east/west
			int x = this.xCoord + delta;
			int limit = x + (Cfg.enderCubeDistance * delta);
			for(; x != limit; x += delta){
				if(this.worldObj.getBlockId(x, this.yCoord, this.zCoord) == AUExtras.blockEnderCube.blockID){
					if(!this.isObstructed(this.worldObj, x, this.yCoord, this.zCoord))
						this._teleportEntity(this.worldObj, x, this.yCoord, this.zCoord, player, x, true);
					break;
				}
			}
		}
	}

	private void _teleportEntity(World world, int x, int y, int z, EntityLiving entity, int direction_coord, boolean spawn_particles){
		world.playSoundAtEntity(entity, "mob.endermen.portal", 1.0F, 1.0F);
		entity.setPositionAndUpdate(x + 0.5F, y + 1.1F, z + 0.5F);
		world.playSoundAtEntity(entity, "mob.endermen.portal", 1.0F, 1.0F);

		if(spawn_particles)
			world.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType().blockID, TileEntityEnderCube.CLIENT_EVENT_SPAWN_PARTICLES, direction_coord);
	}

	public boolean isObstructed(World world, int x, int y, int z){
		if(world.isAirBlock(x, y+1, z) && world.isAirBlock(x, y+2, z)) return false; // 2 air blocks
		Block block = Block.blocksList[world.getBlockId(x, y+1, z)];
		if(!this.checkBlockForObstructions(block)){
			block = Block.blocksList[world.getBlockId(x, y+2, z)];
			return this.checkBlockForObstructions(block);
		}
		return true;
	}
	private boolean checkBlockForObstructions(Block block){
		if(block == null) return false;
		if(block instanceof BlockBasePressurePlate) return false;
		if(block instanceof BlockButton) return false;
		if(block instanceof BlockLever) return false;
		if(block instanceof BlockRailBase) return false;
		if(block instanceof BlockSign) return false;
		if(block instanceof BlockTorch) return false;
		if(block instanceof BlockTripWire) return false;
		return true;
	}

	//////////

	@Override
	public String getInvName(){
		return "au.tileentity.EnderCube";
	}
}
