package com.qzx.au.extras;

import cpw.mods.fml.common.IWorldGenerator;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
//import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenFlowers implements IWorldGenerator {
	private int plantBlockId;

	public WorldGenFlowers(int id){
		this.plantBlockId = id;
	}

	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider){
		// convert chunk coords to block coords
		chunkX = chunkX<<4;
		chunkZ = chunkZ<<4;

		// 2 patches per chunk (vanilla flowers use 2)
		for(int i = 0; i < 2; i++){
			int x = chunkX + random.nextInt(16) + 8;
			int y = random.nextInt(128);
			int z = chunkZ + random.nextInt(16) + 8;
			int color = WorldGenFlowers.getBiomeColor(world, chunkX, chunkZ, random);

			// upto 32 flowers per patch (vanilla flowers use 64)
			for(int ii = 0; ii < 32; ++ii){
				int xx = x + random.nextInt(8) - random.nextInt(8);
				int yy = y + random.nextInt(4) - random.nextInt(4);
				int zz = z + random.nextInt(8) - random.nextInt(8);
				if(world.isAirBlock(xx, yy, zz) && (!world.provider.hasNoSky || yy < 127) && GET_BLOCK_BY_ID(this.plantBlockId).canBlockStay(world, xx, yy, zz))
					world.setBlock(xx, yy, zz, this.plantBlockId, color, 2);
			}
		}
	}

	//////////

	public static final int[] FLOWERS_NETHER	= {0};				// black
	public static final int[] FLOWERS_END		= {0,5};			// black, purple

	public static final int[] FLOWERS_15		= {0,8};			// black, dark gray					-- [nether], desert
	public static final int[] FLOWERS_12		= {2,3,14};			// green, brown, orange				-- jungle
	public static final int[] FLOWERS_09		= {5,13};			// purple, magenta					-- mushroom island
	public static final int[] FLOWERS_08		= {11,10,9,12};		// yellow, lime, pink, light blue	-- plains, swamp, beach
	public static final int[] FLOWERS_07		= {1,4,6};			// red, blue, cyan					-- forest
	public static final int[] FLOWERS_05		= {11,10,9};		// yellow, lime, pink				-- [end], ocean, river
	public static final int[] FLOWERS_02		= {1,4,6,7};		// red, blue, cyan, light gray		-- extreme hills
	public static final int[] FLOWERS_00		= {7,15};			// light gray, white				-- taiga, ice

	public static final int[] FLOWERS_ALL		= {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};

	public static int getBiomeColor(World world, int x, int z, Random random){
		int[] flowers = WorldGenFlowers.FLOWERS_ALL;
		Chunk chunk = world.getChunkFromBlockCoords(x, z);
		if(chunk != null){
			BiomeGenBase biome = chunk.getBiomeGenForWorldCoords(x & 15, z & 15, world.getWorldChunkManager());
			if(biome != null){
				int temp = (int)(biome.temperature * 10.0F);
				if(biome == BiomeGenBase.hell) flowers = WorldGenFlowers.FLOWERS_NETHER;
				else if(biome == BiomeGenBase.sky) flowers = WorldGenFlowers.FLOWERS_END;
				else if(temp >= 15) flowers = WorldGenFlowers.FLOWERS_15;
				else if(temp >= 12) flowers = WorldGenFlowers.FLOWERS_12;
				else if(temp >= 9) flowers = WorldGenFlowers.FLOWERS_09;
				else if(temp >= 8) flowers = WorldGenFlowers.FLOWERS_08;
				else if(temp >= 7) flowers = WorldGenFlowers.FLOWERS_07;
				else if(temp >= 5) flowers = WorldGenFlowers.FLOWERS_05;
				else if(temp >= 2) flowers = WorldGenFlowers.FLOWERS_02;
				else flowers = WorldGenFlowers.FLOWERS_00;
			}
		}
		return flowers[random.nextInt(flowers.length)];
	}
}
