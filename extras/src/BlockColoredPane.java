package com.qzx.au.extras;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

IMPORT_FORGE_DIRECTION

import java.util.List;

import com.qzx.au.core.BlockCoord;
import com.qzx.au.core.IConnectedTexture;

public class BlockColoredPane extends BlockColored {
	public static final float SIDE_WIDTH = BlockCoord.ADD_1_32; // two times this
	public static final float SIDE_WIDTH_BOUNDS = BlockCoord.ADD_1_16; // two times this (easy to build from at this size)

	@SideOnly(Side.CLIENT)
	private MC_ICON sidesIcon;
	@SideOnly(Side.CLIENT)
	private MC_ICON[][] blockIcons;

	private Block parentBlock;
	private boolean hasConnectedTextures;
	private int renderInPass;

	public BlockColoredPane(int id, String name, Class<? extends ItemBlock> itemblockclass, Material material, Block parentBlock, boolean hasConnectedTextures, int pass){
		super(id, name, itemblockclass, material);
		this.parentBlock = parentBlock;
		this.hasConnectedTextures = hasConnectedTextures;
		this.renderInPass = pass;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(MC_ICON_REGISTER iconRegister){
		// block textures registered in BlockGlass

		this.sidesIcon = iconRegister.registerIcon("au_extras:colorPaneSides");

		// both tinted panes use special item icons for now
		if(this.parentBlock == null){
			this.blockIcons = new MC_ICON[16][1];
			for(int c = 0; c < 16; c++)
				this.blockIcons[c][0] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("tile.au.", "")+c);
		} else if(this.parentBlock == THIS_MOD.blockGlassTinted || this.parentBlock == THIS_MOD.blockGlassTintedNoFrame){
			this.blockIcons = new MC_ICON[16][1];
			for(int c = 0; c < 16; c++)
				this.blockIcons[c][0] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("tile.au.", "").replace("Pane", "")+c+"-item");
		} else
			this.blockIcons = null;
	}

	@SideOnly(Side.CLIENT)
	private MC_ICON[] getBlockIcons(int color){
		// glass panes use their parnet icons
		if(this.parentBlock instanceof BlockGlass) return ((BlockGlass)this.parentBlock).getBlockIcons(color);

		return this.blockIcons[color];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public MC_ICON getIcon(int side, int color){
		// both tinted panes use special item icons for now
		if(this.blockIcons != null) return this.blockIcons[color][0];

		return this.getBlockIcons(color)[this.hasConnectedTextures ? IConnectedTexture.ctm_default : 0];
	}

	@SideOnly(Side.CLIENT)
	public MC_ICON getSidesIcon(){
		return this.sidesIcon;
	}

	@SideOnly(Side.CLIENT)
	public boolean hasConnectedTextures(){
		return this.hasConnectedTextures;
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
	@SideOnly(Side.CLIENT)
	public int getRenderType(){
		return ClientProxy.paneRenderType;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass(){
		return this.renderInPass;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canRenderInPass(int pass){
		ClientProxy.renderPass = pass;
		return (pass == this.renderInPass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess access, int x, int y, int z, int side){
		// coordinates are the block at each side, not the block being rendered

		if(!(BlockCoord.getBlock(access, x, y, z) instanceof BlockColoredPane))
			return super.shouldSideBeRendered(access, x, y, z, side);

		boolean thisVertical = (this.minY == 0.0F);
		boolean neighborVertical = (this.canPaneConnectTo(access, x, y, z, ForgeDirection.UP) || this.canPaneConnectTo(access, x, y, z, ForgeDirection.DOWN));

		return thisVertical != neighborVertical;
	}

	@SideOnly(Side.CLIENT)
	public boolean canConnectSideTexturesVertical(Block block, int metadata, int side, BlockCoord neighbor){
		if(!this.hasConnectedTextures) return false; // no connected textures

		// connect to same color of glass pane on sides
		if(neighbor.getBlock() != block || neighbor.getBlockMetadata() != metadata) return false;

		// don't connect to horizontal panes
		if(!this.canPaneConnectTo(neighbor.access, neighbor.x, neighbor.y, neighbor.z, ForgeDirection.UP)
		&& !this.canPaneConnectTo(neighbor.access, neighbor.x, neighbor.y, neighbor.z, ForgeDirection.DOWN))
			return false;

		return true;
	}

	@SideOnly(Side.CLIENT)
	public boolean canConnectCornerTexturesVertical(Block block, int metadata, int side, BlockCoord diagonal){
		if(!this.hasConnectedTextures) return false; // no connected textures

		// connect to same color glass pane (corners)
		if(diagonal.getBlock() != block || diagonal.getBlockMetadata() != metadata) return false;

		// don't connect to horizontal panes
		if(!this.canPaneConnectTo(diagonal.access, diagonal.x, diagonal.y, diagonal.z, ForgeDirection.UP)
		&& !this.canPaneConnectTo(diagonal.access, diagonal.x, diagonal.y, diagonal.z, ForgeDirection.DOWN))
			return false;

		return true;
	}

	public int getPaneWidthOnSide(IBlockAccess access, int x, int y, int z, int side){
		final float SIDE_N = 0.5F - BlockColoredPane.SIDE_WIDTH;
		final float SIDE_P = 0.5F + BlockColoredPane.SIDE_WIDTH;
		boolean connect_d = this.canPaneConnectTo(access, x, y, z, ForgeDirection.DOWN);
		boolean connect_u = this.canPaneConnectTo(access, x, y, z, ForgeDirection.UP);

		if(!connect_d && !connect_u){
			return 0;
		} else {
			float minX = SIDE_N, maxX = SIDE_P;
			float minZ = SIDE_N, maxZ = SIDE_P;
			boolean connect_n = this.canPaneConnectTo(access, x, y, z, ForgeDirection.NORTH);
			boolean connect_s = this.canPaneConnectTo(access, x, y, z, ForgeDirection.SOUTH);
			boolean connect_w = this.canPaneConnectTo(access, x, y, z, ForgeDirection.WEST);
			boolean connect_e = this.canPaneConnectTo(access, x, y, z, ForgeDirection.EAST);

			if((!connect_w || !connect_e) && (connect_w || connect_e || connect_n || connect_s)){
				if(connect_w && !connect_e)
					minX = 0.0F;
				else if(!connect_w && connect_e)
					maxX = 1.0F;
			} else {
				minX = 0.0F;
				maxX = 1.0F;
			}

			if((!connect_n || !connect_s) && (connect_w || connect_e || connect_n || connect_s)){
				if(connect_n && !connect_s)
					minZ = 0.0F;
				else if(!connect_n && connect_s)
					maxZ = 1.0F;
			} else {
				minZ = 0.0F;
				maxZ = 1.0F;
			}

			return (side == 2 ? (maxX == 1.0F ? 2 : 0) | (minX == 0.0F ? 1 : 0) : (minZ == 0.0F ? 2 : 0) | (maxZ == 1.0F ? 1 : 0));
		}
	}

	@SideOnly(Side.CLIENT)
	public MC_ICON[] getBlockTextureVertical(IBlockAccess access, int x, int y, int z, int side){
		BlockCoord coord = new BlockCoord(access, x, y, z);
		Block block = coord.getBlock();
		int blockColor = coord.getBlockMetadata();

		MC_ICON[] halves = new MC_ICON[2];

		if(!this.hasConnectedTextures){
			halves[0] = this.getBlockIcons(blockColor)[0];
			return halves; // no connected textures
		}

		BlockCoord u = (new BlockCoord(coord)).translateToSideAtDirection(side, BlockCoord.UP);
		BlockCoord d = (new BlockCoord(coord)).translateToSideAtDirection(side, BlockCoord.DOWN);
		BlockCoord l = (new BlockCoord(coord)).translateToSideAtDirection(side, BlockCoord.LEFT);
		BlockCoord r = (new BlockCoord(coord)).translateToSideAtDirection(side, BlockCoord.RIGHT);
		BlockCoord ul = (new BlockCoord(coord)).translateToDiagonalAtDirection(side, BlockCoord.UP, BlockCoord.LEFT);
		BlockCoord ur = (new BlockCoord(coord)).translateToDiagonalAtDirection(side, BlockCoord.UP, BlockCoord.RIGHT);
		BlockCoord dl = (new BlockCoord(coord)).translateToDiagonalAtDirection(side, BlockCoord.DOWN, BlockCoord.LEFT);
		BlockCoord dr = (new BlockCoord(coord)).translateToDiagonalAtDirection(side, BlockCoord.DOWN, BlockCoord.RIGHT);
		boolean connect_t = this.canConnectSideTexturesVertical(block, blockColor, side, u);
		boolean connect_r = this.canConnectSideTexturesVertical(block, blockColor, side, r);
		boolean connect_b = this.canConnectSideTexturesVertical(block, blockColor, side, d);
		boolean connect_l = this.canConnectSideTexturesVertical(block, blockColor, side, l);
		boolean connect_tl = this.canConnectCornerTexturesVertical(block, blockColor, side, ul);
		boolean connect_tr = this.canConnectCornerTexturesVertical(block, blockColor, side, ur);
		boolean connect_bl = this.canConnectCornerTexturesVertical(block, blockColor, side, dl);
		boolean connect_br = this.canConnectCornerTexturesVertical(block, blockColor, side, dr);
		int panes_t = connect_t ? this.getPaneWidthOnSide(access, u.x, u.y, u.z, side) : 0;
		connect_r = connect_r ? this.getPaneWidthOnSide(access, r.x, r.y, r.z, side) != 0 : false;
		int panes_b = connect_b ? this.getPaneWidthOnSide(access, d.x, d.y, d.z, side) : 0;
		connect_l = connect_l ? this.getPaneWidthOnSide(access, l.x, l.y, l.z, side) != 0 : false;
		int panes_tl = connect_tl ? this.getPaneWidthOnSide(access, ul.x, ul.y, ul.z, side) : 0;
		int panes_tr = connect_tr ? this.getPaneWidthOnSide(access, ur.x, ur.y, ur.z, side) : 0;
		int panes_bl = connect_bl ? this.getPaneWidthOnSide(access, dl.x, dl.y, dl.z, side) : 0;
		int panes_br = connect_br ? this.getPaneWidthOnSide(access, dr.x, dr.y, dr.z, side) : 0;
		boolean pane_l = (side == 2 ? this.maxX == 1.0F : this.minZ == 0.0F);
		boolean pane_r = (side == 2 ? this.minX == 0.0F : this.maxZ == 1.0F);

		// disconnect T-panes
		if(pane_l && pane_r)
			if((side == 4 ? this.maxX == 1.0F : this.minZ == 0.0F) || (side == 4 ? this.minX == 0.0F : this.maxZ == 1.0F))
				pane_l = pane_r = false;

		// left pane
		int textureL = 0;
		if((panes_t&2) != 2) textureL |= 1<<0;								// T
			else if(connect_l && (panes_tl&1) != 1) textureL |= 2<<0;		// tl
		if(!pane_r) textureL |= 1<<2;										// R
			else if((panes_t&2) == 2 && (panes_t&1) != 1) textureL |= 2<<2;	// tr
		if((panes_b&2) != 2) textureL |= 1<<4;								// B
			else if(pane_r && (panes_b&1) != 1) textureL |= 2<<4;			// br
		if(!connect_l) textureL |= 1<<6;									// L
			else if((panes_b&2) == 2 && (panes_bl&1) != 1) textureL |= 2<<6;// bl
		halves[0] = this.getBlockIcons(blockColor)[textureL];

		// right pane
		int textureR = 0;
		if((panes_t&1) != 1) textureR |= 1<<0;								// T
			else if(pane_l && (panes_t&2) != 2) textureR |= 2<<0;			// tl
		if(!connect_r) textureR |= 1<<2;									// R
			else if((panes_t&1) == 1 && (panes_tr&2) != 2) textureR |= 2<<2;// tr
		if((panes_b&1) != 1) textureR |= 1<<4;								// B
			else if(connect_r && (panes_br&2) != 2) textureR |= 2<<4;		// br
		if(!pane_l) textureR |= 1<<6;										// L
			else if((panes_b&1) == 1 && (panes_b&2) != 2) textureR |= 2<<6;	// bl
		halves[1] = this.getBlockIcons(blockColor)[textureR];

		return halves;
	}

	//////////

	@SideOnly(Side.CLIENT)
	public boolean canConnectSideTexturesHorizontal(Block block, int metadata, int side, BlockCoord neighbor){
		if(!this.hasConnectedTextures) return false; // no connected textures

		// connect to same color of glass pane on sides
		if(neighbor.getBlock() != block || neighbor.getBlockMetadata() != metadata) return false;

		// don't connect to vertical panes
		if(this.canPaneConnectTo(neighbor.access, neighbor.x, neighbor.y, neighbor.z, ForgeDirection.UP)
		|| this.canPaneConnectTo(neighbor.access, neighbor.x, neighbor.y, neighbor.z, ForgeDirection.DOWN))
			return false;

		return true;
	}

	@SideOnly(Side.CLIENT)
	public boolean canConnectCornerTexturesHorizontal(Block block, int metadata, int side, BlockCoord diagonal){
		if(!this.hasConnectedTextures) return false; // no connected textures

		// connect to same color glass pane (corners)
		if(diagonal.getBlock() != block || diagonal.getBlockMetadata() != metadata) return false;

		// don't connect to vertical panes
		if(this.canPaneConnectTo(diagonal.access, diagonal.x, diagonal.y, diagonal.z, ForgeDirection.UP)
		|| this.canPaneConnectTo(diagonal.access, diagonal.x, diagonal.y, diagonal.z, ForgeDirection.DOWN))
			return false;

		return true;
	}

	@SideOnly(Side.CLIENT)
	public MC_ICON getBlockTextureHorizontal(IBlockAccess access, int x, int y, int z, int side){
		BlockCoord coord = new BlockCoord(access, x, y, z);
		Block block = coord.getBlock();
		int blockColor = coord.getBlockMetadata();

		if(!this.hasConnectedTextures) return this.getBlockIcons(blockColor)[0]; // no connected textures

		BlockCoord u = (new BlockCoord(coord)).translateToSideAtDirection(side, BlockCoord.UP);
		BlockCoord d = (new BlockCoord(coord)).translateToSideAtDirection(side, BlockCoord.DOWN);
		BlockCoord l = (new BlockCoord(coord)).translateToSideAtDirection(side, BlockCoord.LEFT);
		BlockCoord r = (new BlockCoord(coord)).translateToSideAtDirection(side, BlockCoord.RIGHT);
		BlockCoord ul = (new BlockCoord(coord)).translateToDiagonalAtDirection(side, BlockCoord.UP, BlockCoord.LEFT);
		BlockCoord ur = (new BlockCoord(coord)).translateToDiagonalAtDirection(side, BlockCoord.UP, BlockCoord.RIGHT);
		BlockCoord dl = (new BlockCoord(coord)).translateToDiagonalAtDirection(side, BlockCoord.DOWN, BlockCoord.LEFT);
		BlockCoord dr = (new BlockCoord(coord)).translateToDiagonalAtDirection(side, BlockCoord.DOWN, BlockCoord.RIGHT);
		boolean connect_t = this.canConnectSideTexturesHorizontal(block, blockColor, side, u);
		boolean connect_r = this.canConnectSideTexturesHorizontal(block, blockColor, side, r);
		boolean connect_b = this.canConnectSideTexturesHorizontal(block, blockColor, side, d);
		boolean connect_l = this.canConnectSideTexturesHorizontal(block, blockColor, side, l);
		boolean connect_tl = this.canConnectCornerTexturesHorizontal(block, blockColor, side, ul);
		boolean connect_tr = this.canConnectCornerTexturesHorizontal(block, blockColor, side, ur);
		boolean connect_bl = this.canConnectCornerTexturesHorizontal(block, blockColor, side, dl);
		boolean connect_br = this.canConnectCornerTexturesHorizontal(block, blockColor, side, dr);

		int texture = 0;
		if(!connect_t) texture |= 1<<0;							// T
			else if(connect_l && !connect_tl) texture |= 2<<0;	// tl
		if(!connect_r) texture |= 1<<2;							// R
			else if(connect_t && !connect_tr) texture |= 2<<2;	// tr
		if(!connect_b) texture |= 1<<4;							// B
			else if(connect_r && !connect_br) texture |= 2<<4;	// br
		if(!connect_l) texture |= 1<<6;							// L
			else if(connect_b && !connect_bl) texture |= 2<<6;	// bl

		return this.getBlockIcons(blockColor)[texture];
	}

	//////////

	// modified from net.minecraft.block.BlockPane
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity){
		final float SIDE_N = 0.5F;// - BlockColoredPane.SIDE_WIDTH;
		final float SIDE_P = 0.5F;// + BlockColoredPane.SIDE_WIDTH;
		boolean connect_d = this.canPaneConnectTo(world, x, y, z, ForgeDirection.DOWN);
		boolean connect_u = this.canPaneConnectTo(world, x, y, z, ForgeDirection.UP);

		if(!connect_d && !connect_u){
			this.setBlockBounds(0.0F, SIDE_N, 0.0F, 1.0F, SIDE_P, 1.0F);
			super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
		} else {
			boolean connect_n = this.canPaneConnectTo(world, x, y, z, ForgeDirection.NORTH);
			boolean connect_s = this.canPaneConnectTo(world, x, y, z, ForgeDirection.SOUTH);
			boolean connect_w = this.canPaneConnectTo(world, x, y, z, ForgeDirection.WEST);
			boolean connect_e = this.canPaneConnectTo(world, x, y, z, ForgeDirection.EAST);

			if((!connect_w || !connect_e) && (connect_w || connect_e || connect_n || connect_s)){
				if(connect_w && !connect_e){
					this.setBlockBounds(0.0F, 0.0F, SIDE_N, 0.5F, 1.0F, SIDE_P);
					super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
				} else if(!connect_w && connect_e){
					this.setBlockBounds(0.5F, 0.0F, SIDE_N, 1.0F, 1.0F, SIDE_P);
					super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
				}
			} else {
				this.setBlockBounds(0.0F, 0.0F, SIDE_N, 1.0F, 1.0F, SIDE_P);
				super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
			}

			if((!connect_n || !connect_s) && (connect_w || connect_e || connect_n || connect_s)){
				if(connect_n && !connect_s){
					this.setBlockBounds(SIDE_N, 0.0F, 0.0F, SIDE_P, 1.0F, 0.5F);
					super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
				} else if(!connect_n && connect_s){
					this.setBlockBounds(SIDE_N, 0.0F, 0.5F, SIDE_P, 1.0F, 1.0F);
					super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
				}
			} else {
				this.setBlockBounds(SIDE_N, 0.0F, 0.0F, SIDE_P, 1.0F, 1.0F);
				super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
			}
		}
	}

	// modified from net.minecraft.block.BlockPane
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z){
		final float SIDE_N = 0.5F - BlockColoredPane.SIDE_WIDTH_BOUNDS;
		final float SIDE_P = 0.5F + BlockColoredPane.SIDE_WIDTH_BOUNDS;
		boolean connect_d = this.canPaneConnectTo(access, x, y, z, ForgeDirection.DOWN);
		boolean connect_u = this.canPaneConnectTo(access, x, y, z, ForgeDirection.UP);

		if(!connect_d && !connect_u){
			this.setBlockBounds(0.0F, SIDE_N, 0.0F, 1.0F, SIDE_P, 1.0F);
		} else {
			float minX = SIDE_N, maxX = SIDE_P;
			float minZ = SIDE_N, maxZ = SIDE_P;
			boolean connect_n = this.canPaneConnectTo(access, x, y, z, ForgeDirection.NORTH);
			boolean connect_s = this.canPaneConnectTo(access, x, y, z, ForgeDirection.SOUTH);
			boolean connect_w = this.canPaneConnectTo(access, x, y, z, ForgeDirection.WEST);
			boolean connect_e = this.canPaneConnectTo(access, x, y, z, ForgeDirection.EAST);

			if((!connect_w || !connect_e) && (connect_w || connect_e || connect_n || connect_s)){
				if(connect_w && !connect_e)
					minX = 0.0F;
				else if(!connect_w && connect_e)
					maxX = 1.0F;
			} else {
				minX = 0.0F;
				maxX = 1.0F;
			}

			if((!connect_n || !connect_s) && (connect_w || connect_e || connect_n || connect_s)){
				if(connect_n && !connect_s)
					minZ = 0.0F;
				else if(!connect_n && connect_s)
					maxZ = 1.0F;
			} else {
				minZ = 0.0F;
				maxZ = 1.0F;
			}

			this.setBlockBounds(minX, 0.0F, minZ, maxX, 1.0F, maxZ);
		}
	}

	private boolean canPaneConnectTo(IBlockAccess access, int x, int y, int z, ForgeDirection direction){
		Block neighbor_block = BlockCoord.getBlock(access, x+direction.offsetX, y+direction.offsetY, z+direction.offsetZ);
		if(neighbor_block == null) return false;

		if(access.isBlockSolidOnSide(x+direction.offsetX, y+direction.offsetY, z+direction.offsetZ, direction.getOpposite(), false) || Block.opaqueCubeLookup[GET_BLOCK_ID(neighbor_block)])
			return true;
		if(!(neighbor_block instanceof BlockColoredPane)) return false;

		// vertical panes can't connect to horizontal panes
		if(direction != ForgeDirection.DOWN && direction != ForgeDirection.UP)
			if(this.canPaneConnectTo(access, x, y, z, ForgeDirection.UP) || this.canPaneConnectTo(access, x, y, z, ForgeDirection.DOWN))
				if(!this.canPaneConnectTo(access, x+direction.offsetX, y, z+direction.offsetZ, ForgeDirection.UP)
				&& !this.canPaneConnectTo(access, x+direction.offsetX, y, z+direction.offsetZ, ForgeDirection.DOWN))
					return false;
		return true;
	}

	//////////

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, World world, int x, int y, int z){
		return false;
	}
}
