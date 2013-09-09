package com.qzx.au.hud;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import com.qzx.au.util.UI;

@SideOnly(Side.CLIENT)
public class ArmorHUD {
	private UI ui = new UI();

	public ArmorHUD(){}

	//////////

	private void drawItemStack(Minecraft mc, RenderItem itemRenderer, ItemStack itemstack, int x, int y, String s){
		if(itemstack == null) return;
		itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, itemstack, x, y);
		itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, itemstack, x, y, s);
	}

	private int countItemsInInventory(EntityPlayer player, int itemID, int itemDamage){
		int nr_items = 0;
		for(int i = 0; i < 36; i++){
			ItemStack item = player.inventory.mainInventory[i];
			if(item != null)
				if(item.itemID == itemID && item.getItemDamage() == itemDamage)
					nr_items += item.stackSize;
		}
		return nr_items;
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

		int nr_hand = 0, nr_ammo = -1;
		if(hand != null){
			nr_hand = (hand.getMaxStackSize() > 1 ? countItemsInInventory(player, hand.itemID, hand.getItemDamage()) : 1);

			// bow ammo
			if(hand.itemID == Item.bow.itemID)
				nr_ammo = countItemsInInventory(player, Item.arrow.itemID, 0);
		}

		GL11.glTranslatef(0.0F, 0.0F, 32.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		GL11.glEnable(32826); // GL_RESCALE_NORMAL_EXT + GL_RESCALE_NORMAL_EXT
		RenderHelper.enableStandardItemLighting();
		RenderHelper.enableGUIStandardItemLighting();

		this.drawItemStack(mc, itemRenderer, helmet, x, y, null);
		this.drawItemStack(mc, itemRenderer, chest, x, y+16, null);
		this.drawItemStack(mc, itemRenderer, pants, x, y+32, null);
		this.drawItemStack(mc, itemRenderer, boots, x, y+48, null);
		this.drawItemStack(mc, itemRenderer, hand, x, y+64, (nr_hand > 1 ? String.format("%d", nr_hand) : null));

		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(32826); // GL_RESCALE_NORMAL_EXT + GL_RESCALE_NORMAL_EXT

// TODO: display ammo count for held item

		GL11.glPopMatrix();
	}
}
