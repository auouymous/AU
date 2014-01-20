package com.qzx.au.core;

// no support for 147
#ifndef MC147

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import java.util.Random;

import org.lwjgl.opengl.GL11;

public class RenderUtils {
	@SideOnly(Side.CLIENT)
	public static void renderInventoryBlock(Block block, RenderBlocks renderer, Icon iconYNeg, Icon iconYPos, Icon iconZNeg, Icon iconZPos, Icon iconXNeg, Icon iconXPos){
		Tessellator tessellator = Tessellator.instance;

		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, iconYNeg);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, iconYPos);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, iconZNeg);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, iconZPos);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, iconXNeg);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, iconXPos);
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
	@SideOnly(Side.CLIENT)
	public static void renderInventoryBlock(Block block, RenderBlocks renderer, Icon icon){
		RenderUtils.renderInventoryBlock(block, renderer, icon, icon, icon, icon, icon, icon);
	}

	@SideOnly(Side.CLIENT)
	public static void renderWorldBlockSide(Block block, RenderBlocks renderer, int x, int y, int z, int side, float offset, Icon icon, int brightness, int colorMultiplier){
		Tessellator tessellator = Tessellator.instance;
		if(brightness > -1) tessellator.setBrightness(brightness);
		Color color = (new Color(colorMultiplier)).anaglyph();
		tessellator.setColorOpaque_F(color.r, color.g, color.b);

		if(side == 0){
			renderer.setRenderBounds(0.0F, 0.0F-offset, 0.0F, 1.0F, 1.0F, 1.0F);
			renderer.renderFaceYNeg(block, (double)x, (double)y, (double)z, icon);
		} else if(side == 1){
			renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F+offset, 1.0F);
			renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, icon);
		} else if(side == 2){
			renderer.setRenderBounds(0.0F, 0.0F, 0.0F-offset, 1.0F, 1.0F, 1.0F);
			renderer.renderFaceZNeg(block, (double)x, (double)y, (double)z, icon);
		} else if(side == 3){
			renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F+offset);
			renderer.renderFaceZPos(block, (double)x, (double)y, (double)z, icon);
		} else if(side == 4){
			renderer.setRenderBounds(0.0F-offset, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			renderer.renderFaceXNeg(block, (double)x, (double)y, (double)z, icon);
		} else if(side == 5){
			renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F+offset, 1.0F, 1.0F);
			renderer.renderFaceXPos(block, (double)x, (double)y, (double)z, icon);
		}
	}

	//////////

	public static void spawnParticles(World world, float x, float y, float z, Random random, int nr_particles, String type, float dx, float dy, float dz){
		double dxx = (double)dx/2.0D;
		double dyy = (double)dy/2.0D;
		double dzz = (double)dz/2.0D;
		for(int p = 0; p < nr_particles; p++){
			double xx = x + (random.nextDouble()*dx - dxx);
			double yy = y + (random.nextDouble()*dy - dyy);
			double zz = z + (random.nextDouble()*dz - dzz);
			world.spawnParticle(type, xx, yy, zz, 0.0D, 0.0D, 0.0D);
		}
	}
}

#endif
// no support for 147
