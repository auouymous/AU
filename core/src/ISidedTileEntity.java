package com.qzx.au.core;

public interface ISidedTileEntity {
	public abstract SidedBlockInfo getSidedBlockInfo();
	public abstract Class<? extends ISidedRenderer> getSidedRenderer();
	public abstract void toggleSide(byte side);
	public abstract void setSide(byte side, byte slot);
}
