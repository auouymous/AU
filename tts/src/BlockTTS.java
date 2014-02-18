package com.qzx.au.tts;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.qzx.au.core.TileEntityAU;

public class BlockTTS extends Block implements ITileEntityProvider {
	public BlockTTS(int id, String name){
		super(id, Material.iron);
		this.setUnlocalizedName(name);
		GameRegistry.registerBlock(this, name);

		this.disableStats();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister){
		this.blockIcon = iconRegister.registerIcon("au_tts:"+this.getUnlocalizedName().replace("tile.au.", ""));
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
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess access, int x, int y, int z, int side){
		Icon icon = null;
		TileEntity tileEntity = (TileEntity)access.getBlockTileEntity(x, y, z);
		if(tileEntity instanceof TileEntityTTS)
			icon = ((TileEntityAU)tileEntity).getCamoIcon(side); // block camo
		return (icon == null ? this.blockIcon : icon);
	}

	//////////

	public TileEntity createNewTileEntity(World world){
		return null;
	}
	@Override
	public TileEntity createTileEntity(World world, int metadata){
		return new TileEntityTTS();
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

		TileEntity tileEntity = (TileEntity)world.getBlockTileEntity(x, y, z);
		if(tileEntity instanceof TileEntityTTS){
			TileEntityTTS te = (TileEntityTTS)tileEntity;
			boolean is_powered = world.isBlockIndirectlyGettingPowered(x, y, z);
			boolean was_powered = te.isPowered();

			if(is_powered && !was_powered){
				te.sendSoundToClients();
				te.setPowered(true);
			} else if(!is_powered && was_powered)
				te.setPowered(false);
		}
	}

	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int par5){
		if(world.isRemote) return;

		// set initial powered state
		TileEntity tileEntity = (TileEntity)world.getBlockTileEntity(x, y, z);
		if(tileEntity instanceof TileEntityTTS)
			if(world.isBlockIndirectlyGettingPowered(x, y, z))
				((TileEntityTTS)tileEntity).setPowered(true);
	}

	//////////

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
		if(player.isSneaking()){
			// shift right click opens GUI
			if(world.isRemote) return true;
			player.openGui(THIS_MOD.instance, Guis.TILE_GUI, world, x, y, z);
		} else if(world.isRemote){
			// right click plays sound only on this client

// TODO: chat message "only you can hear this"

			TileEntity tileEntity = (TileEntity)world.getBlockTileEntity(x, y, z);
			if(tileEntity instanceof TileEntityTTS)
				((TileEntityTTS)tileEntity).playSoundOnClient(player);
		}
		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int side, int metadata){
		world.removeBlockTileEntity(x, y, z);
	}
}
