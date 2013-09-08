package com.qzx.au.hud;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;

import java.util.EnumSet;

@SideOnly(Side.CLIENT)
public class TickHandlerHUD implements ITickHandler {
	private InfoHUD infoHUD = new InfoHUD();
	private ArmorHUD armorHUD = new ArmorHUD();
	private PotionHUD potionHUD = new PotionHUD();

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData){
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData){
		Minecraft mc = Minecraft.getMinecraft();

		if(mc.gameSettings.showDebugInfo || !(mc.inGameHasFocus || mc.currentScreen == null || mc.currentScreen instanceof GuiChat))
			return;

		if(mc.thePlayer != null){
			ScaledResolution screen = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);

			if(Cfg.enable_info_hud) this.infoHUD.draw(mc, screen, mc.thePlayer);
			if(Cfg.enable_armor_hud) this.armorHUD.draw(mc, screen, mc.thePlayer);
			if(Cfg.enable_potion_hud) this.potionHUD.draw(mc, screen, mc.thePlayer);
		}
	}

	@Override
	public EnumSet<TickType> ticks(){
		return EnumSet.of(TickType.RENDER);
	}

	@Override
	public String getLabel(){
		return "AUHud: Render Tick";
	}
}
