package com.qzx.au.extras;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeDirection;

import java.util.Random;

import com.qzx.au.core.TileEntityAU;

public class BlockEnderCube extends Block implements ITileEntityProvider {
	public BlockEnderCube(int id, String name, String readableName){
		super(id, Material.rock);
		this.setUnlocalizedName(name);
		GameRegistry.registerBlock(this, name);
		LanguageRegistry.addName(this, readableName);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister){
		this.blockIcon = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("tile.au.", ""));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int color){
		return this.blockIcon;
	}

	@Override
	public boolean isOpaqueCube(){
		return false; // block camo
	}

	@Override
	public boolean renderAsNormalBlock(){
		return false; // block camo
	}

	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side){
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass(){
		return 1; // block camo
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canRenderInPass(int pass){
		return (pass == 1); // block camo
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess access, int x, int y, int z, int side){
		Icon icon = null;
		TileEntity tileEntity = (TileEntity)access.getBlockTileEntity(x, y, z);
		if(tileEntity instanceof TileEntityEnderCube)
			icon = ((TileEntityAU)tileEntity).getCamoIcon(side);
		return (icon == null ? this.blockIcon : icon);
	}

	//////////

	public TileEntity createNewTileEntity(World world){
		return new TileEntityEnderCube();
	}
	@Override
	public TileEntity createTileEntity(World world, int metadata){
		return new TileEntityEnderCube();
	}

	//////////

	@Override
	public boolean onBlockEventReceived(World world, int x, int y, int z, int eventID, int value){
		TileEntity tileEntity = (TileEntity)world.getBlockTileEntity(x, y, z);
		if(tileEntity == null) return false;
		if(!tileEntity.receiveClientEvent(eventID, value)) return false;
		world.markBlockForUpdate(x, y, z);
		return true;
	}

//	@Override
//	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
//		if(!world.isRemote){
//Debug.print("open ender block UI");
//		}
//		return true;
//	}

	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity entity){
		if(!world.isRemote){
if(entity instanceof EntityPlayer) Debug.print("player on ender block");
else Debug.print("entity on ender block");
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int side, int metadata){
		world.removeBlockTileEntity(x, y, z);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity, ItemStack itemstack){
		if(world.isRemote)
			TileEntityEnderCube.spawnParticles(world, x, y, z, new Random(), 2.0F, false);
	}
	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int par5){
		if(world.isRemote)
			TileEntityEnderCube.spawnParticles(world, x, y, z, new Random(), 2.0F, false);
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, World world, int x, int y, int z){
		return false;
	}

	@Override
	public int getMobilityFlag(){
		return 2; // prevent moving with pistons
	}
}
