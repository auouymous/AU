package com.qzx.au.hud;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;

#ifdef MC147
import ic2.api.ElectricItem;
import ic2.api.IElectricItem;
#else
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
#endif

import org.lwjgl.opengl.GL11;

import com.qzx.au.core.UI;

@SideOnly(Side.CLIENT)
public class ArmorHUD {
	private UI ui = new UI();

	public ArmorHUD(){}

	//////////

	private int getDurability(ItemStack itemstack, int max_durability){
		try {
			Item item = itemstack.getItem();
			if(AUHud.supportIC2)
				if(item instanceof IElectricItem)
					#ifdef MC147
					return (int)Math.round((float)max_durability * (float)(itemstack.getMaxDamage()-itemstack.getItemDamage())/26.0F);
					#else
					return ElectricItem.manager.getCharge(itemstack);
					#endif
			return max_durability - itemstack.getItemDamage();
		} catch(Exception e){
			Failure.log("armor hud, getDurability");
			return 0;
		}
	}
	private int getMaxDurability(ItemStack itemstack){
		try {
			Item item = itemstack.getItem();
			if(AUHud.supportIC2)
				if(item instanceof IElectricItem)
					#ifdef MC147
					return ((IElectricItem)item).getMaxCharge();
					#else
					return ((IElectricItem)item).getMaxCharge(itemstack);
					#endif
			return itemstack.getMaxDamage();
		} catch(Exception e){
			Failure.log("armor hud, getMaxDurability");
			return 1; // prevent divide by zero
		}
	}

	private void drawItemStack(Minecraft mc, RenderItem itemRenderer, ItemStack itemstack, int x, int y, int quantity, boolean force_quantity){
		if(itemstack == null) return;

		GL11.glEnable(32826); // GL_RESCALE_NORMAL_EXT + GL_RESCALE_NORMAL_EXT
		RenderHelper.enableStandardItemLighting();
		RenderHelper.enableGUIStandardItemLighting();

		this.ui.setCursor(x, y);

		try {
			itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, itemstack, this.ui.getScaledX()+this.icon_offset, this.ui.getScaledY());
		} catch(Exception e){
			Failure.log("armor hud, draw itemstack");
		}

		int durability_style = (Cfg.show_inspector ? Cfg.HUD_DURABILITY_VALUE : Cfg.armor_hud_durability);

