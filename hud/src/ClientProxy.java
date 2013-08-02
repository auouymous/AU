package com.qzx.au.hud;

import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import com.qzx.au.hud.CommonProxy;
import com.qzx.au.hud.TickHandlerHUD;
import com.qzx.au.hud.KeyHandlerHUD;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	public static TickHandlerHUD tickHandler = new TickHandlerHUD();
	public static KeyHandlerHUD keyHandler;

	public static String keyH = "AU HUD";
	public static String keyI = "AU HUD Inspector";

	@Override
	public void registerRenderers(){}

	@Override
	public void registerHandlers(){
		super.registerHandlers();

		KeyBinding[] keys = {
			new KeyBinding(keyH, Keyboard.KEY_H),
			new KeyBinding(keyI, Keyboard.KEY_I)
		};
		boolean[] repeats = {
			false,
			false
		};
		this.keyHandler = new KeyHandlerHUD(keys, repeats);
		KeyBindingRegistry.registerKeyBinding(this.keyHandler);

		TickRegistry.registerTickHandler(this.tickHandler, Side.CLIENT);
	}

	@Override
	public void postInit(){
//		KeybindManagerHUD.readInKeybinds();
	}
}
