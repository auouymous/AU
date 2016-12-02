package com.qzx.au.tts;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import com.qzx.au.core.Button;
import com.qzx.au.core.GuiContainerAU;
import com.qzx.au.core.PacketUtils;
import com.qzx.au.core.TextField;
import com.qzx.au.core.UI;

@SideOnly(Side.CLIENT)
public class GuiTTS extends GuiContainerAU {
	private UI ui = new UI();
	public static boolean update_text;

	public GuiTTS(InventoryPlayer inventoryPlayer, TileEntityTTS tileEntity){
		super(inventoryPlayer, tileEntity);
	}

//	@Override
//	protected void drawGuiContainerForegroundLayer(int cursor_x, int cursor_y){
//		super.drawGuiContainerForegroundLayer(cursor_x, cursor_y);
//	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int cursor_x, int cursor_y){
		super.drawGuiContainerBackgroundLayer(f, cursor_x, cursor_y);
		this.fontRenderer.drawString("TTS Cube", this.upperX, this.upperY-11, 0xffffff);

		TileEntityTTS tileEntity = (TileEntityTTS)this.tileEntity;

		this.updateButtons();

		if(GuiTTS.update_text){
			this.ttsText.setText(tileEntity.getText());
			GuiTTS.update_text = false;
		}
	}

/*
	private Button addPlayerButton(TTSButton id, int textureX, int textureXactive, String tooltip){
		Button button = this.ui.newButton(UI.ALIGN_LEFT, id.ordinal(), null, 12, 12, 0)
//						.initState(((TileEntityTTS)this.tileEntity).getPlayerDirection() == id)
						.initImage("au_tts", THIS_MOD.texturePath+"/gui/container.png", textureX, 2*12, textureXactive, 2*12)
						.setTooltip(tooltip);
		this.buttonList.add(button);
		return button;
	}
	private Button addDirectionButton(TTSButton id, int textureX, int textureXactive, String tooltip){
		Button button = this.ui.newButton(UI.ALIGN_LEFT, id.ordinal(), null, 12, 12, 0)
//						.initState(((TileEntityTTS)this.tileEntity).getTeleportDirection() == id)
						.initImage("au_tts", THIS_MOD.texturePath+"/gui/container.png", textureX, 1*12, textureXactive, 1*12)
						.setTooltip(tooltip);
		this.buttonList.add(button);
		return button;
	}
	private Button addControlButton(TTSButton id, int textureX, int textureXactive, boolean state, String tooltip){
		Button button = this.ui.newButton(UI.ALIGN_LEFT, id.ordinal(), null, 12, 12, 0)
						.initState(state)
						.initImage("au_tts", THIS_MOD.texturePath+"/gui/container.png", textureX, 2*12, textureXactive, 2*12)
						.setTooltip(tooltip);
		this.buttonList.add(button);
		return button;
	}
	private Button addPCLButton(TTSButton id, int textureX, String tooltip){
		Button button = this.ui.newButton(UI.ALIGN_LEFT, id.ordinal(), null, 12, 12, 0)
						.initImage("au_tts", THIS_MOD.texturePath+"/gui/container.png", textureX, 1*12, textureX, 1*12)
						.setTooltip(tooltip);
		this.buttonList.add(button);
		return button;
	}
*/

