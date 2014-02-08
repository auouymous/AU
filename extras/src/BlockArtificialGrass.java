package com.qzx.au.extras;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockArtificialGrass extends BlockGrass {
	// blockIcon = grass top

	private final int[] grassShades = {
		0x5C694E, // 1	black		swamp				dark green
		0x79C05A, // 2	red			forest				green
		0x2d6f1e, // 3	green		jungle [---]		bright green [darkest]
		0x8f893f, // 4	brown		desert [-]			yellow [darker]

		0x60876f, // 5	blue		taiga [-]
		0x6a8a54, // 6	purple		ocean/river [-]		green
		0x8AB689, // 7	cyan		x-hills				blue-green
		0x3a8d26, // 8	l-gray		jungle [--]			bright green [darker]

		0x738361, // 9	d-gray		swamp [+]			dark green [brigher]
		0x91BD59, // 10	pink		beach/plains		green
		0x53CA37, // 11	lime		jungle				bright green
		0xdbd261, // 12	yellow		desert [+]			yellow [brighter]

		0x81B495, // 13	l-blue		taiga				blue-green
		0x8EB971, // 14	magenta		ocean/river			green
		0xBFB755, // 15	orange		desert				yellow
		0x46ab2e, // 16	white		jungle [-]			bright green [dark]
	};

	public BlockArtificialGrass(int id, String name, String readableName){
		super(id);
		this.setUnlocalizedName(name);
		GameRegistry.registerBlock(this, ItemBlockArtificialGrass.class, name);
		for(int s = 0; s < 16; s++){
			ItemStack shadedStack = new ItemStack(this, 1, s);
			LanguageRegistry.addName(shadedStack, readableName+(s+1));
		}

		// won't spread to dirt
		this.setTickRandomly(false);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister){
		this.blockIcon = iconRegister.registerIcon("grass_top");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int shade){
		return this.blockIcon;
	}

	//////////

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int shade){
		return this.grassShades[shade];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess access, int x, int y, int z){
		int shade = access.getBlockMetadata(x, y, z);
		return this.grassShades[shade];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess access, int x, int y, int z, int side){
		return this.blockIcon;
	}

	//////////

	@Override
	public void updateTick(World world, int x, int y, int z, Random random){
		// won't spread to dirt
	}

	@Override
	public int idDropped(int shade, Random random, int unknown){
		return this.blockID;
	}
	@Override
	public int damageDropped(int shade){
		return shade;
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata){
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int unknown, CreativeTabs tab, List subItems){
		for(int s = 0; s < 16; s++)
			subItems.add(new ItemStack(this, 1, s));
	}
}
