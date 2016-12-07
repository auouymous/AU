package com.qzx.au.extras;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemDye;
import net.minecraft.world.IBlockAccess;

import com.qzx.au.core.BlockCoord;
import com.qzx.au.core.Color;
import com.qzx.au.core.RenderUtils;

@SideOnly(Side.CLIENT)
public class RendererChromaInfuser implements ISimpleBlockRenderingHandler {
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer){
		renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		MC_ICON side = block.getIcon(2, metadata);
		RenderUtils.renderInventoryBlock(block, renderer,
			block.getIcon(0, metadata),
			block.getIcon(1, metadata),
			side, side, side, side);
	}

	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer){
		Tessellator tessellator = Tessellator.instance;

		renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

		if(ClientProxy.renderPass == 0){
			// render exterior of block
			renderer.renderStandardBlock(block, x, y, z);

			tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));

			Color color = (new Color(block.colorMultiplier(world, x, y, z))).anaglyph();
			tessellator.setColorOpaque_F(color.r, color.g, color.b);

			// render interior of block
			MC_ICON icon = block.getBlockTexture(world, x, y, z, 2);
			renderer.renderFaceXPos(block, (double)((float)x - 1.0F + BlockCoord.ADD_1_8), (double)y, (double)z, icon);
			renderer.renderFaceXNeg(block, (double)((float)x + 1.0F - BlockCoord.ADD_1_8), (double)y, (double)z, icon);
			renderer.renderFaceZPos(block, (double)x, (double)y, (double)((float)z - 1.0F + BlockCoord.ADD_1_8), icon);
			renderer.renderFaceZNeg(block, (double)x, (double)y, (double)((float)z + 1.0F - BlockCoord.ADD_1_8), icon);
			icon = BlockChromaInfuser.getIconByName("inner");
			renderer.renderFaceYPos(block, (double)x, (double)((float)y - 1.0F + 0.25F), (double)z, icon);
			renderer.renderFaceYNeg(block, (double)x, (double)((float)y + 1.0F - 0.75F), (double)z, icon);
		} else {
			TileEntityChromaInfuser tileEntity = (TileEntityChromaInfuser)BlockCoord.getTileEntity(world, x, y, z);
			if(tileEntity != null){
				if(tileEntity.getWater()){
					if(tileEntity.getDyeVolume() > 0){
						Color waterColor = (new Color(ItemDye.dyeColors[tileEntity.getDyeColor()])).anaglyph();
						tessellator.setColorOpaque_F(waterColor.r, waterColor.g, waterColor.b);
					} else
						tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
					tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
					MC_ICON waterIcon = tileEntity.getDyeVolume() > 0 ? BlockChromaInfuser.getWaterIcon() : BlockFluid.getFluidIcon("water_still");
					renderer.setRenderBounds(BlockCoord.ADD_1_8, 0.0F, BlockCoord.ADD_1_8, BlockCoord.SUB_1_8, 1.0F, BlockCoord.SUB_1_8);
					renderer.renderFaceYPos(block, (double)x, (double)((float)y - (tileEntity.getDyeVolume() > 0 ? BlockCoord.ADD_1_16 : 0.5F)), (double)z, waterIcon);
				}
			}
		}

		return true;
	}

	public boolean shouldRender3DInInventory(){
		return true;
	}

	public int getRenderId(){
		return ClientProxy.infuserRenderType;
	}
}
