package com.qzx.au.hud;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;

import com.qzx.au.core.Button;
import com.qzx.au.core.UI;

@SideOnly(Side.CLIENT)
public class GuiOptions extends GuiScreen {
	private static int CONFIG_WINDOW_WIDTH = 230;

	private UI ui;
	private int window_height = 0;

	private EntityPlayer player;
	public GuiOptions(EntityPlayer player){
		this.ui = new UI();
		this.ui.setLineHeight(23);
	}

	private void drawTitle(){
		this.ui.setCursor((this.width - CONFIG_WINDOW_WIDTH)/2, (this.window_height == 0 ? 2 : (this.height - this.window_height)/2));
		this.ui.drawString(UI.ALIGN_CENTER, "AU HUD Options", 0xffffff, CONFIG_WINDOW_WIDTH);
		this.ui.lineBreak(10);
	}

	@Override
	public void drawScreen(int x, int y, float f){
		// draw shadow
		this.drawDefaultBackground();

		// draw settings title
		this.drawTitle();

		// draw controls
		super.drawScreen(x, y, f);
	}

	public enum ButtonID {
		BUTTON_SERVER_INFO,

		BUTTON_INFO_HUD, BUTTON_INFO_HUD_OPTIONS,
		BUTTON_ARMOR_HUD, BUTTON_ARMOR_HUD_OPTIONS,
		BUTTON_POTION_HUD, BUTTON_POTION_HUD_OPTIONS,
		BUTTON_SHOP_SIGNS_HUD, BUTTON_SHOP_SIGNS_HUD_OPTIONS,

		BUTTON_INSPECTOR, BUTTON_ADVANCED_INSPECTOR,

		BUTTON_DONE
	}

	private GuiButton addStateButton(int align, ButtonID id, String s, boolean active, int width, int height){
		GuiButton button = this.ui.newButton(align, id.ordinal(), s, width, height, CONFIG_WINDOW_WIDTH).initState(active);
		#ifdef MC147
		this.controlList.add(button);
		#else
		this.buttonList.add(button);
		#endif
		return button;
	}
	private GuiButton addButton(int align, ButtonID id, String s, int width, int height){
		GuiButton button = this.ui.newButton(align, id.ordinal(), s, width, height, CONFIG_WINDOW_WIDTH);
		#ifdef MC147
		this.controlList.add(button);
		#else
		this.buttonList.add(button);
		#endif
		return button;
	}

	@Override
	public void initGui(){
		#ifdef MC147
		this.controlList.clear();
		#else
		this.buttonList.clear();
		#endif

		TickHandlerHUD.force_hud = 0;
		this.drawTitle();

		this.ui.lineBreak(7);
		this.addButton(UI.ALIGN_CENTER, ButtonID.BUTTON_SERVER_INFO, "Server Info...", 100, 20);
		this.ui.lineBreak();

		this.ui.lineBreak(7);
		this.addStateButton(UI.ALIGN_LEFT, ButtonID.BUTTON_INFO_HUD, "Info HUD", Cfg.enable_info_hud, 180, 20);
		this.ui.drawSpace(10);
		this.addButton(UI.ALIGN_LEFT, ButtonID.BUTTON_INFO_HUD_OPTIONS, "...", 40, 20);
		this.ui.lineBreak();

		this.ui.lineBreak(7);
		this.addStateButton(UI.ALIGN_LEFT, ButtonID.BUTTON_ARMOR_HUD, "Armor HUD", Cfg.enable_armor_hud, 180, 20);
		this.ui.drawSpace(10);
		this.addButton(UI.ALIGN_LEFT, ButtonID.BUTTON_ARMOR_HUD_OPTIONS, "...", 40, 20);
		this.ui.lineBreak();

		this.ui.lineBreak(7);
		this.addStateButton(UI.ALIGN_LEFT, ButtonID.BUTTON_POTION_HUD, "Potion HUD", Cfg.enable_potion_hud, 180, 20);
		this.ui.drawSpace(10);
		this.addButton(UI.ALIGN_LEFT, ButtonID.BUTTON_POTION_HUD_OPTIONS, "...", 40, 20);
		this.ui.lineBreak();

		this.ui.lineBreak(7);
		this.addStateButton(UI.ALIGN_LEFT, ButtonID.BUTTON_SHOP_SIGNS_HUD, "Shop Signs HUD", Cfg.enable_shop_signs_hud, 180, 20);
		this.ui.drawSpace(10);
		this.addButton(UI.ALIGN_LEFT, ButtonID.BUTTON_SHOP_SIGNS_HUD_OPTIONS, "...", 40, 20);
		this.ui.lineBreak();

		this.ui.lineBreak(7);
		this.addStateButton(UI.ALIGN_LEFT, ButtonID.BUTTON_INSPECTOR, "Inspector", Cfg.show_inspector, 110, 20);
		this.ui.drawSpace(10);
		this.addStateButton(UI.ALIGN_LEFT, ButtonID.BUTTON_ADVANCED_INSPECTOR, "Advanced", Cfg.enable_advanced_inspector, 110, 20);
		this.ui.lineBreak();

		this.ui.lineBreak(7);
		this.addButton(UI.ALIGN_CENTER, ButtonID.BUTTON_DONE, "Done", 100, 20);

		if(this.window_height == 0){
			// vertically center UI
			this.ui.lineBreak();
			this.window_height = this.ui.getY();
			this.initGui();
		}
	}

