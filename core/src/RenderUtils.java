package com.qzx.au.core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
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

	private static boolean enableAO;					// both
	private static final float colorTop = 1.0F;			// both
	private static final float colorSide = 0.80F;		// both
	private static final float colorBottom = 0.5F;		// both
	private static int color;							// both
	private static float x;
	private static float y;
	private static float z;
	private static int mb_block;						// both
	private static int mb_d;
	private static int mb_u;
	private static int mb_n;
	private static int mb_s;
	private static int mb_w;
	private static int mb_e;
	private static float ao_block;
	private static float ao_d;
	private static float ao_u;
	private static float ao_n;
	private static float ao_s;
	private static float ao_w;
	private static float ao_e;

	public static void initLighting(IBlockAccess access, Block block, int x, int y, int z){
		RenderUtils.mb_block = block.getMixedBrightnessForBlock(access, x, y, z);
		RenderUtils.color = block.colorMultiplier(access, x, y, z);
		if(Minecraft.isAmbientOcclusionEnabled() && block.getLightValue(access, x, y, z) == 0){
			RenderUtils.enableAO = true;
			RenderUtils.x = (float)x;
			RenderUtils.y = (float)y;
			RenderUtils.z = (float)z;

			#define AVG_MB(mb, x, y, z) mb = block.getMixedBrightnessForBlock(access, x, y, z); if(mb == 0) mb = RenderUtils.mb_block;
			AVG_MB(RenderUtils.mb_d, x, y-1, z);
			AVG_MB(RenderUtils.mb_u, x, y+1, z);
			AVG_MB(RenderUtils.mb_n, x, y, z-1);
			AVG_MB(RenderUtils.mb_s, x, y, z+1);
			AVG_MB(RenderUtils.mb_w, x-1, y, z);
			AVG_MB(RenderUtils.mb_e, x+1, y, z);

			RenderUtils.ao_block = block.getAmbientOcclusionLightValue(access, x, y, z);
			#define AVG_AO(ao, x, y, z) ao = block.getAmbientOcclusionLightValue(access, x, y, z); if(ao == 0.0F) ao = RenderUtils.ao_block;
			AVG_AO(RenderUtils.ao_d, x, y-1, z);
			AVG_AO(RenderUtils.ao_u, x, y+1, z);
			AVG_AO(RenderUtils.ao_n, x, y, z-1);
			AVG_AO(RenderUtils.ao_s, x, y, z+1);
			AVG_AO(RenderUtils.ao_w, x-1, y, z);
			AVG_AO(RenderUtils.ao_e, x+1, y, z);
		} else
			RenderUtils.enableAO = false;
	}

	private static void setFaceLighting(float colorMultiplier){
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(RenderUtils.mb_block);
		Color color = (new Color(RenderUtils.color)).multiplier(colorMultiplier).anaglyph();
		tessellator.setColorOpaque_F(color.r, color.g, color.b);
	}

	private static void setVertexLighting(float fx, float fy, float fz, float colorMultiplier, int side){
		Tessellator tessellator = Tessellator.instance;

		float dx = fx - RenderUtils.x, dy = fy - RenderUtils.y, dz = fz - RenderUtils.z;

		// select MB sides
		int mb_x = (dx < 0.5F ? RenderUtils.mb_w : (dx > 0.5F ? RenderUtils.mb_e : RenderUtils.mb_block));
		int mb_y = (dy < 0.5F ? RenderUtils.mb_d : (dy > 0.5F ? RenderUtils.mb_u : RenderUtils.mb_block));
		int mb_z = (dz < 0.5F ? RenderUtils.mb_n : (dz > 0.5F ? RenderUtils.mb_s : RenderUtils.mb_block));
		// select AO sides
		float ao_x = (dx < 0.5F ? RenderUtils.ao_w : (dx > 0.5F ? RenderUtils.ao_e : RenderUtils.ao_block));
		float ao_y = (dy < 0.5F ? RenderUtils.ao_d : (dy > 0.5F ? RenderUtils.ao_u : RenderUtils.ao_block));
		float ao_z = (dz < 0.5F ? RenderUtils.ao_n : (dz > 0.5F ? RenderUtils.ao_s : RenderUtils.ao_block));

		#define GET_SIDE(d, mb_side, ao_side)\
			if(d < 0.5F){ d *= 2.0F;					mb_side = (int)(mb_side*(1.0F-d) + RenderUtils.mb_block*d) & 0x00ff00ff;		ao_side = ao_side*(1.0F-d) + RenderUtils.ao_block*d; }\
			else if(d > 0.5F){ d = (d - 0.5F) * 2.0F;	mb_side = (int)(RenderUtils.mb_block*(1.0F-d) + mb_side*d) & 0x00ff00ff;		ao_side = RenderUtils.ao_block*(1.0F-d) + ao_side*d; }\
			else {										mb_side = RenderUtils.mb_block;													ao_side = RenderUtils.ao_block; }
		GET_SIDE(dx, mb_x, ao_x);
		GET_SIDE(dy, mb_y, ao_y);
		GET_SIDE(dz, mb_z, ao_z);

		int mb   = ((side == -1 ? mb_z : mb_x) + (side == 0 ? mb_x : mb_y) + (side == 1 ? mb_x : mb_z) + RenderUtils.mb_block) >> 2 & 0x00ff00ff;
		float ao = ((side == -1 ? ao_z : ao_x) + (side == 0 ? ao_x : ao_y) + (side == 1 ? ao_x : ao_z) + RenderUtils.ao_block) / 4.0F;

		tessellator.setBrightness(mb);
		Color color = (new Color(RenderUtils.color)).multiplier(colorMultiplier * ao).anaglyph();
		tessellator.setColorOpaque_F(color.r, color.g, color.b);
	}

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
		if(!RenderUtils.enableAO) RenderUtils.setFaceLighting(RenderUtils.colorSide);
		int side = (RenderUtils.enableAO ? (Math.abs(x2 - x1) > Math.abs(z2 - z1) ? 1 : -1) : 0);
		// face is always vertical, use top/bottom methods for slopes
		if(RenderUtils.enableAO) RenderUtils.setVertexLighting(x2, y2, z2, RenderUtils.colorSide, side);		tessellator.addVertexWithUV(x2, y2, z2,  r, t);
		if(RenderUtils.enableAO) RenderUtils.setVertexLighting(x1, y2, z1, RenderUtils.colorSide, side);		tessellator.addVertexWithUV(x1, y2, z1,  l, t);
		if(RenderUtils.enableAO) RenderUtils.setVertexLighting(x1, y1, z1, RenderUtils.colorSide, side);		tessellator.addVertexWithUV(x1, y1, z1,  l, b);
		if(RenderUtils.enableAO) RenderUtils.setVertexLighting(x2, y1, z2, RenderUtils.colorSide, side);		tessellator.addVertexWithUV(x2, y1, z2,  r, b);
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
		if(!RenderUtils.enableAO) RenderUtils.setFaceLighting(RenderUtils.colorTop);
		if(slope_NS){
			if(RenderUtils.enableAO) RenderUtils.setVertexLighting(x2, y2, z2, RenderUtils.colorTop, 0);		tessellator.addVertexWithUV(x2, y2, z2,  r, b);
			if(RenderUtils.enableAO) RenderUtils.setVertexLighting(x2, y1, z1, RenderUtils.colorTop, 0);		tessellator.addVertexWithUV(x2, y1, z1,  r, t);
			if(RenderUtils.enableAO) RenderUtils.setVertexLighting(x1, y1, z1, RenderUtils.colorTop, 0);		tessellator.addVertexWithUV(x1, y1, z1,  l, t);
			if(RenderUtils.enableAO) RenderUtils.setVertexLighting(x1, y2, z2, RenderUtils.colorTop, 0);		tessellator.addVertexWithUV(x1, y2, z2,  l, b);
		} else { // slope_WE
			if(RenderUtils.enableAO) RenderUtils.setVertexLighting(x2, y2, z2, RenderUtils.colorTop, 0);		tessellator.addVertexWithUV(x2, y2, z2,  r, b);
			if(RenderUtils.enableAO) RenderUtils.setVertexLighting(x2, y2, z1, RenderUtils.colorTop, 0);		tessellator.addVertexWithUV(x2, y2, z1,  r, t);
			if(RenderUtils.enableAO) RenderUtils.setVertexLighting(x1, y1, z1, RenderUtils.colorTop, 0);		tessellator.addVertexWithUV(x1, y1, z1,  l, t);
			if(RenderUtils.enableAO) RenderUtils.setVertexLighting(x1, y1, z2, RenderUtils.colorTop, 0);		tessellator.addVertexWithUV(x1, y1, z2,  l, b);
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
		if(!RenderUtils.enableAO) RenderUtils.setFaceLighting(RenderUtils.colorBottom);
		if(slope_NS){
			if(RenderUtils.enableAO) RenderUtils.setVertexLighting(x1, y2, z2, RenderUtils.colorBottom, 0);		tessellator.addVertexWithUV(x1, y2, z2,  l, b);
			if(RenderUtils.enableAO) RenderUtils.setVertexLighting(x1, y1, z1, RenderUtils.colorBottom, 0);		tessellator.addVertexWithUV(x1, y1, z1,  l, t);
			if(RenderUtils.enableAO) RenderUtils.setVertexLighting(x2, y1, z1, RenderUtils.colorBottom, 0);		tessellator.addVertexWithUV(x2, y1, z1,  r, t);
			if(RenderUtils.enableAO) RenderUtils.setVertexLighting(x2, y2, z2, RenderUtils.colorBottom, 0);		tessellator.addVertexWithUV(x2, y2, z2,  r, b);
		} else { // slope_WE
			if(RenderUtils.enableAO) RenderUtils.setVertexLighting(x1, y1, z2, RenderUtils.colorBottom, 0);		tessellator.addVertexWithUV(x1, y1, z2,  l, b);
			if(RenderUtils.enableAO) RenderUtils.setVertexLighting(x1, y1, z1, RenderUtils.colorBottom, 0);		tessellator.addVertexWithUV(x1, y1, z1,  l, t);
			if(RenderUtils.enableAO) RenderUtils.setVertexLighting(x2, y2, z1, RenderUtils.colorBottom, 0);		tessellator.addVertexWithUV(x2, y2, z1,  r, t);
			if(RenderUtils.enableAO) RenderUtils.setVertexLighting(x2, y2, z2, RenderUtils.colorBottom, 0);		tessellator.addVertexWithUV(x2, y2, z2,  r, b);
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
