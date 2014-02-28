package com.qzx.au.idmap;

IMPORT_FML
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.network.NetworkMod;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

@Mod(modid=THIS_MOD.modID, name="Altered Unification ID Map", version=THIS_MOD.modVersion)
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class THIS_MOD {
	@Instance(THIS_MOD.modID)
	public static THIS_MOD instance;

	public static final String modID = "au_idmap";
	public static final String modVersion = "0.0.0";

	private static final String filename = "au_ID_map.txt";

	FML_PREINIT
	public void preInit(FMLPreInitializationEvent event){}

	FML_INIT
	public void load(FMLInitializationEvent event){}

	FML_POSTINIT
	public void postInit(FMLPostInitializationEvent event){
		String dir;
		try {
			dir = Minecraft.getMinecraft().mcDataDir.getAbsolutePath();
		} catch(NoClassDefFoundError e){
			dir = MinecraftServer.getServer().getFile("").getAbsolutePath();
		}
		File file = new File(dir, THIS_MOD.filename);
		PrintWriter out;
		try {
			file.createNewFile();
			out = new PrintWriter(new FileWriter(file));
		} catch(Throwable e){
			System.err.println(THIS_MOD.modID+": Unable to create file: "+dir+"/"+THIS_MOD.filename);
			return;
		}

		out.println("---------------------------------------------");

		int start = -1, nr_blocks = Block.blocksList.length, nr_items = Item.itemsList.length;
		for(int i = 0; i < nr_blocks; i++){
			if(Block.blocksList[i] == null){
				if(start == -1) start = i;
			} else if(start != -1){
				showFreeRange(out, "block", start, i-1);
				start = -1;
			}
		}
		if(start != -1)
			showFreeRange(out, "block", start, nr_blocks-1);

		out.println("---------------------------------------------");

		start = -1;
		for(int i = 0; i < nr_items; i++){
			if(Item.itemsList[i] == null){
				if(start == -1) start = i;
			} else if(start != -1){
				showFreeRange(out, " item", start, i-1);
				start = -1;
			}
		}
		if(start != -1)
			showFreeRange(out, " item", start, nr_items-1);

		out.println("---------------------------------------------");
		out.close();
	}

	private void showFreeRange(PrintWriter out, String type, int start, int end){
		if(start == end)
			out.println("     1  "+type+"  available at  "+String.format("%5d", start));
		else
			out.println(String.format("%6d", end-start+1)+"  "+type+"s available at  "+String.format("%5d", start)+"  -  "+String.format("%5d", end));
	}
}
