package com.qzx.au.hud;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

import com.qzx.au.util.UI;

@SideOnly(Side.CLIENT)
public class GuiScreenHUD extends GuiScreen {
	private static int CONFIG_WINDOW_WIDTH = 230;
	private static String HUD_INFO_NAME = "Info HUD";

	private UI ui;
	private int window_height = 0;

	public GuiScreenHUD(EntityPlayer player){
		this.ui = new UI();
		this.ui.setLineHeight(23);
	}

	private void drawTitle(){
		ui.setCursor((this.width - CONFIG_WINDOW_WIDTH)/2, (this.window_height == 0 ? 2 : (this.height - this.window_height)/2));
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
		BUTTON_INFO_HUD,

		BUTTON_WORLD, BUTTON_BIOME,
		BUTTON_POSITION, BUTTON_POSITION_EYES,
		BUTTON_LIGHT, BUTTON_TIME, BUTTON_WEATHER,
		BUTTON_USED_INVENTORY,
		BUTTON_FPS, BUTTON_CHUNK_UPDATES,
		BUTTON_ENTITIES, BUTTON_PARTICLES,
		BUTTON_BLOCK_NAME, BUTTON_BLOCK_INSPECTOR,

		BUTTON_DONE
	}

	private GuiButton addButton(ButtonID id, String s, boolean value, int width, int height){
		GuiButton button = this.ui.newStateButton(id.ordinal(), s, width, height, value, "OFF", "ON");
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

	private GuiButton button_position = null;
	private GuiButton button_position_eyes = null;
	private GuiButton button_block_name = null;
	private GuiButton button_block_inspector = null;

	@Override
	public void initGui(){
		#ifdef MC147
		this.controlList.clear();
		#else
		this.buttonList.clear();
		#endif

		this.drawTitle();

		this.ui.lineBreak(7);
		this.addButtonCentered(ButtonID.BUTTON_INFO_HUD, getEnableButtonName(HUD_INFO_NAME, Cfg.enable_info_hud), 100, 20);
		this.ui.lineBreak();
		this.ui.lineBreak(7);

		this.addButton(ButtonID.BUTTON_WORLD, "World", Cfg.show_world, 110, 20);
		this.ui.drawSpace(10);
		this.addButton(ButtonID.BUTTON_BIOME, "Biome", Cfg.show_biome, 110, 20);
		this.ui.lineBreak();

		this.button_position = this.addButton(ButtonID.BUTTON_POSITION, "Position", Cfg.show_position, 110, 20);
		this.ui.drawSpace(10);
		this.button_position_eyes = this.addButton(ButtonID.BUTTON_POSITION_EYES, "At Eyes", Cfg.show_position_eyes, 110, 20);
		this.ui.lineBreak();

		this.addButton(ButtonID.BUTTON_LIGHT, "Light", Cfg.show_light, 70, 20);
		this.ui.drawSpace(10);
		this.addButton(ButtonID.BUTTON_TIME, "Time", Cfg.show_time, 70, 20);
		this.ui.drawSpace(10);
		this.addButton(ButtonID.BUTTON_WEATHER, "Weather", Cfg.show_weather, 70, 20);
		this.ui.lineBreak();

		this.addButton(ButtonID.BUTTON_USED_INVENTORY, "Used Inventory", Cfg.show_used_inventory, 230, 20);
		this.ui.lineBreak();

		this.addButton(ButtonID.BUTTON_FPS, "FPS", Cfg.show_fps, 110, 20);
		this.ui.drawSpace(10);
		this.addButton(ButtonID.BUTTON_CHUNK_UPDATES, "Chunk Updates", Cfg.show_chunk_updates, 110, 20);
		this.ui.lineBreak();

		this.addButton(ButtonID.BUTTON_ENTITIES, "Entities", Cfg.show_entities, 110, 20);
		this.ui.drawSpace(10);
		this.addButton(ButtonID.BUTTON_PARTICLES, "Particles", Cfg.show_particles, 110, 20);
		this.ui.lineBreak();

		this.button_block_name = this.addButton(ButtonID.BUTTON_BLOCK_NAME, "Block/Mob Name", Cfg.show_block_name, 110, 20);
		this.ui.drawSpace(10);
		this.button_block_inspector = this.addButton(ButtonID.BUTTON_BLOCK_INSPECTOR, "Inspector", Cfg.show_block_inspector, 110, 20);
		this.ui.lineBreak();

		this.ui.lineBreak(7);
		this.addButtonCentered(ButtonID.BUTTON_DONE, "Done", 100, 20);

		if(this.window_height == 0){
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
		case BUTTON_INFO_HUD:			s = Cfg.enable_info_hud		= (Cfg.enable_info_hud		? false : true);	break;
		case BUTTON_WORLD:				s = Cfg.show_world			= (Cfg.show_world			? false : true);	break;
		case BUTTON_BIOME:				s = Cfg.show_biome			= (Cfg.show_biome			? false : true);	break;
		case BUTTON_POSITION:			s = Cfg.show_position		= (Cfg.show_position		? false : true);	break;
		case BUTTON_POSITION_EYES:		s = Cfg.show_position_eyes	= (Cfg.show_position_eyes	? false : true);	break;
		case BUTTON_LIGHT:				s = Cfg.show_light			= (Cfg.show_light			? false : true);	break;
		case BUTTON_TIME:				s = Cfg.show_time			= (Cfg.show_time			? false : true);	break;
		case BUTTON_WEATHER:			s = Cfg.show_weather		= (Cfg.show_weather			? false : true);	break;
		case BUTTON_USED_INVENTORY:		s = Cfg.show_used_inventory	= (Cfg.show_used_inventory	? false : true);	break;
		case BUTTON_FPS:				s = Cfg.show_fps			= (Cfg.show_fps				? false : true);	break;
		case BUTTON_CHUNK_UPDATES:		s = Cfg.show_chunk_updates	= (Cfg.show_chunk_updates	? false : true);	break;
		case BUTTON_ENTITIES:			s = Cfg.show_entities		= (Cfg.show_entities		? false : true);	break;
		case BUTTON_PARTICLES:			s = Cfg.show_particles		= (Cfg.show_particles		? false : true);	break;
		case BUTTON_BLOCK_NAME:			s = Cfg.show_block_name		= (Cfg.show_block_name		? false : true);	break;
		case BUTTON_BLOCK_INSPECTOR:	s = Cfg.show_block_inspector= (Cfg.show_block_inspector	? false : true);	break;
		default:
			this.mc.thePlayer.closeScreen();
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
		if(button == this.button_block_name && s == false){
			// block name turned off, turn off inspector
			Cfg.show_block_inspector = false;
			this.updateButton(this.button_block_inspector, false);
		}
		if(button == this.button_block_inspector && s == true){
			// inspector turned on, turn on block name
			Cfg.show_block_name = true;
			this.updateButton(this.button_block_name, true);
		}

		// update button state
		if(button.id == ButtonID.BUTTON_INFO_HUD.ordinal())
			button.displayString = getEnableButtonName(HUD_INFO_NAME, Cfg.enable_info_hud);
		else
			this.updateButton(button, s);

		// save config
		Cfg.save();
	}

	@Override
	protected void keyTyped(char key, int keyCode){
		// close when ESC or "AU HUD" key are pressed
		if(keyCode == 1)
			this.mc.thePlayer.closeScreen();
		if(keyCode == ClientProxy.keyHandler.keyCodeHUD){
			ClientProxy.keyHandler.ignoreHudKey = true;
			this.mc.thePlayer.closeScreen();
		}

		// this.mc.gameSettings.keyBindInventory.keyCode
	}

	@Override
	public boolean doesGuiPauseGame(){
		return true;
	}
}
