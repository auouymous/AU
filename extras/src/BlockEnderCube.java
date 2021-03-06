package com.qzx.au.extras;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

IMPORT_FORGE_DIRECTION

import java.util.Random;

import com.qzx.au.core.BlockCoord;
import com.qzx.au.core.Light;
import com.qzx.au.core.RenderUtils;
import com.qzx.au.core.TileEntityAU;

public class BlockEnderCube extends Block implements ITileEntityProvider {
	public static final int nrPortalParticles = 128;

	public BlockEnderCube(int id, String name){
		super(id, Material.rock);
		this.setUnlocalizedName(name);
		GameRegistry.registerBlock(this, name);

		this.disableStats();

		this.setLightValue(Light.level[15]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(MC_ICON_REGISTER iconRegister){
		this.blockIcon = iconRegister.registerIcon("au_extras:"+this.getUnlocalizedName().replace("tile.au.", ""));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public MC_ICON getIcon(int side, int color){
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
	public boolean canRenderInPass(int pass){
		return (pass == 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public MC_ICON getBlockTexture(IBlockAccess access, int x, int y, int z, int side){
		MC_ICON icon = null;
		TileEntity tileEntity = (TileEntity)BlockCoord.getTileEntity(access, x, y, z);
		if(tileEntity instanceof TileEntityEnderCube)
			icon = ((TileEntityAU)tileEntity).getCamoIcon(side); // block camo
		return (icon == null ? this.blockIcon : icon);
	}

	//////////

	public TileEntity createNewTileEntity(World world){
		return null;
	}
	@Override
	public TileEntity createTileEntity(World world, int metadata){
		return new TileEntityEnderCube();
	}

	@Override
	public int idPicked(World world, int x, int y, int z){
		return TileEntityAU.getCamoCloakID(this, world, x, y, z);
	}
	@Override
	public int getDamageValue(World world, int x, int y, int z){
		return TileEntityAU.getCamoCloakMeta(world, x, y, z);
	}

	//////////

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int neighborBlockID){
		if(world.isRemote) return;

		TileEntity tileEntity = (TileEntity)BlockCoord.getTileEntity(world, x, y, z);
		if(tileEntity instanceof TileEntityEnderCube){
			TileEntityEnderCube te = (TileEntityEnderCube)tileEntity;
			boolean is_powered = world.isBlockIndirectlyGettingPowered(x, y, z);
			boolean was_powered = te.isPowered();

			if(is_powered && !was_powered){
				te.teleportAll();
				te.setPowered(true);
			} else if(!is_powered && was_powered)
				te.setPowered(false);
		}
	}

	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int par5){
		if(world.isRemote) return;

		// set initial powered state
		TileEntity tileEntity = (TileEntity)BlockCoord.getTileEntity(world, x, y, z);
		if(tileEntity instanceof TileEntityEnderCube)
			if(world.isBlockIndirectlyGettingPowered(x, y, z))
				((TileEntityEnderCube)tileEntity).setPowered(true);
	}

	//////////

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
		if(player.isSneaking()) return false;
		if(world.isRemote) return true;

		player.openGui(THIS_MOD.instance, Guis.TILE_GUI, world, x, y, z);
		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int side, int metadata){
		world.removeBlockTileEntity(x, y, z);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack){
		if(world.isRemote)
			RenderUtils.spawnParticles(world, (float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F, new Random(), 2.0F, 2.0F, 2.0F, BlockEnderCube.nrPortalParticles, "portal", 0.0F, 0.0F, 0.0F);

		else {
			TileEntity tileEntity = (TileEntity)BlockCoord.getTileEntity(world, x, y, z);
			if(tileEntity instanceof TileEntityEnderCube)
				((TileEntityEnderCube)tileEntity).setPlayerControlWhitelist(entity.getEntityName(), true);
		}
	}
	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int par5){
		if(world.isRemote)
			RenderUtils.spawnParticles(world, (float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F, new Random(), 2.0F, 2.0F, 2.0F, BlockEnderCube.nrPortalParticles, "portal", 0.0F, 0.0F, 0.0F);
	}

	@Override
	public int getMobilityFlag(){
		return 2; // prevent moving with pistons
	}
}
