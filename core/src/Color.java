package com.qzx.au.core;

import net.minecraft.client.renderer.EntityRenderer;

public class Color {
	public float r;
	public float g;
	public float b;

	public Color(int color){
		this.r = (float)(color >> 16 & 255) / 255.0F;
		this.g = (float)(color >> 8 & 255) / 255.0F;
		this.b = (float)(color & 255) / 255.0F;
	}
	public Color(int r, int g, int b){
		this.r = r / 255.0F;
		this.g = g / 255.0F;
		this.b = b / 255.0F;
	}
	public Color(float r, float g, float b){
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public Color anaglyph(){
		if(EntityRenderer.anaglyphEnable){
			float rr = (this.r * 30.0F + this.g * 59.0F + this.b * 11.0F) / 100.0F;
			float gg = (this.r * 30.0F + this.g * 70.0F) / 100.0F;
			float bb = (this.r * 30.0F + this.b * 70.0F) / 100.0F;
			this.r = rr;
			this.g = gg;
			this.b = bb;
		}
		return this;
	}

	// wool colors are the reverse of dye colors
	// the following are dye colors

	public static final String[] readableColors = {
		"Black", "Red", "Green", "Brown",
		"Blue", "Purple", "Cyan", "Light Gray",
		"Dark Gray", "Pink", "Lime", "Yellow",
		"Light Blue", "Magenta", "Orange", "White"
	};

	public static final String[] colors = {
		"black", "red", "green", "brown",
		"blue", "purple", "cyan", "lightGray",
		"darkGray", "pink", "lime", "yellow",
		"lightBlue", "magenta", "orange", "white"
	};

	public static final String[] oreDyes = {
		"dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray",
		"dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite"
	};

	// net.minecraft.item.ItemDye
	// ItemDye.dyeColors
}
