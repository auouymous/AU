package com.qzx.au.hud;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

import com.qzx.au.util.UI;

@SideOnly(Side.CLIENT)
public class GuiInfoOptions extends GuiScreen {
	private static int CONFIG_WINDOW_WIDTH = 230;

	private UI ui;
	private int window_height = 0;

	private GuiScreen parentScreen;
	public GuiInfoOptions(EntityPlayer player, GuiScreen parent){
		this.ui = new UI();
		this.ui.setLineHeight(23);
		this.parentScreen = parent;
	}

	private void drawTitle(){
		this.ui.setCursor((this.width - CONFIG_WINDOW_WIDTH)/2, (this.window_height == 0 ? 2 : (this.height - this.window_height)/2));
		this.ui.drawStringCentered("AU HUD Options (Info)", 0xffffff, CONFIG_WINDOW_WIDTH);
		this.ui.lineBreak(10);
	}

	int posX_x, posX_y;
	private void drawPositionX(){
		this.ui.x = posX_x;
		this.ui.y = posX_y + 5;
		this.ui.x = this.ui.drawStringCentered(String.format("x: %d", Cfg.info_hud_x), 0xffffff, 40);
		this.ui.drawString("x:", 0xaaaaaa);
		this.ui.x = posX_x + 40;
		this.ui.y = posX_y;
	}
	int posY_x, posY_y;
	private void drawPositionY(){
		this.ui.x = posY_x;
		this.ui.y = posY_y + 5;
		this.ui.x = this.ui.drawStringCentered(String.format("y: %d", Cfg.info_hud_y), 0xffffff, 40);
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

		BUTTON_WORLD, BUTTON_BIOME,
		BUTTON_POSITION, BUTTON_POSITION_EYES,
		BUTTON_LIGHT, BUTTON_TIME, BUTTON_WEATHER,
		BUTTON_USED_INVENTORY, BUTTON_ANIMATE_USED_INVENTORY,
		BUTTON_FPS, BUTTON_CHUNK_UPDATES,
		BUTTON_ENTITIES, BUTTON_PARTICLES,
		BUTTON_BLOCK_NAME,

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

	private GuiButton button_position = null;
	private GuiButton button_position_eyes = null;
	private GuiButton button_block_name = null;

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
		this.ui.lineBreak(7);

		this.addStateButton(ButtonID.BUTTON_WORLD, "World", Cfg.show_world, 110, 20);
		this.ui.drawSpace(10);
		this.addStateButton(ButtonID.BUTTON_BIOME, "Biome", Cfg.show_biome, 110, 20);
		this.ui.lineBreak();

		this.button_position = this.addStateButton(ButtonID.BUTTON_POSITION, "Position", Cfg.show_position, 110, 20);
		this.ui.drawSpace(10);
		this.button_position_eyes = this.addStateButton(ButtonID.BUTTON_POSITION_EYES, "At Eyes", Cfg.show_position_eyes, 110, 20);
		this.ui.lineBreak();

		this.addStateButton(ButtonID.BUTTON_LIGHT, "Light", Cfg.show_light, 70, 20);
		this.ui.drawSpace(10);
		this.addStateButton(ButtonID.BUTTON_TIME, "Time", Cfg.show_time, 70, 20);
		this.ui.drawSpace(10);
		this.addStateButton(ButtonID.BUTTON_WEATHER, "Weather", Cfg.show_weather, 70, 20);
		this.ui.lineBreak();

		this.addStateButton(ButtonID.BUTTON_USED_INVENTORY, "Used Inventory", Cfg.show_used_inventory, 150, 20);
		this.ui.drawSpace(10);
		this.addStateButton(ButtonID.BUTTON_ANIMATE_USED_INVENTORY, "Animate", Cfg.animate_used_inventory, 70, 20);
		this.ui.lineBreak();

		this.addStateButton(ButtonID.BUTTON_FPS, "FPS", Cfg.show_fps, 110, 20);
		this.ui.drawSpace(10);
		this.addStateButton(ButtonID.BUTTON_CHUNK_UPDATES, "Chunk Updates", Cfg.show_chunk_updates, 110, 20);
		this.ui.lineBreak();

		this.addStateButton(ButtonID.BUTTON_ENTITIES, "Entities", Cfg.show_entities, 110, 20);
		this.ui.drawSpace(10);
		this.addStateButton(ButtonID.BUTTON_PARTICLES, "Particles", Cfg.show_particles, 110, 20);
		this.ui.lineBreak();

		this.button_block_name = this.addStateButton(ButtonID.BUTTON_BLOCK_NAME, "Block/Mob Name", Cfg.show_block_name, 230, 20);
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
		boolean s = false, update = true;

		// toggle config state
		switch(ButtonID.values()[button.id]){
		case BUTTON_X_DN:				Cfg.info_hud_x -= 1;	update = false;		break;
		case BUTTON_X_UP:				Cfg.info_hud_x += 1;	update = false;		break;
		case BUTTON_Y_DN:				Cfg.info_hud_y -= 1;	update = false;		break;
		case BUTTON_Y_UP:				Cfg.info_hud_y += 1;	update = false;		break;

		case BUTTON_WORLD:						s = Cfg.show_world				= (Cfg.show_world				? false : true);	break;
		case BUTTON_BIOME:						s = Cfg.show_biome				= (Cfg.show_biome				? false : true);	break;
		case BUTTON_POSITION:					s = Cfg.show_position			= (Cfg.show_position			? false : true);	break;
		case BUTTON_POSITION_EYES:				s = Cfg.show_position_eyes		= (Cfg.show_position_eyes		? false : true);	break;
		case BUTTON_LIGHT:						s = Cfg.show_light				= (Cfg.show_light				? false : true);	break;
		case BUTTON_TIME:						s = Cfg.show_time				= (Cfg.show_time				? false : true);	break;
		case BUTTON_WEATHER:					s = Cfg.show_weather			= (Cfg.show_weather				? false : true);	break;
		case BUTTON_USED_INVENTORY:				s = Cfg.show_used_inventory		= (Cfg.show_used_inventory		? false : true);	break;
		case BUTTON_ANIMATE_USED_INVENTORY:		s = Cfg.animate_used_inventory	= (Cfg.animate_used_inventory	? false : true);	break;
		case BUTTON_FPS:						s = Cfg.show_fps				= (Cfg.show_fps					? false : true);	break;
		case BUTTON_CHUNK_UPDATES:				s = Cfg.show_chunk_updates		= (Cfg.show_chunk_updates		? false : true);	break;
		case BUTTON_ENTITIES:					s = Cfg.show_entities			= (Cfg.show_entities			? false : true);	break;
		case BUTTON_PARTICLES:					s = Cfg.show_particles			= (Cfg.show_particles			? false : true);	break;
		case BUTTON_BLOCK_NAME:					s = Cfg.show_block_name			= (Cfg.show_block_name			? false : true);	break;

		default:
			// 'done' button
			this.mc.displayGuiScreen(this.parentScreen);
			return;
		}

		// update dependent states
		if(button == this.button_position && s == false){
			// position turned off, turn off eyes
			Cfg.show_position_eyes = false;
			this.updateButton(this.button_position_eyes, false);
		}
		if(button == this.button_position_eyes && s == true){
			// eyes turned on, turn on position
			Cfg.show_position = true;
			this.updateButton(this.button_position, true);
		}

		// update button state
		if(update)
			this.updateButton(button, s);

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
