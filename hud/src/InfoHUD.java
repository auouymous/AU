package com.qzx.au.hud;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
//import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.FoodStats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.IShearable;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.qzx.au.util.UI;

@SideOnly(Side.CLIENT)
public class InfoHUD {
	private UI ui = new UI();

	public InfoHUD(){}

	//////////

	private static final String[] toolLevels = { "Wooden", "Stone", "Iron", "Diamond", "Unknown" };
	private static final int[] toolColors = { 0x866526, 0x9a9a9a, 0xffffff, 0x33ebcb, 0x000000 };
	private void showTool(String tool, int level){
		if(level > 3 || level < 0) level = 4;
		this.ui.drawString("(", 0xaaaaaa);
		this.ui.drawString(this.toolLevels[level] + " " + tool, this.toolColors[level]);
		this.ui.drawString(") ", 0xaaaaaa);
	}
	private void showTool(String tool){
		this.ui.drawString("(", 0xaaaaaa);
		this.ui.drawString(tool, 0xffffff); // use iron color
		this.ui.drawString(") ", 0xaaaaaa);
	}

	//////////

	private void showItemName(String name, ItemStack item){
		if(item == null) return;

		this.ui.drawString("   " + name + ": ", 0xaaaaaa);
		this.ui.drawString(item.getDisplayName(), 0xffffff);
		this.ui.lineBreak();
	}

	//////////

	private int invItemX = 0;
	private int invItemCount = 0;

	//////////

	private int lastBlockID = 0;
	private int lastBlockMetadata = 0;
	private class LastDrop {
		public int id;
		public int metadata;
		public String name;
		public LastDrop(int droppedID, int droppedMetadata, String droppedName){
			this.id = droppedID;
			this.metadata = droppedMetadata;
			this.name = droppedName;
		}
		@Override
		public boolean equals(Object obj){
			if(obj instanceof LastDrop){
				LastDrop drop = (LastDrop)obj;
				if(drop.id == this.id && drop.metadata == this.metadata) return true;
			}
			return false;
		}
	}
	private ArrayList<LastDrop> lastDrops = new ArrayList<LastDrop>();

	//////////