		// durability bar/value/percent
		if(durability_style == Cfg.HUD_DURABILITY_BAR){
			try {
				#ifdef MC147
				itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, itemstack, this.ui.getScaledX()+this.icon_offset, this.ui.getScaledY());
				#else
				itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, itemstack, this.ui.getScaledX()+this.icon_offset, this.ui.getScaledY(), (quantity > 1 ? "" : null));
				#endif
			} catch(Exception e){
				Failure.log("armor hud, draw itemstack overlay");
			}

			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(32826); // GL_RESCALE_NORMAL_EXT + GL_RESCALE_NORMAL_EXT

			// no durability text, vertically center quantity text
			this.ui.drawVSpace(4);
		} else {
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(32826); // GL_RESCALE_NORMAL_EXT + GL_RESCALE_NORMAL_EXT

			// vertically center text, durability or quantity
			this.ui.drawVSpace(4);

			try {
				if(itemstack.isItemStackDamageable()){
					// if quantity text, shift durability text up
					if(force_quantity || quantity > 1) this.ui.drawVSpace(-4);

					int max_durability = this.getMaxDurability(itemstack);
					int durability = this.getDurability(itemstack, max_durability);
					int percent = (int)Math.round(100.0 * (float)durability / (float)max_durability);
					int color = (percent > 50 ? 0xaaaaaa : (percent < 25 ? 0xff6666 : 0xffff66));
					if(durability_style == Cfg.HUD_DURABILITY_VALUE){
						if(this.text_align == UI.ALIGN_LEFT){
							this.ui.drawString(UI.ALIGN_LEFT, String.format("%d", durability), color, 0);
							this.ui.drawString(UI.ALIGN_LEFT, String.format("/%d", max_durability), 0xaaaaaa, 0);
						} else {
							this.ui.drawString(UI.ALIGN_RIGHT, String.format("/%d", max_durability), 0xaaaaaa, 0);
							this.ui.drawString(UI.ALIGN_RIGHT, String.format("%d", durability), color, 0);
						}
					} else // Cfg.HUD_DURABILITY_PERCENT
						this.ui.drawString(this.text_align, String.format("%d%%", percent), color, 0);
					this.ui.lineBreak(9);
				}
			} catch(Exception e){
				Failure.log("armor hud, draw itemstack durability/count");
			}
		}

		// quantity below durability value/percent
		if(force_quantity || quantity > 1)
			this.ui.drawString(this.text_align, String.format("%d", quantity), (quantity > 0 ? 0xffffff : 0xff6666), 0);
	}

	private int countItemsInInventory(EntityPlayer player, int itemID, int itemDamage){
		int nr_items = 0;
		for(int i = 0; i < 36; i++){
			ItemStack item = player.inventory.mainInventory[i];
			if(item != null)
				if(item.itemID == itemID && item.getItemDamage() == itemDamage)
					nr_items += item.stackSize;
		}
		return nr_items;
	}

	//////////

	private int text_align;
	private int icon_offset;

	public void draw(Minecraft mc, ScaledResolution screen, EntityPlayer player){
		try {
			RenderItem itemRenderer = new RenderItem();
			itemRenderer.zLevel = 200.0F;

			ItemStack helmet = player.getCurrentItemOrArmor(4);
			ItemStack chest = player.getCurrentItemOrArmor(3);
			ItemStack pants = player.getCurrentItemOrArmor(2);
			ItemStack boots = player.getCurrentItemOrArmor(1);
			ItemStack hand = player.getHeldItem();
			if(TickHandlerHUD.force_hud == TickHandlerHUD.HUD_ARMOR){
				// equip all slots when configuring HUD
				if(helmet == null) helmet = new ItemStack(Item.helmetLeather);
				if(chest == null) chest = new ItemStack(Item.plateLeather);
				if(pants == null) pants = new ItemStack(Item.legsLeather);
				if(boots == null) boots = new ItemStack(Item.bootsLeather);
				if(hand == null) hand = new ItemStack(Item.leather); // quantity will show as zero unless some in inventory
			}
			if(helmet == null && chest == null && pants == null && boots == null && hand == null) return;
			ItemStack ammo = null;

			GL11.glPushMatrix();

			try {
				this.ui.scale(Cfg.armor_hud_scale);

				int width = screen.getScaledWidth();
				int height = screen.getScaledHeight();
				int icon_size = this.ui.unscaleValue(16);
				int gap = this.ui.unscaleValue(2);
				int x = ((Cfg.armor_hud_corner&1) == 0 ? Cfg.armor_hud_x + icon_size + gap :  width-Cfg.armor_hud_x - icon_size - gap); // left : right
				int y = ((Cfg.armor_hud_corner&2) == 0 ? Cfg.armor_hud_y : height-Cfg.armor_hud_y - 5*icon_size); // top : bottom
				this.text_align = ((Cfg.armor_hud_corner&1) == 0 ? UI.ALIGN_LEFT : UI.ALIGN_RIGHT);
				this.icon_offset = ((Cfg.armor_hud_corner&1) == 0 ? -(16+2) : 2); // left : right (icon size and gap)

				int nr_hand = 0, nr_ammo = -1;
				try {
					if(hand != null){
						nr_hand = (hand.getMaxStackSize() > 1 ? countItemsInInventory(player, hand.itemID, hand.getItemDamage()) : 1);

						// bow ammo
						if(hand.getItem() instanceof ItemBow){
							nr_ammo = countItemsInInventory(player, Item.arrow.itemID, 0);
							ammo = new ItemStack(Item.arrow);
						}

						// move HUD up if at bottom and has ammo
						if(ammo != null) y -= ((Cfg.armor_hud_corner&2) == 0 ? 0 : icon_size); // top : bottom
					}
				} catch(Exception e){
					Failure.log("armor hud, count hand/ammo");
				}

				if(TickHandlerHUD.force_hud == TickHandlerHUD.HUD_ARMOR){
					// position grid
					this.ui.setCursor(((Cfg.armor_hud_corner&1) == 0 ? Cfg.armor_hud_x : width-Cfg.armor_hud_x), ((Cfg.armor_hud_corner&2) == 0 ? Cfg.armor_hud_y : height-Cfg.armor_hud_y));
					final int grid_length = 36;
					int xx = this.ui.getScaledX();
					int yy = this.ui.getScaledY();
					this.ui.drawLineH(xx-((Cfg.armor_hud_corner&1) == 0 ? 4 : grid_length-4), yy, grid_length, 0xff00ffff); // left : right
					this.ui.drawLineV(xx, yy-((Cfg.armor_hud_corner&2) == 0 ? 4 : grid_length-4), grid_length, 0xff00ffff); // top : bottom
				}

				GL11.glTranslatef(0.0F, 0.0F, 32.0F);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

				this.drawItemStack(mc, itemRenderer, helmet, x, y+0*icon_size, 0, false);
				this.drawItemStack(mc, itemRenderer, chest,  x, y+1*icon_size, 0, false);
				this.drawItemStack(mc, itemRenderer, pants,  x, y+2*icon_size, 0, false);
				this.drawItemStack(mc, itemRenderer, boots,  x, y+3*icon_size, 0, false);
				this.drawItemStack(mc, itemRenderer, hand,   x, y+4*icon_size, nr_hand, false);
				this.drawItemStack(mc, itemRenderer, ammo,   x, y+5*icon_size, nr_ammo, true);
			} catch(Exception e){
				Failure.log("armor hud, display");
			}

			GL11.glPopMatrix();
		} catch(Exception e){
			Failure.log("armor hud");
		}
	}
}
