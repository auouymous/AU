#if defined MC147 || defined MC152 || defined MC164
	#define IMPORT_BLOCKS import net.minecraft.block.Block;
	#define IMPORT_ITEMS import net.minecraft.item.Item;
	#define MC_BLOCK Block
	#define MC_ITEM Item

	#define FML_PREINIT @PreInit
	#define FML_INIT @Init
	#define FML_POSTINIT @PostInit
#else
	#define IMPORT_BLOCKS import net.minecraft.block.Block; import net.minecraft.init.Blocks;
	#define IMPORT_ITEMS import net.minecraft.item.Item; import net.minecraft.init.Items;
	#define MC_BLOCK Blocks
	#define MC_ITEM Items

	#define FML_PREINIT @EventHandler
	#define FML_INIT @EventHandler
	#define FML_POSTINIT @EventHandler
#endif