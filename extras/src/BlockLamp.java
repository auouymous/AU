package com.qzx.au.extras;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.material.Material;
//import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeDirection;

import java.util.Random;

import com.qzx.au.util.BlockCoord;
import com.qzx.au.util.IConnectedTexture;
import com.qzx.au.util.Light;

public class BlockLamp extends BlockColored implements IConnectedTexture {
	@SideOnly(Side.CLIENT)
	private Icon[][] blockIcons;
	private Icon[] itemIcons_unlit;
	private Icon[] itemIcons_lit;

	private boolean inverted = false;
	private boolean powered = false;

	public BlockLamp(int id, String name, String readableName, boolean inverted, boolean powered){
		super(id, name, readableName, (powered ? null : (inverted ? ItemBlockInvertedLamp.class : ItemBlockLamp.class)), Material.glass);
		if(inverted != powered)
			this.setLightValue(Light.level[15]);
		this.inverted = inverted;
		this.powered = powered;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister){
		this.blockIcons = new Icon[16][IConnectedTexture.ctm_icons];
		this.itemIcons_unlit = new Icon[16];
		this.itemIcons_lit = new Icon[16];
		for(int c = 0; c < 16; c++){
			for(int t = 0; t < 47; t++)
				this.blockIcons[c][IConnectedTexture.ctm[t]]
					= iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("tile.au.", "").replace("Inverted", "").replace("Powered", "")+c+"-"+t);
			this.itemIcons_unlit[c] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("tile.au.", "").replace("Inverted", "").replace("Powered", "")+c+"-unlit");
			this.itemIcons_lit[c] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("tile.au.", "").replace("Inverted", "").replace("Powered", "")+c+"-lit");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int color){
		return this.blockIcons[color][IConnectedTexture.ctm_default];
	}

	@SideOnly(Side.CLIENT)
	public Icon getItemIcon(int color){
		return this.inverted ? this.itemIcons_lit[color] : this.itemIcons_unlit[color];
	}

	public boolean isOpaqueCube(){
		return false;
	}

	public boolean renderAsNormalBlock(){
		return false;
	}

	@Override
	public int getRenderType(){
		return ClientProxy.lampRenderType;
	}

