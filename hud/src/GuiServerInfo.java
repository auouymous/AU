package com.qzx.au.hud;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.EnumGameType;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;

import com.qzx.au.core.Button;
import com.qzx.au.core.UI;

@SideOnly(Side.CLIENT)
public class GuiServerInfo extends GuiScreen {
	private UI ui = new UI();

	private GuiScreen parentScreen;
	public GuiServerInfo(EntityPlayer player, GuiScreen parent){
		this.parentScreen = parent;
	}

	private void drawTitle(){
		this.ui.setCursor(2, 12);
		this.ui.drawString(UI.ALIGN_CENTER, "AU HUD (Server Info)", 0xffffff, this.width);
		this.ui.lineBreak(10);
	}

	@Override
	public void drawScreen(int x, int y, float f){
		// draw shadow
		this.drawDefaultBackground();

		// draw settings title
		this.drawTitle();

		Minecraft mc = Minecraft.getMinecraft();

		try {

			World world = mc.isIntegratedServerRunning() ? mc.getIntegratedServer().worldServerForDimension(mc.thePlayer.dimension) : mc.theWorld;

			if(world != null){
				WorldInfo worldInfo = world.getWorldInfo();

				if(mc.isIntegratedServerRunning()){
					// single player world

					MinecraftServer server = MinecraftServer.getServer();

					// map name and version
					try {
						this.ui.drawString("Map: ", 0xaaaaaa);
						this.ui.drawString(worldInfo.getWorldName(), 0xffffff);
						this.ui.drawString("  ", 0xaaaaaa);
						this.ui.drawString(server.getMinecraftVersion(), 0xaaaaaa);
						this.ui.drawString("  ", 0xaaaaaa);
						this.ui.drawString(worldInfo.getGameType().getName(), 0xffffff);
						this.ui.lineBreak();
					} catch(Exception e){
						Failure.log("server info, SP map");
					}
				} else {
					// multi player world

					ServerData server = Hacks.getServerData(mc);

					if(server != null){
						// server host:port, version, player count
						try {
							this.ui.drawString("Server: ", 0xaaaaaa);
							this.ui.drawString(server.serverName, 0xffffff);
							this.ui.drawString(" (", 0xaaaaaa);
							this.ui.drawString(server.serverIP, 0xffffff);
							this.ui.drawString(")", 0xaaaaaa);
							this.ui.drawString("  ", 0xaaaaaa);
							this.ui.drawString(server.gameVersion, 0xaaaaaa);
							this.ui.drawString("  ", 0xaaaaaa);
							this.ui.drawString(worldInfo.getGameType().getName(), 0xffffff);
							this.ui.lineBreak();
						} catch(Exception e){
							Failure.log("server info, MP server");
						}

						// motd
						try {
							this.ui.drawString("MOTD: ", 0xaaaaaa);
							this.ui.drawString(server.serverMOTD, 0xffffff); // getMOTD
							this.ui.lineBreak();
						} catch(Exception e){
							Failure.log("server info, MP MOTD");
						}
					}
				}

				this.ui.lineBreak();

				// difficulty
				try {
					this.ui.drawString("Difficulty: ", 0xaaaaaa);
					String[] difficulties = {"Peaceful", "Easy", "Normal", "Hard"};
					if(mc.gameSettings.difficulty >= 0 || mc.gameSettings.difficulty <= 3)
						this.ui.drawString(difficulties[mc.gameSettings.difficulty], 0xffffff);
					if(worldInfo.isHardcoreModeEnabled())
						this.ui.drawString("  Hardcore", 0xff6666);
					this.ui.lineBreak();
				} catch(Exception e){
					Failure.log("server info, difficulty");
				}

				// gamerules
				try {
					GameRules rules = worldInfo.getGameRulesInstance();
					this.ui.drawString("Game Rules:", 0xaaaaaa);
					this.ui.drawString("  keepInventory", (rules.getGameRuleBooleanValue("keepInventory") ? 0x66ff66 : 0xff6666));
					this.ui.drawString("  doFireTick", (rules.getGameRuleBooleanValue("doFireTick") ? 0x66ff66 : 0xff6666));
					this.ui.drawString("  mobGriefing", (rules.getGameRuleBooleanValue("mobGriefing") ? 0x66ff66 : 0xff6666));
					this.ui.drawString("  doMobSpawning", (rules.getGameRuleBooleanValue("doMobSpawning") ? 0x66ff66 : 0xff6666));
					this.ui.drawString("  doMobLoot", (rules.getGameRuleBooleanValue("doMobLoot") ? 0x66ff66 : 0xff6666));
					this.ui.lineBreak();
					this.ui.drawString("              ", 0xaaaaaa);
					this.ui.drawString("  doTileDrops", (rules.getGameRuleBooleanValue("doTileDrops") ? 0x66ff66 : 0xff6666));
					this.ui.drawString("  commandBlockOutput", (rules.getGameRuleBooleanValue("commandBlockOutput") ? 0x66ff66 : 0xff6666));
					this.ui.lineBreak();
				} catch(Exception e){
					Failure.log("server info, gamerules");
				}

				// world age
				try {
					long ticks = worldInfo.getWorldTotalTime();
					int days = (int)(ticks / (86400*20)); ticks -= days * (86400*20);
					int hours = (int)ticks / (3600*20); ticks -= hours * (3600*20);
					int minutes = (int)ticks / (60*20); ticks -= minutes * (60*20);
					int seconds = (int)ticks / 20;
					this.ui.drawString("Map Age: ", 0xaaaaaa);
					this.ui.drawString(String.format("%d days, %d hours, %d minutes, %d seconds", days, hours, minutes, seconds), 0xffffff);
					if(Hacks.isGamePaused(mc))
						this.ui.drawString(" PAUSED", 0x6666ff);
					this.ui.lineBreak();
				} catch(Exception e){
					Failure.log("server info, world age");
				}

				this.ui.lineBreak();

				// spawn coordinates
				try {
					this.ui.drawString("Spawn/Home: ", 0xaaaaaa);
					this.ui.drawString(String.format("%d, %d (%d)", worldInfo.getSpawnX(), worldInfo.getSpawnZ(), worldInfo.getSpawnY()), 0xffffff);
					this.ui.lineBreak();
				} catch(Exception e){
					Failure.log("server info, spawn/home");
				}
			}

		} catch(Exception e){
			Failure.log("server info");
		}

		// draw controls
		super.drawScreen(x, y, f);
	}

	public enum ButtonID {
		BUTTON_DONE
	}

	private GuiButton addButton(int align, ButtonID id, String s, int width, int height){
		GuiButton button = this.ui.newButton(align, id.ordinal(), s, width, height, this.width);
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

		this.ui.setY(this.height - 32);
		this.addButton(UI.ALIGN_CENTER, ButtonID.BUTTON_DONE, "Done", 100, 20);
	}

	@Override
	public void actionPerformed(GuiButton button){
		// toggle config state
		switch(ButtonID.values()[button.id]){
		default:
			// 'done' button
			this.mc.displayGuiScreen(this.parentScreen);
			return;
		}
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
