package com.qzx.au.hud;

import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;

import java.util.EnumSet;

import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeyHandlerHUD extends KeyHandler {
	public int keyCodeHUD;
	public boolean ignoreHudKey = false;

	public static KeyBinding keyOptions = new KeyBinding("AU HUD Options", Keyboard.KEY_H);
	public static KeyBinding keyInspector = new KeyBinding("AU HUD Inspector", Keyboard.KEY_I);
	public static KeyBinding keyZoom = new KeyBinding("AU HUD Zoom", Keyboard.CHAR_NONE);
	private static KeyBinding[] keys = {
		keyOptions,
		keyInspector,
		keyZoom
	};
	private static boolean[] repeats = {
		false,
		false,
		false
	};

	//////////

	private EnumSet tickTypes = EnumSet.of(TickType.CLIENT);

	public KeyHandlerHUD(){
		super(KeyHandlerHUD.keys, KeyHandlerHUD.repeats);
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

		if(kb == KeyHandlerHUD.keyInspector){
			// toggle inspector
			Cfg.show_inspector = (Cfg.show_inspector ? false : true);
		} else if(kb == KeyHandlerHUD.keyOptions){
			// open HUD configuration
			this.keyCodeHUD = kb.keyCode;
			if(mc.inGameHasFocus && this.ignoreHudKey == false)
				mc.thePlayer.openGui(THIS_MOD.instance, Guis.HUD_OPTIONS, mc.theWorld, 0, 0, 0);
			this.ignoreHudKey = false;
		}
	}

	@Override
	public EnumSet<TickType> ticks(){
		return tickTypes;
	}
}
