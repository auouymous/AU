package com.qzx.au.extras;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.qzx.au.core.Color;

public class ItemBlockColoredSlab extends ItemBlockColored {
	public ItemBlockColoredSlab(int id){
		super(id);
		// extend and set unlocalized name
	}

	@Override
	public boolean placeBlockAt(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata){
		Block block = this.block;
		if(side == 0 || (side != 1 && hitY > 0.5F))
			block = ((BlockColoredSlab)block).getUpperBlock(); // place upper slab

		if(!world.setBlock(x, y, z, block.blockID, metadata, 3))
			return false;

		if(world.getBlockId(x, y, z) == block.blockID){
			block.onBlockPlacedBy(world, x, y, z, player, itemstack);
			block.onPostBlockPlaced(world, x, y, z, metadata);
		}

		return true;
	}
}
