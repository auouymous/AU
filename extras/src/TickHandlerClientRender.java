package com.qzx.au.extras;

#ifdef MC164
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
#define IMPLEMENTS_ITICKHANDLER implements ITickHandler
#define USE_TICK_REGISTRY
#else
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
#define IMPLEMENTS_ITICKHANDLER
#endif

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

#ifdef USE_TICK_REGISTRY
import java.util.EnumSet;
#endif

import com.qzx.au.core.BlockCoord;
import com.qzx.au.core.PacketUtils;
import com.qzx.au.core.UI;

@SideOnly(Side.CLIENT)
public class TickHandlerClientRender IMPLEMENTS_ITICKHANDLER {
	private UI ui = new UI();
	private boolean playerJumping = false;
	private boolean playerSneaking = false;

	#ifdef USE_TICK_REGISTRY
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData){}
	#endif

	#ifdef USE_TICK_REGISTRY
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData){
	#else
	FML_SUBSCRIBE
	public void onTick(RenderTickEvent event){
	#endif
		Minecraft mc = Minecraft.getMinecraft();
		EntityClientPlayerMP player = mc.thePlayer;

		if(player != null){
			int pos_x = MathHelper.floor_double(player.posX);
			int pos_y = MathHelper.floor_double(player.boundingBox.minY) - 1;
			int pos_z = MathHelper.floor_double(player.posZ);

			TileEntity tileEntity = (TileEntity)BlockCoord.getTileEntity(mc.theWorld, pos_x, pos_y, pos_z);
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
					ScaledResolution screen = new SCALED_RESOLUTION(mc);
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

	#ifdef USE_TICK_REGISTRY
	@Override
	public EnumSet<TickType> ticks(){
		return EnumSet.of(TickType.RENDER);
	}
	#endif

	#ifdef USE_TICK_REGISTRY
	@Override
	public String getLabel(){
		return THIS_MOD.modID+": Client Render Tick";
	}
	#endif
}
