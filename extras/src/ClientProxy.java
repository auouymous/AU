package com.qzx.au.extras;

#ifdef MC164
import cpw.mods.fml.common.registry.TickRegistry;
#define USE_TICK_REGISTRY
#else
import cpw.mods.fml.common.FMLCommonHandler;
#endif

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.qzx.au.core.BlockCoord;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
	public static TickHandlerClientRender tickHandlerClientRender = new TickHandlerClientRender();

	public static int renderPass = 0;
	public static int infuserRenderType;
	public static int slabRenderType;
	public static int glassRenderType;
	public static int paneRenderType;
	public static int lampRenderType;
	public static int flowerRenderType;

	@Override
	public void registerEvents(){}

	@Override
	public void registerRenderers(){
		if(Cfg.enableChromaInfuser){
			infuserRenderType = RenderingRegistry.getNextAvailableRenderId();
			RenderingRegistry.registerBlockHandler(new RendererChromaInfuser());
		}
		if(Cfg.enableChiseledBrickSlabs && Cfg.enableSmoothBrickSlabs){
			slabRenderType = RenderingRegistry.getNextAvailableRenderId();
			RenderingRegistry.registerBlockHandler(new RendererSlab());
		}
		if(Cfg.enableGlassTinted || Cfg.enableGlassTintedNoFrame){
			glassRenderType = RenderingRegistry.getNextAvailableRenderId();
			RenderingRegistry.registerBlockHandler(new RendererGlass());
		}
		if(Cfg.enableGlassPane || Cfg.enableGlassPaneTinted || Cfg.enableGlassPaneTintedNoFrame || Cfg.enableIronBars){
			paneRenderType = RenderingRegistry.getNextAvailableRenderId();
			RenderingRegistry.registerBlockHandler(new RendererPane());
		}
		if(Cfg.enableLamps){
			lampRenderType = RenderingRegistry.getNextAvailableRenderId();
			RenderingRegistry.registerBlockHandler(new RendererLamp());
		}
		if(Cfg.enableFlowers){
			flowerRenderType = RenderingRegistry.getNextAvailableRenderId();
			RenderingRegistry.registerBlockHandler(new RendererFlower());
		}
	}

	@Override
	public void registerHandlers(){
		super.registerHandlers();

		// Tick Handler (client render)
		#ifdef USE_TICK_REGISTRY
		TickRegistry.registerTickHandler(this.tickHandlerClientRender, Side.CLIENT);
		#else
		FMLCommonHandler.instance().bus().register(this.tickHandlerClientRender);
		#endif
	}

	//////////

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z){
		if(id == Guis.TILE_GUI){
			// all TileEntityAUs that have a gui
			TileEntity tileEntity = BlockCoord.getTileEntity(world, x, y, z);
			if(tileEntity instanceof TileEntityChromaInfuser)
				return new GuiChromaInfuser(player.inventory, (TileEntityChromaInfuser)tileEntity);
			if(tileEntity instanceof TileEntityEnderCube)
				return new GuiEnderCube(player.inventory, (TileEntityEnderCube)tileEntity);
		}
		return null;
	}
}
