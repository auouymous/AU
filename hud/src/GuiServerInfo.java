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

import org.lwjgl.opengl.GL11;

import com.qzx.au.util.UI;
import com.qzx.au.util.Button;

@SideOnly(Side.CLIENT)
public class GuiServerInfo extends GuiScreen {
	private UI ui = new UI();

	private long ticks;
	private long time;
	private static int TPS_QUEUE = 10;
	private static int TPS_TAIL = TPS_QUEUE-1;
	private float[] tps_queue = new float[TPS_QUEUE];
	private int tps_tail;

	private GuiScreen parentScreen;
	public GuiServerInfo(EntityPlayer player, GuiScreen parent){
		this.parentScreen = parent;
	}

	private void drawTitle(){
		this.ui.setCursor(2, 2);
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
					this.ui.drawString("Map: ", 0xaaaaaa);
					this.ui.drawString(worldInfo.getWorldName(), 0xffffff);
					this.ui.drawString("  ", 0xaaaaaa);
					this.ui.drawString(server.getMinecraftVersion(), 0xaaaaaa);
					this.ui.drawString("  ", 0xaaaaaa);
					this.ui.drawString(worldInfo.getGameType().getName(), 0xffffff);
					this.ui.lineBreak();
				} else {
					// multi player world

					ServerData server = mc.getServerData();

					// server host:port, version, player count
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

					// motd
					this.ui.drawString("MOTD: ", 0xaaaaaa);
					this.ui.drawString(server.serverMOTD, 0xffffff); // getMOTD
					this.ui.lineBreak();
				}

				this.ui.lineBreak();

				// difficulty
				this.ui.drawString("Difficulty: ", 0xaaaaaa);
				String[] difficulties = {"Peaceful", "Easy", "Normal", "Hard"};
				if(mc.gameSettings.difficulty >= 0 || mc.gameSettings.difficulty <= 3)
					this.ui.drawString(difficulties[mc.gameSettings.difficulty], 0xffffff);
				if(worldInfo.isHardcoreModeEnabled())
					this.ui.drawString("  Hardcore", 0xff6666);
				this.ui.lineBreak();

				// gamerules
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

				// world age and TPS
				long ticks = worldInfo.getWorldTotalTime();
				long time = System.currentTimeMillis();
				if(time >= this.time + 1000){
					if(this.ticks > 0){
						if(this.tps_tail == TPS_TAIL)
							for(int i = 0; i < TPS_TAIL; i++) this.tps_queue[i] = this.tps_queue[i+1];
						else this.tps_tail++;
						this.tps_queue[this.tps_tail] = (float)(ticks - this.ticks) / ((float)(time - this.time) / 1000.0F);
					}
					this.ticks = ticks;
					this.time = time;
				}
				int days = (int)(ticks / (86400*20)); ticks -= days * (86400*20);
				int hours = (int)ticks / (3600*20); ticks -= hours * (3600*20);
				int minutes = (int)ticks / (60*20); ticks -= minutes * (60*20);
				int seconds = (int)ticks / 20;
				this.ui.drawString("Created: ", 0xaaaaaa);
				this.ui.drawString(String.format("%d days, %d hours, %d minutes, %d seconds", days, hours, minutes, seconds), 0xffffff);
				if(mc.isGamePaused)
					this.ui.drawString(" PAUSED", 0x6666ff);
				this.ui.lineBreak();
				if(!mc.isGamePaused){
					this.ui.drawString("TPS: ", 0xaaaaaa);
					if(this.ticks > 0 && this.tps_tail > -1){
						float tps = 0.0F; for(int i = 0; i <= this.tps_tail; i++) tps += tps_queue[i];
						this.ui.drawString(String.format("%d", (int)Math.round(tps/(float)(this.tps_tail+1))), 0xffffff);
						this.ui.drawString(" ticks/second", 0xaaaaaa);
						if(this.tps_tail < TPS_TAIL){
							this.ui.drawString("   averaging over 10 seconds", 0x880000);
							for(int i = this.tps_tail; i < TPS_TAIL; i++) this.ui.drawString(".", 0xff6666);
						}
					} else
						this.ui.drawString("preparing...", 0x880000);
					this.ui.lineBreak();

//					this.ui.drawString("TPS Queue:", 0xaaaaaa);
//					for(int i = 0; i <= TPS_TAIL; i++) this.ui.drawString(String.format("  %.2f", this.tps_queue[i]), 0xcccccc);
//					this.ui.lineBreak();
				}

				this.ui.lineBreak();

				// spawn coordinates
				this.ui.drawString("Spawn/Home: ", 0xaaaaaa);
				this.ui.drawString(String.format("%d, %d (%d)", worldInfo.getSpawnX(), worldInfo.getSpawnZ(), worldInfo.getSpawnY()), 0xffffff);
				this.ui.lineBreak();
			}

		} catch(Exception e){
//			System.out.println("AU HUD: caught exception in server info");
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

		this.ticks = 0;
		this.time = System.currentTimeMillis();
		this.tps_tail = -1;

		this.ui.y = this.height - 22;
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
