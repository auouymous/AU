package com.qzx.au.hud;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;

import java.util.Collection;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import com.qzx.au.util.UI;

@SideOnly(Side.CLIENT)
public class PotionHUD {
	private UI ui = new UI();

	public PotionHUD(){
		this.ui.zLevel = 200.0F;
	}

	//////////

	public void draw(Minecraft mc, ScaledResolution screen, EntityPlayer player){
		Collection potions = player.getActivePotionEffects();
		if(potions.isEmpty()) return;

		int width = screen.getScaledWidth();
		int height = screen.getScaledHeight();
		int x = ((Cfg.potion_hud_corner&1) == 0 ? Cfg.potion_hud_x :  width-18-Cfg.potion_hud_x);
		int y = ((Cfg.potion_hud_corner&2) == 0 ? Cfg.potion_hud_y : height-18-Cfg.potion_hud_y);

		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		for(Iterator iterator = potions.iterator(); iterator.hasNext(); y += ((Cfg.potion_hud_corner&2) == 0 ? 20 : -20)){
			PotionEffect effect = (PotionEffect)iterator.next();
			Potion potion = Potion.potionTypes[effect.getPotionID()];
			String name = StatCollector.translateToLocal(potion.getName());
			String[] amplifiers = {"", " II", " III", " IV", " ?"};
			int amplifier = effect.getAmplifier();
			if(amplifier < 0) amplifier = 0;
			else if(amplifier > amplifiers.length) amplifier = amplifiers.length;
			name = name + amplifiers[amplifier];
			String duration = Potion.getDurationString(effect);

			// potion icon
			if(potion.hasStatusIcon()){
				int icon = potion.getStatusIconIndex();
				mc.renderEngine.bindTexture("/gui/inventory.png");
				this.ui.drawTexturedModalRect(x, y,  0 + icon%8 * 18, 198 + icon/8 * 18, 18, 18);
			}

			// potion name and remaining duration
			int xx = x + ((Cfg.potion_hud_corner&1) == 0 ? 20 : -4);
			mc.fontRenderer.drawStringWithShadow(name, xx + ((Cfg.potion_hud_corner&1) == 0 ? 0 : -mc.fontRenderer.getStringWidth(name)), y, 0xaaaaaa);
			mc.fontRenderer.drawStringWithShadow(duration, xx + ((Cfg.potion_hud_corner&1) == 0 ? 0 : -mc.fontRenderer.getStringWidth(duration)), y+10, 0xffffff);
		}
		GL11.glPopMatrix();
	}
}
