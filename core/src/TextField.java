package com.qzx.au.core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ChatAllowedCharacters;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class TextField extends Gui {
	// modified from net/minecraft/client/gui/GuiTextField.java
	private final FontRenderer fontRenderer;
	private final int xPos;
	private final int yPos;
	private final int width;
	private final int height;

	private String text = "";
	private int maxStringLength = 32;
	private int cursorCounter;

	private boolean canLoseFocus = true;
	private boolean isFocused = false;

	private int lineScrollOffset = 0;
	private int cursorPosition = 0;
	private int selectionEnd = 0;

	private boolean visible = true;
	private boolean isEnabled = true;
	private int enabledColor;
	private int disabledColor;
	private int cursorColor;
	private int selectionColor;
	private int style;
	public static int DEFAULT_STYLE = 1;
	private int borderPadding = 4;

	private String[] tooltip = null;

	#ifdef WITH_API_NEI
	public static boolean with_nei = false;
	public NEIWidget nei_widget = null;
	#endif

	public TextField(FontRenderer fontRenderer, int x, int y, int width, int style){
		this.fontRenderer = fontRenderer;
		this.xPos = x+(this.borderPadding>>1); // inner x
		this.yPos = y+(this.borderPadding>>1); // inner y
		this.width = width-this.borderPadding; // inner width
		this.height = fontRenderer.FONT_HEIGHT; // inner height (outer height is height+4)
		this.style = style;
		this.enabledColor = 0x000000;
		this.disabledColor = 0x777777;
		this.cursorColor = 0xffffffff;
		this.selectionColor = 0xff0000ff;

		#ifdef WITH_API_NEI
		if(TextField.with_nei) this.nei_widget = new NEIWidget(this);
		#endif
	}

	//////////

	public void drawTextBox(){
		if(this.getVisible()){
			UI.drawBorder(this.xPos-(this.borderPadding>>1), this.yPos-(this.borderPadding>>1), this.width+this.borderPadding, this.height+this.borderPadding, 0xff373737, 0xffffffff, 0xff8b8b8b);
			UI.drawRect(this.xPos-(this.borderPadding>>2), this.yPos-(this.borderPadding>>2), this.width+(this.borderPadding>>1), this.height+(this.borderPadding>>1), 0xffaaaaaa);

			int text_color = this.isEnabled ? this.enabledColor : this.disabledColor;
			int cursor = this.cursorPosition - this.lineScrollOffset;
			int selection_end = this.selectionEnd - this.lineScrollOffset;
			String s = this.fontRenderer.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.width);
			boolean valid_cursor = cursor >= 0 && cursor <= s.length();
			boolean draw_cursor = this.isFocused && this.cursorCounter / 6 % 2 == 0 && valid_cursor;
			int x = this.xPos;

			if(selection_end > s.length())
				selection_end = s.length();

			// draw text left of cursor
			if(s.length() > 0){
				String s1 = valid_cursor ? s.substring(0, cursor) : s;
				x = this.fontRenderer.drawString(s1, this.xPos, this.yPos, text_color);
			}

			boolean vertical_cursor = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
			int xx = x;

			if(!valid_cursor){
				xx = cursor > 0 ? this.xPos + this.width : this.xPos;
			} else if(vertical_cursor){
				xx = x - 1;
//				x--;
			}

			// draw text right of cursor
			if(s.length() > 0 && valid_cursor && cursor < s.length())
				this.fontRenderer.drawString(s.substring(cursor), x, this.yPos, text_color);

			// blinking cursor
			if(draw_cursor){
				if(vertical_cursor)
					UI.drawRect(xx, this.yPos - (this.borderPadding>>2), 1, (this.borderPadding>>1) + this.fontRenderer.FONT_HEIGHT, this.cursorColor);
				else
					this.fontRenderer.drawString("_", xx, this.yPos, text_color);
			}

			// highlight selection
			if(selection_end != cursor){
				int end_x = this.xPos + this.fontRenderer.getStringWidth(s.substring(0, selection_end));
				this.drawCursorVertical(xx, this.yPos - 1, end_x - 1, this.yPos + 1 + this.fontRenderer.FONT_HEIGHT);
			}
		}
	}

	private void drawCursorVertical(int x1, int y1, int x2, int y2){
		int tmp;
		if(x1 < x2){
			tmp = x1;
			x1 = x2;
			x2 = tmp;
		}
		if(y1 < y2){
			tmp = y1;
			y1 = y2;
			y2 = tmp;
		}

		Tessellator tessellator = Tessellator.instance;
		UI.setColor(this.selectionColor);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_COLOR_LOGIC_OP);
		GL11.glLogicOp(GL11.GL_OR_REVERSE);
		tessellator.startDrawingQuads();
		tessellator.addVertex((double)x1, (double)y2, 0.0D);
		tessellator.addVertex((double)x2, (double)y2, 0.0D);
		tessellator.addVertex((double)x2, (double)y1, 0.0D);
		tessellator.addVertex((double)x1, (double)y1, 0.0D);
		tessellator.draw();
		GL11.glDisable(GL11.GL_COLOR_LOGIC_OP);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	//////////

	public String[] getTooltip(){
		return this.tooltip;
	}
	public TextField setTooltip(String tooltip){
		this.tooltip = tooltip.split("\n");
		return this;
	}

	public String getText(){
		return this.text;
	}
	public void setText(String text){
		if(text.length() > this.maxStringLength)
			this.text = text.substring(0, this.maxStringLength);
		else
			this.text = text;
		this.setCursorPositionEnd();
	}

	public String getSelectedText(){
		int start = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
		int end = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
		return this.text.substring(start, end);
	}
