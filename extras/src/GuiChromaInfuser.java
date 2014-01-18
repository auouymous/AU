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

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

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

	private int water_x = 0, water_y = 0, water_w = 0, water_h = 0;

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
		this.water_x = x; this.water_y = y; this.water_w = width; this.water_h = height;
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

		// arrow image from input to output
		UI.setColor(1.0F, 1.0F, 1.0F, 1.0F);
		int arrow_y_offset = tileEntity.getLocked() ? 0 : (int)(12.0F*tileEntity.getOutputTick());
		// white arrow image from input to output
		UI.bindTexture(this.mc, "au_extras", AUExtras.texturePath+"/gui/container.png");
		if(!tileEntity.getLocked() && arrow_y_offset > 0)
			UI.drawTexturedRect(this.upperX+1*18+4, this.upperY+2*18-3, 10, 0, 10, arrow_y_offset, 0.0F);
		// arrow image from input to output
		if(arrow_y_offset < 12)
			UI.drawTexturedRect(this.upperX+1*18+4, this.upperY+2*18-3+arrow_y_offset, 0, arrow_y_offset, 10, 12-arrow_y_offset, 0.0F);

		// output patterns for each recipe button
		RenderItem itemRenderer = new RenderItem();
		itemRenderer.zLevel = 200.0F;

		this.drawOutputPattern(tileEntity, ChromaButton.BUTTON_BLANK,		recipeButtons[0]);
		this.drawOutputPattern(tileEntity, ChromaButton.BUTTON_SQUARE,		recipeButtons[1]);
		this.drawOutputPattern(tileEntity, ChromaButton.BUTTON_SQUARE_DOT,	recipeButtons[2]);
		this.drawOutputPattern(tileEntity, ChromaButton.BUTTON_DOT,			recipeButtons[3]);

		this.updateRecipeButtons();
	}

	private ItemStack[] recipePatternItems = new ItemStack[4];
	private int[][] recipePatternCoords = new int[4][2];

	private void drawOutputPattern(TileEntityChromaInfuser te, ChromaButton recipeButton, Button button){
		recipePatternItems[recipeButton.ordinal()] = null;
		ItemStack input = te.getInput();
		if(input != null){
			// lookup recipe
			ChromaRecipe recipe = ChromaRegistry.getRecipe(recipeButton, input);
			ItemStack output
				= (recipe == null || !te.getWater() || te.getDyeVolume() == 0
				? null
				: new ItemStack(recipe.output.getItem().itemID, recipe.output.stackSize, recipe.getOutputColor(te.getDyeColor())));
			// draw itemstack
			if(output != null){
				// set tooltip coordinates
				recipePatternItems[recipeButton.ordinal()] = output;
				recipePatternCoords[recipeButton.ordinal()][0] = button.xPosition+15;
				recipePatternCoords[recipeButton.ordinal()][1] = button.yPosition-2;

				RenderItem itemRenderer = new RenderItem();
				itemRenderer.zLevel = 200.0F;
				try {
					UI.drawItemStack(this.mc, itemRenderer, output, button.xPosition+15, button.yPosition-2, true);
				} catch(Exception e){
					Debug.error("Failed to render chroma infuser output pattern");
				}
			}
		}
	}

	private Button addRecipeButton(ChromaButton id, int textureX, int textureXactive, String tooltip){
		Button button = this.ui.newButton(UI.ALIGN_LEFT, id.ordinal(), null, 12, 12, 0)
						.initState(((TileEntityChromaInfuser)this.tileEntity).getRecipeButton() == id)
						.initImage("au_extras", AUExtras.texturePath+"/gui/container.png", textureX, 0, textureXactive, 0)
						.setTooltip(tooltip);
		this.buttonList.add(button);
		return button;
	}
	private Button addLockButton(int id, int textureX, int textureXactive, String tooltip){
		Button button = this.ui.newButton(UI.ALIGN_LEFT, id, null, 12, 12, 0)
						.initState(!((TileEntityChromaInfuser)this.tileEntity).getLocked())
						.initImage("au_extras", AUExtras.texturePath+"/gui/container.png", textureX, 0, textureXactive, 0)
						.setTooltip(tooltip);
		this.buttonList.add(button);
		return button;
	}

	private Button[] recipeButtons = new Button[4];
	private void updateRecipeButtons(){
		int button = ((TileEntityChromaInfuser)this.tileEntity).getRecipeButton().ordinal();
		for(int i = 0; i < this.recipeButtons.length; i++)
			this.recipeButtons[i].active = (this.recipeButtons[i].id == button ? true : false);
	}

	private static final int BUTTON_LOCKED = 100;

	@Override
	public void initGui(){
		super.initGui();
		this.buttonList.clear();

		this.ui.setCursor(this.upperX + 2*18 + 9, this.upperY + 10);
		recipeButtons[0] = this.addRecipeButton(ChromaButton.BUTTON_BLANK,		20+0*12, 20+1*12,		"'blank' recipe style");
		this.ui.lineBreak(17);
		recipeButtons[1] = this.addRecipeButton(ChromaButton.BUTTON_SQUARE,		20+2*12, 20+3*12,		"'square' recipe style");
		this.ui.lineBreak(17);
		recipeButtons[2] = this.addRecipeButton(ChromaButton.BUTTON_SQUARE_DOT,	20+4*12, 20+5*12,		"'square dot' recipe style");
		this.ui.lineBreak(17);
		recipeButtons[3] = this.addRecipeButton(ChromaButton.BUTTON_DOT,		20+6*12, 20+7*12,		"'dot' recipe style");

		this.ui.setCursor(this.upperX+2, this.upperY+2*18-3);
		this.addLockButton(GuiChromaInfuser.BUTTON_LOCKED,						20+8*12, 20+9*12,		"enable to begin infusing items");
	}

	@Override
	public void actionPerformed(GuiButton button){
		if(button.id == GuiChromaInfuser.BUTTON_LOCKED){
			((Button)button).active = ((Button)button).active ? false : true;
			PacketDispatcher.sendPacketToServer(
				PacketUtils.createPacket(AUExtras.packetChannel, Packets.CHROMA_LOCKED_BUTTON, this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord, ((Button)button).active)
			);
		} else if(!((Button)button).active){
			PacketDispatcher.sendPacketToServer(
				PacketUtils.createPacket(AUExtras.packetChannel, Packets.CHROMA_RECIPE_BUTTON, this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord, (byte)button.id)
			);
		}
	}

	@Override
	protected void drawTooltips(int x, int y){
		// water guage tooltip
		if(x >= this.water_x && x < this.water_x+this.water_w && y >= this.water_y && y < this.water_y+this.water_h){
			TileEntityChromaInfuser tileEntity = (TileEntityChromaInfuser)this.tileEntity;
			ArrayList tooltip = new ArrayList();
			if(!tileEntity.getWater()){
				tooltip.add("wait for rain");
				tooltip.add("or add a bucket of water");
			} else if(tileEntity.getDyeVolume() > 0)
				tooltip.add(String.format("%d units of %s dye", tileEntity.getDyeVolume(), Color.readableColors[tileEntity.getDyeColor()]));
			else
				tooltip.add("add dye to color the water");
			this.drawHoveringText(tooltip, x, y, this.fontRenderer);
			return;
		}

		// recipe patterns
		for(int i = 0; i < 4; i++)
			if(recipePatternItems[i] != null){
				int rp_x = recipePatternCoords[i][0];
				int rp_y = recipePatternCoords[i][1];
				if(x >= rp_x && x < rp_x+16 && y >= rp_y && y < rp_y+16){
					this.drawItemStackTooltip(recipePatternItems[i], x, y);
					return;
				}
			}

		super.drawTooltips(x, y);
	}
}
