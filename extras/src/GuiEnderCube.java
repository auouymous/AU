package com.qzx.au.extras;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import org.lwjgl.opengl.GL11;

import com.qzx.au.core.Button;
import com.qzx.au.core.Color;
import com.qzx.au.core.GuiContainerAU;
import com.qzx.au.core.PacketUtils;
import com.qzx.au.core.UI;

@SideOnly(Side.CLIENT)
public class GuiEnderCube extends GuiContainerAU {
	private UI ui = new UI();

	public GuiEnderCube(InventoryPlayer inventoryPlayer, TileEntityEnderCube tileEntity){
		super(inventoryPlayer, tileEntity);
	}

//	@Override
//	protected void drawGuiContainerForegroundLayer(int cursor_x, int cursor_y){
//		super.drawGuiContainerForegroundLayer(cursor_x, cursor_y);
//	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int cursor_x, int cursor_y){
		super.drawGuiContainerBackgroundLayer(f, cursor_x, cursor_y);
		this.fontRenderer.drawString("Ender Cube", this.upperX, this.upperY-11, 0xffffff);

		TileEntityEnderCube tileEntity = (TileEntityEnderCube)this.tileEntity;

		this.updateButtons();
	}

	private Button addPlayerButton(EnderButton id, int textureX, int textureXactive){
		Button button = this.ui.newButton(UI.ALIGN_LEFT, id.ordinal(), null, 12, 12, 0)
						.initState(((TileEntityEnderCube)this.tileEntity).getPlayerDirection() == id)
						.initImage("au_extras", AUExtras.texturePath+"/gui/container.png", textureX, 2*12, textureXactive, 2*12);
		this.buttonList.add(button);
		return button;
	}
	private Button addDirectionButton(EnderButton id, int textureX, int textureXactive){
		Button button = this.ui.newButton(UI.ALIGN_LEFT, id.ordinal(), null, 12, 12, 0)
						.initState(((TileEntityEnderCube)this.tileEntity).getTeleportDirection() == id)
						.initImage("au_extras", AUExtras.texturePath+"/gui/container.png", textureX, 1*12, textureXactive, 1*12);
		this.buttonList.add(button);
		return button;
	}
	private Button addControlButton(EnderButton id, int textureX, int textureXactive, boolean state){
		Button button = this.ui.newButton(UI.ALIGN_LEFT, id.ordinal(), null, 12, 12, 0)
						.initState(state)
						.initImage("au_extras", AUExtras.texturePath+"/gui/container.png", textureX, 2*12, textureXactive, 2*12);
		this.buttonList.add(button);
		return button;
	}

	private Button[] playerButtons = new Button[3];
	private Button[] directionButtons = new Button[6];
	private Button playerButton = null;
	private Button contactButton = null;
	private Button redstoneButton = null;

	private void updateButtons(){
		TileEntityEnderCube tileEntity = (TileEntityEnderCube)this.tileEntity;

		int button = tileEntity.getPlayerDirection().ordinal();
		for(int i = 0; i < this.playerButtons.length; i++)
			this.playerButtons[i].active = (this.playerButtons[i].id == button ? true : false);

		button = tileEntity.getTeleportDirection().ordinal();
		for(int i = 0; i < this.directionButtons.length; i++)
			this.directionButtons[i].active = (this.directionButtons[i].id == button ? true : false);

		this.playerButton.active = tileEntity.getPlayerControl();
		this.contactButton.active = tileEntity.getContactControl();
		this.redstoneButton.active = tileEntity.getRedstoneControl();
	}

	@Override
	public void initGui(){
		super.initGui();
		this.buttonList.clear();

		this.ui.setCursor(this.upperX + 10, this.upperY + 10);

		// player directions
		playerButtons[0] = this.addPlayerButton(EnderButton.BUTTON_PLAYER_UD,	6*12, 7*12);
		this.ui.drawSpace(5);
		playerButtons[1] = this.addPlayerButton(EnderButton.BUTTON_PLAYER_NS,	8*12, 9*12);
		this.ui.drawSpace(5);
		playerButtons[2] = this.addPlayerButton(EnderButton.BUTTON_PLAYER_EW,	10*12, 11*12);
		this.ui.lineBreak(17);

		// directions
		directionButtons[0] = this.addDirectionButton(EnderButton.BUTTON_DOWN,	0*12, 1*12);
		this.ui.drawSpace(5);
		directionButtons[1] = this.addDirectionButton(EnderButton.BUTTON_UP,	2*12, 3*12);
		this.ui.drawSpace(5);
		directionButtons[2] = this.addDirectionButton(EnderButton.BUTTON_NORTH,	4*12, 5*12);
		this.ui.drawSpace(5);
		directionButtons[3] = this.addDirectionButton(EnderButton.BUTTON_SOUTH,	6*12, 7*12);
		this.ui.drawSpace(5);
		directionButtons[4] = this.addDirectionButton(EnderButton.BUTTON_WEST,	8*12, 9*12);
		this.ui.drawSpace(5);
		directionButtons[5] = this.addDirectionButton(EnderButton.BUTTON_EAST,	10*12, 11*12);
		this.ui.lineBreak(17);

		// controls
		TileEntityEnderCube tileEntity = (TileEntityEnderCube)this.tileEntity;
		playerButton = this.addControlButton(EnderButton.BUTTON_CONTROL_PLAYER,		0*12, 1*12,		tileEntity.getPlayerControl());
		this.ui.drawSpace(5);
		contactButton = this.addControlButton(EnderButton.BUTTON_CONTROL_CONTACT,	2*12, 3*12,		tileEntity.getContactControl());
		this.ui.drawSpace(13);
		redstoneButton = this.addControlButton(EnderButton.BUTTON_CONTROL_REDSTONE,	4*12, 5*12,		tileEntity.getRedstoneControl());
	}

	@Override
	public void actionPerformed(GuiButton button){
		TileEntityEnderCube tileEntity = (TileEntityEnderCube)this.tileEntity;

		switch(EnderButton.values()[button.id]){
		case BUTTON_PLAYER_UD:
		case BUTTON_PLAYER_NS:
		case BUTTON_PLAYER_EW:
			if(tileEntity.getPlayerDirection().ordinal() != button.id)
				PacketDispatcher.sendPacketToServer(
					PacketUtils.createPacket(AUExtras.packetChannel, Packets.ENDER_BUTTON, this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord, (byte)button.id)
				);
			break;
		case BUTTON_DOWN:
		case BUTTON_UP:
		case BUTTON_NORTH:
		case BUTTON_SOUTH:
		case BUTTON_WEST:
		case BUTTON_EAST:
			if(tileEntity.getTeleportDirection().ordinal() != button.id)
				PacketDispatcher.sendPacketToServer(
					PacketUtils.createPacket(AUExtras.packetChannel, Packets.ENDER_BUTTON, this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord, (byte)button.id)
				);
			break;
		case BUTTON_CONTROL_PLAYER:
			PacketDispatcher.sendPacketToServer(
				PacketUtils.createPacket(AUExtras.packetChannel, Packets.ENDER_BUTTON, this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord, (byte)button.id)
			);
			break;
		case BUTTON_CONTROL_CONTACT:
			PacketDispatcher.sendPacketToServer(
				PacketUtils.createPacket(AUExtras.packetChannel, Packets.ENDER_BUTTON, this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord, (byte)button.id)
			);
			break;
		case BUTTON_CONTROL_REDSTONE:
			PacketDispatcher.sendPacketToServer(
				PacketUtils.createPacket(AUExtras.packetChannel, Packets.ENDER_BUTTON, this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord, (byte)button.id)
			);
			break;
		}
	}
}
