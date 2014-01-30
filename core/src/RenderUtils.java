package com.qzx.au.core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderUtils {
/*
	@SideOnly(Side.CLIENT)
	public static void renderInventoryItem(Block block, RenderBlocks renderer, Icon icon){
		Tessellator tessellator = Tessellator.instance;

// TODO: net/minecraft/client/renderer/ItemRenderer.java

//		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.draw();
//		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
*/

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
		if(brightness > -1) tessellator.setBrightness(brightness<<20 | brightness<<4);
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

	// texture origin is upper left corner, tx/ty uses a lower left origin
	public static void renderSideFace(float x1, float y1, float z1, float x2, float y2, float z2, Icon icon, float tx1, float ty1, float tx2, float ty2){
		// xyz1 is bottom left corner
		// xyz2 is upper right corner
		// N	1,0,z   0,1,z	z: E->W
		// S	0,0,z   1,1,z	z: W->E
		// W	x,0,0   x,1,1	x: N->S
		// E	x,0,1   x,1,0	x: S->N
		double l = icon.getInterpolatedU(tx1 * 16.0D);
		double r = icon.getInterpolatedU(tx2 * 16.0D);
		double t = icon.getInterpolatedV(16.0D - ty2 * 16.0D);
		double b = icon.getInterpolatedV(16.0D - ty1 * 16.0D);
		Tessellator tessellator = Tessellator.instance;
		// face is always vertical, use top/bottom methods for slopes
		tessellator.addVertexWithUV(x2, y2, z2,  r, t);
		tessellator.addVertexWithUV(x1, y2, z1,  l, t);
		tessellator.addVertexWithUV(x1, y1, z1,  l, b);
		tessellator.addVertexWithUV(x2, y1, z2,  r, b);
	}
	public static void renderTopFace(float x1, float y1, float z1, float x2, float y2, float z2, Icon icon, float tx1, float ty1, float tx2, float ty2, boolean slope_NS){
		// xyz1 is SE corner
		// xyz2 is NW corner
		// U	0,y,0   1,y,1	y: N->S or W->E
		double l = icon.getInterpolatedU(tx1 * 16.0D);
		double r = icon.getInterpolatedU(tx2 * 16.0D);
		double t = icon.getInterpolatedV(16.0D - ty2 * 16.0D);
		double b = icon.getInterpolatedV(16.0D - ty1 * 16.0D);
		Tessellator tessellator = Tessellator.instance;
		if(slope_NS){
			tessellator.addVertexWithUV(x2, y2, z2,  r, b);
			tessellator.addVertexWithUV(x2, y1, z1,  r, t);
			tessellator.addVertexWithUV(x1, y1, z1,  l, t);
			tessellator.addVertexWithUV(x1, y2, z2,  l, b);
		} else { // slope_WE
			tessellator.addVertexWithUV(x2, y2, z2,  r, b);
			tessellator.addVertexWithUV(x2, y2, z1,  r, t);
			tessellator.addVertexWithUV(x1, y1, z1,  l, t);
			tessellator.addVertexWithUV(x1, y1, z2,  l, b);
		}
	}
	public static void renderBottomFace(float x1, float y1, float z1, float x2, float y2, float z2, Icon icon, float tx1, float ty1, float tx2, float ty2, boolean slope_NS){
		// xyz1 is SW corner
		// xyz2 is NE corner
		// D	0,y,0   1,y,1	y: N->S or W->E
		double l = icon.getInterpolatedU(tx1 * 16.0D);
		double r = icon.getInterpolatedU(tx2 * 16.0D);
		double t = icon.getInterpolatedV(16.0D - ty2 * 16.0D);
		double b = icon.getInterpolatedV(16.0D - ty1 * 16.0D);
		Tessellator tessellator = Tessellator.instance;
		if(slope_NS){
			tessellator.addVertexWithUV(x1, y2, z2,  l, b);
			tessellator.addVertexWithUV(x1, y1, z1,  l, t);
			tessellator.addVertexWithUV(x2, y1, z1,  r, t);
			tessellator.addVertexWithUV(x2, y2, z2,  r, b);
		} else { // slope_WE
			tessellator.addVertexWithUV(x1, y1, z2,  l, b);
			tessellator.addVertexWithUV(x1, y1, z1,  l, t);
			tessellator.addVertexWithUV(x2, y2, z1,  r, t);
			tessellator.addVertexWithUV(x2, y2, z2,  r, b);
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
