package com.qzx.au.tts;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class TTS {
	public static float getFadedAmplifier(EntityPlayer player, int x, int y, int z, int afterBlocks, int maxBlocks){
		// begin fading if player is more than afterBlocks away, cut off sound at maxBlocks
		float dX = (float)(player.posX - x);
		float dY = (float)(player.posY - y);
		float dZ = (float)(player.posZ - z);
		float distance = (float)Math.sqrt(dX*dX + dY*dY + dZ*dZ);
		if(distance > maxBlocks) return 0.0F;
		distance -= afterBlocks;
		if(distance < 0.0F) distance = 0.0F;
		return (maxBlocks - distance) / maxBlocks;
	}

	public static void say(float pitch, float pitchRange, float pitchShift, float rate, String text, float amplifier){
		if(amplifier <= 0.0F) return;

		SpeechThread st = new SpeechThread(pitch, pitchRange, pitchShift, rate, text, amplifier);

System.out.println("--- TTS.say("+pitch+", "+pitchRange+", "+pitchShift+", "+rate+", \""+text+"\", "+amplifier+")");

// TODO: push threads to a stack here
//	- can threads call a method here when done? remove them from stack
//	- if stack is full
//		- pop first thread on stack, and kill it
//		- then push new thread to stack

		Thread t = new Thread(st);

		t.start();
	}
}
