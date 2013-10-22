package com.qzx.au.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class Button extends GuiButton {
	// width, height, xPosition, yPosition, displayString, enabled, drawButton
	private int style;
	private static int DEFAULT_STYLE = 0;
	private static int ON_OFF_STYLE = 1;

	public boolean active; // ON_OFF_STYLE

	public Button(int id, int x, int y, int width, int height, String s){
		super(id, x, y, width, height, s);
		this.style = Button.DEFAULT_STYLE;
		this.active = false;
	}

	public Button initState(boolean active){
		this.style = Button.ON_OFF_STYLE;
		this.active = active;
		return this;
	}

	@Override
	public void drawButton(Minecraft mc, int cursor_x, int cursor_y){
		if(this.style == Button.DEFAULT_STYLE){
			super.drawButton(mc, cursor_x, cursor_y);
		} else if(this.drawButton){
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

			this.field_82253_i = cursor_x >= this.xPosition && cursor_y >= this.yPosition && cursor_x < this.xPosition + this.width && cursor_y < this.yPosition + this.height;

			int text_color = 0xe0e0e0;

//			if(this.style == Button.DEFAULT_STYLE){
//				int h = this.getHoverState(this.field_82253_i); // 0:disabled, 1:normal, 2:hover
//				mc.renderEngine.bindTexture("/gui/gui.png");
//				this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + h * 20, this.width / 2, this.height);
//				this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + h * 20, this.width / 2, this.height);

//				if(!this.enabled)
//					text_color = 0xa0a0a0;
//				else if(this.field_82253_i)
//					text_color = 0xffffa0;
//			} else
			if(this.style == Button.ON_OFF_STYLE){
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

			this.drawCenteredString(mc.fontRenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, text_color);
		}
	}

//	@Override
//	public boolean mousePressed(Minecraft mc, int cursor_x, int cursor_y){
//		return this.enabled && this.drawButton && cursor_x >= this.xPosition && cursor_y >= this.yPosition && cursor_x < this.xPosition + this.width && cursor_y < this.yPosition + this.height;
//	}

//	@Override
//	protected void mouseDragged(Minecraft mc, int cursor_x, int cursor_y){
//		
//	}
}
