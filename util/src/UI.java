package com.qzx.au.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class UI {
	private Minecraft mc = Minecraft.getMinecraft();
	public int base_x = 0;
	private int line_height = 10;
	public int x = 0;
	public int y = 0;

	public UI(){}

	public void setCursor(int x, int y){
		this.mc = mc;
		this.base_x = x;
		this.x = x;
		this.y = y;
	}

	public void setLineHeight(int height){
		this.line_height = height;
	}

	public void lineBreak(){
		this.x = this.base_x;
		this.y += this.line_height;
	}
	public void lineBreak(int height){
		this.x = this.base_x;
		this.y += height;
	}

	public void drawSpace(int width){
		this.x += width;
	}

	public void drawString(String s, int color){
		if(s == null) return;

		this.mc.fontRenderer.drawStringWithShadow(s, this.x, this.y, color);
		this.x += this.mc.fontRenderer.getStringWidth(s);
	}

	public void drawStringCentered(String s, int color, int box_width){
		if(s == null) return;

		int sw = this.mc.fontRenderer.getStringWidth(s);
		this.x = this.base_x + (box_width/2 - sw/2);
		this.mc.fontRenderer.drawStringWithShadow(s, this.x, this.y, color);
		this.x += sw;
	}

	public GuiButton newButton(int id, String s, int width, int height){
		GuiButton b = new GuiButton(id, this.x, this.y, width, height, s);
		this.x += width;
		return b;
	}

	public GuiButton newButtonCentered(int id, String s, int width, int height, int box_width){
		this.x = this.base_x + (box_width/2 - width/2);
		return this.newButton(id, s, width, height);
	}

	public GuiButton newStateButton(int id, String s, int width, int height, boolean value, String state0, String state1){
		return this.newButton(id, s + (value ? ": "+state1 : ": "+state0), width, height);
	}
}
