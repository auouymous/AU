package com.qzx.au.extras;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

import java.util.EnumSet;

import com.qzx.au.core.PacketUtils;

@SideOnly(Side.CLIENT)
public class TickHandlerExtras implements ITickHandler {
	private boolean playerJumping = false;
	private boolean playerSneaking = false;

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData){
	}

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
				if(player.movementInput.sneak && !playerSneaking)
					PacketDispatcher.sendPacketToServer(
						PacketUtils.createPacket(AUExtras.packetChannel, Packets.PLAYER_MOVEMENT, pos_x, pos_y, pos_z, (byte)0));
				if(player.movementInput.jump && !playerJumping)
					PacketDispatcher.sendPacketToServer(
						PacketUtils.createPacket(AUExtras.packetChannel, Packets.PLAYER_MOVEMENT, pos_x, pos_y, pos_z, (byte)1));
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
		return "AUExtras: Render Tick";
	}
}
