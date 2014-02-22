package com.qzx.au.extras;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

IMPORT_BLOCKS
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockNetherrack;
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;
import static net.minecraftforge.common.EnumPlantType.*;

import java.util.Random;

import com.qzx.au.core.ItemUtils;

public class BlockFlower extends BlockColored implements IPlantable {
	@SideOnly(Side.CLIENT)
	private Icon[] flowerIcons;

	public BlockFlower(int id, String name){
		super(id, name, ItemBlockFlower.class, Material.plants);
		this.setTickRandomly(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister){
		this.flowerIcons = new Icon[16];
		for(int c = 0; c < 16; c++)
			this.flowerIcons[c] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("tile.au.", "")+c);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int color){
		return this.flowerIcons[color];
	}

	@SideOnly(Side.CLIENT)
	public Icon getItemIcon(int color){
		return this.flowerIcons[color];
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
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType(){
		return ClientProxy.flowerRenderType;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canRenderInPass(int pass){
		return (pass == 0);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z){
		int below = access.getBlockId(x, y - 1, z);
		float offset = (below == MC_BLOCK.tilledField.blockID ? BlockFlowerSeed.y_offset : 0.0F);
		this.setBlockBounds(0.3F, 0.0F-offset, 0.3F, 0.7F, 0.7F-offset, 0.7F);
	}

	//////////

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z){
		return super.canPlaceBlockAt(world, x, y, z) && world.getBlockId(x, y - 1, z) != MC_BLOCK.tilledField.blockID && this.canBlockStay(world, x, y, z);
	}

	private boolean canThisPlantGrowOnThisBlockID(int id){
		Block block = Block.blocksList[id];
		return block instanceof BlockDirt		// podzol is dirt:2
			|| block instanceof BlockGrass
			|| block instanceof BlockNetherrack
			|| block instanceof BlockMycelium
			|| block instanceof BlockSand		// red sand is sand:1
			|| block instanceof BlockSoulSand
			|| id == MC_BLOCK.whiteStone.blockID	// endstone
			|| id == THIS_MOD.blockSand.blockID		// colored sand
			;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int side){
		super.onNeighborBlockChange(world, x, y, z, side);
		this.checkFlowerChange(world, x, y, z);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random){
		this.checkFlowerChange(world, x, y, z);
	}

	private void checkFlowerChange(World world, int x, int y, int z){
		if(!this.canBlockStay(world, x, y, z)){
			this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlockToAir(x, y, z);
		}
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z){
		if(world.getFullBlockLightValue(x, y, z) < 8 || !world.canBlockSeeTheSky(x, y, z)) return false;
		int below = world.getBlockId(x, y - 1, z);
		if(below == MC_BLOCK.tilledField.blockID) return true; // can't plant on farmland but will stay when converted from seed
		return this.canThisPlantGrowOnThisBlockID(below);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z){
		return null;
	}

	//////////

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
		if(!world.isRemote && player.getHeldItem().getItem() instanceof ItemShears){
			if(world.getBlockId(x, y - 1, z) == MC_BLOCK.tilledField.blockID){
				int color = world.getBlockMetadata(x, y, z);

				// revert to stage 0
				world.setBlock(x, y, z, THIS_MOD.blockFlowerSeed.blockID, 0, 2);

				// drop flower
				ItemUtils.dropItemAsEntity(world, x, y, z, new ItemStack(THIS_MOD.blockFlower, 1, color));
			}
		}
		return false;
	}

	//////////

	@Override
	public EnumPlantType getPlantType(World world, int x, int y, int z){
		return Plains;
	}

	@Override
	public int getPlantID(World world, int x, int y, int z){
		return blockID;
	}

	@Override
	public int getPlantMetadata(World world, int x, int y, int z){
		return world.getBlockMetadata(x, y, z);
	}
}
