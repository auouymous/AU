package com.qzx.au.extras;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.qzx.au.core.BlockCoord;
import com.qzx.au.core.Color;

public class ItemBlockColoredHalfSlab extends ItemBlockColored {
	public ItemBlockColoredHalfSlab(int id){
		super(id);
		// extend and set unlocalized name
	}

	#define IS_DOUBLE(block) ((BlockColoredHalfSlab)block).isDoubleSlab()
	#define IS_LOWER(block) ((BlockColoredHalfSlab)block).isLowerSlab()
	#define IS_UPPER(block) ((BlockColoredHalfSlab)block).isUpperSlab()
	#define GET_DOUBLE() ((BlockColoredHalfSlab)this.block).getFullBlock()

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ){
		if(!((BlockColoredHalfSlab)this.block).isLowerSlab())
			return super.onItemUse(itemstack, player, world, x, y, z, side, hitX, hitY, hitZ);
		if(itemstack.stackSize == 0)
			return false;
		if(!player.canPlayerEdit(x, y, z, side, itemstack))
			return false;

		int color = itemstack.getItemDamage();

		BlockCoord coord = new BlockCoord(world, x, y, z);
		Block block = coord.getBlock();
		if(block instanceof BlockColoredHalfSlab && !IS_DOUBLE(block) && ((side == 0 && IS_UPPER(block)) || (side == 1 && IS_LOWER(block)))){
			// attempt to place in upper or lower halves of target block
			if(world.getBlockMetadata(x, y, z) == color) block = GET_DOUBLE(); // place lower/upper slab and form full block
			else return false;
		} else {
			// get neighbor on target's side
			BlockCoord neighbor = coord.translateToSide(side);
			block = neighbor.getBlock();

			if(block instanceof BlockColoredHalfSlab && !IS_DOUBLE(block)){
				// attempt to place in upper or lower halves of neighbor block
				if(neighbor.getBlockMetadata() == color) block = GET_DOUBLE(); // place lower/upper slab and form full block
				else return false;
			} else if(block == null){
				if(side == 0 || (side != 1 && hitY > 0.5F))
					block = ((BlockColoredHalfSlab)this.block).getUpperBlock(); // place upper slab
				else
					block = this.block; // place lower slab
			} else
				return false;

			x = neighbor.x;
			y = neighbor.y;
			z = neighbor.z;
			if(!player.canPlayerEdit(x, y, z, side, itemstack))
				return false;
		}

		if(!world.checkNoEntityCollision(block.getCollisionBoundingBoxFromPool(world, x, y, z)))
			return false;
		if(!world.setBlock(x, y, z, block.blockID, color, 3))
			return false;

		world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F),
								block.stepSound.getPlaceSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
		itemstack.stackSize--;

		return true;
	}

	public boolean canPlaceItemBlockOnSide(World world, int x, int y, int z, int side, EntityPlayer player, ItemStack itemstack){
		BlockCoord coord = new BlockCoord(world, x, y, z);
		Block block = coord.getBlock();
		if(block instanceof BlockColoredHalfSlab && !IS_DOUBLE(block) && ((side == 0 && IS_UPPER(block)) || (side == 1 && IS_LOWER(block)))) return true;

		Block neighbor = coord.translateToSide(side).getBlock();
		if(neighbor instanceof BlockColoredHalfSlab && !IS_DOUBLE(neighbor)) return true;

		return super.canPlaceItemBlockOnSide(world, x, y, z, side, player, itemstack);
	}
}
