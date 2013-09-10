package com.qzx.au.extras;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeDirection;

import com.qzx.au.util.BlockColored;
import com.qzx.au.util.BlockCoord;
import com.qzx.au.util.IConnectedTexture;

public class BlockGlass extends BlockColored implements IConnectedTexture {
	@SideOnly(Side.CLIENT)
	private Icon[][] blockIcons;

	public BlockGlass(int id, String name, String readableName){
		super("au_extras", id, name, readableName, ItemBlockGlass.class, Material.glass);
//		this.setBlockBounds(0.001F, 0.001F, 0.001F, 0.999F, 0.999F, 0.999F);
// TODO: causes gaps between connected textures
// TODO: use ISimpleBlockRenderHandler to render all 6 sides
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister){
		this.blockIcons = new Icon[16][IConnectedTexture.ctm_icons];
		for(int c = 0; c < 16; c++)
			for(int t = 0; t < 47; t++)
				this.blockIcons[c][IConnectedTexture.ctm[t]] = iconRegister.registerIcon(this.modPath+this.getUnlocalizedName().replace("tile.au.", "")+c+"-"+t);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int color){
		return this.blockIcons[color][IConnectedTexture.ctm_default];
	}

	public boolean isOpaqueCube(){
		return false;
	}

	public boolean renderAsNormalBlock(){
		return false;
	}

/*
	public int getRenderBlockPass(){
		return 1;
// TODO: this fades frame color
	}
*/

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess access, int x, int y, int z, int side){
		// coordinates are the block at each side, not the block being rendered

		BlockCoord neighbor = new BlockCoord(access, x, y, z);
		BlockCoord coord = (new BlockCoord(neighbor)).translateToSide(ForgeDirection.OPPOSITES[side]);
		if(coord.getBlockID() != neighbor.getBlockID()) return true;
		if(coord.getBlockMetadata() != neighbor.getBlockMetadata()) return true;
		return false;
	}

	public boolean canConnectTextures(int id, int meta, int side, BlockCoord neighbor){
		// must be same color of glass to connect
		if(neighbor.getBlockID() != id) return false;
		if(neighbor.getBlockMetadata() != meta) return false;

		// must not have same color of glass on this side (to render inner frames)
		BlockCoord diagonal = (new BlockCoord(neighbor)).translateToSide(side);
		if(diagonal.getBlockID() == id && diagonal.getBlockMetadata() == meta) return false;

		return true;
	}

	@Override
	public Icon getBlockTexture(IBlockAccess access, int x, int y, int z, int side){
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
		boolean connect_tl = ul.getBlockID() == blockID && ul.getBlockMetadata() == blockColor;
		boolean connect_tr = ur.getBlockID() == blockID && ur.getBlockMetadata() == blockColor;
		boolean connect_bl = dl.getBlockID() == blockID && dl.getBlockMetadata() == blockColor;
		boolean connect_br = dr.getBlockID() == blockID && dr.getBlockMetadata() == blockColor;

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
}
