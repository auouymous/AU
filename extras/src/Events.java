package com.qzx.au.extras;

import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.Event.Result;

public class Events {
	FML_SUBSCRIBE
	public void onUseBonemeal(BonemealEvent event){
		if(event.ID == THIS_MOD.blockFlowerSeed.blockID){
			if(!event.world.isRemote)
				((BlockFlowerSeed)THIS_MOD.blockFlowerSeed).growFlower(event.world, event.X, event.Y, event.Z);
			event.setResult(Result.ALLOW);
		}
	}
}
