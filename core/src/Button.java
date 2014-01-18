package com.qzx.au.core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

@SideOnly(Side.CLIENT)
public class Button extends GuiButton {
	// width, height, xPosition, yPosition, displayString, enabled, drawButton
	private int style = Button.DEFAULT_STYLE;
	private static int DEFAULT_STYLE = 0;
	private static int ON_OFF_STYLE = 1;
	private static int IMAGE_STYLE = 2;

	public boolean active = false; // ON_OFF_STYLE

	private String imageMod;
	private String imageTexture;
	private int imageX;
	private int imageY;
	private int imageXactive;
	private int imageYactive;

	private String[] tooltip = null;

	public Button(int id, int x, int y, int width, int height, String s){
		super(id, x, y, width, height, s);
	}

	public Button initState(boolean active){
		this.style = Button.ON_OFF_STYLE;
		this.active = active;
		return this;
	}

	public Button initImage(String mod, String texture, int textureX, int textureY, int textureXactive, int textureYactive){
		this.style = Button.IMAGE_STYLE;
		this.imageMod = mod;
		this.imageTexture = texture;
		this.imageX = textureX;
		this.imageY = textureY;
		this.imageXactive = textureXactive;
		this.imageYactive = textureYactive;
		return this;
	}

	@Override
	public void drawButton(Minecraft mc, int cursor_x, int cursor_y){
		if(this.style == Button.DEFAULT_STYLE){
			super.drawButton(mc, cursor_x, cursor_y);
		} else if(this.drawButton){
			UI.setColor(1.0F, 1.0F, 1.0F, 1.0F);

			this.field_82253_i = cursor_x >= this.xPosition && cursor_y >= this.yPosition && cursor_x < this.xPosition + this.width && cursor_y < this.yPosition + this.height;

			int text_color = 0xe0e0e0;

//			if(this.style == Button.DEFAULT_STYLE){
//				int h = this.getHoverState(this.field_82253_i); // 0:disabled, 1:normal, 2:hover
//				UI.bindTexture(mc, "/gui/gui.png");
//				this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + h * 20, this.width / 2, this.height);
//				this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + h * 20, this.width / 2, this.height);

//				if(!this.enabled)
//					text_color = 0xa0a0a0;
//				else if(this.field_82253_i)
//					text_color = 0xffffa0;
//			} else
			if(this.style == Button.IMAGE_STYLE){
				UI.setColor(1.0F, 1.0F, 1.0F, 1.0F);
				UI.bindTexture(mc, this.imageMod, this.imageTexture);
				if(this.active)
					UI.drawTexturedRect(this.xPosition, this.yPosition, this.imageXactive, this.imageYactive, this.width, this.height, 0.0F);
				else
					UI.drawTexturedRect(this.xPosition, this.yPosition, this.imageX, this.imageY, this.width, this.height, 0.0F);
			} else if(this.style == Button.ON_OFF_STYLE){
				UI.drawBorder(this.xPosition, this.yPosition, this.width, this.height, 0xff000000, 0xff000000, 0xff000000);
				UI.drawBorder(this.xPosition+1, this.yPosition+1, this.width-2, this.height-2,
								this.active ? 0xff004400 : 0xff440000,
								this.active ? 0xff006600 : 0xff220000,
								this.active ? 0xff005500 : 0xff330000);
				UI.drawRect(this.xPosition+2, this.yPosition+2, this.width-4, this.height-4, this.active ? 0xff005500 : 0xff330000);

				if(!this.enabled)
					text_color = 0xa0a0a0;
				else if(this.field_82253_i)
					text_color = 0xffffa0;
			}

//			this.mouseDragged(mc, cursor_x, cursor_y);

			if(this.displayString != null)
				this.drawCenteredString(mc.fontRenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, text_color);
		}
	}

	public String[] getTooltip(){
		return this.tooltip;
	}
	public Button setTooltip(String tooltip){
		this.tooltip = tooltip.split("\n");
		return this;
	}

	public boolean isMouseOver(){
		return this.getHoverState(this.field_82253_i) == 2; // 0:disabled, 1:normal, 2:hover
	}
}
