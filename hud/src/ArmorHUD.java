package com.qzx.au.hud;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ArmorHUD {
	public ArmorHUD(){}

	//////////

	private void drawItemStack(Minecraft mc, RenderItem itemRenderer, ItemStack itemStack, int x, int y, String s){
		if(itemStack == null) return;
		itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, itemStack, x, y);
		itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, itemStack, x, y, s);
	}

	//////////

	public void draw(Minecraft mc, ScaledResolution screen, EntityPlayer player){
		GL11.glPushMatrix();

		RenderItem itemRenderer = new RenderItem();
		itemRenderer.zLevel = 200.0F;

		ItemStack helmet = mc.thePlayer.getCurrentArmor(3);
		ItemStack chest = mc.thePlayer.getCurrentArmor(2);
		ItemStack pants = mc.thePlayer.getCurrentArmor(1);
		ItemStack boots = mc.thePlayer.getCurrentArmor(0);
		ItemStack hand = mc.thePlayer.getHeldItem();

		int width = screen.getScaledWidth();
		int height = screen.getScaledHeight();
		int x = ((Cfg.armor_hud_corner&1) == 0 ? Cfg.armor_hud_x :  width-16-Cfg.armor_hud_x);
		int y = ((Cfg.armor_hud_corner&2) == 0 ? Cfg.armor_hud_y : height-80-Cfg.armor_hud_y);

		int nr_hand = 0;
		if(hand != null)
			for(int i = 0; i < 36; i++){
				ItemStack item = mc.thePlayer.inventory.mainInventory[i];
				if(item != null)
					if(item.itemID == hand.itemID && item.getItemDamage() == hand.getItemDamage())
						nr_hand += item.stackSize;
			}

		RenderHelper.disableStandardItemLighting();
		RenderHelper.enableGUIStandardItemLighting();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
		GL11.glTranslatef(0.0F, 0.0F, 32.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_LIGHTING);
		this.drawItemStack(mc, itemRenderer, helmet, x, y, null);
		this.drawItemStack(mc, itemRenderer, chest, x, y+16, null);
		this.drawItemStack(mc, itemRenderer, pants, x, y+32, null);
		this.drawItemStack(mc, itemRenderer, boots, x, y+48, null);
		this.drawItemStack(mc, itemRenderer, hand, x, y+64, (nr_hand > 1 ? String.format("%d", nr_hand) : null));
		RenderHelper.enableStandardItemLighting();

		GL11.glPopMatrix();
	}
}
