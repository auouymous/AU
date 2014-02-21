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

import com.qzx.au.core.Color;
import com.qzx.au.core.RenderUtils;

@SideOnly(Side.CLIENT)
public class RendererSlab implements ISimpleBlockRenderingHandler {
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer){
		renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		RenderUtils.renderInventoryBlock(block, renderer, block.getIcon(0, metadata));

// TODO: useQuarterTexture for sides

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer){
		Tessellator tessellator = Tessellator.instance;

		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		int colorMultiplier = block.colorMultiplier(world, x, y, z);
		Color color;
		#define COLOR_MULTIPLIER(cm)\
			color = (new Color(colorMultiplier)).multiplier(cm).anaglyph();\
			tessellator.setColorOpaque_F(color.r, color.g, color.b);

		Icon icon = block.getBlockTexture(world, x, y, z, 0);

		renderer.setRenderBoundsFromBlock(block);
		COLOR_MULTIPLIER(RenderUtils.colorTop)
		renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, icon);
		COLOR_MULTIPLIER(RenderUtils.colorBottom)
		renderer.renderFaceYNeg(block, (double)x, (double)y, (double)z, icon);

		boolean lowerHalf = (renderer.renderMinY == 0.0D);
		double y_offset;

		// render bottom of texture
		renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
		y_offset = (lowerHalf ? 0.0F : 0.5F);
		COLOR_MULTIPLIER(RenderUtils.colorSideWE)
		renderer.renderFaceXPos(block, (double)x, (double)y + y_offset, (double)z, icon);
		renderer.renderFaceXNeg(block, (double)x, (double)y + y_offset, (double)z, icon);
		COLOR_MULTIPLIER(RenderUtils.colorSideNS)
		renderer.renderFaceZPos(block, (double)x, (double)y + y_offset, (double)z, icon);
		renderer.renderFaceZNeg(block, (double)x, (double)y + y_offset, (double)z, icon);

		// render top of texture
		renderer.setRenderBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
		y_offset = (lowerHalf ? -0.5F : 0.0F);
		COLOR_MULTIPLIER(RenderUtils.colorSideWE)
		renderer.renderFaceXPos(block, (double)x, (double)y + y_offset, (double)z, icon);
		renderer.renderFaceXNeg(block, (double)x, (double)y + y_offset, (double)z, icon);
		COLOR_MULTIPLIER(RenderUtils.colorSideNS)
		renderer.renderFaceZPos(block, (double)x, (double)y + y_offset, (double)z, icon);
		renderer.renderFaceZNeg(block, (double)x, (double)y + y_offset, (double)z, icon);

// TODO: smooth lighting

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(){
		return true;
	}

	@Override
	public int getRenderId(){
		return ClientProxy.slabRenderType;
	}
}
