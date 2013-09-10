package com.qzx.au.hud;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

import com.qzx.au.util.UI;

@SideOnly(Side.CLIENT)
public class GuiOptions extends GuiScreen {
	private static int CONFIG_WINDOW_WIDTH = 230;
	private static String HUD_NAME_INFO = "Info HUD";
	private static String HUD_NAME_ARMOR = "Armor HUD";
	private static String HUD_NAME_POTION = "Potion HUD";

	private UI ui;
	private int window_height = 0;

	private EntityPlayer player;
	public GuiOptions(EntityPlayer player){
		this.ui = new UI();
		this.ui.setLineHeight(23);
	}

	private void drawTitle(){
		this.ui.setCursor((this.width - CONFIG_WINDOW_WIDTH)/2, (this.window_height == 0 ? 2 : (this.height - this.window_height)/2));
		this.ui.drawStringCentered("AU HUD Options", 0xffffff, CONFIG_WINDOW_WIDTH);
		this.ui.lineBreak(10);
	}

	@Override
	public void drawScreen(int x, int y, float f){
		// draw shadow
		this.drawDefaultBackground();

		// draw window
//		int tex = this.mc.renderEngine.getTexture(CONFIG_WINDOW_PNG);
//		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//		this.mc.renderEngine.bindTexture(tex);
//		this.drawTexturedModalRect((this.width - CONFIG_WINDOW_WIDTH)/2, (this.height - CONFIG_WINDOW_HEIGHT)/2, 0, 0, CONFIG_WINDOW_WIDTH, CONFIG_WINDOW_HEIGHT);

		// draw settings title
		this.drawTitle();

		// draw controls
		super.drawScreen(x, y, f);
	}

	public enum ButtonID {
		BUTTON_INFO_HUD, BUTTON_INFO_HUD_OPTIONS,
		BUTTON_ARMOR_HUD, BUTTON_ARMOR_HUD_OPTIONS,
		BUTTON_POTION_HUD, BUTTON_POTION_HUD_OPTIONS,

		BUTTON_INSPECTOR,

		BUTTON_DONE
	}

	private GuiButton addStateButton(ButtonID id, String s, boolean value, int width, int height){
		GuiButton button = this.ui.newStateButton(id.ordinal(), s, width, height, value, "OFF", "ON");
		#ifdef MC147
		this.controlList.add(button);
		#else
		this.buttonList.add(button);
		#endif
		return button;
	}
	private GuiButton addButton(ButtonID id, String s, int width, int height){
		GuiButton button = this.ui.newButton(id.ordinal(), s, width, height);
		#ifdef MC147
		this.controlList.add(button);
		#else
		this.buttonList.add(button);
		#endif
		return button;
	}
	private GuiButton addButtonCentered(ButtonID id, String s, int width, int height){
		GuiButton button = this.ui.newButtonCentered(id.ordinal(), s, width, height, CONFIG_WINDOW_WIDTH);
		#ifdef MC147
		this.controlList.add(button);
		#else
		this.buttonList.add(button);
		#endif
		return button;
	}

	private String getEnableButtonName(String name, boolean value){
		return String.format("%s %s", (value ? "Disable" : "Enable"), name);
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
		this.addButton(ButtonID.BUTTON_INFO_HUD, getEnableButtonName(HUD_NAME_INFO, Cfg.enable_info_hud), 180, 20);
		this.ui.drawSpace(10);
		this.addButton(ButtonID.BUTTON_INFO_HUD_OPTIONS, "...", 40, 20);
		this.ui.lineBreak();

		this.ui.lineBreak(7);
		this.addButton(ButtonID.BUTTON_ARMOR_HUD, getEnableButtonName(HUD_NAME_ARMOR, Cfg.enable_armor_hud), 180, 20);
		this.ui.drawSpace(10);
		this.addButton(ButtonID.BUTTON_ARMOR_HUD_OPTIONS, "...", 40, 20);
		this.ui.lineBreak();

		this.ui.lineBreak(7);
		this.addButton(ButtonID.BUTTON_POTION_HUD, getEnableButtonName(HUD_NAME_POTION, Cfg.enable_potion_hud), 180, 20);
		this.ui.drawSpace(10);
		this.addButton(ButtonID.BUTTON_POTION_HUD_OPTIONS, "...", 40, 20);
		this.ui.lineBreak();

		this.ui.lineBreak(7);
		this.addStateButton(ButtonID.BUTTON_INSPECTOR, "Inspector", Cfg.show_inspector, 230, 20);
		this.ui.lineBreak();

		this.ui.lineBreak(7);
		this.addButtonCentered(ButtonID.BUTTON_DONE, "Done", 100, 20);

		if(this.window_height == 0){
			// vertically center UI
			this.ui.lineBreak();
			this.window_height = this.ui.y;
			this.initGui();
		}
	}

	private void updateButton(GuiButton button, boolean value){
		String t = button.displayString;
		button.displayString = t.substring(0, t.indexOf(":")) + (value ? ": ON" : ": OFF");
	}

	@Override
	public void actionPerformed(GuiButton button){
		boolean s = false;

		// toggle config state
		switch(ButtonID.values()[button.id]){
		case BUTTON_INFO_HUD:		s = Cfg.enable_info_hud		= (Cfg.enable_info_hud		? false : true);	break;
		case BUTTON_ARMOR_HUD:		s = Cfg.enable_armor_hud	= (Cfg.enable_armor_hud		? false : true);	break;
		case BUTTON_POTION_HUD:		s = Cfg.enable_potion_hud	= (Cfg.enable_potion_hud	? false : true);	break;
		case BUTTON_INSPECTOR:		s = Cfg.show_inspector		= (Cfg.show_inspector		? false : true);	break;
		case BUTTON_INFO_HUD_OPTIONS:
			// option info options
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
		default:
			// 'done' button
			this.mc.thePlayer.closeScreen();
			return;
		}

		// update button state
		if(button.id == ButtonID.BUTTON_INFO_HUD.ordinal())
			button.displayString = getEnableButtonName(HUD_NAME_INFO, Cfg.enable_info_hud);
		else if(button.id == ButtonID.BUTTON_ARMOR_HUD.ordinal())
			button.displayString = getEnableButtonName(HUD_NAME_ARMOR, Cfg.enable_armor_hud);
		else if(button.id == ButtonID.BUTTON_POTION_HUD.ordinal())
			button.displayString = getEnableButtonName(HUD_NAME_POTION, Cfg.enable_potion_hud);
		else
			this.updateButton(button, s);

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
