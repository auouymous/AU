package com.qzx.au.extras;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

import java.util.EnumSet;

import com.qzx.au.core.PacketUtils;
import com.qzx.au.core.UI;

@SideOnly(Side.CLIENT)
public class TickHandlerClientRender implements ITickHandler {
	private UI ui = new UI();
	private boolean playerJumping = false;
	private boolean playerSneaking = false;

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData){}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData){
		Minecraft mc = Minecraft.getMinecraft();
		EntityClientPlayerMP player = mc.thePlayer;

		if(player != null){
			int pos_x = MathHelper.floor_double(player.posX);
			int pos_y = MathHelper.floor_double(player.boundingBox.minY) - 1;
			int pos_z = MathHelper.floor_double(player.posZ);

			TileEntity tileEntity = (TileEntity)mc.theWorld.getBlockTileEntity(pos_x, pos_y, pos_z);
			if(tileEntity instanceof TileEntityEnderCube){
				TileEntityEnderCube te = (TileEntityEnderCube)tileEntity;
				if(te.getPlayerControl() && te.canPlayerControl(player.getEntityName())
				&& !mc.gameSettings.showDebugInfo && (mc.inGameHasFocus || mc.currentScreen == null || mc.currentScreen instanceof GuiChat)
				){
					// show message below cursor
					EnderButton directions = te.getPlayerDirection();
					String action_jump;
					String action_sneak;
					if(directions == EnderButton.BUTTON_PLAYER_UD){
						action_jump = "up";
						action_sneak = "down";
					} else {
						int heading = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3; // SWNE = 0123
						String[][][] headings = {
							// NS							EW
							{{"forward",	"backward"},	{"left",		"right"}},		// S
							{{"left",		"right"},		{"backward",	"forward"}},	// W
							{{"backward",	"forward"},		{"right",		"left"}},		// N
							{{"right",		"left"},		{"forward",		"backward"}}	// E
						};
						String[] h = headings[heading][directions.ordinal()-1];
						action_jump = h[0];
						action_sneak = h[1];
					}
					ScaledResolution screen = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
					this.ui.setCursor(screen.getScaledWidth()/2-10, screen.getScaledHeight()/2+10);
					this.ui.drawString(UI.ALIGN_RIGHT, action_jump, 0xffffff, 0);
					this.ui.drawString(UI.ALIGN_RIGHT, "JUMP to go ", 0xaaaaaa, 0);
					this.ui.setCursor(screen.getScaledWidth()/2+10, screen.getScaledHeight()/2+10);
					this.ui.drawString(UI.ALIGN_LEFT, "SNEAK to go ", 0xaaaaaa, 0);
					this.ui.drawString(UI.ALIGN_LEFT, action_sneak, 0xffffff, 0);

					if(te.getPlayerRedstoneControl()){
						this.ui.setCursor(screen.getScaledWidth()/2, screen.getScaledHeight()/2+20);
						this.ui.drawString(UI.ALIGN_CENTER, "redstone signal required", 0xff6666, 0);
					}
				}

				if(player.movementInput.sneak && !playerSneaking)
					PacketUtils.sendToServer(THIS_MOD.packetChannel, Packets.SERVER_ENDER_PLAYER_MOVEMENT, pos_x, pos_y, pos_z, (byte)0);
				if(player.movementInput.jump && !playerJumping)
					PacketUtils.sendToServer(THIS_MOD.packetChannel, Packets.SERVER_ENDER_PLAYER_MOVEMENT, pos_x, pos_y, pos_z, (byte)1);
			}
			playerJumping = player.movementInput.jump;
			playerSneaking = player.movementInput.sneak;
		}
	}

	@Override
	public EnumSet<TickType> ticks(){
		return EnumSet.of(TickType.RENDER);
	}

	@Override
	public String getLabel(){
		return THIS_MOD.modID+": Client Render Tick";
	}
}
