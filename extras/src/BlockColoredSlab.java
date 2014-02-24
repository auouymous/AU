package com.qzx.au.extras;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeDirection;

import java.util.List;
import java.util.Random;

public class BlockColoredSlab extends Block {
	private final boolean useQuarterTexture;
	private boolean isLowerHalf;
	private Block fullBlock;
	private Block lowerBlock;
	private Block upperBlock;

	public BlockColoredSlab(int id, String name, Class<? extends ItemBlock> itemblockclass, Block block, boolean useQuarterTexture){
		super(id, block.blockMaterial);
		this.setUnlocalizedName(name);
		if(itemblockclass != null)
			GameRegistry.registerBlock(this, itemblockclass, name);
		else
			GameRegistry.registerBlock(this, name);

		this.useQuarterTexture = useQuarterTexture;
		this.isLowerHalf = false;
		this.fullBlock = block;
		this.lowerBlock = this;
		this.upperBlock = this;

		this.setHardness(block.blockHardness);
		this.setResistance(block.blockResistance / 3.0F);
		this.setStepSound(block.stepSound);

		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);

		// hack to fix lighting glitch
		this.setLightOpacity(0); // slabs allow light sources to pass through them
	}

	public void setLowerBlock(Block lowerBlock){
		this.isLowerHalf = false;
		this.lowerBlock = lowerBlock;
	}
	public void setUpperBlock(Block upperBlock){
		this.isLowerHalf = true;
		this.upperBlock = upperBlock;
	}

	public Block getUpperBlock(){
		return this.upperBlock;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister){
		// suppress log errors for missing textures
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int color){
		return this.fullBlock.getIcon(side, color);
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
		return (this.isLowerHalf && side == ForgeDirection.DOWN) || (!this.isLowerHalf && side == ForgeDirection.UP);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType(){
		return (this.useQuarterTexture ? ClientProxy.slabRenderType : 0);
	}

/*
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
*/

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int color){
		// artificial grass
		return this.fullBlock.getRenderColor(color);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess access, int x, int y, int z){
		// artificial grass
		int color = access.getBlockMetadata(x, y, z);
		return this.fullBlock.getRenderColor(color);
	}

	//////////

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity){
		this.setBlockBoundsBasedOnState(world, x, y, z);
		super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z){
		if(this.isLowerHalf)
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		else
			this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	//////////

	@Override
	public int idDropped(int color, Random random, int unknown){
		// always drop bottom slab
		return this.lowerBlock.blockID;
	}

	@Override
	public int idPicked(World world, int x, int y, int z){
		// always pick bottom slab
		return this.lowerBlock.blockID;
	}

	@Override
	public int damageDropped(int color){
		return color;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int unknown, CreativeTabs tab, List subItems){
		if(this.isLowerHalf)
			for(int c = 0; c < 16; c++)
				subItems.add(new ItemStack(this, 1, c));
	}

	//////////

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, World world, int x, int y, int z){
		return false;
	}
}
