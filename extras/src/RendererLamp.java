package com.qzx.au.extras;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.src.FMLRenderAccessLibrary;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RendererLamp implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer){
		Tessellator tessellator = Tessellator.instance;
		Icon icon = ((BlockLamp)block).getItemIcon(metadata);

		block.setBlockBoundsForItemRender();
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		tessellator.addTranslation(0.0F, 0.0F, 0.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.addTranslation(0.0F, 0.0F, -0.0F);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		tessellator.addTranslation(0.0F, 0.0F, -0.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.addTranslation(0.0F, 0.0F, 0.0F);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		tessellator.addTranslation(0.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.addTranslation(-0.0F, 0.0F, 0.0F);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		tessellator.addTranslation(-0.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, icon);
		tessellator.addTranslation(0.0F, 0.0F, 0.0F);
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer){
		boolean isOn = ((BlockLamp)block).isOn();

		if(ClientProxy.renderPass == 1){
			if(isOn)
				renderer.setRenderBounds(-0.02F, -0.02F, -0.02F, 1.02F, 1.02F, 1.02F);
			else
				renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			renderer.renderStandardBlock(block, x, y, z);
			return true;
		}
		if(isOn){
			renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			renderer.renderStandardBlock(block, x, y, z);
			return true;
		}
		return false;
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
