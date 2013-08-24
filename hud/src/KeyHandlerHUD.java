package com.qzx.au.hud;

import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;

import java.util.EnumSet;

@SideOnly(Side.CLIENT)
public class KeyHandlerHUD extends KeyHandler {
	public int keyCodeHUD;
	public boolean ignoreHudKey = false;

	private EnumSet tickTypes = EnumSet.of(TickType.CLIENT);

	public KeyHandlerHUD(KeyBinding[] keyBindings, boolean[] repeatings){
		super(keyBindings, repeatings);
	}

	@Override
	public String getLabel(){
		return "AUHUDKey";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat){}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd){
		// called twice per press, only trigger on last
		if(!tickEnd)
			return;

		// don't trigger for keys entered in chat, signs, etc
		Minecraft mc = Minecraft.getMinecraft();
		if(mc.gameSettings.showDebugInfo || !(mc.inGameHasFocus || mc.currentScreen == null) || mc.thePlayer == null)
			return;

		if(kb.keyDescription.equals(ClientProxy.keyI)){
			// toggle block inspector, only if block name is enabled
			if(Cfg.show_block_name)
				Cfg.show_block_inspector
					= (Cfg.show_block_inspector ? false : true);
		} else if(kb.keyDescription.equals(ClientProxy.keyH)){
			// open HUD configuration
			this.keyCodeHUD = kb.keyCode;
			if(mc.inGameHasFocus && this.ignoreHudKey == false)
				mc.thePlayer.openGui(AUHud.instance, Guis.HUD_OPTIONS, mc.theWorld, 0, 0, 0);
			this.ignoreHudKey = false;
		}
	}

	@Override
	public EnumSet<TickType> ticks(){
		return tickTypes;
	}
}
