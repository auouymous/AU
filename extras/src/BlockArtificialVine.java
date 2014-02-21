package com.qzx.au.extras;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockArtificialVine extends BlockVine {
	// blockIcon = vine

	private final int[] vineShades = {
		0x496137, // 1	black		swamp				dark green
		0x59AE30, // 2	red			forest				green
		0x29BC05, // 3	green		jungle				bright green
		0x827b1f, // 4	brown		desert [-]			yellow [darker]					75%

		0x4a7959, // 5	blue		taiga [-]											75%
		0x8dd060, // 6	purple		ocean/river [+]		green							125%
		0x6DA36B, // 7	cyan		x-hills				blue-green
		0x1e8d03, // 8	l-gray		jungle [-]			bright green [darker]			75%

		0x5b7944, // 9	d-gray		swamp [+]			dark green [brigher]			125%
		0x77AB2F, // 10	pink		beach/plains		green
		0x33eb06, // 11	lime		jungle [+]			bright green [brighter]			125%
		0xc8bc30, // 12	yellow		desert [+]			yellow [brighter]				115%

		0x63A277, // 13	l-blue		taiga				blue-green
		0x71A74D, // 14	magenta		ocean/river			green
		0xAEA42A, // 15	orange		desert				yellow
		0xe2d536, // 16	white		desert [++]			yellow [brightest]				130%
	};
	private int shade;

	public BlockArtificialVine(int id, String name, int shade){
		super(id);
		this.shade = shade;
		this.setUnlocalizedName(name);
		GameRegistry.registerBlock(this, ItemBlockArtificialVine.class, name);

		// won't spread
		this.setTickRandomly(false);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister){
		this.blockIcon = iconRegister.registerIcon("vine");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata){
		return this.blockIcon;
	}

	//////////

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int metadata){
		return this.vineShades[this.shade];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess access, int x, int y, int z){
		return this.vineShades[this.shade];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess access, int x, int y, int z, int side){
		return this.blockIcon;
	}

	//////////

	@Override
	public void updateTick(World world, int x, int y, int z, Random random){
		// won't spread
	}
}
