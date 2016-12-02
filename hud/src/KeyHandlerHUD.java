package com.qzx.au.hud;

#if defined MC147 || defined MC152 || defined MC164
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
#define IMPLEMENTS_KEYHANDLER extends KeyHandler
#define USE_KEY_REGISTRY
#else
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.client.registry.ClientRegistry;
#define IMPLEMENTS_KEYHANDLER
#endif

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;

#ifdef USE_KEY_REGISTRY
import java.util.EnumSet;
#endif

import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeyHandlerHUD IMPLEMENTS_KEYHANDLER {
	public int keyCodeHUD;
	public boolean ignoreHudKey = false;

	#ifdef USE_KEY_REGISTRY
	#define NEW_KEYBINDING(name, key, category) new KeyBinding(name, key);
	#else
	#define NEW_KEYBINDING(name, key, category) new KeyBinding(name, key, category);
	#endif

	public static KeyBinding keyOptions		= NEW_KEYBINDING("AU HUD Options", Keyboard.KEY_H, "AU HUD");
	public static KeyBinding keyInspector	= NEW_KEYBINDING("AU HUD Inspector", Keyboard.KEY_I, "AU HUD");
	public static KeyBinding keyZoom		= NEW_KEYBINDING("AU HUD Zoom", Keyboard.CHAR_NONE, "AU HUD");
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

	#ifdef USE_KEY_REGISTRY
	private EnumSet tickTypes = EnumSet.of(TickType.CLIENT);
	#endif

	public KeyHandlerHUD(){
		#ifdef USE_KEY_REGISTRY
		super(KeyHandlerHUD.keys, KeyHandlerHUD.repeats);
		#else
		for(KeyBinding kb : KeyHandlerHUD.keys)
			ClientRegistry.registerKeyBinding(kb);
		#endif
	}

	#ifdef USE_KEY_REGISTRY
	@Override
	public String getLabel(){
		return "AUHUDKey";
	}
	#endif

	#ifdef USE_KEY_REGISTRY
	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat){}
	#endif

	#ifndef USE_KEY_REGISTRY
	private KeyBinding getKeyBinding(){
		int keyCode = Keyboard.getEventKey();
		for(KeyBinding kb : KeyHandlerHUD.keys)
			if(kb.getKeyCode() == keyCode)
				return kb;
		return null;
	}
	#endif

	#ifdef USE_KEY_REGISTRY
	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd){
		// called twice per press, only trigger on last
		if(!tickEnd)
			return;
	#else
	FML_SUBSCRIBE
	public void onKey(KeyInputEvent event){
		KeyBinding kb = this.getKeyBinding();
		if(kb == null) return;
		#ifndef MC172
		// ignore key press events, only process the key release events
		if(Keyboard.getEventKeyState())
			return;
		#endif
	#endif

		// don't trigger for keys entered in chat, signs, etc
		Minecraft mc = Minecraft.getMinecraft();
		if(mc.gameSettings.showDebugInfo || !(mc.inGameHasFocus || mc.currentScreen == null) || mc.thePlayer == null)
			return;

		if(kb == KeyHandlerHUD.keyInspector){
			// toggle inspector
			Cfg.show_inspector = (Cfg.show_inspector ? false : true);
		} else if(kb == KeyHandlerHUD.keyOptions){
			// open HUD configuration
			this.keyCodeHUD = kb.GET_KEY_CODE;
			#ifdef MC172
			if(mc.inGameHasFocus)
			#else
			if(mc.inGameHasFocus && this.ignoreHudKey == false)
			#endif
				mc.thePlayer.openGui(THIS_MOD.instance, Guis.HUD_OPTIONS, mc.theWorld, 0, 0, 0);
			this.ignoreHudKey = false;
		}
	}

	#ifdef USE_KEY_REGISTRY
	@Override
	public EnumSet<TickType> ticks(){
		return tickTypes;
	}
	#endif
}
