package com.qzx.au.hud;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;

import com.qzx.au.util.Button;
import com.qzx.au.util.UI;

@SideOnly(Side.CLIENT)
public class GuiInfoOptions extends GuiScreen {
	private static int CONFIG_WINDOW_WIDTH = 230;

	private UI ui = new UI();
	private int window_height = 0;

	private GuiScreen parentScreen;
	public GuiInfoOptions(EntityPlayer player, GuiScreen parent){
		this.ui.setLineHeight(23);
		this.parentScreen = parent;
	}

	private void drawTitle(){
		this.ui.setCursor((this.width - CONFIG_WINDOW_WIDTH)/2, (this.window_height == 0 ? 2 : (this.height - this.window_height)/2));
		this.ui.drawString(UI.ALIGN_CENTER, "AU HUD Options (Info)", 0xffffff, CONFIG_WINDOW_WIDTH);
		this.ui.lineBreak(10);
	}

	int posX_x, posX_y;
	private void drawPositionX(){
		this.ui.x = posX_x;
		this.ui.y = posX_y + 5;
		this.ui.x = this.ui.drawString(UI.ALIGN_CENTER, String.format("x: %d", Cfg.info_hud_x), 0xffffff, 40);
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
		this.ui.x = this.ui.drawString(UI.ALIGN_CENTER, String.format("y: %d", Cfg.info_hud_y), 0xffffff, 40);
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

		BUTTON_WORLD, BUTTON_BIOME,
		BUTTON_POSITION, BUTTON_POSITION_EYES,
		BUTTON_LIGHT, BUTTON_TIME, BUTTON_WEATHER,
		BUTTON_USED_INVENTORY, BUTTON_ANIMATE_USED_INVENTORY,
		BUTTON_FPS, BUTTON_CHUNK_UPDATES,
		BUTTON_ENTITIES, BUTTON_PARTICLES,
		BUTTON_TPS,
		BUTTON_BLOCK_NAME,

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

	private GuiButton button_position = null;
	private GuiButton button_position_eyes = null;

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
		this.ui.lineBreak(7);

		this.addStateButton(UI.ALIGN_LEFT, ButtonID.BUTTON_WORLD, "World", Cfg.show_world, 110, 16);
		this.ui.drawSpace(10);
		this.addStateButton(UI.ALIGN_LEFT, ButtonID.BUTTON_BIOME, "Biome", Cfg.show_biome, 110, 16);
		this.ui.lineBreak(19);

		this.button_position = this.addStateButton(UI.ALIGN_LEFT, ButtonID.BUTTON_POSITION, "Position", Cfg.show_position, 110, 16);
		this.ui.drawSpace(10);
		this.button_position_eyes = this.addStateButton(UI.ALIGN_LEFT, ButtonID.BUTTON_POSITION_EYES, "At Eyes", Cfg.show_position_eyes, 110, 16);
		this.ui.lineBreak(19);

		this.addStateButton(UI.ALIGN_LEFT, ButtonID.BUTTON_LIGHT, "Light", Cfg.show_light, 70, 16);
		this.ui.drawSpace(10);
		this.addStateButton(UI.ALIGN_LEFT, ButtonID.BUTTON_TIME, "Time", Cfg.show_time, 70, 16);
		this.ui.drawSpace(10);
		this.addStateButton(UI.ALIGN_LEFT, ButtonID.BUTTON_WEATHER, "Weather", Cfg.show_weather, 70, 16);
		this.ui.lineBreak(19);

		this.addStateButton(UI.ALIGN_LEFT, ButtonID.BUTTON_USED_INVENTORY, "Used Inventory", Cfg.show_used_inventory, 150, 16);
		this.ui.drawSpace(10);
		this.addStateButton(UI.ALIGN_LEFT, ButtonID.BUTTON_ANIMATE_USED_INVENTORY, "Animate", Cfg.animate_used_inventory, 70, 16);
		this.ui.lineBreak(19);

		this.addStateButton(UI.ALIGN_LEFT, ButtonID.BUTTON_FPS, "FPS", Cfg.show_fps, 110, 16);
		this.ui.drawSpace(10);
		this.addStateButton(UI.ALIGN_LEFT, ButtonID.BUTTON_CHUNK_UPDATES, "Chunk Updates", Cfg.show_chunk_updates, 110, 16);
		this.ui.lineBreak(19);

		this.addStateButton(UI.ALIGN_LEFT, ButtonID.BUTTON_ENTITIES, "Entities", Cfg.show_entities, 110, 16);
		this.ui.drawSpace(10);
		this.addStateButton(UI.ALIGN_LEFT, ButtonID.BUTTON_PARTICLES, "Particles", Cfg.show_particles, 110, 16);
		this.ui.lineBreak(19);

		this.addStateButton(UI.ALIGN_LEFT, ButtonID.BUTTON_TPS, "TPS (Server Lag)", Cfg.show_tps, 230, 16);
		this.ui.lineBreak(19);

		this.addStateButton(UI.ALIGN_LEFT, ButtonID.BUTTON_BLOCK_NAME, "Block/Mob Name", Cfg.show_block_name, 230, 16);
		this.ui.lineBreak(19);

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
			for(int i = 0; i < this.buttonList.size(); i++){
				Button button = (Button)this.buttonList.get(i);
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
		case BUTTON_X_DN:		Cfg.info_hud_x -= (mouse_button == 1 ? 10 : 20);	break;
		case BUTTON_X_UP:		Cfg.info_hud_x += (mouse_button == 1 ? 10 : 20);	break;
		case BUTTON_Y_DN:		Cfg.info_hud_y -= (mouse_button == 1 ? 10 : 20);	break;
		case BUTTON_Y_UP:		Cfg.info_hud_y += (mouse_button == 1 ? 10 : 20);	break;
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
		case BUTTON_X_DN:		Cfg.info_hud_x -= 1;	break;
		case BUTTON_X_UP:		Cfg.info_hud_x += 1;	break;
		case BUTTON_Y_DN:		Cfg.info_hud_y -= 1;	break;
		case BUTTON_Y_UP:		Cfg.info_hud_y += 1;	break;

		case BUTTON_WORLD:						((Button)button).active = Cfg.show_world				= (Cfg.show_world				? false : true);	break;
		case BUTTON_BIOME:						((Button)button).active = Cfg.show_biome				= (Cfg.show_biome				? false : true);	break;
		case BUTTON_POSITION:					((Button)button).active = Cfg.show_position				= (Cfg.show_position			? false : true);	break;
		case BUTTON_POSITION_EYES:				((Button)button).active = Cfg.show_position_eyes		= (Cfg.show_position_eyes		? false : true);	break;
		case BUTTON_LIGHT:						((Button)button).active = Cfg.show_light				= (Cfg.show_light				? false : true);	break;
		case BUTTON_TIME:						((Button)button).active = Cfg.show_time					= (Cfg.show_time				? false : true);	break;
		case BUTTON_WEATHER:					((Button)button).active = Cfg.show_weather				= (Cfg.show_weather				? false : true);	break;
		case BUTTON_USED_INVENTORY:				((Button)button).active = Cfg.show_used_inventory		= (Cfg.show_used_inventory		? false : true);	break;
		case BUTTON_ANIMATE_USED_INVENTORY:		((Button)button).active = Cfg.animate_used_inventory	= (Cfg.animate_used_inventory	? false : true);	break;
		case BUTTON_FPS:						((Button)button).active = Cfg.show_fps					= (Cfg.show_fps					? false : true);	break;
		case BUTTON_CHUNK_UPDATES:				((Button)button).active = Cfg.show_chunk_updates		= (Cfg.show_chunk_updates		? false : true);	break;
		case BUTTON_ENTITIES:					((Button)button).active = Cfg.show_entities				= (Cfg.show_entities			? false : true);	break;
		case BUTTON_PARTICLES:					((Button)button).active = Cfg.show_particles			= (Cfg.show_particles			? false : true);	break;
		case BUTTON_TPS:						((Button)button).active = Cfg.show_tps					= (Cfg.show_tps					? false : true);	break;
		case BUTTON_BLOCK_NAME:					((Button)button).active = Cfg.show_block_name			= (Cfg.show_block_name			? false : true);	break;

		default:
			// 'done' button
			this.mc.displayGuiScreen(this.parentScreen);
			return;
		}

		// update dependent states
		if(button == this.button_position && Cfg.show_position == false){
			// position turned off, turn off eyes
			((Button)this.button_position_eyes).active = Cfg.show_position_eyes = false;
		}
		if(button == this.button_position_eyes && Cfg.show_position_eyes == true){
			// eyes turned on, turn on position
			((Button)this.button_position).active = Cfg.show_position = true;
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