//	private Button[] playerButtons = new Button[3];
//	private Button[] directionButtons = new Button[6];
//	private Button playerButton = null;
//	private Button playerRedstoneButton = null;
//	private Button redstoneButton = null;
	private TextField ttsText = null;

	private void updateButtons(){
		TileEntityTTS tileEntity = (TileEntityTTS)this.tileEntity;

//		this.playerButton.active = tileEntity.getPlayerControl();
//		this.playerRedstoneButton.active = tileEntity.getPlayerRedstoneControl();
//		this.redstoneButton.active = tileEntity.getRedstoneControl();

//		int button = tileEntity.getPlayerDirection().ordinal();
//		for(int i = 0; i < this.playerButtons.length; i++)
//			this.playerButtons[i].active = (this.playerButton.active && this.playerButtons[i].id == button ? true : false);

//		button = tileEntity.getTeleportDirection().ordinal();
//		for(int i = 0; i < this.directionButtons.length; i++)
//			this.directionButtons[i].active = (this.redstoneButton.active && this.directionButtons[i].id == button ? true : false);
	}

	@Override
	public void initGui(){
		super.initGui();
		this.buttonList.clear();

		TileEntityTTS tileEntity = (TileEntityTTS)this.tileEntity;

		this.ui.setCursor(this.upperX + 10, this.upperY + 10);

/*
		// redstone control directions
		redstoneButton = this.addControlButton(TTSButton.BUTTON_CONTROL_REDSTONE,	4*12, 5*12,		tileEntity.getRedstoneControl(),	"redstone signal teleports all entities above block");
		this.ui.drawSpace(22);
		directionButtons[0] = this.addDirectionButton(TTSButton.BUTTON_DOWN,	0*12, 1*12,		"teleport down");
		this.ui.drawSpace(5);
		directionButtons[1] = this.addDirectionButton(TTSButton.BUTTON_UP,	2*12, 3*12,		"teleport up");
		this.ui.drawSpace(5);
		directionButtons[2] = this.addDirectionButton(TTSButton.BUTTON_NORTH,	4*12, 5*12,		"teleport north");
		this.ui.drawSpace(5);
		directionButtons[3] = this.addDirectionButton(TTSButton.BUTTON_SOUTH,	6*12, 7*12,		"teleport south");
		this.ui.drawSpace(5);
		directionButtons[4] = this.addDirectionButton(TTSButton.BUTTON_WEST,	8*12, 9*12,		"teleport west");
		this.ui.drawSpace(5);
		directionButtons[5] = this.addDirectionButton(TTSButton.BUTTON_EAST,	10*12, 11*12,	"teleport east");
		this.ui.lineBreak(17);

		// player control directions
		playerButton = this.addControlButton(TTSButton.BUTTON_CONTROL_PLAYER,				0*12, 1*12,		tileEntity.getPlayerControl(),	"player controls teleport with JUMP and SNEAK");
		this.ui.drawSpace(5);
		playerRedstoneButton = this.addControlButton(TTSButton.BUTTON_CONTROL_PLAYER_RS,	4*12, 5*12,		tileEntity.getPlayerRedstoneControl(),	"require redstone signal for player control");
		this.ui.drawSpace(22);
		playerButtons[0] = this.addPlayerButton(TTSButton.BUTTON_PLAYER_UD,	6*12, 7*12,		"up/down directions");
		this.ui.drawSpace(5);
		playerButtons[1] = this.addPlayerButton(TTSButton.BUTTON_PLAYER_NS,	8*12, 9*12,		"north/south directions");
		this.ui.drawSpace(5);
		playerButtons[2] = this.addPlayerButton(TTSButton.BUTTON_PLAYER_EW,	10*12, 11*12,	"east/west directions");
		this.ui.lineBreak(17);
*/

		// text
		this.ui.lineBreak(7 + 14);
		this.ui.drawSpace(-6);
		String text = tileEntity.getText();
		ttsText = this.ui.newTextField(text == null ? "" : text, 200, 120, TextField.DEFAULT_STYLE);
		ttsText.setTooltip("text to convert to speech\n- press ENTER to save");
		this.textFieldList.add(ttsText);
		GuiTTS.update_text = false;
	}

	@Override
	public void onTextFieldChanged(TextField field){
		TileEntityTTS tileEntity = (TileEntityTTS)this.tileEntity;
		String oldText = tileEntity.getText();
		String newText = field.getText().trim();
		if(!oldText.equals(newText))
			PacketUtils.sendToServer(THIS_MOD.packetChannel, Packets.SERVER_TTS_SET_TEXT, this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord, (String)newText);
		else
			ttsText.setText(newText);
		field.setFocused(false);
	}

	@Override
	public void actionPerformed(GuiButton button){
		TileEntityTTS tileEntity = (TileEntityTTS)this.tileEntity;

/*
		switch(TTSButton.values()[button.id]){
		case BUTTON_PLAYER_UD:
		case BUTTON_PLAYER_NS:
		case BUTTON_PLAYER_EW:
			if(tileEntity.getPlayerControl() && tileEntity.getPlayerDirection().ordinal() != button.id)
				PacketUtils.sendToServer(THIS_MOD.packetChannel, Packets.SERVER_TTS_SET_BUTTON, this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord, (byte)button.id);
			break;
		case BUTTON_DOWN:
		case BUTTON_UP:
		case BUTTON_NORTH:
		case BUTTON_SOUTH:
		case BUTTON_WEST:
		case BUTTON_EAST:
			if(tileEntity.getRedstoneControl() && tileEntity.getTeleportDirection().ordinal() != button.id)
				PacketUtils.sendToServer(THIS_MOD.packetChannel, Packets.SERVER_TTS_SET_BUTTON, this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord, (byte)button.id);
			break;
		case BUTTON_CONTROL_PLAYER:
			PacketUtils.sendToServer(THIS_MOD.packetChannel, Packets.SERVER_TTS_SET_BUTTON, this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord, (byte)button.id);
			break;
		case BUTTON_CONTROL_PLAYER_RS:
			PacketUtils.sendToServer(THIS_MOD.packetChannel, Packets.SERVER_TTS_SET_BUTTON, this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord, (byte)button.id);
			break;
		case BUTTON_CONTROL_REDSTONE:
			PacketUtils.sendToServer(THIS_MOD.packetChannel, Packets.SERVER_TTS_SET_BUTTON, this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord, (byte)button.id);
			break;
		}
*/
	}
}