//	@Override
//	public int getRenderColor(int color){
//		return 16777215;
//	}

	public int getRenderBlockPass(){
		return 1;
	}
	public boolean canRenderInPass(int pass){
		ClientProxy.renderPass = pass;
		return true;
	}

	public boolean isOn(){
		return (this.inverted != this.powered);
	}

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess access, int x, int y, int z, int side){
		// coordinates are the block at each side, not the block being rendered

		if(ClientProxy.renderPass == 0) return true;

		BlockCoord neighbor = new BlockCoord(access, x, y, z);
		BlockCoord coord = (new BlockCoord(neighbor)).translateToSide(ForgeDirection.OPPOSITES[side]);
		if(coord.getBlockID() != neighbor.getBlockID()) return true;
		if(coord.getBlockMetadata() != neighbor.getBlockMetadata()) return true;
		return false;
	}

	@SideOnly(Side.CLIENT)
	public boolean canConnectTextures(int id, int meta, int side, BlockCoord neighbor){
		int neighbor_id = neighbor.getBlockID();

		// connect to all lamps that are on (sides)
		if(neighbor_id == AUExtras.blockInvertedLamp.blockID || neighbor_id == AUExtras.blockLampPowered.blockID) return true;
		// connect to same color of glass (sides)
		if(neighbor_id != id || neighbor.getBlockMetadata() != meta) return false;

		//////////

		/*
			A
			BC

			- when rendering the top of C on the side that connects to B
			- diagonal is block is A
		*/

		BlockCoord diagonal = (new BlockCoord(neighbor)).translateToSide(side);
		int diagonal_id = diagonal.getBlockID();

		// connect to all lamps that are on (diagonals)
		if(diagonal_id == AUExtras.blockInvertedLamp.blockID || diagonal_id == AUExtras.blockLampPowered.blockID) return true;
		// must not have same color of lamp on this side (to render inner frames)
		if(diagonal_id == id && diagonal.getBlockMetadata() == meta) return false;

		return true;
	}

	public boolean canConnectCornerTextures(int id, int meta, BlockCoord diagonal){
		int diagonal_id = diagonal.getBlockID();

		// connect to all lamps that are on (corners)
		if(diagonal_id == AUExtras.blockInvertedLamp.blockID || diagonal_id == AUExtras.blockLampPowered.blockID) return true;

		// connect to same color lamps (corners)
		return (diagonal.getBlockID() == id && diagonal.getBlockMetadata() == meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess access, int x, int y, int z, int side){
		if(this.isOn()) return this.blockIcons[access.getBlockMetadata(x, y, z)][IConnectedTexture.ctm_borderless];

		BlockCoord coord = new BlockCoord(access, x, y, z);
		int blockID = coord.getBlockID();
		int blockColor = coord.getBlockMetadata();
		BlockCoord u = (new BlockCoord(coord)).translateToSideAtDirection(side, BlockCoord.UP);
		BlockCoord d = (new BlockCoord(coord)).translateToSideAtDirection(side, BlockCoord.DOWN);
		BlockCoord l = (new BlockCoord(coord)).translateToSideAtDirection(side, BlockCoord.LEFT);
		BlockCoord r = (new BlockCoord(coord)).translateToSideAtDirection(side, BlockCoord.RIGHT);
		BlockCoord ul = (new BlockCoord(coord)).translateToDiagonalAtDirection(side, BlockCoord.UP, BlockCoord.LEFT);
		BlockCoord ur = (new BlockCoord(coord)).translateToDiagonalAtDirection(side, BlockCoord.UP, BlockCoord.RIGHT);
		BlockCoord dl = (new BlockCoord(coord)).translateToDiagonalAtDirection(side, BlockCoord.DOWN, BlockCoord.LEFT);
		BlockCoord dr = (new BlockCoord(coord)).translateToDiagonalAtDirection(side, BlockCoord.DOWN, BlockCoord.RIGHT);
		boolean connect_t = this.canConnectTextures(blockID, blockColor, side, u);
		boolean connect_r = this.canConnectTextures(blockID, blockColor, side, r);
		boolean connect_b = this.canConnectTextures(blockID, blockColor, side, d);
		boolean connect_l = this.canConnectTextures(blockID, blockColor, side, l);
		boolean connect_tl = this.canConnectCornerTextures(blockID, blockColor, ul);
		boolean connect_tr = this.canConnectCornerTextures(blockID, blockColor, ur);
		boolean connect_bl = this.canConnectCornerTextures(blockID, blockColor, dl);
		boolean connect_br = this.canConnectCornerTextures(blockID, blockColor, dr);

		int texture = 0;
		if(!connect_t) texture |= 1<<0;							// T
			else if(connect_l && !connect_tl) texture |= 2<<0;	// tl
		if(!connect_r) texture |= 1<<2;							// R
			else if(connect_t && !connect_tr) texture |= 2<<2;	// tr
		if(!connect_b) texture |= 1<<4;							// B
			else if(connect_r && !connect_br) texture |= 2<<4;	// br
		if(!connect_l) texture |= 1<<6;							// L
			else if(connect_b && !connect_bl) texture |= 2<<6;	// bl

		return this.blockIcons[blockColor][texture];
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, World world, int x, int y, int z){
		return false;
	}

	//////////

	private void updateBlockState(World world, int x, int y, int z){
		if(world.isRemote) return;

		boolean has_power = world.isBlockIndirectlyGettingPowered(x, y, z);

		if(this.powered && !has_power)
			// signal update to change to unpowered state
			world.scheduleBlockUpdate(x, y, z, this.blockID, 4);
		else if(!this.powered && has_power)
			// change to powered state
			world.setBlock(x, y, z, (this.inverted ? AUExtras.blockInvertedLampPowered.blockID : AUExtras.blockLampPowered.blockID), world.getBlockMetadata(x, y, z), 2);
	}
	public void onBlockAdded(World world, int x, int y, int z){
		this.updateBlockState(world, x, y, z);
	}
	public void onNeighborBlockChange(World world, int x, int y, int z, int neighborBlockID){
		this.updateBlockState(world, x, y, z);
	}

	public void updateTick(World world, int x, int y, int z, Random random){
		if(world.isRemote) return;

		// change to unpowered state
		if(this.powered && !world.isBlockIndirectlyGettingPowered(x, y, z))
			world.setBlock(x, y, z, (this.inverted ? AUExtras.blockInvertedLamp.blockID : AUExtras.blockLamp.blockID), world.getBlockMetadata(x, y, z), 2);
//		else
//			Minecraft.getMinecraft().renderGlobal.markBlockForRenderUpdate(x, y, z);
	}

	public int idDropped(int par1, Random par2Random, int par3){
		// always drop unpowered block
		return (this.inverted ? AUExtras.blockInvertedLamp.blockID : AUExtras.blockLamp.blockID);
	}

	public int idPicked(World par1World, int par2, int par3, int par4){
		// always pick unpowered block
		return (this.inverted ? AUExtras.blockInvertedLamp.blockID : AUExtras.blockLamp.blockID);
	}
}
