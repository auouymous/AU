package com.qzx.au.extras;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

IMPORT_FORGE_DIRECTION

import com.qzx.au.core.BlockCoord;
import com.qzx.au.core.IConnectedTexture;

public class BlockGlass extends BlockColored implements IConnectedTexture {
	@SideOnly(Side.CLIENT)
	private MC_ICON[][] blockIcons;

	private int style;

	public BlockGlass(int id, String name, Class<? extends ItemBlock> itemblockclass, int style){
		super(id, name, itemblockclass, Material.glass);
		this.style = style;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(MC_ICON_REGISTER iconRegister){
		switch(this.style){
		case 0: // clear
			this.blockIcons = new MC_ICON[16][IConnectedTexture.ctm_icons];
			for(int c = 0; c < 16; c++)
				for(int t = 0; t < 47; t++)
					this.blockIcons[c][IConnectedTexture.ctm[t]] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("tile.au.", "")+c+"-"+t);
			break;
		case 1: // tinted
			this.blockIcons = new MC_ICON[16][IConnectedTexture.ctm_icons];
			for(int c = 0; c < 16; c++)
				for(int t = 0; t < 47; t++)
					this.blockIcons[c][IConnectedTexture.ctm[t]] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("tile.au.", "")+c+"-"+t);
			break;
		case 2: // tinted frameless
			this.blockIcons = new MC_ICON[16][1];
			for(int c = 0; c < 16; c++)
				this.blockIcons[c][0] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("tile.au.", "")+c);
			break;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public MC_ICON getIcon(int side, int color){
		return this.blockIcons[color][this.style == 2 ? 0 : IConnectedTexture.ctm_default];
	}

	@SideOnly(Side.CLIENT)
	public MC_ICON[] getBlockIcons(int color){
		return this.blockIcons[color];
	}

	@SideOnly(Side.CLIENT)
	public boolean renderInPass0(){
		return (this.style == 0 || this.style == 1);
	}
	@SideOnly(Side.CLIENT)
	public boolean renderInPass1(){
		return (this.style == 1 || this.style == 2);
	}

	@Override
	public boolean isOpaqueCube(){
		return false;
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
		return ClientProxy.glassRenderType;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass(){
		return (this.style == 0 ? 0 : 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canRenderInPass(int pass){
		ClientProxy.renderPass = pass;
		return (this.style == 0 && pass == 0) || (this.style != 0 && pass != 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess access, int x, int y, int z, int side){
		// coordinates are the block at each side, not the block being rendered

		if(this.style == 2) return super.shouldSideBeRendered(access, x, y, z, side); // no connected textures

		BlockCoord neighbor = new BlockCoord(access, x, y, z);
		BlockCoord coord = (new BlockCoord(neighbor)).translateToSide(ForgeDirection.OPPOSITES[side]);
		if(coord.getBlock() != neighbor.getBlock()) return true;
		if(coord.getBlockMetadata() != neighbor.getBlockMetadata()) return true;
		return false;
	}

	@SideOnly(Side.CLIENT)
	public boolean canConnectSideTextures(Block block, int metadata, int side, BlockCoord neighbor){
		if(this.style == 2) return false; // no connected textures

		// connect to same color of glass on sides
		if(neighbor.getBlock() != block || neighbor.getBlockMetadata() != metadata) return false;

		/*
			A
			BC

			- when rendering the top of C on the side that connects to B
			- diagonal block is A
		*/

		BlockCoord diagonal = (new BlockCoord(neighbor)).translateToSide(side);

		// must not have same color of glass on this side (to render inner frames)
		if(diagonal.getBlock() == block && diagonal.getBlockMetadata() == metadata) return false;

		return true;
	}

	@SideOnly(Side.CLIENT)
	public boolean canConnectCornerTextures(Block block, int metadata, int side, BlockCoord diagonal){
		if(this.style == 2) return false; // no connected textures

		// connect to same color glass (corners)
		if(diagonal.getBlock() != block || diagonal.getBlockMetadata() != metadata) return false;

		/*
			AB
			CD
			- when rendering the top/bottom of D on the corner that connects to A
			- diagonal_diagonal block is above/below block A
		*/

		BlockCoord diagonal_diagonal = (new BlockCoord(diagonal)).translateToSide(side);

		// must not have same color of glass on this corner (to render inner frames)
		if(diagonal_diagonal.getBlock() == block && diagonal_diagonal.getBlockMetadata() == metadata) return false;

		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public MC_ICON getBlockTexture(IBlockAccess access, int x, int y, int z, int side){
		BlockCoord coord = new BlockCoord(access, x, y, z);
		Block block = coord.getBlock();
		int blockColor = coord.getBlockMetadata();

		if(this.style == 2) return this.blockIcons[blockColor][0]; // no connected textures

		BlockCoord u = (new BlockCoord(coord)).translateToSideAtDirection(side, BlockCoord.UP);
		BlockCoord d = (new BlockCoord(coord)).translateToSideAtDirection(side, BlockCoord.DOWN);
		BlockCoord l = (new BlockCoord(coord)).translateToSideAtDirection(side, BlockCoord.LEFT);
		BlockCoord r = (new BlockCoord(coord)).translateToSideAtDirection(side, BlockCoord.RIGHT);
		BlockCoord ul = (new BlockCoord(coord)).translateToDiagonalAtDirection(side, BlockCoord.UP, BlockCoord.LEFT);
		BlockCoord ur = (new BlockCoord(coord)).translateToDiagonalAtDirection(side, BlockCoord.UP, BlockCoord.RIGHT);
		BlockCoord dl = (new BlockCoord(coord)).translateToDiagonalAtDirection(side, BlockCoord.DOWN, BlockCoord.LEFT);
		BlockCoord dr = (new BlockCoord(coord)).translateToDiagonalAtDirection(side, BlockCoord.DOWN, BlockCoord.RIGHT);
		boolean connect_t = this.canConnectSideTextures(block, blockColor, side, u);
		boolean connect_r = this.canConnectSideTextures(block, blockColor, side, r);
		boolean connect_b = this.canConnectSideTextures(block, blockColor, side, d);
		boolean connect_l = this.canConnectSideTextures(block, blockColor, side, l);
		boolean connect_tl = this.canConnectCornerTextures(block, blockColor, side, ul);
		boolean connect_tr = this.canConnectCornerTextures(block, blockColor, side, ur);
		boolean connect_bl = this.canConnectCornerTextures(block, blockColor, side, dl);
		boolean connect_br = this.canConnectCornerTextures(block, blockColor, side, dr);

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
}
