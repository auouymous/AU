package com.qzx.au.hud;

#if defined MC147 || defined MC152 || defined MC164
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
#define IMPLEMENTS_ITICKHANDLER implements ITickHandler
#define USE_TICK_REGISTRY
#else
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
#define IMPLEMENTS_ITICKHANDLER
#endif

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;

#ifdef USE_TICK_REGISTRY
import java.util.EnumSet;
#endif

@SideOnly(Side.CLIENT)
public class TickHandlerHUD IMPLEMENTS_ITICKHANDLER {
	private InfoHUD infoHUD = new InfoHUD();
	private ArmorHUD armorHUD = new ArmorHUD();
	private PotionHUD potionHUD = new PotionHUD();
	private ShopSignsHUD shopSignsHUD = new ShopSignsHUD();

	private boolean zooming = false;
	private float mouseSensitivity = 0.5F;

	public static int force_hud = 0;
	public static final int HUD_INFO			= 1;
	public static final int HUD_ARMOR			= 2;
	public static final int HUD_POTION			= 3;
//	public static final int HUD_SHOPSIGNS		= 4;

	//////////

	#ifdef USE_TICK_REGISTRY
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData){}
	#endif

	#ifdef USE_TICK_REGISTRY
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData){
	#else
	FML_SUBSCRIBE
	public void onTick(RenderTickEvent event){
	#endif
		Minecraft mc = Minecraft.getMinecraft();

		if(TickHandlerHUD.force_hud == 0)
			if(mc.gameSettings.showDebugInfo || !(mc.inGameHasFocus || mc.currentScreen == null || mc.currentScreen instanceof GuiChat) || mc.thePlayer == null)
				return;

		ScaledResolution screen = new SCALED_RESOLUTION(mc);

		Failure.reset();

		try {
			if(TickHandlerHUD.force_hud != 0){
				switch(TickHandlerHUD.force_hud){
				case TickHandlerHUD.HUD_INFO:			this.infoHUD.draw(mc, screen, mc.thePlayer); break;
				case TickHandlerHUD.HUD_ARMOR:			this.armorHUD.draw(mc, screen, mc.thePlayer); break;
				case TickHandlerHUD.HUD_POTION:			this.potionHUD.draw(mc, screen, mc.thePlayer); break;
//				case TickHandlerHUD.HUD_SHOPSIGNS:		this.shopSignsHUD.draw(mc, screen, mc.thePlayer); break;
				}
			} else {
				if(Cfg.enable_info_hud && (!(mc.currentScreen instanceof GuiChat) || Cfg.always_show_info_hud)) this.infoHUD.draw(mc, screen, mc.thePlayer);
				if(Cfg.enable_armor_hud && (!(mc.currentScreen instanceof GuiChat) || Cfg.always_show_armor_hud)) this.armorHUD.draw(mc, screen, mc.thePlayer);
				if(Cfg.enable_potion_hud && (!(mc.currentScreen instanceof GuiChat) || Cfg.always_show_potion_hud)) this.potionHUD.draw(mc, screen, mc.thePlayer);
				if(Cfg.enable_shop_signs_hud && (!(mc.currentScreen instanceof GuiChat) || Cfg.always_show_shop_signs_hud)) this.shopSignsHUD.draw(mc, screen, mc.thePlayer);
			}
		} catch(Exception e){
			Failure.log("tickEnd/onTick catch-all");
		}

		Failure.show(screen);

		if(!(mc.currentScreen instanceof GuiChat)){
			// zoom player view
			if(KeyHandlerHUD.keyZoom.IS_KEY_PRESSED){
				if(!zooming){
					this.mouseSensitivity = mc.gameSettings.mouseSensitivity;
					mc.gameSettings.mouseSensitivity /= 10.0F;
				}
				this.zooming = true;
				Hacks.setCameraZoom(5.0D);
			} else if(zooming){
				this.zooming = false;
				mc.gameSettings.mouseSensitivity = this.mouseSensitivity;
				Hacks.setCameraZoom(1.0D);
			}
		}
	}

	#ifdef USE_TICK_REGISTRY
	@Override
	public EnumSet<TickType> ticks(){
		return EnumSet.of(TickType.RENDER);
	}
	#endif

	#ifdef USE_TICK_REGISTRY
	@Override
	public String getLabel(){
		return THIS_MOD.modID+": Client Render Tick";
	}
	#endif
}
