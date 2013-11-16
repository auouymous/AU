package com.qzx.au.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.entity.player.EntityPlayer;

import java.lang.reflect.Field;

public class Hacks {
	public static boolean isGamePaused(Minecraft mc){
		#if defined MC147 || defined MC152
		return mc.isGamePaused;
		#else
		Field gamePaused = Hacks.getGamePausedField();
		try {
			return (Boolean)gamePaused.get(mc);
		} catch(IllegalAccessException e){
			System.out.println("AU HUD: caught exception in isGamePaused");
			return false;
		}
		#endif
	}

	#if !defined MC147 && !defined MC152
	private static Field gamePausedFieldInstance = null;
	private static Field getGamePausedField(){
		if(Hacks.gamePausedFieldInstance == null){
			try {
				Hacks.gamePausedFieldInstance = Minecraft.class.getDeclaredField("isGamePaused");
				Hacks.gamePausedFieldInstance.setAccessible(true);
			} catch(NoSuchFieldException e){
				try {
					Hacks.gamePausedFieldInstance = Minecraft.class.getDeclaredField("field_71445_n");
					Hacks.gamePausedFieldInstance.setAccessible(true);
				} catch(NoSuchFieldException e1){
					try {
						#ifdef MC162
						Hacks.gamePausedFieldInstance = Minecraft.class.getDeclaredField("V");
						#elif defined MC164
						Hacks.gamePausedFieldInstance = Minecraft.class.getDeclaredField("V");
						#else
						System.out.println("AU HUD: unsupported minecraft version in getGamePausedField");
						return null;
						#endif
						Hacks.gamePausedFieldInstance.setAccessible(true);
					} catch(NoSuchFieldException e2){
						System.out.println("AU HUD: caught exception in getGamePausedField");
					}
				}
			}
		}
		return Hacks.gamePausedFieldInstance;
	}
	#endif

	//////////

	public static ServerData getServerData(Minecraft mc){
		#if defined MC147 || defined MC152
		return mc.getServerData();
		#else
		Field serverData = Hacks.getServerDataField();
		try {
			return (ServerData)serverData.get(mc);
		} catch(IllegalAccessException e){
			System.out.println("AU HUD: caught exception in getServerData");
			return null;
		}
		#endif

	}

	#if !defined MC147 && !defined MC152
	private static Field serverDataFieldInstance = null;
	private static Field getServerDataField(){
		if(Hacks.serverDataFieldInstance == null){
			try {
				Hacks.serverDataFieldInstance = Minecraft.class.getDeclaredField("currentServerData");
				Hacks.serverDataFieldInstance.setAccessible(true);
			} catch(NoSuchFieldException e){
				try {
					Hacks.serverDataFieldInstance = Minecraft.class.getDeclaredField("field_71422_O");
					Hacks.serverDataFieldInstance.setAccessible(true);
				} catch(NoSuchFieldException e1){
					try {
						#ifdef MC162
						Hacks.serverDataFieldInstance = Minecraft.class.getDeclaredField("M");
						#elif defined MC164
						Hacks.serverDataFieldInstance = Minecraft.class.getDeclaredField("M");
						#else
						System.out.println("AU HUD: unsupported minecraft version in getServerDataField");
						return null;
						#endif
						Hacks.serverDataFieldInstance.setAccessible(true);
					} catch(NoSuchFieldException e2){
						System.out.println("AU HUD: caught exception in getServerDataField");
					}
				}
			}
		}
		return Hacks.serverDataFieldInstance;
	}
	#endif

	//////////

	// Copyright (c) MachineMuse, 2013 http://machinemuse.net
	public static void setFOVMult(EntityPlayer player, float fovmult){
		Field movementFactor = Hacks.getMovementFactorField();
		try {
			movementFactor.set(player, fovmult);
		} catch(IllegalAccessException e){
			System.out.println("AU HUD: caught exception in setFOVMult");
		}
	}

	private static Field movementFactorFieldInstance = null;
	private static Field getMovementFactorField(){
		if(Hacks.movementFactorFieldInstance == null){
			try {
				Hacks.movementFactorFieldInstance = EntityPlayer.class.getDeclaredField("speedOnGround");
				Hacks.movementFactorFieldInstance.setAccessible(true);
			} catch(NoSuchFieldException e){
				try {
					Hacks.movementFactorFieldInstance = EntityPlayer.class.getDeclaredField("field_71108_cd");
					Hacks.movementFactorFieldInstance.setAccessible(true);
				} catch(NoSuchFieldException e1){
					try {
						#ifdef MC147
						Hacks.movementFactorFieldInstance = EntityPlayer.class.getDeclaredField("ch");
						#elif defined MC152
						Hacks.movementFactorFieldInstance = EntityPlayer.class.getDeclaredField("ci");
						#elif defined MC162
						Hacks.movementFactorFieldInstance = EntityPlayer.class.getDeclaredField("bK");
						#elif defined MC164
						Hacks.movementFactorFieldInstance = EntityPlayer.class.getDeclaredField("bK");
						#else
						System.out.println("AU HUD: unsupported minecraft version in getMovementFactorField");
						return null;
						#endif
						Hacks.movementFactorFieldInstance.setAccessible(true);
					} catch(NoSuchFieldException e2){
						System.out.println("AU HUD: caught exception in getMovementFactorField");
					}
				}
			}
		}
		return Hacks.movementFactorFieldInstance;
	}
}
