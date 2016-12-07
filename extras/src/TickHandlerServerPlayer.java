package com.qzx.au.extras;

#ifdef MC164
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
#define IMPLEMENTS_ITICKHANDLER implements ITickHandler
#define USE_TICK_REGISTRY
#else
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
#define IMPLEMENTS_ITICKHANDLER
#endif

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

#ifdef USE_TICK_REGISTRY
import java.util.EnumSet;
#endif

public class TickHandlerServerPlayer IMPLEMENTS_ITICKHANDLER {
	#ifdef USE_TICK_REGISTRY
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData){
//		EntityPlayer player = (EntityPlayer)tickData[0];
	#else
	FML_SUBSCRIBE
	public void onTick(PlayerTickEvent event){
//		EntityPlayer player = event.player;
	#endif
/*
		if(player != null){
			ItemStack itemstack = player.GET_ARMOR_SLOT(0);
			Item item = (itemstack == null ? null : itemstack.getItem());
			if(item instanceof ItemEnderXT){
				
			}
		}
*/
	}

	#ifdef USE_TICK_REGISTRY
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData){}
	#endif

	#ifdef USE_TICK_REGISTRY
	@Override
	public EnumSet<TickType> ticks(){
		return EnumSet.of(TickType.PLAYER);
	}
	#endif

	#ifdef USE_TICK_REGISTRY
	@Override
	public String getLabel(){
		return THIS_MOD.modID+": Server Player Tick";
	}
	#endif
}
