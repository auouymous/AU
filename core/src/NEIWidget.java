package com.qzx.au.core;

#ifdef WITH_API_NEI

import net.minecraft.client.gui.Gui;

import codechicken.nei.LayoutManager;
import codechicken.nei.Widget;

public class NEIWidget extends Widget {
	private Gui gui_widget;
	public NEIWidget(Gui widget){
		this.gui_widget = widget;
	}

	@Override
	public boolean handleKeyPress(int keyID, char keyChar){
		if(!this.focused())
			return false;

		// consume all keys to keep NEI from triggering
		if(this.gui_widget instanceof TextField) return ((TextField)this.gui_widget).textboxKeyTyped(keyChar, keyID);
		return false;
	}

	public void setFocus(boolean focus){
		if(focus)
			LayoutManager.setInputFocused(this);
		else if(this.focused())
			LayoutManager.setInputFocused(null);
	}

	public boolean focused(){
		return LayoutManager.getInputFocused() == this;
	}

	public void draw(int mousex, int mousey){}
}

#endif
