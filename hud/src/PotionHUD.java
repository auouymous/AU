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
	public PotionHUD(){}

	//////////

	public void draw(Minecraft mc, ScaledResolution screen, EntityPlayer player){
		try {
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
				try {
					PotionEffect effect = (PotionEffect)iterator.next();
					Potion potion = Potion.potionTypes[effect.getPotionID()];
					String name = StatCollector.translateToLocal(potion.getName());
					String[] amplifiers = {"", " II", " III", " IV", " V", " VI", " VII", " VIII", " IX", " X"};
					int amplifier = effect.getAmplifier();
					if(amplifier < 0) amplifier = 0;
					if(amplifier < amplifiers.length)
						name = name + amplifiers[amplifier];
					else
						name = name + String.format(" %d", amplifier);
					String duration = Potion.getDurationString(effect);

					// potion icon
					if(potion.hasStatusIcon()){
						int icon = potion.getStatusIconIndex();
						#if defined MC147 || defined MC152
						UI.bindTexture(mc, "/gui/inventory.png");
						#else
						UI.bindTexture(mc, "textures/gui/container/inventory.png");
						#endif
						UI.drawTexturedRect(x, y,  0 + icon%8 * 18, 198 + icon/8 * 18, 18, 18, 200.0F);
					}

					// potion name and remaining duration
					int xx = x + ((Cfg.potion_hud_corner&1) == 0 ? 20 : -4);
					mc.fontRenderer.drawStringWithShadow(name, xx + ((Cfg.potion_hud_corner&1) == 0 ? 0 : -mc.fontRenderer.getStringWidth(name)), y, 0xaaaaaa);
					mc.fontRenderer.drawStringWithShadow(duration, xx + ((Cfg.potion_hud_corner&1) == 0 ? 0 : -mc.fontRenderer.getStringWidth(duration)), y+10, 0xffffff);
				} catch(Exception e){
					Failure.log("potion hud, iterator");
				}
			}
			GL11.glPopMatrix();
		} catch(Exception e){
			Failure.log("potion hud");
		}
	}
}
