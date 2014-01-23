package com.qzx.au.extras;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import com.qzx.au.core.RenderUtils;

@SideOnly(Side.CLIENT)
public class RendererGlass implements ISimpleBlockRenderingHandler {
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer){
		renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_BLEND);
		RenderUtils.renderInventoryBlock(block, renderer, ((BlockGlass)block).getIcon(0, metadata));
		GL11.glDisable(GL11.GL_BLEND);

		// render untinted frame over the tinted glass
		if(((BlockGlass)block).isTintedWithFrame())
			RenderUtils.renderInventoryBlock(block, renderer, AUExtras.blockGlass.getIcon(0, metadata));
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer){
		if(ClientProxy.renderPass != block.getRenderBlockPass()) return false;

		renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		renderer.renderStandardBlock(block, x, y, z);
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(){
		return true;
	}

	@Override
	public int getRenderId(){
		return ClientProxy.glassRenderType;
	}
}
