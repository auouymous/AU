package com.qzx.au.tts;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeDirection;

import java.util.List;
import java.util.Random;

import com.qzx.au.core.PacketUtils;
import com.qzx.au.core.TileEntityAU;

public class TileEntityTTS extends TileEntityAU {
	private boolean isPowered; // NBT
	private String ttsText; // NBT
	private int ttsMinBlocks; // NBT
	private int ttsMaxBlocks; // NBT
	private int ttsPitch; // NBT
	private int ttsPitchRange; // NBT
	private int ttsPitchShift; // NBT
	private int ttsRate; // NBT

	public TileEntityTTS(){
		super();

		this.isPowered = false;
		this.ttsText = null;
		this.ttsMinBlocks = 16;
		this.ttsMaxBlocks = 64;
		this.ttsPitch = 50;			// default = 100
		this.ttsPitchRange = 11;	// default = 11
		this.ttsPitchShift = 2;		// default = 1
		this.ttsRate = 150;			// default = 150;
	}

	//////////

	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);

		short cfg = nbt.getShort("_cfg");

		this.isPowered = (cfg & 0x1) != 0;	// 0000 0000 0001

		String text = nbt.getString("_msg");
		if(text != null){
			text = text.trim();
			if(text.equals("")) text = null;
		}

		if(text != null)
			this.ttsText = text;
		else
			this.ttsText = null;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);

		short cfg = (short)0;
		if(this.isPowered) cfg |= 0x1;

		nbt.setShort("_cfg", cfg);

		if(this.ttsText != null)
			nbt.setString("_msg", this.ttsText);
	}

	//////////

	@Override
	public ContainerTTS getContainer(InventoryPlayer inventory){
		return new ContainerTTS(inventory, this);
	}

	//////////

	@Override
	public boolean canRotate(){
		return false;
	}

	@Override
	public boolean canCamo(){
		return true;
	}

	@Override
	public boolean canOwn(){
		return false;
	}

	//////////

	public boolean isPowered(){
		return this.isPowered;
	}
	public void setPowered(boolean isPowered){
		// not sent to client
		this.isPowered = isPowered;

		this.markChunkModified();
	}

	public String getText(){
		return this.ttsText == null ? "" : this.ttsText;
	}
	public void setText(String text, boolean server){
		if(text != null){
			text = text.trim();
			if(text.equals("")) text = null;
		}
		if(text == null && this.ttsText == null) return;
		if(text != null && this.ttsText != null && text.equals(this.ttsText)) return;

		this.ttsText = text;

		if(server){
			PacketUtils.sendToAllAround(this.worldObj, PacketUtils.MAX_RANGE, THIS_MOD.packetChannel, Packets.CLIENT_TTS_SET_TEXT,
										this.xCoord, this.yCoord, this.zCoord, this.ttsText == null ? "" : this.ttsText);
			this.markChunkModified();
		} else
			GuiTTS.update_text = true;
	}

	public int getMinBlocks(){
		return this.ttsMinBlocks;
	}
	public void setMinBlocks(int blocks, boolean server){
		if(blocks < 0) blocks = 0;
		if(blocks > 64) blocks = 64;
		if(blocks != this.ttsMinBlocks){
			this.ttsMinBlocks = blocks;

			if(this.ttsMaxBlocks < this.ttsMinBlocks) this.setMaxBlocks(this.ttsMinBlocks, false);

			if(server){
				PacketUtils.sendToAllAround(this.worldObj, PacketUtils.MAX_RANGE, THIS_MOD.packetChannel, Packets.CLIENT_TTS_SET_MIN_BLOCKS,
											this.xCoord, this.yCoord, this.zCoord, this.ttsMinBlocks);
				this.markChunkModified();
			}
		}
	}

	public int getMaxBlocks(){
		return this.ttsMaxBlocks;
	}
	public void setMaxBlocks(int blocks, boolean server){
		if(blocks < 8) blocks = 8;
		if(blocks > 64) blocks = 64;
		if(blocks != this.ttsMaxBlocks){
			this.ttsMaxBlocks = blocks;

			if(this.ttsMinBlocks > this.ttsMaxBlocks) this.setMinBlocks(this.ttsMaxBlocks, false);

			if(server){
				PacketUtils.sendToAllAround(this.worldObj, PacketUtils.MAX_RANGE, THIS_MOD.packetChannel, Packets.CLIENT_TTS_SET_MAX_BLOCKS,
											this.xCoord, this.yCoord, this.zCoord, this.ttsMaxBlocks);
				this.markChunkModified();
			}
		}
	}

	@Override
	public boolean canUpdate(){
		return false;
	}

	//////////

	public void playSoundOnClient(EntityPlayer player){
		if(this.ttsText != null){
			// play sound on the client
			float amplifier = TTS.getFadedAmplifier(player, this.xCoord, this.yCoord, this.zCoord, this.ttsMinBlocks, this.ttsMaxBlocks);
			TTS.say(this.ttsPitch, this.ttsPitchRange, this.ttsPitchShift, this.ttsRate, this.ttsText, amplifier);
		}
	}

	public void sendSoundToClients(){
		if(this.ttsText != null){
			// send to all players within 64 blocks
			PacketUtils.sendToAllAround(this.worldObj, this.ttsMaxBlocks, THIS_MOD.packetChannel, Packets.CLIENT_TTS_PLAY_SOUND, this.xCoord, this.yCoord, this.zCoord);
		}
	}

	//////////

	@Override
	public String getInvName(){
		return "au.tileentity.TTS";
	}
}
