package com.qzx.au.hud;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

import com.qzx.au.core.Button;
import com.qzx.au.core.UI;

@SideOnly(Side.CLIENT)
public class GuiShopSignsOptions extends GuiScreen {
	private static int CONFIG_WINDOW_WIDTH = 230;

	private UI ui = new UI();
	private int window_height = 0;

	private GuiScreen parentScreen;
	public GuiShopSignsOptions(EntityPlayer player, GuiScreen parent){
		this.ui.setLineHeight(23);
		this.parentScreen = parent;
	}

	private void drawTitle(){
		this.ui.setCursor((this.width - CONFIG_WINDOW_WIDTH)/2, (this.window_height == 0 ? 2 : (this.height - this.window_height)/2));
		this.ui.drawString(UI.ALIGN_CENTER, "AU HUD Options (Shop Signs)", 0xffffff, CONFIG_WINDOW_WIDTH);
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
		BUTTON_SCALE,

		BUTTON_DONE
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

		this.drawTitle();

		this.ui.lineBreak(7);

		this.addButton(UI.ALIGN_CENTER, ButtonID.BUTTON_SCALE, Cfg.getScaleName(Cfg.shop_signs_hud_scale), 182, 20);
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
		case BUTTON_SCALE:
			Cfg.shop_signs_hud_scale = Cfg.toggleScale(Cfg.shop_signs_hud_scale);
			button.displayString = Cfg.getScaleName(Cfg.shop_signs_hud_scale);
			break;

		default:
			// 'done' button
			this.mc.displayGuiScreen(this.parentScreen);
			return;
		}

		// save config
		Cfg.save();
	}

	@Override
	protected void keyTyped(char key, int keyCode){
		// close when ESC, inventory or "AU HUD" key are pressed
		if(keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.keyCode){
			this.mc.displayGuiScreen(this.parentScreen);
		} else if(keyCode == ClientProxy.keyHandler.keyCodeHUD){
			ClientProxy.keyHandler.ignoreHudKey = true;
			this.mc.displayGuiScreen(this.parentScreen);
		}
	}

	@Override
	public boolean doesGuiPauseGame(){
		return true;
	}
}
