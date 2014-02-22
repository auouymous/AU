package com.qzx.au.tts;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

@SideOnly(Side.CLIENT)
public class SpeechThread implements Runnable {
	private float voicePitch;
	private float voicePitchRange;
	private float voicePitchShift;
	private float voiceRate;
	private String voiceText;
	private float voiceAmplifier;

	public SpeechThread(float pitch, float pitchRange, float pitchShift, float rate, String text, float amplifier){
		super();
		this.voicePitch = pitch;
		this.voicePitchRange = pitchRange;
		this.voicePitchShift = pitchShift;
		this.voiceRate = rate;
		this.voiceText = text;
		this.voiceAmplifier = amplifier;
	}

	@Override
	public void run(){
		System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

System.out.println("--- run "+this.voicePitch+", "+this.voicePitchRange+", "+this.voicePitchShift+", "+this.voiceRate+", \""+this.voiceText+"\" @ "+this.voiceAmplifier);

		try {
			String voice_name = "kevin16"; // "kevin"

			VoiceManager vm = VoiceManager.getInstance();
			Voice voice;
			voice = vm.getVoice(voice_name);
			voice.allocate();
			// minecraft volume is boosted to better match game sound levels
			voice.setVolume(this.voiceAmplifier * ((Minecraft.getMinecraft().gameSettings.soundVolume / 4.0F) + 0.75F));
			voice.setPitch(this.voicePitch);
			voice.setPitchRange(this.voicePitchRange);
			voice.setPitchShift(this.voicePitchShift);
			voice.setRate(this.voiceRate);
			voice.speak(this.voiceText);
System.out.println("--- DONE SPEAKING");

			return;
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
