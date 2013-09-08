package com.qzx.au.hud;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

import com.qzx.au.util.UI;

@SideOnly(Side.CLIENT)
public class GuiArmorOptions extends GuiScreen {
	private static int CONFIG_WINDOW_WIDTH = 230;

	private UI ui;
	private int window_height = 0;

	public GuiArmorOptions(EntityPlayer player){
		this.ui = new UI();
		this.ui.setLineHeight(23);
	}

	private void drawTitle(){
		this.ui.setCursor((this.width - CONFIG_WINDOW_WIDTH)/2, (this.window_height == 0 ? 2 : (this.height - this.window_height)/2));
		this.ui.drawStringCentered("AU HUD Options (Armor)", 0xffffff, CONFIG_WINDOW_WIDTH);
		this.ui.lineBreak(10);
	}

	int posX_x, posX_y;
	private void drawPositionX(){
		this.ui.x = posX_x;
		this.ui.y = posX_y + 5;
		this.ui.x = this.ui.drawStringCentered(String.format("x: %d", Cfg.armor_hud_x), 0xffffff, 40);
		this.ui.drawString("x:", 0xaaaaaa);
		this.ui.x = posX_x + 40;
		this.ui.y = posX_y;
	}
	int posY_x, posY_y;
	private void drawPositionY(){
		this.ui.x = posY_x;
		this.ui.y = posY_y + 5;
		this.ui.x = this.ui.drawStringCentered(String.format("y: %d", Cfg.armor_hud_y), 0xffffff, 40);
		this.ui.drawString("y:", 0xaaaaaa);
		this.ui.x = posY_x + 40;
		this.ui.y = posY_y;
	}

	@Override
	public void drawScreen(int x, int y, float f){
		// draw shadow
		this.drawDefaultBackground();

		// draw settings title
		this.drawTitle();

		// draw position values
		this.drawPositionX();
		this.drawPositionY();

		// draw controls
		super.drawScreen(x, y, f);
	}

	public enum ButtonID {
		BUTTON_X_DN, BUTTON_X_UP, BUTTON_Y_DN, BUTTON_Y_UP,

		BUTTON_CORNER,

		BUTTON_DONE
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

	@Override
	public void initGui(){
		#ifdef MC147
		this.controlList.clear();
		#else
		this.buttonList.clear();
		#endif

		this.drawTitle();

		this.ui.lineBreak(7);
		this.ui.drawSpace((int)Math.floor((CONFIG_WINDOW_WIDTH - 2*80)/3));
		this.addButton(ButtonID.BUTTON_X_DN, "-", 20, 20);
		this.posX_x = this.ui.x;
		this.posX_y = this.ui.y;
		this.drawPositionX();
		this.addButton(ButtonID.BUTTON_X_UP, "+", 20, 20);
		this.ui.drawSpace((int)Math.floor((CONFIG_WINDOW_WIDTH - 2*80)/3));
		this.addButton(ButtonID.BUTTON_Y_DN, "-", 20, 20);
		this.posY_x = this.ui.x;
		this.posY_y = this.ui.y;
		this.drawPositionY();
		this.addButton(ButtonID.BUTTON_Y_UP, "+", 20, 20);
		this.ui.lineBreak();

		this.addButtonCentered(ButtonID.BUTTON_CORNER, Cfg.getCornerName(Cfg.armor_hud_corner), 182, 20);
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

	@Override
	public void actionPerformed(GuiButton button){
		boolean s = false, update = true;

		// toggle config state
		switch(ButtonID.values()[button.id]){
		case BUTTON_X_DN:		Cfg.armor_hud_x -= 1;	update = false;		break;
		case BUTTON_X_UP:		Cfg.armor_hud_x += 1;	update = false;		break;
		case BUTTON_Y_DN:		Cfg.armor_hud_y -= 1;	update = false;		break;
		case BUTTON_Y_UP:		Cfg.armor_hud_y += 1;	update = false;		break;

		case BUTTON_CORNER:
			Cfg.armor_hud_corner = (Cfg.armor_hud_corner < Cfg.corners.length-1 ? Cfg.armor_hud_corner+1 : 0);
			button.displayString = Cfg.getCornerName(Cfg.armor_hud_corner);
			break;

		default:
			// 'done' button
			this.mc.thePlayer.closeScreen();
			this.mc.thePlayer.openGui(AUHud.instance, Guis.HUD_OPTIONS, this.mc.theWorld, 0, 0, 0);
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
			this.mc.thePlayer.openGui(AUHud.instance, Guis.HUD_OPTIONS, this.mc.theWorld, 0, 0, 0);
		} else if(keyCode == ClientProxy.keyHandler.keyCodeHUD){
			ClientProxy.keyHandler.ignoreHudKey = true;
			this.mc.thePlayer.closeScreen();
			this.mc.thePlayer.openGui(AUHud.instance, Guis.HUD_OPTIONS, this.mc.theWorld, 0, 0, 0);
		}
	}

	@Override
	public boolean doesGuiPauseGame(){
		return true;
	}
}
