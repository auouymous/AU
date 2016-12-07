package com.qzx.au.extras;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
//import net.minecraft.client.Minecraft;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

IMPORT_FORGE_DIRECTION

import java.util.Random;

import com.qzx.au.core.BlockCoord;
import com.qzx.au.core.IConnectedTexture;
import com.qzx.au.core.Light;

public class BlockLamp extends BlockColored {
	@SideOnly(Side.CLIENT)
	private MC_ICON[] blockIcons;
	@SideOnly(Side.CLIENT)
	private MC_ICON[] blockIcons_glow;

	private boolean inverted = false;
	private boolean powered = false;

	public BlockLamp(int id, String name, boolean inverted, boolean powered){
		super(id, name, (powered ? null : ItemBlockColoredIcon.class), Material.glass);
		if(inverted != powered)
			this.setLightValue(Light.level[15]);
		this.inverted = inverted;
		this.powered = powered;

		this.disableStats();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(MC_ICON_REGISTER iconRegister){
		this.blockIcons = new MC_ICON[16];
		if(this.inverted != this.powered) this.blockIcons_glow = new MC_ICON[16];
		for(int c = 0; c < 16; c++){
			if(this.inverted != this.powered){
				this.blockIcons[c] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("tile.au.", "").replace("Inverted", "").replace("Powered", "")+c+"-lit");
				this.blockIcons_glow[c] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("tile.au.", "").replace("Inverted", "").replace("Powered", "")+c+"-glow");
			} else
				this.blockIcons[c] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("tile.au.", "").replace("Inverted", "").replace("Powered", "")+c+"-unlit");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public MC_ICON getIcon(int side, int color){
		return this.blockIcons[color];
	}

	@SideOnly(Side.CLIENT)
	public MC_ICON getGlowIcon(int color){
		return (this.inverted != this.powered ? this.blockIcons_glow[color] : null);
	}

	@Override
	public boolean isOpaqueCube(){
		return true;
	}

	@Override
	public boolean renderAsNormalBlock(){
		return false;
	}

	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side){
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType(){
		return ClientProxy.lampRenderType;
	}

	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass(){
		return (this.inverted != this.powered ? 1 : 0);
	}

	@SideOnly(Side.CLIENT)
	public boolean canRenderInPass(int pass){
		ClientProxy.renderPass = pass;
		return true;
	}

	public boolean isOn(){
		return (this.inverted != this.powered);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public MC_ICON getBlockTexture(IBlockAccess access, int x, int y, int z, int side){
		return this.blockIcons[access.getBlockMetadata(x, y, z)];
	}

	//////////

	@Override
	public int tickRate(World world){
		return 4;
	}

	private void updateBlockState(World world, int x, int y, int z){
		if(world.isRemote) return;

		boolean has_power = world.isBlockIndirectlyGettingPowered(x, y, z);

		if(this.powered && !has_power)
			// signal update to change to unpowered state
			world.scheduleBlockUpdate(x, y, z, this.blockID, this.tickRate(world));
		else if(!this.powered && has_power)
			// change to powered state
			world.setBlock(x, y, z, (this.inverted ? THIS_MOD.blockInvertedLampPowered.blockID : THIS_MOD.blockLampPowered.blockID), world.getBlockMetadata(x, y, z), 2);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z){
		this.updateBlockState(world, x, y, z);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int neighborBlockID){
		this.updateBlockState(world, x, y, z);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random){
		if(world.isRemote) return;

		// change to unpowered state
		if(this.powered && !world.isBlockIndirectlyGettingPowered(x, y, z))
			world.setBlock(x, y, z, (this.inverted ? THIS_MOD.blockInvertedLamp.blockID : THIS_MOD.blockLamp.blockID), world.getBlockMetadata(x, y, z), 2);
	}

	@Override
	public int idDropped(int color, Random random, int unknown){
		// always drop unpowered block
		return (this.inverted ? THIS_MOD.blockInvertedLamp.blockID : THIS_MOD.blockLamp.blockID);
	}

	@Override
	public int idPicked(World world, int x, int y, int z){
		// always pick unpowered block
		return (this.inverted ? THIS_MOD.blockInvertedLamp.blockID : THIS_MOD.blockLamp.blockID);
	}
}
