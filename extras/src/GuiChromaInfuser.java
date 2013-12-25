package com.qzx.au.extras;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.util.Icon;

import com.qzx.au.core.Button;
import com.qzx.au.core.Color;
import com.qzx.au.core.GuiContainerAU;
import com.qzx.au.core.PacketUtils;
import com.qzx.au.core.UI;

@SideOnly(Side.CLIENT)
public class GuiChromaInfuser extends GuiContainerAU {
	private UI ui = new UI();

	public GuiChromaInfuser(InventoryPlayer inventoryPlayer, TileEntityChromaInfuser tileEntity){
		super(inventoryPlayer, tileEntity);
	}

//	@Override
//	protected void drawGuiContainerForegroundLayer(int cursor_x, int cursor_y){
//		super.drawGuiContainerForegroundLayer(cursor_x, cursor_y);
//	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int cursor_x, int cursor_y){
		super.drawGuiContainerBackgroundLayer(f, cursor_x, cursor_y);
		this.fontRenderer.drawString("Chroma Infuser", this.upperX, this.upperY-11, 0xffffff);

		TileEntityChromaInfuser tileEntity = (TileEntityChromaInfuser)this.tileEntity;

		// water gauge
		int x = this.upperX + 5*18;
		int y = this.upperY + 1*18+9 + 1 - 5;
		int width = 18;
		int height = 33;
		UI.drawBorder(x, y, width, height, 0xff373737, 0xffffffff, 0xff8b8b8);
		// draw water
		if(tileEntity.getWater()){
			if(tileEntity.getDyeVolume() > 0){
//				Color waterColor = (new Color(ItemDye.dyeColors[tileEntity.getDyeColor()])).anaglyph();
//				UI.setColor(waterColor.r, waterColor.g, waterColor.b, 1.0F);
//				Icon waterIcon = BlockChromaInfuser.getWaterIcon();
//				UI.bindTexture(this.mc, "au_extras", AUExtras.texturePath+"/blocks/chromaInfuser_water.png");

				// colored water box
				int water_height = (height-2) * tileEntity.getDyeVolume() / 8;
				int water_top = (y+1) + (height-2 - water_height);
				UI.drawRect(x+3, water_top, width-6, water_height, 0xff000000+ItemDye.dyeColors[tileEntity.getDyeColor()]);
//				UI.drawTexturedRect(x+3, water_top, waterIcon, width-6, water_height, 0.0F);
			} else {
				// blue interior stripe to represent uncolored water
				UI.drawBorder(x+7, y+7, width-14, height-14, 0xff345fda, 0xff345fda, 0xff345fda);
				UI.drawBorder(x+8, y+8, width-16, height-16, 0xff345fda, 0xff345fda, 0xff345fda);
			}
		} // else no water, show nothing in the gauge
		// draw level marks
		for(int i = 1; i < 8; i++){
			int yy = y + i*height/8;
			int w = 4 + (i == 4 ? 2 : (i == 2 || i == 6 ? 1 : 0));
			UI.drawLineH(x+1, yy, w, 0xff373737);
			UI.drawLineH(x+width-1-w, yy, w, 0xff6e6e6e);
		}

		// arrow image from input to result
		UI.setColor(1.0F, 1.0F, 1.0F, 1.0F);
		UI.bindTexture(this.mc, "au_extras", AUExtras.texturePath+"/gui/container.png");
		UI.drawTexturedRect(this.upperX+1*18+4, this.upperY+2*18-3, 0, 0, 10, 12, 0.0F);

		this.updateRecipeButtons();
	}

	private Button addRecipeButton(ChromaButton id, int textureX, int textureXactive){
		Button button = this.ui.newButton(UI.ALIGN_LEFT, id.ordinal(), null, 12, 12, 0)
						.initState(((TileEntityChromaInfuser)this.tileEntity).getRecipeButton() == id)
						.initImage("au_extras", AUExtras.texturePath+"/gui/container.png", textureX, 0, textureXactive, 0);
		this.buttonList.add(button);
		return button;
	}

	private Button[] recipeButtons = new Button[4];
	private void updateRecipeButtons(){
		int button = ((TileEntityChromaInfuser)this.tileEntity).getRecipeButton().ordinal();
		for(int i = 0; i < this.recipeButtons.length; i++)
			this.recipeButtons[i].active = (this.recipeButtons[i].id == button ? true : false);
	}

	@Override
	public void initGui(){
		super.initGui();
		this.buttonList.clear();
		this.ui.setCursor(this.upperX + 2*18 + 9, this.upperY + 14);

		recipeButtons[0] = this.addRecipeButton(ChromaButton.BUTTON_BLANK,		10+0*12, 10+1*12);
		this.ui.lineBreak(14);
		recipeButtons[1] = this.addRecipeButton(ChromaButton.BUTTON_SQUARE,		10+2*12, 10+3*12);
		this.ui.lineBreak(14);
		recipeButtons[2] = this.addRecipeButton(ChromaButton.BUTTON_SQUARE_DOT,	10+4*12, 10+5*12);
		this.ui.lineBreak(14);
		recipeButtons[3] = this.addRecipeButton(ChromaButton.BUTTON_DOT,		10+6*12, 10+7*12);
	}

	@Override
	public void actionPerformed(GuiButton button){
		PacketDispatcher.sendPacketToServer(
			PacketUtils.createPacket(AUExtras.packetChannel, Packets.RECIPE_BUTTON, this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord, (byte)button.id)
		);
	}

/*
	protected void mouseClicked(int x, int y, int button){
		int x = this.upperX + 5*18;
		int y = this.upperY + 1*18+9 + 1 - 5;

	}
*/
}
