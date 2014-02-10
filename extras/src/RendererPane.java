package com.qzx.au.extras;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import com.qzx.au.core.BlockCoord;
import com.qzx.au.core.Color;
import com.qzx.au.core.RenderUtils;

@SideOnly(Side.CLIENT)
public class RendererPane implements ISimpleBlockRenderingHandler {
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer){
/*
		boolean isTinted = ((BlockColoredPane)block).isTinted();
		renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		if(isTinted) GL11.glEnable(GL11.GL_BLEND);
		RenderUtils.renderInventoryItem(block, renderer, ((BlockColoredPane)block).getIcon(0, metadata));
		if(isTinted) GL11.glDisable(GL11.GL_BLEND);

// TODO PANE: render 2D icon with semi-transparency

		// render untinted frame over the tinted glass pane
		if(((BlockColoredPane)block).isTintedWithFrame())
			RenderUtils.renderInventoryItem(block, renderer, AUExtras.blockGlassPane.getIcon(0, metadata));
*/
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer){
		if(ClientProxy.renderPass != block.getRenderBlockPass()) return false;

		// get block bounds
		float minX = (float)block.getBlockBoundsMinX();
		float maxX = (float)block.getBlockBoundsMaxX();
		float minY = (float)block.getBlockBoundsMinY();
		float maxY = (float)block.getBlockBoundsMaxY();
		float minZ = (float)block.getBlockBoundsMinZ();
		float maxZ = (float)block.getBlockBoundsMaxZ();
		boolean draw_y = (minY > 0.0F && maxY < 1.0F);
		boolean draw_n = (!draw_y && minZ == 0.0F);
		boolean draw_s = (!draw_y && maxZ == 1.0F);
		boolean draw_w = (!draw_y && minX == 0.0F);
		boolean draw_e = (!draw_y && maxX == 1.0F);
		final float CENTER_OFFSET = (ClientProxy.renderPass == 1 ? BlockCoord.ADD_1_64/16 : 0.0F);
		final float X_CENTER_N = x + 0.5F - CENTER_OFFSET;
		final float X_CENTER_P = x + 0.5F + CENTER_OFFSET;
		final float Y_CENTER_N = y + 0.5F - CENTER_OFFSET;
		final float Y_CENTER_P = y + 0.5F + CENTER_OFFSET;
		final float Z_CENTER_N = z + 0.5F - CENTER_OFFSET;
		final float Z_CENTER_P = z + 0.5F + CENTER_OFFSET;

		RenderUtils.initLighting(world, block, x, y, z);

		Icon icon_side = ((BlockColoredPane)block).getSidesIcon();
		float sx1 = (float)world.getBlockMetadata(x,y,z) * BlockCoord.ADD_1_16 + BlockCoord.ADD_1_64, sx2 = sx1 + BlockCoord.ADD_1_16 - BlockCoord.ADD_1_32;
		final float sy1 = 0.0F, sy2 = BlockCoord.ADD_1_16;
		final float side_inset = BlockCoord.ADD_1_64/4;

		int panes_d_ns = 0, panes_d_we = 0, panes_u_ns = 0, panes_u_we = 0;

		if(draw_y){
			Icon icon_y = ((BlockColoredPane)block).getBlockTextureHorizontal(world, x, y, z, 0);
			RenderUtils.renderBottomFace(x, Y_CENTER_N, z, x+1.0F, Y_CENTER_N, z+1.0F, icon_y, 0.0F, 0.0F, 1.0F, 1.0F, false);
			RenderUtils.renderTopFace(	 x, Y_CENTER_P, z, x+1.0F, Y_CENTER_P, z+1.0F, icon_y, 0.0F, 0.0F, 1.0F, 1.0F, false);
			// render top and bottom since each are drawn with different color multipliers

			RenderUtils.setColorOverride(RenderUtils.colorSide);
			float SIDE_N = y + 0.5F - BlockColoredPane.SIDE_WIDTH;
			float SIDE_P = y + 0.5F + BlockColoredPane.SIDE_WIDTH;
			if(RenderUtils.shouldSideBeRendered(block, world, x, y, z, 2)){
				RenderUtils.renderSideFace(x+1.0F, SIDE_N, z+side_inset, x+0.0F, SIDE_P, z+side_inset, icon_side, sx1, sy1, sx2, sy2);
				if(ClientProxy.renderPass == 0)
				RenderUtils.renderSideFace(x+0.0F, SIDE_N, z+side_inset, x+1.0F, SIDE_P, z+side_inset, icon_side, sx1, sy1, sx2, sy2);
			}
			if(RenderUtils.shouldSideBeRendered(block, world, x, y, z, 3)){
				RenderUtils.renderSideFace(x+0.0F, SIDE_N, z+1.0F-side_inset, x+1.0F, SIDE_P, z+1.0F-side_inset, icon_side, sx1, sy1, sx2, sy2);
				if(ClientProxy.renderPass == 0)
				RenderUtils.renderSideFace(x+1.0F, SIDE_N, z+1.0F-side_inset, x+0.0F, SIDE_P, z+1.0F-side_inset, icon_side, sx1, sy1, sx2, sy2);
			}
			if(RenderUtils.shouldSideBeRendered(block, world, x, y, z, 4)){
				RenderUtils.renderSideFace(x+side_inset, SIDE_N, z+0.0F, x+side_inset, SIDE_P, z+1.0F, icon_side, sx1, sy1, sx2, sy2);
				if(ClientProxy.renderPass == 0)
				RenderUtils.renderSideFace(x+side_inset, SIDE_N, z+1.0F, x+side_inset, SIDE_P, z+0.0F, icon_side, sx1, sy1, sx2, sy2);
			}
			if(RenderUtils.shouldSideBeRendered(block, world, x, y, z, 5)){
				RenderUtils.renderSideFace(x+1.0F-side_inset, SIDE_N, z+1.0F, x+1.0F-side_inset, SIDE_P, z+0.0F, icon_side, sx1, sy1, sx2, sy2);
				if(ClientProxy.renderPass == 0)
				RenderUtils.renderSideFace(x+1.0F-side_inset, SIDE_N, z+0.0F, x+1.0F-side_inset, SIDE_P, z+1.0F, icon_side, sx1, sy1, sx2, sy2);
			}
			RenderUtils.unsetColorOverride();
		} else {
			// get connections to panes above and below for vertical panes
			if(!RenderUtils.shouldSideBeRendered(block, world, x, y, z, 0)){
				panes_d_ns = ((BlockColoredPane)block).getPaneWidthOnSide(world, x, y-1, z, 4);
				panes_d_we = ((BlockColoredPane)block).getPaneWidthOnSide(world, x, y-1, z, 2);
			}
			if(!RenderUtils.shouldSideBeRendered(block, world, x, y, z, 1)){
				panes_u_ns = ((BlockColoredPane)block).getPaneWidthOnSide(world, x, y+1, z, 4);
				panes_u_we = ((BlockColoredPane)block).getPaneWidthOnSide(world, x, y+1, z, 2);
			}
		}
		if(draw_n || draw_s){
			Icon[] icon_x = ((BlockColoredPane)block).getBlockTextureVertical(world, x, y, z, 4);
			if(draw_n && draw_s){
				// FULL PANE
				if(((BlockColoredPane)block).hasConnectedTextures()){
					RenderUtils.renderSideFace(X_CENTER_N, y, z+0.00F,	X_CENTER_N, y+1.0F, z+0.25F,	icon_x[0], 0.00F, 0.0F, 0.25F, 1.0F); // west face:  left half - texture left
					RenderUtils.renderSideFace(X_CENTER_N, y, z+0.25F,	X_CENTER_N, y+1.0F, z+0.50F,	icon_x[0], 0.75F, 0.0F, 1.00F, 1.0F); // west face:  left half - texture right
					RenderUtils.renderSideFace(X_CENTER_N, y, z+0.50F,	X_CENTER_N, y+1.0F, z+0.75F,	icon_x[1], 0.00F, 0.0F, 0.25F, 1.0F); // west face: right half - texture left
					RenderUtils.renderSideFace(X_CENTER_N, y, z+0.75F,	X_CENTER_N, y+1.0F, z+1.00F,	icon_x[1], 0.75F, 0.0F, 1.00F, 1.0F); // west face: right half - texture right
					RenderUtils.renderSideFace(X_CENTER_P, y, z+1.00F,	X_CENTER_P, y+1.0F, z+0.75F,	icon_x[1], 1.00F, 0.0F, 0.75F, 1.0F); // east face: right half - texture right
					RenderUtils.renderSideFace(X_CENTER_P, y, z+0.75F,	X_CENTER_P, y+1.0F, z+0.50F,	icon_x[1], 0.25F, 0.0F, 0.00F, 1.0F); // east face: right half - texture left
					RenderUtils.renderSideFace(X_CENTER_P, y, z+0.50F,	X_CENTER_P, y+1.0F, z+0.25F,	icon_x[0], 1.00F, 0.0F, 0.75F, 1.0F); // east face:  left half - texture right
					RenderUtils.renderSideFace(X_CENTER_P, y, z+0.25F,	X_CENTER_P, y+1.0F, z+0.00F,	icon_x[0], 0.25F, 0.0F, 0.00F, 1.0F); // east face:  left half - texture left
				} else {
					RenderUtils.renderSideFace(X_CENTER_N, y, z+0.00F,	X_CENTER_N, y+1.0F, z+1.00F,	icon_x[0], 0.00F, 0.0F, 1.00F, 1.0F); // west face:  texture both
					RenderUtils.renderSideFace(X_CENTER_P, y, z+1.00F,	X_CENTER_P, y+1.0F, z+0.00F,	icon_x[0], 1.00F, 0.0F, 0.00F, 1.0F); // east face:  texture both
				}
			} else if(draw_n){
				// NORTH PANE ONLY
				if(((BlockColoredPane)block).hasConnectedTextures()){
					RenderUtils.renderSideFace(X_CENTER_N, y, z+0.00F,	X_CENTER_N, y+1.0F, z+0.25F,	icon_x[0], 0.00F, 0.0F, 0.25F, 1.0F); // west face:  left half - texture left
					RenderUtils.renderSideFace(X_CENTER_N, y, z+0.25F,	X_CENTER_N, y+1.0F, z+0.50F,	icon_x[0], 0.75F, 0.0F, 1.00F, 1.0F); // west face:  left half - texture right
					RenderUtils.renderSideFace(X_CENTER_P, y, z+0.50F,	X_CENTER_P, y+1.0F, z+0.25F,	icon_x[0], 1.00F, 0.0F, 0.75F, 1.0F); // east face:  left half - texture right
					RenderUtils.renderSideFace(X_CENTER_P, y, z+0.25F,	X_CENTER_P, y+1.0F, z+0.00F,	icon_x[0], 0.25F, 0.0F, 0.00F, 1.0F); // east face:  left half - texture left
				} else {
					RenderUtils.renderSideFace(X_CENTER_N, y, z+0.00F,	X_CENTER_N, y+1.0F, z+0.50F,	icon_x[0], 0.00F, 0.0F, 0.50F, 1.0F); // west face:  texture left
					RenderUtils.renderSideFace(X_CENTER_P, y, z+0.50F,	X_CENTER_P, y+1.0F, z+0.00F,	icon_x[0], 0.50F, 0.0F, 0.00F, 1.0F); // east face:  texture left
				}
			} else {
				// SOUTH PANE ONLY
				if(((BlockColoredPane)block).hasConnectedTextures()){
					RenderUtils.renderSideFace(X_CENTER_N, y, z+0.50F,	X_CENTER_N, y+1.0F, z+0.75F,	icon_x[1], 0.00F, 0.0F, 0.25F, 1.0F); // west face: right half - texture left
					RenderUtils.renderSideFace(X_CENTER_N, y, z+0.75F,	X_CENTER_N, y+1.0F, z+1.00F,	icon_x[1], 0.75F, 0.0F, 1.00F, 1.0F); // west face: right half - texture right
					RenderUtils.renderSideFace(X_CENTER_P, y, z+1.00F,	X_CENTER_P, y+1.0F, z+0.75F,	icon_x[1], 1.00F, 0.0F, 0.75F, 1.0F); // east face: right half - texture right
					RenderUtils.renderSideFace(X_CENTER_P, y, z+0.75F,	X_CENTER_P, y+1.0F, z+0.50F,	icon_x[1], 0.25F, 0.0F, 0.00F, 1.0F); // east face: right half - texture left
				} else {
					RenderUtils.renderSideFace(X_CENTER_N, y, z+0.50F,	X_CENTER_N, y+1.0F, z+1.00F,	icon_x[0], 0.50F, 0.0F, 1.00F, 1.0F); // west face: texture right
					RenderUtils.renderSideFace(X_CENTER_P, y, z+1.00F,	X_CENTER_P, y+1.0F, z+0.50F,	icon_x[0], 1.00F, 0.0F, 0.50F, 1.0F); // east face: texture right
				}
			}

			RenderUtils.setColorOverride(RenderUtils.colorSide);
			float SIDE_N = x + 0.5F - BlockColoredPane.SIDE_WIDTH;
			float SIDE_P = x + 0.5F + BlockColoredPane.SIDE_WIDTH;
			if(panes_d_ns != 3){
				RenderUtils.renderBottomFace(SIDE_N, y+side_inset, z+(draw_n && (panes_d_ns&2)==0 ? 0.0F : 0.5F),
											 SIDE_P, y+side_inset, z+(draw_s && (panes_d_ns&1)==0 ? 1.0F : 0.5F), icon_side, sx1, sy1, sx2, sy2, false);
				if(ClientProxy.renderPass == 0)
				RenderUtils.renderTopFace(SIDE_N, y+side_inset, z+(draw_n && (panes_d_ns&2)==0 ? 0.0F : 0.5F),
											 SIDE_P, y+side_inset, z+(draw_s && (panes_d_ns&1)==0 ? 1.0F : 0.5F), icon_side, sx1, sy1, sx2, sy2, false);
			}
			if(panes_u_ns != 3){
				RenderUtils.renderTopFace(SIDE_N, y+1.0F-side_inset, z+(draw_n && (panes_u_ns&2)==0 ? 0.0F : 0.5F),
										  SIDE_P, y+1.0F-side_inset, z+(draw_s && (panes_u_ns&1)==0 ? 1.0F : 0.5F), icon_side, sx1, sy1, sx2, sy2, false);
				if(ClientProxy.renderPass == 0)
				RenderUtils.renderBottomFace(SIDE_N, y+1.0F-side_inset, z+(draw_n && (panes_u_ns&2)==0 ? 0.0F : 0.5F),
										  SIDE_P, y+1.0F-side_inset, z+(draw_s && (panes_u_ns&1)==0 ? 1.0F : 0.5F), icon_side, sx1, sy1, sx2, sy2, false);
			}
			if((draw_n || (!draw_w && !draw_e)) && RenderUtils.shouldSideBeRendered(block, world, x, y, z, 2)){
				float z_side = z + (draw_n ? 0.0F : 0.5F)+side_inset;
				RenderUtils.renderSideFace(SIDE_P, y, z_side, SIDE_N, y+1.0F, z_side, icon_side, sx1, sy1, sx2, sy2);
				if(ClientProxy.renderPass == 0)
				RenderUtils.renderSideFace(SIDE_N, y, z_side, SIDE_P, y+1.0F, z_side, icon_side, sx1, sy1, sx2, sy2);
			}
			if((draw_s || (!draw_w && !draw_e)) && RenderUtils.shouldSideBeRendered(block, world, x, y, z, 3)){
				float z_side = z + (draw_s ? 1.0F : 0.5F)-side_inset;
				RenderUtils.renderSideFace(SIDE_N, y, z_side, SIDE_P, y+1.0F, z_side, icon_side, sx1, sy1, sx2, sy2);
				if(ClientProxy.renderPass == 0)
				RenderUtils.renderSideFace(SIDE_P, y, z_side, SIDE_N, y+1.0F, z_side, icon_side, sx1, sy1, sx2, sy2);
			}
			RenderUtils.unsetColorOverride();
		}
		if(draw_w || draw_e){
			Icon[] icon_z = ((BlockColoredPane)block).getBlockTextureVertical(world, x, y, z, 2);
			if(draw_w && draw_e){
				// FULL PANE
				if(((BlockColoredPane)block).hasConnectedTextures()){
					RenderUtils.renderSideFace(x+1.00F, y, Z_CENTER_N,	x+0.75F, y+1.0F, Z_CENTER_N,	icon_z[0], 0.00F, 0.0F, 0.25F, 1.0F); // north face:  left half - texture left
					RenderUtils.renderSideFace(x+0.75F, y, Z_CENTER_N,	x+0.50F, y+1.0F, Z_CENTER_N,	icon_z[0], 0.75F, 0.0F, 1.00F, 1.0F); // north face:  left half - texture right
					RenderUtils.renderSideFace(x+0.50F, y, Z_CENTER_N,	x+0.25F, y+1.0F, Z_CENTER_N,	icon_z[1], 0.00F, 0.0F, 0.25F, 1.0F); // north face: right half - texture left
					RenderUtils.renderSideFace(x+0.25F, y, Z_CENTER_N,	x+0.00F, y+1.0F, Z_CENTER_N,	icon_z[1], 0.75F, 0.0F, 1.00F, 1.0F); // north face: right half - texture right
					RenderUtils.renderSideFace(x+0.00F, y, Z_CENTER_P,	x+0.25F, y+1.0F, Z_CENTER_P,	icon_z[1], 1.00F, 0.0F, 0.75F, 1.0F); // south face: right half - texture right
					RenderUtils.renderSideFace(x+0.25F, y, Z_CENTER_P,	x+0.50F, y+1.0F, Z_CENTER_P,	icon_z[1], 0.25F, 0.0F, 0.00F, 1.0F); // south face: right half - texture left
					RenderUtils.renderSideFace(x+0.50F, y, Z_CENTER_P,	x+0.75F, y+1.0F, Z_CENTER_P,	icon_z[0], 1.00F, 0.0F, 0.75F, 1.0F); // south face:  left half - texture right
					RenderUtils.renderSideFace(x+0.75F, y, Z_CENTER_P,	x+1.00F, y+1.0F, Z_CENTER_P,	icon_z[0], 0.25F, 0.0F, 0.00F, 1.0F); // south face:  left half - texture left
				} else {
					RenderUtils.renderSideFace(x+1.00F, y, Z_CENTER_N,	x+0.00F, y+1.0F, Z_CENTER_N,	icon_z[0], 0.00F, 0.0F, 1.00F, 1.0F); // north face: texture both
					RenderUtils.renderSideFace(x+0.00F, y, Z_CENTER_P,	x+1.00F, y+1.0F, Z_CENTER_P,	icon_z[0], 1.00F, 0.0F, 0.00F, 1.0F); // south face: texture both
				}
			} else if(draw_w){
				// WEST PANE ONLY
				if(((BlockColoredPane)block).hasConnectedTextures()){
					RenderUtils.renderSideFace(x+0.50F, y, Z_CENTER_N,	x+0.25F, y+1.0F, Z_CENTER_N,	icon_z[1], 0.00F, 0.0F, 0.25F, 1.0F); // north face: right half - texture left
					RenderUtils.renderSideFace(x+0.25F, y, Z_CENTER_N,	x+0.00F, y+1.0F, Z_CENTER_N,	icon_z[1], 0.75F, 0.0F, 1.00F, 1.0F); // north face: right half - texture right
					RenderUtils.renderSideFace(x+0.00F, y, Z_CENTER_P,	x+0.25F, y+1.0F, Z_CENTER_P,	icon_z[1], 1.00F, 0.0F, 0.75F, 1.0F); // south face: right half - texture right
					RenderUtils.renderSideFace(x+0.25F, y, Z_CENTER_P,	x+0.50F, y+1.0F, Z_CENTER_P,	icon_z[1], 0.25F, 0.0F, 0.00F, 1.0F); // south face: right half - texture left
				} else {
					RenderUtils.renderSideFace(x+0.50F, y, Z_CENTER_N,	x+0.00F, y+1.0F, Z_CENTER_N,	icon_z[0], 0.50F, 0.0F, 1.00F, 1.0F); // north face: texture right
					RenderUtils.renderSideFace(x+0.00F, y, Z_CENTER_P,	x+0.50F, y+1.0F, Z_CENTER_P,	icon_z[0], 1.00F, 0.0F, 0.50F, 1.0F); // south face: texture right
				}
			} else {
				// EAST PANE ONLY
				if(((BlockColoredPane)block).hasConnectedTextures()){
					RenderUtils.renderSideFace(x+1.00F, y, Z_CENTER_N,	x+0.75F, y+1.0F, Z_CENTER_N,	icon_z[0], 0.00F, 0.0F, 0.25F, 1.0F); // north face:  left half - texture left
					RenderUtils.renderSideFace(x+0.75F, y, Z_CENTER_N,	x+0.50F, y+1.0F, Z_CENTER_N,	icon_z[0], 0.75F, 0.0F, 1.00F, 1.0F); // north face:  left half - texture right
					RenderUtils.renderSideFace(x+0.50F, y, Z_CENTER_P,	x+0.75F, y+1.0F, Z_CENTER_P,	icon_z[0], 1.00F, 0.0F, 0.75F, 1.0F); // south face:  left half - texture right
					RenderUtils.renderSideFace(x+0.75F, y, Z_CENTER_P,	x+1.00F, y+1.0F, Z_CENTER_P,	icon_z[0], 0.25F, 0.0F, 0.00F, 1.0F); // south face:  left half - texture left
				} else {
					RenderUtils.renderSideFace(x+1.00F, y, Z_CENTER_N,	x+0.50F, y+1.0F, Z_CENTER_N,	icon_z[0], 0.00F, 0.0F, 0.50F, 1.0F); // north face: texture left
					RenderUtils.renderSideFace(x+0.50F, y, Z_CENTER_P,	x+1.00F, y+1.0F, Z_CENTER_P,	icon_z[0], 0.50F, 0.0F, 0.00F, 1.0F); // south face: texture left
				}
			}

			RenderUtils.setColorOverride(RenderUtils.colorSide);
			float SIDE_N = z + 0.5F - BlockColoredPane.SIDE_WIDTH;
			float SIDE_P = z + 0.5F + BlockColoredPane.SIDE_WIDTH;
			if(panes_d_we != 3){
				RenderUtils.renderBottomFace(x+(draw_w && (panes_d_we&1)==0 ? 0.0F : 0.5F), y+side_inset, SIDE_N,
											 x+(draw_e && (panes_d_we&2)==0 ? 1.0F : 0.5F), y+side_inset, SIDE_P, icon_side, sx1, sy1, sx2, sy2, false);
				if(ClientProxy.renderPass == 0)
				RenderUtils.renderTopFace(x+(draw_w && (panes_d_we&1)==0 ? 0.0F : 0.5F), y+side_inset, SIDE_N,
											 x+(draw_e && (panes_d_we&2)==0 ? 1.0F : 0.5F), y+side_inset, SIDE_P, icon_side, sx1, sy1, sx2, sy2, false);
			}
			if(panes_u_we != 3){
				RenderUtils.renderTopFace(x+(draw_w && (panes_u_we&1)==0 ? 0.0F : 0.5F), y+1.0F-side_inset, SIDE_N,
										  x+(draw_e && (panes_u_we&2)==0 ? 1.0F : 0.5F), y+1.0F-side_inset, SIDE_P, icon_side, sx1, sy1, sx2, sy2, false);
				if(ClientProxy.renderPass == 0)
				RenderUtils.renderBottomFace(x+(draw_w && (panes_u_we&1)==0 ? 0.0F : 0.5F), y+1.0F-side_inset, SIDE_N,
										  x+(draw_e && (panes_u_we&2)==0 ? 1.0F : 0.5F), y+1.0F-side_inset, SIDE_P, icon_side, sx1, sy1, sx2, sy2, false);
			}
			if((draw_w || (!draw_n && !draw_s)) && RenderUtils.shouldSideBeRendered(block, world, x, y, z, 4)){
				float x_side = x + (draw_w ? 0.0F : 0.5F)+side_inset;
				RenderUtils.renderSideFace(x_side, y, SIDE_N, x_side, y+1.0F, SIDE_P, icon_side, sx1, sy1, sx2, sy2);
				if(ClientProxy.renderPass == 0)
				RenderUtils.renderSideFace(x_side, y, SIDE_P, x_side, y+1.0F, SIDE_N, icon_side, sx1, sy1, sx2, sy2);
			}
			if((draw_e || (!draw_n && !draw_s)) && RenderUtils.shouldSideBeRendered(block, world, x, y, z, 5)){
				float x_side = x + (draw_e ? 1.0F : 0.5F)-side_inset;
				RenderUtils.renderSideFace(x_side, y, SIDE_P, x_side, y+1.0F, SIDE_N, icon_side, sx1, sy1, sx2, sy2);
				if(ClientProxy.renderPass == 0)
				RenderUtils.renderSideFace(x_side, y, SIDE_N, x_side, y+1.0F, SIDE_P, icon_side, sx1, sy1, sx2, sy2);
			}
			RenderUtils.unsetColorOverride();
		}

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(){
		return false;
	}

	@Override
	public int getRenderId(){
		return ClientProxy.paneRenderType;
	}
}