// TODO: setSelectedText ?

	public int getMaxStringLength(){
		return this.maxStringLength;
	}
	public void setMaxStringLength(int max){
		this.maxStringLength = max;
		if(this.text.length() > max)
			this.text = this.text.substring(0, max);
	}

	public int getCursorPosition(){
		return this.cursorPosition;
	}
// TODO: setCursorPosition ?

	// no gets
	public void setTextColor(int color){
		this.enabledColor = color;
	}
	public void setDisabledTextColor(int color){
		this.disabledColor = color;
	}

	public boolean isFocused(){
		return this.isFocused;
	}
	public void setFocused(boolean focused){
		if(focused && !this.isFocused)
			this.cursorCounter = 0;

		this.isFocused = focused;

		#ifdef WITH_API_NEI
		if(TextField.with_nei) this.nei_widget.setFocus(focused);
		#endif
	}

	// no get
	public void setEnabled(boolean enabled){
		this.isEnabled = enabled;
	}

	public int getSelectionEnd(){
		return this.selectionEnd;
	}
	public void setSelectionPos(int position){
		int j = this.text.length();

		if(position > j)
			position = j;

		if(position < 0)
			position = 0;

		this.selectionEnd = position;

		if(this.fontRenderer != null){
			if(this.lineScrollOffset > j)
				this.lineScrollOffset = j;

			int k = this.width;
			String s = this.fontRenderer.trimStringToWidth(this.text.substring(this.lineScrollOffset), k);
			int l = s.length() + this.lineScrollOffset;

			if(position == this.lineScrollOffset)
				this.lineScrollOffset -= this.fontRenderer.trimStringToWidth(this.text, k, true).length();

			if(position > l)
				this.lineScrollOffset += position - l;
			else if(position <= this.lineScrollOffset)
				this.lineScrollOffset -= this.lineScrollOffset - position;

			if(this.lineScrollOffset < 0)
				this.lineScrollOffset = 0;

			if(this.lineScrollOffset > j)
				this.lineScrollOffset = j;
		}
	}

	public boolean canLoseFocus(){
		return this.canLoseFocus;
	}
	public void setCanLoseFocus(boolean canLoseFocus){
		this.canLoseFocus = canLoseFocus;
	}

	public boolean getVisible(){
		return this.visible;
	}
	public void setVisible(boolean visible){
		this.visible = visible;
	}

	//////////

	public void writeText(String par1Str){
		String s1 = "";
		String s2 = ChatAllowedCharacters.filerAllowedCharacters(par1Str);
		int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
		int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
		int k = this.maxStringLength - this.text.length() - (i - this.selectionEnd);
		boolean flag = false;

		if(this.text.length() > 0)
			s1 = s1 + this.text.substring(0, i);

		int l;

		if(k < s2.length()){
			s1 = s1 + s2.substring(0, k);
			l = k;
		} else {
			s1 = s1 + s2;
			l = s2.length();
		}

		if(this.text.length() > 0 && j < this.text.length())
			s1 = s1 + this.text.substring(j);

		this.text = s1;
		this.moveCursorBy(i - this.selectionEnd + l);
	}

	public void deleteWords(int par1){
		if(this.text.length() != 0){
			if(this.selectionEnd != this.cursorPosition)
				this.writeText("");
			else
				this.deleteFromCursor(this.getNthWordFromCursor(par1) - this.cursorPosition);
		}
	}

	public void deleteFromCursor(int par1){
		if(this.text.length() != 0){
			if(this.selectionEnd != this.cursorPosition){
				this.writeText("");
			} else {
				boolean flag = par1 < 0;
				int j = flag ? this.cursorPosition + par1 : this.cursorPosition;
				int k = flag ? this.cursorPosition : this.cursorPosition + par1;
				String s = "";

				if(j >= 0)
					s = this.text.substring(0, j);

				if(k < this.text.length())
					s = s + this.text.substring(k);

				this.text = s;

				if(flag)
					this.moveCursorBy(par1);
			}
		}
	}

	public int getNthWordFromCursor(int par1){
		return this.getNthWordFromPos(par1, this.getCursorPosition());
	}

	public int getNthWordFromPos(int par1, int par2){
		return this.func_73798_a(par1, this.getCursorPosition(), true);
	}

	public int func_73798_a(int par1, int par2, boolean par3){
		int k = par2;
		boolean flag1 = par1 < 0;
		int l = Math.abs(par1);

		for (int i1 = 0; i1 < l; ++i1){
			if(flag1){
				while (par3 && k > 0 && this.text.charAt(k - 1) == 32)
					--k;

				while (k > 0 && this.text.charAt(k - 1) != 32)
					--k;
			} else {
				int j1 = this.text.length();
				k = this.text.indexOf(32, k);

				if(k == -1)
					k = j1;
				else
					while (par3 && k < j1 && this.text.charAt(k) == 32)
						++k;
			}
		}

		return k;
	}

	public void moveCursorBy(int par1){
		this.setCursorPosition(this.selectionEnd + par1);
	}

	public void setCursorPosition(int par1){
		this.cursorPosition = par1;
		int j = this.text.length();

		if(this.cursorPosition < 0)
			this.cursorPosition = 0;

		if(this.cursorPosition > j)
			this.cursorPosition = j;

		this.setSelectionPos(this.cursorPosition);
	}

	public void setCursorPositionZero(){
		this.setCursorPosition(0);
	}

	public void setCursorPositionEnd(){
		this.setCursorPosition(this.text.length());
	}

	//////////

	public void updateCursorCounter(){
		this.cursorCounter++;
	}

	public boolean textboxKeyTyped(char par1, int par2){
		if(this.isEnabled && this.isFocused){
			switch (par1){
				case 1:
					this.setCursorPositionEnd();
					this.setSelectionPos(0);
					return true;
				case 3:
					GuiScreen.setClipboardString(this.getSelectedText());
					return true;
				case 22:
					this.writeText(GuiScreen.getClipboardString());
					return true;
				case 24:
					GuiScreen.setClipboardString(this.getSelectedText());
					this.writeText("");
					return true;
				default:
					switch (par2){
						case 14:
							if(GuiScreen.isCtrlKeyDown())
								this.deleteWords(-1);
							else
								this.deleteFromCursor(-1);

							return true;
						case 199:
							if(GuiScreen.isShiftKeyDown())
								this.setSelectionPos(0);
							else
								this.setCursorPositionZero();

							return true;
						case 203:
							if(GuiScreen.isShiftKeyDown()){
								if(GuiScreen.isCtrlKeyDown())
									this.setSelectionPos(this.getNthWordFromPos(-1, this.getSelectionEnd()));
								else
									this.setSelectionPos(this.getSelectionEnd() - 1);
							} else if(GuiScreen.isCtrlKeyDown())
								this.setCursorPosition(this.getNthWordFromCursor(-1));
							else
								this.moveCursorBy(-1);

							return true;
						case 205:
							if(GuiScreen.isShiftKeyDown()){
								if(GuiScreen.isCtrlKeyDown())
									this.setSelectionPos(this.getNthWordFromPos(1, this.getSelectionEnd()));
								else
									this.setSelectionPos(this.getSelectionEnd() + 1);
							}
							else if(GuiScreen.isCtrlKeyDown())
								this.setCursorPosition(this.getNthWordFromCursor(1));
							else
								this.moveCursorBy(1);

							return true;
						case 207:
							if(GuiScreen.isShiftKeyDown())
								this.setSelectionPos(this.text.length());
							else
								this.setCursorPositionEnd();

							return true;
						case 211:
							if(GuiScreen.isCtrlKeyDown())
								this.deleteWords(1);
							else
								this.deleteFromCursor(1);

							return true;
						default:
							if(ChatAllowedCharacters.isAllowedCharacter(par1)){
								this.writeText(Character.toString(par1));
								return true;
							} else
								return false;
					}
			}
		} else
			return false;
	}

	public void mouseClicked(int x, int y, int button){
		boolean flag = x >= this.xPos && x < this.xPos + this.width && y >= this.yPos && y < this.yPos + this.height;

		if(this.canLoseFocus)
			this.setFocused(this.isEnabled && flag);

		if(this.isFocused && button == 0){
			int l = x - this.xPos;

			String s = this.fontRenderer.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.width);
			this.setCursorPosition(this.fontRenderer.trimStringToWidth(s, l).length() + this.lineScrollOffset);
		}
	}
	public boolean isMouseOver(int x, int y){
		// x and y are relative to container
		if(x < this.xPos - (this.borderPadding>>1) || y < this.yPos - (this.borderPadding>>1)) return false;
		if(x < this.xPos + (this.borderPadding>>1) + this.width && y < this.yPos + (this.borderPadding>>1) + this.height) return true;
		return false;
	}
}
