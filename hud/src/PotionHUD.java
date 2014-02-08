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
import java.util.HashMap;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import com.qzx.au.core.UI;

@SideOnly(Side.CLIENT)
public class PotionHUD {
	private UI ui = new UI();

	public PotionHUD(){}

	//////////

	public void draw(Minecraft mc, ScaledResolution screen, EntityPlayer player){
		try {
			Collection potions;
			if(TickHandlerHUD.force_hud == TickHandlerHUD.HUD_POTION){
				// add two potions when configuring HUD
				HashMap potionMap = new HashMap();
				potionMap.put(Integer.valueOf(Potion.regeneration.id), new PotionEffect(Potion.regeneration.id, 60*20, 0, false));
				potionMap.put(Integer.valueOf(Potion.invisibility.id), new PotionEffect(Potion.invisibility.id, 60*20, 0, false));
				potions = potionMap.values();
			} else
				potions = player.getActivePotionEffects();
			if(potions.isEmpty()) return;

			GL11.glPushMatrix();

			try {
				this.ui.scale(Cfg.potion_hud_scale);

				int width = screen.getScaledWidth();
				int height = screen.getScaledHeight();
				int icon_size = this.ui.unscaleValue(18);
				int gap = this.ui.unscaleValue(2);
				int x = ((Cfg.potion_hud_corner&1) == 0 ? Cfg.potion_hud_x + icon_size + gap : width-Cfg.potion_hud_x - icon_size - gap); // left : right
				int y = ((Cfg.potion_hud_corner&2) == 0 ? Cfg.potion_hud_y : height-Cfg.potion_hud_y - icon_size); // top : bottom
				int text_align = ((Cfg.potion_hud_corner&1) == 0 ? UI.ALIGN_LEFT : UI.ALIGN_RIGHT);
				int icon_offset = ((Cfg.potion_hud_corner&1) == 0 ? -(18+2) : 2); // left : right (icon size and gap)

				if(TickHandlerHUD.force_hud == TickHandlerHUD.HUD_POTION){
					// position grid
					this.ui.setCursor(((Cfg.potion_hud_corner&1) == 0 ? Cfg.potion_hud_x : width-Cfg.potion_hud_x), ((Cfg.potion_hud_corner&2) == 0 ? Cfg.potion_hud_y : height-Cfg.potion_hud_y));
					final int grid_length = 36;
					int xx = this.ui.getScaledX();
					int yy = this.ui.getScaledY();
					this.ui.drawLineH(xx-((Cfg.potion_hud_corner&1) == 0 ? 4 : grid_length-4), yy, grid_length, 0xff00ffff); // left : right
					this.ui.drawLineV(xx, yy-((Cfg.potion_hud_corner&2) == 0 ? 4 : grid_length-4), grid_length, 0xff00ffff); // top : bottom
				}

				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glDisable(GL11.GL_LIGHTING);
				for(Iterator iterator = potions.iterator(); iterator.hasNext(); y += ((Cfg.potion_hud_corner&2) == 0 ? icon_size+gap : -(icon_size+gap))){ // top : bottom
					try {
						this.ui.setCursor(x, y);

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
							UI.drawTexturedRect(this.ui.getScaledX()+icon_offset, this.ui.getScaledY(),  0 + icon%8 * 18, 198 + icon/8 * 18, 18, 18, 200.0F);
						}

						// potion name and remaining duration
						this.ui.drawString(text_align, name, 0xaaaaaa, 0);
						this.ui.lineBreak(10);
						this.ui.drawString(text_align, duration, 0xffffff, 0);
					} catch(Exception e){
						Failure.log("potion hud, iterator");
					}
				}
			} catch(Exception e){
				Failure.log("potion hud, display");
			}

			GL11.glPopMatrix();
		} catch(Exception e){
			Failure.log("potion hud");
		}
	}
}
