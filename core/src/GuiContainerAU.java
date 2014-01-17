package com.qzx.au.core;

// no support for 147
#ifndef MC147

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.qzx.au.core.UI;

@SideOnly(Side.CLIENT)
public class GuiContainerAU extends GuiContainer {
	// protected int xSize = 176;
	// protected int ySize = 166;
	// public Container inventorySlots;
	// protected int guiLeft;
	// protected int guiTop;
	// public Container inventorySlots;

	protected UI ui;
	protected ContainerAU containerAU;
	protected TileEntityAU tileEntity;
	protected int upperX, upperY;
	protected int lowerX, lowerY;

	public GuiContainerAU(InventoryPlayer inventoryPlayer, TileEntityAU tileEntity){
		super(tileEntity.getContainer(inventoryPlayer));
		this.ui = new UI();
		this.containerAU = (ContainerAU)this.inventorySlots;
		this.tileEntity = tileEntity;
		this.xSize = (ContainerAU.borderThickness<<1) + (this.containerAU.upperWidth > ContainerAU.lowerWidth ? this.containerAU.upperWidth : ContainerAU.lowerWidth);
		this.ySize = this.containerAU.lowerOffsetY + ContainerAU.lowerHeight + ContainerAU.borderThickness;
	}

	@Override
	public void initGui(){
		super.initGui();

		this.upperX = this.guiLeft + this.containerAU.upperOffsetX;
		this.upperY = this.guiTop + this.containerAU.upperOffsetY;
		this.lowerX = this.guiLeft + this.containerAU.lowerOffsetX;
		this.lowerY = this.guiTop + this.containerAU.lowerOffsetY;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int cursor_x, int cursor_y){
		// cursor_x/y is currsor position but all rendering is done with offsets

/*
		// shaded slots
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		this.zLevel = 201.0F;
		ArrayList slots = (ArrayList)this.containerAU.inventorySlots;
		int mask = 0x3f8b8b8b;
		for(int s = 0; s < slots.size(); s++){
			SlotAU slot = (SlotAU)slots.get(s);
			if(slot.shaded){
				int x = slot.xDisplayPosition;
				int y = slot.yDisplayPosition;
				this.drawGradientRect(x, y, x+16, y+16, mask, mask); // background
			}
		}
		this.zLevel = 0.0F;
		GL11.glPopMatrix();
*/
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int cursor_x, int cursor_y){
		// cursor_x/y is exact cursor position
		int x, y, width, height;

		// upper background and border
		x = this.upperX - ContainerAU.borderThickness;
		y = this.upperY - ContainerAU.borderThickness;
		width = this.containerAU.upperWidth + (ContainerAU.borderThickness<<1);
		height = this.containerAU.upperHeight + (ContainerAU.borderThickness<<1);
		UI.drawRect(x, y, width, height, 0x3fc6c6c6);
		UI.drawRect(x+3, y+3, width-6, height-6, 0xffc6c6c6);
		UI.drawBorder(x+2, y+2, width-4, height-4, 0x6fffffff, 0xbf373737, 0x3f8b8b8b);

		// lower background and border (player inventory)
		x = this.lowerX - ContainerAU.borderThickness;
		y = this.lowerY - ContainerAU.borderThickness;
		width = ContainerAU.lowerWidth + (ContainerAU.borderThickness<<1);
		height = ContainerAU.lowerHeight + (ContainerAU.borderThickness<<1);
		UI.drawRect(x, y, width, height, 0x3fc6c6c6);
		UI.drawRect(x+3, y+3, width-6, height-6, 0xffc6c6c6);
		UI.drawBorder(x+2, y+2, width-4, height-4, 0x6fffffff, 0xbf373737, 0x3f8b8b8b);

		// tabs on upper left side, outside border
// TODO: draw tabs on upper left side

		// slot borders
		ArrayList slots = (ArrayList)this.containerAU.inventorySlots;
		for(int s = 0; s < slots.size(); s++){
			SlotAU slot = (SlotAU)slots.get(s);
			x = this.guiLeft + slot.xDisplayPosition - 1 - (slot.borderPadding>>1);
			y = this.guiTop + slot.yDisplayPosition - 1 - (slot.borderPadding>>1);
			int size = 18 + slot.borderPadding;
			UI.drawBorder(x, y, size, size, 0xff373737, 0xffffffff, 0xff8b8b8b);
			UI.drawRect(x+1, y+1, size-2, size-2, 0xff8b8b8b); // background
			if(slot.getStack() == null && slot.getFilterItemStack() != null){
				// draw itemstack background
				GL11.glPushMatrix();
				RenderItem itemRenderer = new RenderItem();
				itemRenderer.zLevel = 0.0F;
				try {
					UI.drawItemStack(this.mc, itemRenderer, slot.getFilterItemStack(), x+1, y+1, false);
				} catch(Exception e){
					Debug.error("Failed to render slot filter icon");
				}
				GL11.glPopMatrix();

				// fade icon
				UI.drawRect(x+1, y+1, size-2, size-2, 0xbb8b8b8b);
			}
		}
	}

	@Override
	public void drawScreen(int x, int y, float f){
		super.drawScreen(x, y, f);
		this.drawTooltips(x, y);
	}

	protected void drawTooltips(int x, int y){
		// find any buttons below cursor
		for(int i = 0; i < this.buttonList.size(); i++){
			Button button = (Button)this.buttonList.get(i);
			if(button.isMouseOver()){
				String text = button.getTooltip();
				if(text != null){
					ArrayList tooltip = new ArrayList();
					tooltip.add(text);
					this.drawHoveringText(tooltip, x, y, this.fontRenderer);
				}
				return;
			}
		}
		// find any slots below cursor
		ArrayList slots = (ArrayList)this.containerAU.inventorySlots;
		for(int s = 0; s < slots.size(); s++){
			SlotAU slot = (SlotAU)slots.get(s);
			if(slot.isMouseOver(x - this.guiLeft, y - this.guiTop)){
				String text = slot.getTooltip();
				if(slot.getStack() == null && text != null){
					ArrayList tooltip = new ArrayList();
					tooltip.add(text);
					this.drawHoveringText(tooltip, x, y, this.fontRenderer);
				}
				return;
			}
		}

		// override this to add more tooltips
	}

// TODO: methods to render tab pages and handle actions

}

#endif
// no support for 147
