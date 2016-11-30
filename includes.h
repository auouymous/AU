#if defined MC147 || defined MC152 || defined MC164
	#define IMPORT_FML import cpw.mods.fml.common.Mod;\
		import cpw.mods.fml.common.Mod.Instance;\
		import cpw.mods.fml.common.event.FMLPreInitializationEvent;\
		import cpw.mods.fml.common.event.FMLInitializationEvent;\
		import cpw.mods.fml.common.event.FMLPostInitializationEvent;\
		import cpw.mods.fml.common.network.NetworkMod;\
		import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
	#define FML_PREINIT @cpw.mods.fml.common.Mod.PreInit
	#define FML_INIT @cpw.mods.fml.common.Mod.Init
	#define FML_POSTINIT @cpw.mods.fml.common.Mod.PostInit

	#define FML_SUBSCRIBE @net.minecraftforge.event.ForgeSubscribe

	#define NETWORK_REGISTRY_INSTANCE NetworkRegistry.instance()

	// 1.7+ imports Block, Blocks, Item and Items
	#define IMPORT_BLOCKS import net.minecraft.block.Block;
	#define IMPORT_ITEMS import net.minecraft.item.Item;
	#define MC_BLOCK net.minecraft.block.Block
	#define MC_ITEM net.minecraft.item.Item
	#define MC_BLOCK_SILVERFISH MC_BLOCK.silverfish
	#define MC_BLOCK_STONEBRICK MC_BLOCK.stoneBrick
	#define MC_ITEM_ITEMFRAME MC_ITEM.itemFrame
	#define MC_ITEM_LEATHERHELMET MC_ITEM.helmetLeather
	#define MC_ITEM_LEATHERCHEST MC_ITEM.plateLeather
	#define MC_ITEM_LEATHERLEGS MC_ITEM.legsLeather
	#define MC_ITEM_LEATHERBOOTS MC_ITEM.bootsLeather

	#define GET_BLOCK_ID(block) block.blockID
	#define GET_ITEM_ID(item) item.itemID
	#define GET_ITEMSTACK_ID(itemstack) itemstack.itemID
	#define SET_BLOCK_NAME setUnlocalizedName
	#define GET_BLOCK_BY_ID(id) net.minecraft.block.Block.blocksList[id]

	#define MC_ICON_REGISTER net.minecraft.client.renderer.texture.IconRegister
	#define MC_ICON net.minecraft.util.Icon

	#define IMPORT_FORGE_DIRECTION import net.minecraftforge.common.ForgeDirection;

	#define SET_BLOCK_HARVEST_TOOL(block, tool, level) MinecraftForge.setBlockHarvestLevel(block, tool, level)
	//#define SET_BLOCK_HARVEST_TOOL_META(block, meta, tool, level) MinecraftForge.setBlockHarvestLevel(block, tool, level)

	#define MOVINGOBJECTTYPE_BLOCK net.minecraft.util.EnumMovingObjectType.TILE
	#define MOVINGOBJECTTYPE_ENTITY net.minecraft.util.EnumMovingObjectType.ENTITY

	#define GET_ENTITY_NAME getEntityName

	#define GET_ARMOR_SLOT getCurrentItemOrArmor

	#define GET_KEY_CODE keyCode
	#define IS_KEY_PRESSED pressed

	#define GET_NBT_TAGLIST(name, type) getTagList(name)
	#define GET_NBT_TAGCOMPOUND_AT tagAt
#else
	#define NO_IDS 1

	#define IMPORT_FML import cpw.mods.fml.common.Mod;\
		import cpw.mods.fml.common.Mod.Instance;\
		import cpw.mods.fml.common.event.FMLPreInitializationEvent;\
		import cpw.mods.fml.common.event.FMLInitializationEvent;\
		import cpw.mods.fml.common.event.FMLPostInitializationEvent;
	#define FML_PREINIT @cpw.mods.fml.common.Mod.EventHandler
	#define FML_INIT @cpw.mods.fml.common.Mod.EventHandler
	#define FML_POSTINIT @cpw.mods.fml.common.Mod.EventHandler

	#define FML_SUBSCRIBE @cpw.mods.fml.common.eventhandler.SubscribeEvent

	#define NETWORK_REGISTRY_INSTANCE NetworkRegistry.INSTANCE

	#define IMPORT_BLOCKS import net.minecraft.block.Block; import net.minecraft.init.Blocks;
	#define IMPORT_ITEMS import net.minecraft.item.Item; import net.minecraft.init.Items;
	#define MC_BLOCK net.minecraft.init.Blocks
	#define MC_ITEM net.minecraft.init.Items
	#define MC_BLOCK_SILVERFISH MC_BLOCK.monster_egg
	#define MC_BLOCK_STONEBRICK MC_BLOCK.stonebrick
	#define MC_ITEM_ITEMFRAME MC_ITEM.item_frame
	#define MC_ITEM_LEATHERHELMET MC_ITEM.leather_helmet
	#define MC_ITEM_LEATHERCHEST MC_ITEM.leather_chestplate
	#define MC_ITEM_LEATHERLEGS MC_ITEM.leather_leggings
	#define MC_ITEM_LEATHERBOOTS MC_ITEM.leather_boots

	#define GET_BLOCK_ID(_block) net.minecraft.block.Block.getIdFromBlock(_block)
	#define GET_ITEM_ID(_item) net.minecraft.item.Item.getIdFromItem(_item)
	#define GET_ITEMSTACK_ID(itemstack) net.minecraft.item.Item.getIdFromItem(itemstack.getItem())
	#define SET_BLOCK_NAME setBlockName
	#define GET_BLOCK_BY_ID(id) net.minecraft.block.Block.getBlockById(id)

	#define MC_ICON_REGISTER net.minecraft.client.renderer.texture.IIconRegister
	#define MC_ICON net.minecraft.util.IIcon

	#define IMPORT_FORGE_DIRECTION import net.minecraftforge.common.util.ForgeDirection;

	#define SET_BLOCK_HARVEST_TOOL(block, tool, level) block.setHarvestLevel(tool, level)
	//#define SET_BLOCK_HARVEST_TOOL_META(block, meta, tool, level) block.setHarvestLevel(tool, level, meta)

	#define MOVINGOBJECTTYPE_BLOCK net.minecraft.util.MovingObjectPosition.MovingObjectType.BLOCK
	#define MOVINGOBJECTTYPE_ENTITY net.minecraft.util.MovingObjectPosition.MovingObjectType.ENTITY

	#define GET_ENTITY_NAME getCommandSenderName

	#define GET_ARMOR_SLOT getEquipmentInSlot

	#define GET_KEY_CODE getKeyCode()
	#define IS_KEY_PRESSED getIsKeyPressed()

	#define GET_NBT_TAGLIST(name, type) getTagList(name, type)
	#define GET_NBT_TAGCOMPOUND_AT getCompoundTagAt
#endif

#if defined MC147 || defined MC152 || defined MC164 || defined MC172
	#define SCALED_RESOLUTION(mc) ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight)
#else
	#define SCALED_RESOLUTION(mc) ScaledResolution(mc, mc.displayWidth, mc.displayHeight)
#endif
