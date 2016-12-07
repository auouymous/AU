package com.qzx.au.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
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
			Failure.log("hacks, isGamePaused");
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
						#ifdef MC164
						Hacks.gamePausedFieldInstance = Minecraft.class.getDeclaredField("V");
						#elif defined MC172
						Hacks.gamePausedFieldInstance = Minecraft.class.getDeclaredField("T");
						#elif defined MC17A
						Hacks.gamePausedFieldInstance = Minecraft.class.getDeclaredField("V");
						#else
						Failure.log("hacks, unsupported minecraft version in getGamePausedField");
						return null;
						#endif
						Hacks.gamePausedFieldInstance.setAccessible(true);
					} catch(NoSuchFieldException e2){
						Failure.log("hacks, getGamePausedField");
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
			Failure.log("hacks, getServerData");
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
						#ifdef MC164
						Hacks.serverDataFieldInstance = Minecraft.class.getDeclaredField("M");
						#elif defined MC172
						Hacks.serverDataFieldInstance = Minecraft.class.getDeclaredField("K");
						#elif defined MC17A
						Hacks.serverDataFieldInstance = Minecraft.class.getDeclaredField("K");
						#else
						Failure.log("hacks, unsupported minecraft version in getServerDataField");
						return null;
						#endif
						Hacks.serverDataFieldInstance.setAccessible(true);
					} catch(NoSuchFieldException e2){
						Failure.log("hacks, getServerDataField");
					}
				}
			}
		}
		return Hacks.serverDataFieldInstance;
	}
	#endif

	//////////

	public static void setCameraZoom(double zoom){
		Field cameraZoom = Hacks.getCameraZoomField();
		try {
			cameraZoom.set(Minecraft.getMinecraft().entityRenderer, zoom);
		} catch(IllegalAccessException e){
			Failure.log("hacks, setCameraZoom");
		}
	}

	private static Field cameraZoomFieldInstance = null;
	private static Field getCameraZoomField(){
		if(Hacks.cameraZoomFieldInstance == null){
			try {
				Hacks.cameraZoomFieldInstance = EntityRenderer.class.getDeclaredField("cameraZoom");
				Hacks.cameraZoomFieldInstance.setAccessible(true);
			} catch(NoSuchFieldException e){
				try {
					Hacks.cameraZoomFieldInstance = EntityRenderer.class.getDeclaredField("field_78503_V");
					Hacks.cameraZoomFieldInstance.setAccessible(true);
				} catch(NoSuchFieldException e1){
					try {
						#ifdef MC147
						Hacks.cameraZoomFieldInstance = EntityRenderer.class.getDeclaredField("X");
						#elif defined MC152
						Hacks.cameraZoomFieldInstance = EntityRenderer.class.getDeclaredField("X");
						#elif defined MC164
						Hacks.cameraZoomFieldInstance = EntityRenderer.class.getDeclaredField("Y");
						#elif defined MC172
						Hacks.cameraZoomFieldInstance = EntityRenderer.class.getDeclaredField("af");
						#elif defined MC17A
						Hacks.cameraZoomFieldInstance = EntityRenderer.class.getDeclaredField("af");
						#else
						Failure.log("hacks, unsupported minecraft version in getCameraZoomField");
						return null;
						#endif
						Hacks.cameraZoomFieldInstance.setAccessible(true);
					} catch(NoSuchFieldException e2){
						Failure.log("hacks, getCameraZoomField");
					}
				}
			}
		}
		return Hacks.cameraZoomFieldInstance;
	}

	//////////

	public static int getEntityID(Entity entity){
		#if defined MC147 || defined MC152 || defined MC164
		return entity.entityId;
		#else
		Field entityID = Hacks.getEntityIDField();
		try {
			return (Integer)entityID.get(entity);
		} catch(IllegalAccessException e){
			Failure.log("hacks, getEntityID");
			return 0;
		}
		#endif
	}

	#if !defined MC147 && !defined MC152 && !defined MC164
	private static Field entityIDFieldInstance = null;
	private static Field getEntityIDField(){
		if(Hacks.entityIDFieldInstance == null){
			try {
				Hacks.entityIDFieldInstance = Entity.class.getDeclaredField("entityId");
				Hacks.entityIDFieldInstance.setAccessible(true);
			} catch(NoSuchFieldException e){
				try {
					Hacks.entityIDFieldInstance = Entity.class.getDeclaredField("field_145783_c");
					Hacks.entityIDFieldInstance.setAccessible(true);
				} catch(NoSuchFieldException e1){
					try {
						#ifdef MC172
						Hacks.entityIDFieldInstance = Minecraft.class.getDeclaredField("c");
						#elif defined MC17A
						Hacks.entityIDFieldInstance = Minecraft.class.getDeclaredField("c");
						#else
						Failure.log("hacks, unsupported minecraft version in getEntityIDField");
						return null;
						#endif
						Hacks.entityIDFieldInstance.setAccessible(true);
					} catch(NoSuchFieldException e2){
						Failure.log("hacks, getEntityIDField");
					}
				}
			}
		}
		return Hacks.entityIDFieldInstance;
	}
	#endif
}
