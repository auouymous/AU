package com.qzx.au.extras;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

IMPORT_FORGE_DIRECTION

import java.util.List;
import java.util.Random;

public class BlockColoredHalfSlab extends Block {
	@SideOnly(Side.CLIENT)
	private MC_ICON[] blockIconTop;
	@SideOnly(Side.CLIENT)
	private MC_ICON[] blockIconSide;

	private final boolean useQuarterTexture;
	private boolean isSmoothSlab;
	private boolean isDoubleSlab;
	private boolean isLowerHalf;
	private Block fullBlock;
	private Block lowerBlock;
	private Block upperBlock;

	public BlockColoredHalfSlab(int id, String name, Class<? extends ItemBlock> itemblockclass, Material material, Block block, boolean useQuarterTexture, boolean full_block){
		super(id, (block == null ? material : block.blockMaterial));
		this.setUnlocalizedName(name);
		if(itemblockclass != null)
			GameRegistry.registerBlock(this, itemblockclass, name);
		else
			GameRegistry.registerBlock(this, name);

		this.useQuarterTexture = useQuarterTexture;
		this.isSmoothSlab = (full_block && block != null);
		this.isDoubleSlab = false;
		this.isLowerHalf = false;
		this.fullBlock = this;
		this.lowerBlock = this;
		this.upperBlock = this;

		if(block != null){
			this.setHardness(block.blockHardness);
			this.setResistance(block.blockResistance / 3.0F);
			this.setStepSound(block.stepSound);
		}

		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, (full_block ? 1.0F : 0.5F), 1.0F);
	}
	public BlockColoredHalfSlab(int id, String name, Class<? extends ItemBlock> itemblockclass, Material material){
		// double slabs
		this(id, name, itemblockclass, material, null, false, true);
	}
	public BlockColoredHalfSlab(int id, String name, Class<? extends ItemBlock> itemblockclass, Block block){
		// smooth slabs (double)
		this(id, name, itemblockclass, null, block, false, true);
	}
	public BlockColoredHalfSlab(int id, String name, Class<? extends ItemBlock> itemblockclass, Block block, boolean useQuarterTexture){
		// half slabs
		this(id, name, itemblockclass, null, block, useQuarterTexture, false);

		// hack to fix lighting glitch
		this.setLightOpacity(0); // slabs allow light sources to pass through them
	}

	public void setFullBlock(Block lowerBlock){
		this.isDoubleSlab = true;
		this.lowerBlock = lowerBlock;
	}
	public void setSmoothBlock(Block fullBlock){
		this.isDoubleSlab = true;
		this.fullBlock = fullBlock;
	}
	public void setLowerBlock(Block fullBlock, Block lowerBlock){
		this.isLowerHalf = false;
		this.fullBlock = fullBlock;
		this.lowerBlock = lowerBlock;
	}
	public void setUpperBlock(Block fullBlock, Block upperBlock){
		this.isLowerHalf = true;
		this.fullBlock = fullBlock;
		this.upperBlock = upperBlock;
	}

	public Block getFullBlock(){
		return this.fullBlock;
	}
	public Block getLowerBlock(){
		return this.lowerBlock;
	}
	public Block getUpperBlock(){
		return this.upperBlock;
	}

	public boolean isDoubleSlab(){
		return this.isDoubleSlab;
	}
	public boolean isLowerSlab(){
		return (!this.isDoubleSlab && this.isLowerHalf);
	}
	public boolean isUpperSlab(){
		return (!this.isDoubleSlab && !this.isLowerHalf);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(MC_ICON_REGISTER iconRegister){
		if(this.isDoubleSlab && !this.isSmoothSlab){
			this.blockIconTop = new MC_ICON[16];
			this.blockIconSide = new MC_ICON[16];
			for(int c = 0; c < 16; c++){
				this.blockIconTop[c] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("tile.au.", "")+"-top"+c);
				this.blockIconSide[c] = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("tile.au.", "")+"-side"+c);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public MC_ICON getIcon(int side, int color){
		if(!this.isDoubleSlab)
			return this.fullBlock.getIcon(side, color);
		if(this.isSmoothSlab)
			return this.fullBlock.getIcon(1, color);
		return (side == 0 || side == 1 ? this.blockIconTop[color] : this.blockIconSide[color]);
	}

	@Override
	public boolean isOpaqueCube(){
		return this.isDoubleSlab;
	}

	@Override
	public boolean renderAsNormalBlock(){
		return this.isDoubleSlab;
	}

	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side){
		return this.isDoubleSlab || (this.isLowerHalf && side == ForgeDirection.DOWN) || (!this.isLowerHalf && side == ForgeDirection.UP);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType(){
		return (!this.isDoubleSlab && this.useQuarterTexture ? ClientProxy.slabRenderType : 0);
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
		if(this.isDoubleSlab) return super.getRenderColor(color);
		return  this.fullBlock.getRenderColor(color);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess access, int x, int y, int z){
		if(this.isDoubleSlab) return super.colorMultiplier(access, x, y, z);
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
		if(this.isDoubleSlab)
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		else if(this.isLowerHalf)
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		else
			this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	//////////

	@Override
	public int quantityDropped(Random random){
		return this.isDoubleSlab && !this.isSmoothSlab ? 2 : 1;
	}


	@Override
	public int idDropped(int color, Random random, int unknown){
		// always drop bottom slab, unless smooth
		return (this.lowerBlock == null ? this.blockID : this.lowerBlock.blockID);
	}

	@Override
	public int idPicked(World world, int x, int y, int z){
		// always pick bottom slab
		return (this.isDoubleSlab ? this.blockID : this.lowerBlock.blockID);
	}

	@Override
	public int damageDropped(int color){
		return color;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int unknown, CreativeTabs tab, List subItems){
		if(this.isDoubleSlab || this.isLowerHalf)
			for(int c = 0; c < 16; c++)
				subItems.add(new ItemStack(this, 1, c));
	}

	//////////

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, World world, int x, int y, int z){
		return this.isDoubleSlab || !this.isLowerHalf;
	}
}
