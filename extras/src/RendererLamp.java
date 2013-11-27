package com.qzx.au.extras;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.src.FMLRenderAccessLibrary;
import net.minecraft.world.IBlockAccess;

import com.qzx.au.core.RenderUtils;

@SideOnly(Side.CLIENT)
public class RendererLamp implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer){
		block.setBlockBoundsForItemRender();
		RenderUtils.renderInventoryBlock(block, renderer, ((BlockLamp)block).getIcon(0, metadata));
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer){
		if(ClientProxy.renderPass == 1 && ((BlockLamp)block).isOn()){
			float size = 0.02F;
			renderer.setRenderBounds(0.0F-size, 0.0F-size, 0.0F-size, 1.0F+size, 1.0F+size, 1.0F+size);
			renderer.setOverrideBlockTexture(((BlockLamp)block).getGlowIcon(world.getBlockMetadata(x, y, z)));
			renderer.renderStandardBlock(block, x, y, z);
			renderer.clearOverrideBlockTexture();
			return true;
		}

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
		return ClientProxy.lampRenderType;
	}
}