	@Override
	public void actionPerformed(GuiButton button){
		// toggle config state
		switch(ButtonID.values()[button.id]){
		case BUTTON_INFO_HUD:				((Button)button).active =  Cfg.enable_info_hud				= (Cfg.enable_info_hud				? false : true);	break;
		case BUTTON_ARMOR_HUD:				((Button)button).active =  Cfg.enable_armor_hud				= (Cfg.enable_armor_hud				? false : true);	break;
		case BUTTON_POTION_HUD:				((Button)button).active =  Cfg.enable_potion_hud			= (Cfg.enable_potion_hud			? false : true);	break;
		case BUTTON_SHOP_SIGNS_HUD:			((Button)button).active =  Cfg.enable_shop_signs_hud		= (Cfg.enable_shop_signs_hud		? false : true);	break;
		case BUTTON_INSPECTOR:				((Button)button).active =  Cfg.show_inspector				= (Cfg.show_inspector				? false : true);	break;
		case BUTTON_ADVANCED_INSPECTOR:		((Button)button).active =  Cfg.enable_advanced_inspector	= (Cfg.enable_advanced_inspector	? false : true);	break;
		case BUTTON_SERVER_INFO:
			// open server info
			this.mc.displayGuiScreen(new GuiServerInfo(this.player, this));
			return;
		case BUTTON_INFO_HUD_OPTIONS:
			// open info options
			this.mc.displayGuiScreen(new GuiInfoOptions(this.player, this));
			return;
		case BUTTON_ARMOR_HUD_OPTIONS:
			// open armor options
			this.mc.displayGuiScreen(new GuiArmorOptions(this.player, this));
			return;
		case BUTTON_POTION_HUD_OPTIONS:
			// open potion options
			this.mc.displayGuiScreen(new GuiPotionOptions(this.player, this));
			return;
		case BUTTON_SHOP_SIGNS_HUD_OPTIONS:
			// open shop signs options
			this.mc.displayGuiScreen(new GuiShopSignsOptions(this.player, this));
			return;
		default:
			// 'done' button
			this.mc.thePlayer.closeScreen();
			return;
		}

		// save config
		Cfg.save();
	}

	@Override
	protected void keyTyped(char key, int keyCode){
		// close when ESC, inventory or "AU HUD" key are pressed
		if(keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.keyCode){
			this.mc.thePlayer.closeScreen();
		} else if(keyCode == ClientProxy.keyHandler.keyCodeHUD){
			ClientProxy.keyHandler.ignoreHudKey = true;
			this.mc.thePlayer.closeScreen();
		}
	}

	@Override
	public boolean doesGuiPauseGame(){
		return true;
	}
}
