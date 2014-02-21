package com.qzx.au.extras;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlockArtificialVine extends ItemBlock {
	protected Block block;

	public ItemBlockArtificialVine(int id){
		super(id);
		this.block = Block.blocksList[id + 256];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack itemstack, int par2){
		return this.block.getRenderColor(0);
	}

	@Override
	public boolean placeBlockAt(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata){
		Block block = this.block;
		if(world.getBlockId(x, y, z) == block.blockID){
			// put multiple vines in same block
			int current_metadata = world.getBlockMetadata(x, y, z);
			if(current_metadata != (current_metadata | metadata))
				world.setBlock(x, y, z, block.blockID, current_metadata | metadata, 3);
			return false;
		}

		if(!world.setBlock(x, y, z, block.blockID, metadata, 3))
			return false;

		if(world.getBlockId(x, y, z) == block.blockID){
			block.onBlockPlacedBy(world, x, y, z, player, itemstack);
			block.onPostBlockPlaced(world, x, y, z, metadata);
		}

		return true;
	}
}
