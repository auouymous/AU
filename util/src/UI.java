package com.qzx.au.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;

public class UI {
	private Minecraft mc = Minecraft.getMinecraft();
	public int base_x = 0;
	private int line_height = 10;
	public int x = 0;
	public int y = 0;
	public float zLevel = 0.0F;

	public UI(){}

	public void setCursor(int x, int y){
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

	//////////

	public void drawString(String s, int color){
		if(s == null) return;

		this.mc.fontRenderer.drawStringWithShadow(s, this.x, this.y, color);
		this.x += this.mc.fontRenderer.getStringWidth(s);
	}

	public int drawStringCentered(String s, int color, int box_width){
		if(s == null) return this.x;

		int x = this.x + (box_width/2 - this.mc.fontRenderer.getStringWidth(s)/2);
		this.mc.fontRenderer.drawStringWithShadow(s, x, this.y, color);
		this.x += box_width;

		return x;
	}

	public int drawStringRight(String s, int color){
		if(s == null) return this.x;

		this.x -= this.mc.fontRenderer.getStringWidth(s);
		this.mc.fontRenderer.drawStringWithShadow(s, this.x, this.y, color);

		return this.x;
	}

	//////////

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

	//////////

	public GuiTextField newTextField(String s, int max_length, int width, int height, boolean draw_background){
		GuiTextField t = new GuiTextField(this.mc.fontRenderer, this.x, this.y, width, height);
		t.setText(s);
		t.setMaxStringLength(max_length);
		t.setEnableBackgroundDrawing(draw_background);
		this.x += width;
		return t;
	}

	//////////

	public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height){
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0,		y + height,	this.zLevel, f * (textureX + 0),	 f1 * (textureY + height));
		tessellator.addVertexWithUV(x + width,	y + height,	this.zLevel, f * (textureX + width), f1 * (textureY + height));
		tessellator.addVertexWithUV(x + width,	y + 0,		this.zLevel, f * (textureX + width), f1 * (textureY + 0));
		tessellator.addVertexWithUV(x + 0,		y + 0,		this.zLevel, f * (textureX + 0),	 f1 * (textureY + 0));
		tessellator.draw();
	}
}
