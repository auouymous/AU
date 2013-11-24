package com.qzx.au.hud;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

import com.qzx.au.util.Button;
import com.qzx.au.util.UI;

@SideOnly(Side.CLIENT)
public class GuiArmorOptions extends GuiScreen {
	private static int CONFIG_WINDOW_WIDTH = 230;

	private UI ui = new UI();
	private int window_height = 0;

	private GuiScreen parentScreen;
	public GuiArmorOptions(EntityPlayer player, GuiScreen parent){
		this.ui.setLineHeight(23);
		this.parentScreen = parent;
	}

	private void drawTitle(){
		this.ui.setCursor((this.width - CONFIG_WINDOW_WIDTH)/2, (this.window_height == 0 ? 2 : (this.height - this.window_height)/2));
		this.ui.drawString(UI.ALIGN_CENTER, "AU HUD Options (Armor)", 0xffffff, CONFIG_WINDOW_WIDTH);
		this.ui.lineBreak(10);
	}

	int posX_x, posX_y;
	private void drawPositionX(){
		this.ui.x = posX_x;
		this.ui.y = posX_y + 5;
		this.ui.x = this.ui.drawString(UI.ALIGN_CENTER, String.format("x: %d", Cfg.armor_hud_x), 0xffffff, 40);
		this.ui.drawString("x:", 0xaaaaaa);
		this.ui.x = posX_x - 30;
		this.ui.drawString(UI.ALIGN_RIGHT, "right-click by 10", 0x555555, 0);
		this.ui.x = posX_x + 40;
		this.ui.y = posX_y;
	}
	int posY_x, posY_y;
	private void drawPositionY(){
		this.ui.x = posY_x;
		this.ui.y = posY_y + 5;
		this.ui.x = this.ui.drawString(UI.ALIGN_CENTER, String.format("y: %d", Cfg.armor_hud_y), 0xffffff, 40);
		this.ui.drawString("y:", 0xaaaaaa);
		this.ui.x = posY_x + 40 + 30;
		this.ui.drawString(UI.ALIGN_LEFT, "middle-click by 20", 0x555555, 0);
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

		BUTTON_CORNER, BUTTON_ALWAYS_SHOW, BUTTON_DURABILITY,

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

		this.drawTitle();

		this.ui.lineBreak(7);
		this.ui.drawSpace((int)Math.floor((CONFIG_WINDOW_WIDTH - 2*80)/3));
		this.addButton(UI.ALIGN_LEFT, ButtonID.BUTTON_X_DN, "-", 20, 20);
		this.posX_x = this.ui.x;
		this.posX_y = this.ui.y;
		this.drawPositionX();
		this.addButton(UI.ALIGN_LEFT, ButtonID.BUTTON_X_UP, "+", 20, 20);
		this.ui.drawSpace((int)Math.floor((CONFIG_WINDOW_WIDTH - 2*80)/3));
		this.addButton(UI.ALIGN_LEFT, ButtonID.BUTTON_Y_DN, "-", 20, 20);
		this.posY_x = this.ui.x;
		this.posY_y = this.ui.y;
		this.drawPositionY();
		this.addButton(UI.ALIGN_LEFT, ButtonID.BUTTON_Y_UP, "+", 20, 20);
		this.ui.lineBreak();

		this.addButton(UI.ALIGN_CENTER, ButtonID.BUTTON_CORNER, Cfg.getCornerName(Cfg.armor_hud_corner), 182, 20);
		this.ui.lineBreak();

		this.addStateButton(UI.ALIGN_CENTER, ButtonID.BUTTON_ALWAYS_SHOW, "Show when chat is open", Cfg.always_show_armor_hud, 182, 20);
		this.ui.lineBreak();

		this.addButton(UI.ALIGN_CENTER, ButtonID.BUTTON_DURABILITY, Cfg.getDurabilityName(Cfg.armor_hud_durability), 182, 20);
		this.ui.lineBreak();

		this.ui.lineBreak(7);
		this.addButton(UI.ALIGN_CENTER, ButtonID.BUTTON_DONE, "Done", 100, 20);

		if(this.window_height == 0){
			// vertically center UI
			this.ui.lineBreak();
			this.window_height = this.ui.y;
			this.initGui();
		}
	}

	@Override
	protected void mouseClicked(int cursor_x, int cursor_y, int mouse_button){
		if(mouse_button == 0) super.mouseClicked(cursor_x, cursor_y, mouse_button);
		else {
			#ifdef MC147
			List buttons = this.controlList;
			#else
			List buttons = this.buttonList;
			#endif
			for(int i = 0; i < buttons.size(); i++){
				Button button = (Button)buttons.get(i);
				if(button.mousePressed(this.mc, cursor_x, cursor_y)){
					this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
					this.actionPerformed(button, mouse_button); // 1:right, 2:middle
				}
			}
		}
	}

	public void actionPerformed(GuiButton button, int mouse_button){
		// toggle config state
		switch(ButtonID.values()[button.id]){
		case BUTTON_X_DN:		Cfg.armor_hud_x -= (mouse_button == 1 ? 10 : 20);	break;
		case BUTTON_X_UP:		Cfg.armor_hud_x += (mouse_button == 1 ? 10 : 20);	break;
		case BUTTON_Y_DN:		Cfg.armor_hud_y -= (mouse_button == 1 ? 10 : 20);	break;
		case BUTTON_Y_UP:		Cfg.armor_hud_y += (mouse_button == 1 ? 10 : 20);	break;
		default:
			return;
		}

		// save config
		Cfg.save();
	}

	@Override
	public void actionPerformed(GuiButton button){
		// toggle config state
		switch(ButtonID.values()[button.id]){
		case BUTTON_X_DN:		Cfg.armor_hud_x -= 1;	break;
		case BUTTON_X_UP:		Cfg.armor_hud_x += 1;	break;
		case BUTTON_Y_DN:		Cfg.armor_hud_y -= 1;	break;
		case BUTTON_Y_UP:		Cfg.armor_hud_y += 1;	break;

		case BUTTON_CORNER:
			Cfg.armor_hud_corner = (Cfg.armor_hud_corner < Cfg.corners.length-1 ? Cfg.armor_hud_corner+1 : 0);
			button.displayString = Cfg.getCornerName(Cfg.armor_hud_corner);
			break;

		case BUTTON_ALWAYS_SHOW:
			((Button)button).active = Cfg.always_show_armor_hud = (Cfg.always_show_armor_hud ? false : true);
			break;

		case BUTTON_DURABILITY:
			Cfg.armor_hud_durability = (Cfg.armor_hud_durability < Cfg.durability_values.length-1 ? Cfg.armor_hud_durability+1 : 0);
			button.displayString = Cfg.getDurabilityName(Cfg.armor_hud_durability);
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