	public void draw(Minecraft mc, ScaledResolution screen, EntityPlayer player){
		GL11.glPushMatrix();

		int pos_x = MathHelper.floor_double(mc.thePlayer.posX);
		int pos_y = MathHelper.floor_double(mc.thePlayer.posY);
		int pos_z = MathHelper.floor_double(mc.thePlayer.posZ);
		int feet_y = MathHelper.floor_double(mc.thePlayer.boundingBox.minY);

		World world = mc.isIntegratedServerRunning() ? mc.getIntegratedServer().worldServerForDimension(mc.thePlayer.dimension) : mc.theWorld;
		if(world == null) return;

		Chunk chunk = world.getChunkFromBlockCoords(pos_x, pos_z);
		if(chunk == null) return;

		this.ui.setCursor(Cfg.info_hud_x, Cfg.info_hud_y);

		try {

			// world
			// biome
			if(Cfg.show_world || Cfg.show_biome){
				if(Cfg.show_world)
					this.ui.drawString(world.provider.getDimensionName(), 0xffffff);
				if(Cfg.show_biome){
					BiomeGenBase biome = chunk.getBiomeGenForWorldCoords(pos_x & 15, pos_z & 15, world.getWorldChunkManager());
					if(Cfg.show_world) this.ui.drawString(" (", 0xaaaaaa);
					this.ui.drawString(biome.biomeName, 0x22aa22);
					if(Cfg.show_world) this.ui.drawString(")", 0xaaaaaa);
				}
				this.ui.lineBreak();
			}

			// player position
			if(Cfg.show_position){
				this.ui.drawString(String.format("%+.1f", mc.thePlayer.posX), 0xffffff);
				this.ui.drawString(", ", 0xaaaaaa);
				this.ui.drawString(String.format("%+.1f", mc.thePlayer.posZ), 0xffffff);
				this.ui.drawString(" f: ", 0xaaaaaa);
				this.ui.drawString(String.format("%.1f", mc.thePlayer.boundingBox.minY), 0xffffff);
				if(Cfg.show_position_eyes){
					this.ui.drawString(" e: ", 0xaaaaaa);
					this.ui.drawString(String.format("%.1f", mc.thePlayer.posY), 0xffffff);
				}
				this.ui.lineBreak();
			}

			// light levels -- 0-6 red, 7-8 yellow, 9-15 green
			// time
			// raining
			if(Cfg.show_light || Cfg.show_time || Cfg.show_weather){
				if(Cfg.show_light){
					int light = chunk.getSavedLightValue(EnumSkyBlock.Block, pos_x & 15, feet_y, pos_z & 15);
					this.ui.drawString("light ", 0xaaaaaa);
					this.ui.drawString(String.format("%d ", light), (light < 7 ? 0xff6666 : (light < 9 ? 0xffff66 : 0x66ff66)));
				}

				if(Cfg.show_time){
					this.ui.drawString("time ", 0xaaaaaa);
					long time = world.getWorldTime();
					int hours = (int) (((time / 1000L) + 6) % 24L);
					int minutes = (int) (time % 1000L / 1000.0D * 60.0D);
					this.ui.drawString((hours < 10 ? "0" : "") + String.valueOf(hours) + ":" + (minutes < 10 ? "0" : "") + String.valueOf(minutes) + " ",
						(world.calculateSkylightSubtracted(1.0F) < 4 ? 0xffff66 : 0x666666));
				}
				boolean is_raining = false;
				if(Cfg.show_weather){
					is_raining = world.isRaining();
					if(is_raining){
						if(Cfg.show_light || Cfg.show_time) this.ui.drawString("(", 0xaaaaaa);
						this.ui.drawString("raining/snowing", 0x6666ff);
						if(Cfg.show_light || Cfg.show_time) this.ui.drawString(")", 0xaaaaaa);
					}
				}
				if(Cfg.show_light || Cfg.show_time || (Cfg.show_weather && is_raining))
					this.ui.lineBreak();
			}

			// player inventory
			if(Cfg.show_used_inventory){
				String invText = "used inventory slots: ";
				ItemStack[] mainInventory = player.inventory.mainInventory;
				int nr_items = 0;
				for(int i = 0; i < 36; i++) if(mainInventory[i] != null) nr_items++;
				if(nr_items == 36 && Cfg.animate_used_inventory){
					if(this.invItemCount != 36)
						this.invItemX = screen.getScaledWidth()/2 - mc.fontRenderer.getStringWidth(invText+"100%")/2;
				} else
					this.invItemX = this.ui.base_x;
				this.ui.x = this.invItemX;
				if(this.invItemX > this.ui.base_x) this.invItemX--;
				invItemCount = nr_items;
				this.ui.drawString(invText, 0xaaaaaa);
				this.ui.drawString(String.format("%d", nr_items*100/36), (nr_items == 36 ? 0xff6666 : (nr_items >= 27 ? 0xffff66 : 0x66ff66)));
				this.ui.drawString("%", 0xaaaaaa);
				this.ui.lineBreak();
			}

			// fps and chunk updates
			if(Cfg.show_fps || Cfg.show_chunk_updates){
				if(Cfg.show_fps){
					this.ui.drawString(mc.debug.substring(0, mc.debug.indexOf(" fps")), 0xffffff);
					this.ui.drawString(" FPS ", 0xaaaaaa);
				}
				if(Cfg.show_chunk_updates){
					String chunk_updates = mc.debug.substring(mc.debug.indexOf(" fps, ")+6, mc.debug.indexOf(" chunk"));
					int length = chunk_updates.length();
					this.ui.drawString(chunk_updates, (length < 3 ? 0xffffff : chunk_updates.charAt(0) == '1' && length == 3 ? 0xffff66 : 0xff6666));
					this.ui.drawString(" chunk updates", 0xaaaaaa);
				}
				this.ui.lineBreak();
			}

			// particles, rendered entities, total entities
			if(Cfg.show_entities || Cfg.show_particles){
				if(Cfg.show_entities){
					String entities = mc.getEntityDebug();
					this.ui.drawString(entities.substring(entities.indexOf(' ') + 1, entities.indexOf('/')), 0xffffff);
					this.ui.drawString("/", 0xaaaaaa);
					this.ui.drawString(entities.substring(entities.indexOf('/') + 1, entities.indexOf('.')), 0xffffff);
					this.ui.drawString(" entities ", 0xaaaaaa);
				}
				if(Cfg.show_particles){
					this.ui.drawString(mc.effectRenderer.getStatistics(), 0xffffff);
					this.ui.drawString(" particles", 0xaaaaaa);
				}
				this.ui.lineBreak();
			}

		} catch(Exception e){
//			System.out.println("AU HUD: caught exception in elements");
		}

		// block at cursor
		if(Cfg.show_block_name || Cfg.show_inspector){
			if(mc.objectMouseOver != null){
				if(mc.objectMouseOver.typeOfHit == EnumMovingObjectType.ENTITY){
					try {
						// name and ID of entity
						this.ui.drawString(mc.objectMouseOver.entityHit.getEntityName(), 0xffffff);
						if(Cfg.show_inspector){
							this.ui.drawString(" (", 0xaaaaaa);
							this.ui.drawString(String.format("%d", mc.objectMouseOver.entityHit.entityId), 0xffffff);
							this.ui.drawString(")", 0xaaaaaa);
						}
						this.ui.lineBreak();

						if(Cfg.show_inspector && mc.objectMouseOver.entityHit instanceof EntityLiving){
							EntityLiving entity = (EntityLiving)mc.objectMouseOver.entityHit;

							// health, armor and xp
							this.ui.drawString("   max health: ", 0xaaaaaa);
							this.ui.drawString(String.format("%d", entity.getMaxHealth()), 0xffffff);
							int armorValue = entity.getTotalArmorValue();
							if(armorValue > 0){
								this.ui.drawString(" armor: ", 0xaaaaaa);
								this.ui.drawString(String.format("%d", armorValue), 0xffffff);
							}
							if(entity.experienceValue > 0){
								this.ui.drawString(" xp: ", 0xaaaaaa);
								this.ui.drawString(String.format("%d", entity.experienceValue), 0xffffff);
							}
							this.ui.lineBreak();

							// name of armor and item (5 lines)
							showItemName("helmet", entity.getCurrentArmor(3));
							showItemName("chest", entity.getCurrentArmor(2));
							showItemName("pants", entity.getCurrentArmor(1));
							showItemName("boots", entity.getCurrentArmor(0));
							showItemName("hand", entity.getHeldItem());
						}
					} catch(Exception e){
//						System.out.println("AU HUD: caught exception in entity inspector");
					}
				} else if(mc.objectMouseOver.typeOfHit == EnumMovingObjectType.TILE){
					int blockID = world.getBlockId(mc.objectMouseOver.blockX, mc.objectMouseOver.blockY, mc.objectMouseOver.blockZ);
					if(blockID > 0){
						Block block = Block.blocksList[blockID];
						int inspectX = mc.objectMouseOver.blockX;
						int inspectY = mc.objectMouseOver.blockY;
						int inspectZ = mc.objectMouseOver.blockZ;
						if(block != null){
							try {
								int blockMetadata = world.getBlockMetadata(inspectX, inspectY, inspectZ);

								if(blockID != lastBlockID || blockMetadata != lastBlockMetadata){
									// reset drops
									lastBlockID = blockID;
									lastBlockMetadata = blockMetadata;
									lastDrops.clear();
								}

								// name and ID if block is picked
								int pickedID = block.idPicked(world, inspectX, inspectY, inspectZ);
								if(pickedID > 0){
									int pickedMetadata = block.getDamageValue(world, inspectX, inspectY, inspectZ);
									ItemStack stackPicked = new ItemStack(pickedID, 1, pickedMetadata);
									String pickedName = null;
									try {
										pickedName = stackPicked.getDisplayName();
									} catch(Exception e){
//										System.out.println("AU HUD: caught exception in block inspector, pickedName");
									}
									if(pickedName == null)
										this.ui.drawString("<Unknown Block>", 0xffffff);
									else if(pickedName.equals(""))
										this.ui.drawString("<Unknown Block>", 0xffffff);
									else
										this.ui.drawString(pickedName, 0xffffff);

									if(Cfg.show_inspector){
										if(blockID == pickedID && blockMetadata == pickedMetadata){
											// ID of placed block
											this.ui.drawString(" (", 0xaaaaaa);
											this.ui.drawString(String.format("%d:%d", blockID, blockMetadata), 0xffffff);
											this.ui.drawString(")", 0xaaaaaa);
										} else {
											// ID of picked block
											this.ui.drawString(" (i: ", 0xaaaaaa);
											this.ui.drawString(String.format("%d:%d", pickedID, pickedMetadata), 0xffffff);
											this.ui.drawString(")", 0xaaaaaa);
											// ID of placed block
											this.ui.drawString(" (b: ", 0xaaaaaa);
											this.ui.drawString(String.format("%d:%d", blockID, blockMetadata), 0xffffff);
											this.ui.drawString(")", 0xaaaaaa);
										}
									}
								} else if(Cfg.show_inspector){
									this.ui.drawString("<Unknown Block>", 0xffffff);

									// ID of placed block
									this.ui.drawString(" (b: ", 0xaaaaaa);
									this.ui.drawString(String.format("%d:%d", blockID, blockMetadata), 0xffffff);
									this.ui.drawString(")", 0xaaaaaa);
								}
								this.ui.lineBreak();

								if(Cfg.show_inspector){

									// creative tab name
									CreativeTabs tab = block.getCreativeTabToDisplayOn();
									String tabName = "";
									if(tab != null)
										tabName = tab.getTranslatedTabLabel();
									if(!tabName.equals("")){
										this.ui.drawString("   tab ", 0xaaaaaa);
										this.ui.drawString(tabName, 0xffffff);
										this.ui.lineBreak();
									}

									// required tools and levels
									int swordLevel = MinecraftForge.getBlockHarvestLevel(block, blockMetadata, "sword");
									int axeLevel = MinecraftForge.getBlockHarvestLevel(block, blockMetadata, "axe");
									int pickaxeLevel = MinecraftForge.getBlockHarvestLevel(block, blockMetadata, "pickaxe");
									int shovelLevel = MinecraftForge.getBlockHarvestLevel(block, blockMetadata, "shovel");
									int scoopLevel = MinecraftForge.getBlockHarvestLevel(block, blockMetadata, "scoop"); // TC
									boolean shearable = block instanceof IShearable;
										// TC grafter
									if(swordLevel != -1 || axeLevel != -1 || pickaxeLevel != -1 || shovelLevel != -1 || scoopLevel != -1 || shearable){
										this.ui.drawString("   use ", 0xaaaaaa);
										if(swordLevel != -1) this.showTool("Sword", swordLevel);
										if(axeLevel != -1) this.showTool("Axe", axeLevel);
										if(pickaxeLevel != -1) this.showTool("Pickaxe", pickaxeLevel);
										if(shovelLevel != -1) this.showTool("Shovel", shovelLevel);
										if(scoopLevel != -1) this.showTool("Scoop");
										if(shearable) this.showTool("Shears");
										this.ui.lineBreak();
									}

									// item dropped when broken
									int droppedID = block.idDropped(blockMetadata, new Random(), 0);
									if(droppedID > 0){
										int droppedMetadata = block.damageDropped(blockMetadata);
										ItemStack stackDropped = new ItemStack(droppedID, 1, droppedMetadata);
										String droppedName = null;
										try {
											droppedName = stackDropped.getDisplayName();
										} catch(Exception e){
//											System.out.println("AU HUD: caught exception in block inspector, droppedName");
										}
										if(droppedID == 0 || droppedName != null){
											// add to drops list, if not already
											LastDrop thisDrop = new LastDrop(droppedID, droppedMetadata, droppedName);
											if(!lastDrops.contains(thisDrop))
												lastDrops.add(thisDrop);
										}
										int nr_drops = lastDrops.size();
										boolean dropSelf = true;
										for(int i = 0; i < nr_drops; i++){
											LastDrop drop = lastDrops.get(i);
											if(drop.id == 0){
												this.ui.drawString("   no drop", 0xaaaaaa);
												dropSelf = false;
											} else {
												if(drop.id == blockID && drop.metadata == blockMetadata){
													// drops itself
													this.ui.drawString("   drops as is", 0xaaaaaa);
												} else {
													// drops something other than self
													this.ui.drawString("   drops ", 0xaaaaaa);
													this.ui.drawString((drop.name == null ? "<Unknown>" : drop.name), 0xffffff);
													this.ui.drawString(" (", 0xaaaaaa);
													this.ui.drawString(String.format("%d:%d", drop.id, drop.metadata), 0xffffff);
													this.ui.drawString(")", 0xaaaaaa);
													dropSelf = false;
												}
											}
											this.ui.lineBreak();
										}

										// silkable
										boolean silkable = block.canSilkHarvest(world, player, inspectX, inspectY, inspectZ, blockMetadata);
										if(silkable && !dropSelf){
											this.ui.drawString("   silkable", 0xb25bfd);
											this.ui.lineBreak();
										}
									}

									// brightness, hardness, resistance
									int brightness = block.getLightValue(world, inspectX, inspectY, inspectZ);
									float hardness = block.getBlockHardness(world, inspectX, inspectY, inspectZ);
									float resistance = block.getExplosionResistance(null);
									this.ui.drawString("   b: ", 0xaaaaaa);
									this.ui.drawString((brightness > 0 ? String.format("%d", brightness) : "_"), 0xffffff);
									this.ui.drawString(" h: ", 0xaaaaaa);
									this.ui.drawString((hardness == -1 ? "unbreakable" : String.format("%.1f", hardness)), 0xffffff);
									this.ui.drawString(" r: ", 0xaaaaaa);
									this.ui.drawString(String.format("%.1f", resistance), 0xffffff);
									this.ui.lineBreak();

									// has tile entity, tick rate, random ticks
									boolean hasTileEntity = block.hasTileEntity(blockMetadata);
									TileEntity tileEntity = (hasTileEntity ? world.getBlockTileEntity(inspectX, inspectY, inspectZ) : null);
									boolean tickRandomly = block.getTickRandomly();
									int tickRate = 0;
									if(tileEntity != null){
										if(tileEntity.canUpdate()){
											#ifdef MC147
											tickRate = block.tickRate();
											#else
											tickRate = block.tickRate(world);
											#endif
										}
									}
									this.ui.drawString("   e: ", 0xaaaaaa);
									this.ui.drawString((hasTileEntity ? "x" : "_"), 0xffffff);
									this.ui.drawString(" r: ", 0xaaaaaa);
									this.ui.drawString((tickRandomly ? "x" : "_"), 0xffffff);
									this.ui.drawString(" t: ", 0xaaaaaa);
									this.ui.drawString((tickRate > 0 ? String.format("%d", tickRate) : "_"), 0xffffff);
									ArrayList tileEntityList = (ArrayList)world.loadedTileEntityList;
									int nr_tileEntities = tileEntityList.size();
									int nr_tileEntitiesAtPos = 0;
									for(int t = 0; t < nr_tileEntities; t++){
										TileEntity te = (TileEntity)tileEntityList.get(t);
										if(te.xCoord == inspectX && te.yCoord == inspectY && te.zCoord == inspectZ)
											nr_tileEntitiesAtPos++;
									}
									this.ui.drawString(" b: ", 0xaaaaaa);
									this.ui.drawString((nr_tileEntitiesAtPos > 0 ? String.format("%d", nr_tileEntitiesAtPos) : "_"), 0xffffff);
									this.ui.drawString(" a: ", 0xaaaaaa);
									this.ui.drawString((nr_tileEntities > 0 ? String.format("%d", nr_tileEntities) : "_"), 0xffffff);
									this.ui.lineBreak();

									// normal, opaque, solid
									boolean solid = block.isBlockSolid(world, inspectX, inspectY, inspectZ, mc.objectMouseOver.sideHit);
									this.ui.drawString("   n: ", 0xaaaaaa);
									this.ui.drawString((block.isNormalCube(blockID) ? "x" : "_"), 0xffffff);
									this.ui.drawString(" o: ", 0xaaaaaa);
									this.ui.drawString((block.isOpaqueCube() ? "x" : "_"), 0xffffff);
									this.ui.drawString(" s: ", 0xaaaaaa);
									this.ui.drawString((solid ? "x" : "_"), 0xffffff);
									this.ui.lineBreak();

								}
							} catch(Exception e){
//								System.out.println("AU HUD: caught exception in block inspector");
							}
						}
					}
				}
			}
		}

		// SELF: armor value inspector
		if(Cfg.show_inspector && !mc.thePlayer.capabilities.isCreativeMode){
			int armorValue = player.getTotalArmorValue();
			if(armorValue > 0){
				this.ui.setCursor(screen.getScaledWidth()/2 - 95, screen.getScaledHeight() - 70);
				this.ui.drawString(" armor: ", 0xaaaaaa);
				this.ui.drawString(String.format("%d", armorValue), 0xffffff);
			}
		}

		// SELF: daamge inspector
		if(Cfg.show_inspector && !mc.thePlayer.capabilities.isCreativeMode){
			ItemStack hand = mc.thePlayer.getHeldItem();
			Item item = (hand != null ? Item.itemsList[hand.itemID] : null);
			int damage = (item != null ? item.getDamageVsEntity(null) : 1);
			this.ui.setCursor(screen.getScaledWidth()/2 + 10, screen.getScaledHeight() - 70);
			this.ui.drawString("damage: ", 0xaaaaaa);
			this.ui.drawString(String.format("%d", damage), 0xffffff);
		}

		// SELF: food inspector
		if(Cfg.show_inspector && !mc.thePlayer.capabilities.isCreativeMode){
			FoodStats food = mc.thePlayer.getFoodStats();
			this.ui.setCursor(screen.getScaledWidth()/2 + 10, screen.getScaledHeight() - 50);
			this.ui.drawString("f: ", 0xaaaaaa);
			this.ui.drawString(String.format("%d%%", 100*food.getFoodLevel()/20), 0xffffff);
			this.ui.drawString("  s: ", 0xaaaaaa);
			this.ui.drawString(String.format("%d%%", (int)Math.round(100*food.getSaturationLevel()/20)), 0xffffff);
		}

// TODO: memory
//	long maxMemory = Runtime.getRuntime().maxMemory();
//	long totalMemory = Runtime.getRuntime().totalMemory();
//	long freeMemory = Runtime.getRuntime().freeMemory();

		GL11.glPopMatrix();
	}
}