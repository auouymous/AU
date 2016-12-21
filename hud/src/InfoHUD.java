package com.qzx.au.hud;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

IMPORT_BLOCKS
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
#if defined MC147 || defined MC152
import net.minecraft.entity.EntityLiving;
#else
import net.minecraft.entity.EntityLivingBase;
#endif
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
IMPORT_ITEMS
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.FoodStats;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

#if !defined MC147 && !defined MC152
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.StatCollector;
#endif

import net.minecraftforge.common.IShearable;

#if defined WITH_API_IC2 && defined MC147
import ic2.api.IWrenchable;
#elif defined WITH_API_IC2
import ic2.api.tile.IWrenchable;
#endif

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.qzx.au.core.BlockCoord;
import com.qzx.au.core.ItemUtils;
import com.qzx.au.core.UI;

@SideOnly(Side.CLIENT)
public class InfoHUD {
	private UI ui = new UI();
	private Random random = new Random();

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

		try {
			this.ui.drawString("   " + name + ": ", 0xaaaaaa);
			this.ui.drawString(ItemUtils.getDisplayName(item), 0xffffff);
			this.ui.lineBreak();
		} catch(Exception e){
			Failure.log("showItemName() entity inspector");
		}
	}

	//////////

	private int invItemCX = 0;
	private int invItemCount = 0;
	private long invItemTime = 0;

	//////////

	private long ticks;
	private long time = 0;
	private static int TPS_QUEUE = 10;
	private static int TPS_TAIL = TPS_QUEUE-1;
	private float[] tps_queue;
	private int tps_tail;
	private float tps_interval; // seconds
	private static float MAX_TPS_INTERVAL = 6.0F; // seconds

	private void initTPS(){
		this.ticks = 0;
		this.time = System.currentTimeMillis();
		this.tps_queue = new float[TPS_QUEUE];
		this.tps_tail = -1;
		this.tps_interval = 1.0F;
	}

	//////////

	private Item lastDropItem = null;
	private int lastDropMetadata = 0;
	private class LastDrop {
		public Item item;
		public int metadata;
		public String name;
		public LastDrop(Item droppedItem, int droppedMetadata, String droppedName){
			this.item = droppedItem;
			this.metadata = droppedMetadata;
			this.name = droppedName;
		}
		@Override
		public boolean equals(Object obj){
			if(obj instanceof LastDrop){
				LastDrop drop = (LastDrop)obj;
				if(drop.item == this.item && drop.metadata == this.metadata) return true;
			}
			return false;
		}
	}
	private ArrayList<LastDrop> lastDrops = new ArrayList<LastDrop>();

	//////////

	private static int SPEED_QUEUE = 10;
	private static int SPEED_TAIL = SPEED_QUEUE-1;
	private double lastSpeedX, lastSpeedY, lastSpeedZ;
	private long lastSpeedTime = 0;
	private float[] speed_queue;
	private int speed_tail;
	private float lastSpeed;

	private void initSpeed(double x, double y, double z){
		this.lastSpeedX = x;
		this.lastSpeedY = y;
		this.lastSpeedZ = z;
		this.lastSpeedTime = System.currentTimeMillis();
		this.speed_queue = new float[SPEED_QUEUE];
		this.speed_tail = -1;
		this.lastSpeed = 0.0F;
	}

	//////////

	public void draw(Minecraft mc, ScaledResolution screen, EntityPlayer player){
		GL11.glPushMatrix();

		int pos_x = MathHelper.floor_double(player.posX);
		int pos_y = MathHelper.floor_double(player.posY);
		int pos_z = MathHelper.floor_double(player.posZ);
		int feet_y = MathHelper.floor_double(player.boundingBox.minY);

		World world = mc.isIntegratedServerRunning() ? mc.getIntegratedServer().worldServerForDimension(player.dimension) : mc.theWorld;
		if(world == null) return;

		Chunk chunk = world.getChunkFromBlockCoords(pos_x, pos_z);
		if(chunk == null) return;

		this.ui.scale(Cfg.info_hud_scale);

		this.ui.setCursor(Cfg.info_hud_x, Cfg.info_hud_y);

		if(TickHandlerHUD.force_hud == TickHandlerHUD.HUD_INFO){
			// position grid
			final int grid_length = 36;
			int xx = this.ui.getScaledX();
			int yy = this.ui.getScaledY();
			this.ui.drawLineH(xx-4, yy, grid_length, 0xff00ffff);
			this.ui.drawLineV(xx, yy-4, grid_length, 0xff00ffff);
		}

		// world
		// biome
		try {
			if(Cfg.show_world || Cfg.show_biome || Cfg.show_heading){
				if(Cfg.show_world)
					this.ui.drawString(world.provider.getDimensionName(), 0xffffff);
				if(Cfg.show_biome){
					BiomeGenBase biome = chunk.getBiomeGenForWorldCoords(pos_x & 15, pos_z & 15, world.getWorldChunkManager());
					if(Cfg.show_world) this.ui.drawString(" (", 0xaaaaaa);
					this.ui.drawString(biome.biomeName, 0x22aa22);
					if(Cfg.show_world) this.ui.drawString(")", 0xaaaaaa);
				}
				if(Cfg.show_heading){
					int heading = MathHelper.floor_double((double)(player.rotationYaw * 8.0F / 360.0F) + 0.5D) & 7; // SWNE = 0123
					String[] headings = {"-S-", "S-W", "-W-", "W-N", "-N-", "N-E", "-E-", "E-S"};
					this.ui.drawString(" "+headings[heading], 0x0090bf);
				}
				this.ui.lineBreak();
			}
		} catch(Exception e){
			Failure.log("world/biome/heading info element");
		}

		// player position
		try {
			if(Cfg.show_position){
				this.ui.drawString(String.format("%+.1f", player.posX), 0xffffff);
				this.ui.drawString(", ", 0xaaaaaa);
				this.ui.drawString(String.format("%+.1f", player.posZ), 0xffffff);
				this.ui.drawString(" f: ", 0xaaaaaa);
				this.ui.drawString(String.format("%.1f", player.boundingBox.minY), 0xffffff);
				if(Cfg.show_position_eyes){
					this.ui.drawString(" e: ", 0xaaaaaa);
					this.ui.drawString(String.format("%.1f", player.posY), 0xffffff);
				}
				this.ui.lineBreak();
			}
		} catch(Exception e){
			Failure.log("player position info element");
		}

		// light levels -- 0-6 red, 7-8 yellow, 9-15 green
		// time
		// raining
		try {
			if(Cfg.show_light || Cfg.show_time || Cfg.show_weather){
				if(Cfg.show_light){
					try {
						int light = chunk.getSavedLightValue(EnumSkyBlock.Block, pos_x & 15, feet_y, pos_z & 15);
						this.ui.drawString("light ", 0xaaaaaa);
						this.ui.drawString(String.format("%d ", light), (light < 7 ? 0xff6666 : (light < 9 ? 0xffff66 : 0x66ff66)));
					} catch(Exception e){
						// above or below build limits
						this.ui.drawString("> build limit < ", 0xff6666);
					}
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
		} catch(Exception e){
			Failure.log("light/time/weather info element");
		}

		// player inventory
		try {
			if(Cfg.show_used_inventory){
				String invText = "used inventory slots: ";
				ItemStack[] mainInventory = player.inventory.mainInventory;
				int nr_items = 0;
				for(int i = 0; i < 36; i++) if(mainInventory[i] != null) nr_items++;

				long time = System.currentTimeMillis();
				if(nr_items == 36 && Cfg.animate_used_inventory){
					if(this.invItemCount != 36){
						this.invItemCX = screen.getScaledWidth()/2 - mc.fontRenderer.getStringWidth(invText+"100%")/2;
						this.invItemTime = time;
					}
				} else
					this.invItemCX = this.ui.getBaseX();
				if(this.invItemCX > this.ui.getBaseX()){
					this.ui.setX(this.invItemCX - (int)((float)this.invItemCX * ((float)(time - this.invItemTime) / 4000.0F)));
					if(this.ui.getX() < this.ui.getBaseX()) this.ui.resetX();
				} else
					this.ui.resetX();
				this.invItemCount = nr_items;

				this.ui.drawString(invText, 0xaaaaaa);
				this.ui.drawString(String.format("%d", nr_items*100/36), (nr_items == 36 ? 0xff6666 : (nr_items >= 27 ? 0xffff66 : 0x66ff66)));
				this.ui.drawString("%", 0xaaaaaa);
				this.ui.lineBreak();
			}
		} catch(Exception e){
			Failure.log("inventory info element");
		}

		// fps and chunk updates
		try {
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
		} catch(Exception e){
			Failure.log("fps/chunk info element");
		}

		// rendered entities, total entities, particles
		try {
			if(Cfg.show_entities || Cfg.show_particles){
				if(Cfg.show_entities){
					String entities = mc.getEntityDebug();
					// 1.4.7+ "E: 6/43. B: 0, I: 37, OptiFine_1.6.4_HD_U_C7"
					// 1.7.10+ "E: 0/293 0/0 (293). TE: 217/636 0/37 (745). B: 0"
					final int start_1	= entities.indexOf(' ') + 1;
					final int end_1		= entities.indexOf('/', start_1);
					final int start_2	= end_1 + 1;
					final int end_A		= entities.indexOf('.', start_2);
					final int end_B		= entities.indexOf(' ', start_2);
					final int end_2		= (end_A > end_B ? end_B : end_A); // use whichever comes first
					this.ui.drawString(entities.substring(start_1, end_1), 0xffffff);
					this.ui.drawString("/", 0xaaaaaa);
					this.ui.drawString(entities.substring(start_2, end_2), 0xffffff);
					this.ui.drawString(" entities ", 0xaaaaaa);
				}
				if(Cfg.show_particles){
					this.ui.drawString(mc.effectRenderer.getStatistics(), 0xffffff);
					this.ui.drawString(" particles", 0xaaaaaa);
				}
				this.ui.lineBreak();
			}
		} catch(Exception e){
			Failure.log("entities/particles info element");
		}

		// memory
		try {
			if(Cfg.show_memory){
				long maxMemory = Runtime.getRuntime().maxMemory();
				long totalMemory = Runtime.getRuntime().totalMemory();
				long freeMemory = Runtime.getRuntime().freeMemory();

				long usedMemory = 100 * (totalMemory - freeMemory) / maxMemory;
				this.ui.drawString(String.format("%d%%", usedMemory), (usedMemory > 90 ? 0xff6666 : (usedMemory > 50 ? 0xffff66 : 0xffffff)));
				this.ui.drawString(" used ", 0xaaaaaa);
				this.ui.drawString(String.format("%d%%", 100 * totalMemory / maxMemory), 0xffffff);
				this.ui.drawString(" allocated", 0xaaaaaa);

				this.ui.lineBreak();
			}
		} catch(Exception e){
			Failure.log("memory info element");
		}

		// TPS
		try {
			if(Cfg.show_tps && !Hacks.isGamePaused(mc)){
				long ticks = world.getWorldInfo().getWorldTotalTime();
				long time = System.currentTimeMillis();
				if(time >= this.time + 60000 || (this.ticks != 0 && Math.abs(ticks - this.ticks) > 1200)) this.initTPS(); // re-initialize TPS if last update was more than 1 minute ago
				if(time >= this.time + (int)(this.tps_interval*1000.0F)){
					if(this.ticks > 0){
						if(this.tps_tail == TPS_TAIL)
							for(int i = 0; i < TPS_TAIL; i++) this.tps_queue[i] = this.tps_queue[i+1];
						else this.tps_tail++;
						float tps = (float)(ticks - this.ticks) / this.tps_interval / ((float)(time - this.time) / (this.tps_interval*1000.0F));
						this.tps_queue[this.tps_tail] = (tps > 20.0F ? 20.0F : tps);
						if(this.tps_interval < MAX_TPS_INTERVAL) this.tps_interval++;
					}
					this.ticks = ticks;
					this.time = time;
				}

				if(this.ticks > 0 && this.tps_tail > -1){
					float tps = 0.0F; for(int i = 0; i <= this.tps_tail; i++) tps += this.tps_queue[i]; tps = Math.round(tps/(float)(this.tps_tail+1));
					int[] colors = {0x66ff66, 0xb2ff66, 0xffff66, 0xff6666};
					if(tps > 20.0F) tps = 20.0F;
					if(tps < 0.0F) tps = 0.0F;
					int lag = (int)Math.floor((20.0F - (int)tps) / 5.0F);
					if(lag > 3) lag = 3;
					this.ui.drawString(String.format("%d", (int)tps), colors[lag]);
					this.ui.drawString(" tps (", 0xaaaaaa);
					this.ui.drawString(String.format("%d%%", (int)tps * 5), colors[lag]);
					this.ui.drawString(" = ", 0xaaaaaa);
					this.ui.drawString(String.format("%.1fx", 20.0F / (tps > 0.01F ? tps : 0.01F)), colors[lag]);
					this.ui.drawString(")", 0xaaaaaa);
					if(this.tps_tail < TPS_TAIL){
//						this.ui.drawString("   averaging over "+String.format("%d", (int)this.tps_interval*10)+" seconds, at "
//											+String.format("%.1f", this.tps_interval)+" second intervals", 0xaa0000);
						this.ui.drawString(" ", 0xaaaaaa);
						for(int i = this.tps_tail; i < TPS_TAIL; i++) this.ui.drawString(".", 0xff6666);
					}
				} else
					this.ui.drawString("preparing TPS...", 0xaa0000);
				this.ui.lineBreak();

//				this.ui.drawString("TPS Queue:", 0xaaaaaa);
//				for(int i = 0; i <= TPS_TAIL; i++) this.ui.drawString(String.format("  %.2f", this.tps_queue[i]), 0xcccccc);
//				this.ui.lineBreak();
			}
		} catch(Exception e){
			Failure.log("tps info element");
		}

		// SELF: player speed
		try {
			if(Cfg.show_inspector){
				long time = System.currentTimeMillis();
				if(time >= this.lastSpeedTime + 500){
					this.initSpeed(player.posX, player.posY, player.posZ);
				} else if(time >= this.lastSpeedTime + 50){
					// get distance between current position and last position
					float dX = (float)(player.posX - this.lastSpeedX);
					float dY = (float)(player.posY - this.lastSpeedY);
					float dZ = (float)(player.posZ - this.lastSpeedZ);
					float distance = (float)Math.sqrt(dX*dX + dY*dY + dZ*dZ);
					this.lastSpeedX = player.posX;
					this.lastSpeedY = player.posY;
					this.lastSpeedZ = player.posZ;
					// get time since last position
					float delta_time = time - this.lastSpeedTime;
					this.lastSpeedTime = time;

					float speed = 1000 * distance / delta_time;
					if(this.speed_tail == SPEED_TAIL)
						for(int i = 0; i < SPEED_TAIL; i++) this.speed_queue[i] = this.speed_queue[i+1];
					else this.speed_tail++;
					this.speed_queue[this.speed_tail] = speed;

					if(this.speed_tail != -1 && speed != 0.0F){
						this.lastSpeed = 0.0F; for(int i = 0; i <= this.speed_tail; i++) this.lastSpeed += this.speed_queue[i]; this.lastSpeed /= (float)(this.speed_tail+1);
					} else
						this.lastSpeed = speed;
				}

				this.ui.drawString(String.format("%.1f", this.lastSpeed), 0xffffff);
				this.ui.drawString(" blocks/second", 0xaaaaaa);
				this.ui.lineBreak();
			}
		} catch(Exception e){
			Failure.log("player speed inspector");
		}

		// block or entity at cursor
		try {
			if(Cfg.show_block_name || Cfg.show_inspector){
				if(mc.objectMouseOver != null){
					if(mc.objectMouseOver.typeOfHit == MOVINGOBJECTTYPE_ENTITY){
						// name and ID of entity
						try {
							if(mc.objectMouseOver.entityHit instanceof EntityItemFrame){
								ItemStack stackItemFrame = new ItemStack(MC_ITEM_ITEMFRAME);
								this.ui.drawString(ItemUtils.getDisplayName(stackItemFrame), 0xffffff);
							} else
								this.ui.drawString(mc.objectMouseOver.entityHit.GET_ENTITY_NAME(), 0xffffff);
							if(Cfg.show_inspector){
								this.ui.drawString(" (", 0xaaaaaa);
								if(mc.objectMouseOver.entityHit instanceof EntityItemFrame)
									this.ui.drawString("e:", 0xaaaaaa);
								this.ui.drawString(String.format("%d", Hacks.getEntityID(mc.objectMouseOver.entityHit)), 0xffffff);
								if(mc.objectMouseOver.entityHit instanceof EntityItemFrame){
									this.ui.drawString(" i:", 0xaaaaaa);
									this.ui.drawString(String.format("%d", GET_ITEM_ID(MC_ITEM_ITEMFRAME)), 0xffffff);
								}
								this.ui.drawString(")", 0xaaaaaa);
							}
							this.ui.lineBreak();
						} catch(Exception e){
							Failure.log("entity name/ID info element");
						}

						// name and ID of item in item frame
						try {
							if(mc.objectMouseOver.entityHit instanceof EntityItemFrame){
								ItemStack itemstack = ((EntityItemFrame)mc.objectMouseOver.entityHit).getDisplayedItem();
								if(itemstack != null){
									this.ui.drawString("   ", 0xaaaaaa);
									this.ui.drawString(ItemUtils.getDisplayName(itemstack), 0xffffff);
									if(Cfg.show_inspector){
										this.ui.drawString(" (", 0xaaaaaa);
										if(itemstack.isItemStackDamageable()){
											int max_durability = itemstack.getMaxDamage();
											int durability = max_durability - itemstack.getItemDamage();
											this.ui.drawString(String.format("%d  %d/%d", GET_ITEMSTACK_ID(itemstack), durability, max_durability), 0xffffff);
										} else
											this.ui.drawString(String.format("%d:%d", GET_ITEMSTACK_ID(itemstack), itemstack.getItemDamage()), 0xffffff);
										this.ui.drawString(")", 0xaaaaaa);
									}
									this.ui.lineBreak();
								}
							}
						} catch(Exception e){
							Failure.log("entity name/ID info element, item frame contents name/ID");
						}

						#if defined MC147 || defined MC152
						if(mc.objectMouseOver.entityHit instanceof EntityLiving){
							EntityLiving entity = (EntityLiving)mc.objectMouseOver.entityHit;
						#else
						if(mc.objectMouseOver.entityHit instanceof EntityLivingBase){
							EntityLivingBase entity = (EntityLivingBase)mc.objectMouseOver.entityHit;
						#endif
							// health, armor and xp
							try {
								#if defined MC147 || defined MC152
								if(Cfg.show_inspector){
									this.ui.drawString("   max health: ", 0xaaaaaa);
									this.ui.drawString(String.format("%d", (int)entity.getMaxHealth()), 0xffffff);
								}
								#else
								this.ui.drawString("   health: ", 0xaaaaaa);
								int health = (int)entity.getHealth();
								int maxHealth = (int)entity.getMaxHealth();
								int healthPct = 100 * health / maxHealth;
								int healthColor = (healthPct > 66 ? 0x66ff66 : (healthPct < 33 ? 0xff6666 : 0xffff66));
								this.ui.drawString(String.format("%d", health), healthColor);
								this.ui.drawString(String.format("/%d  ", maxHealth), 0xffffff);
								if(healthPct > 100) healthPct = 100;
// TODO: the health bar should reflect health above 100%, add an additional | or + character for each 10% over
								healthPct /= 10;
								this.ui.drawString("||||||||||".substring(0, healthPct), healthColor);
								if(healthPct < 10) this.ui.drawString("||||||||||".substring(healthPct, 10), 0xaaaaaa);
								#endif
								if(Cfg.show_inspector){
									int armorValue = entity.getTotalArmorValue();
									if(armorValue > 0){
										this.ui.drawString("  armor: ", 0xaaaaaa);
										this.ui.drawString(String.format("%d", armorValue), 0xffffff);
									}
//									if(entity.experienceValue > 0){
//										this.ui.drawString(" xp: ", 0xaaaaaa);
//										this.ui.drawString(String.format("%d", entity.experienceValue), 0xffffff);
//									}
								}
								#if defined MC147 || defined MC152
								if(Cfg.show_inspector)
									this.ui.lineBreak();
								#else
								this.ui.lineBreak();
								#endif
							} catch(Exception e){
								Failure.log("entity inspector, health/armor_value");
							}

							// invulnerable
							try {
								if(entity.isEntityInvulnerable()){
									this.ui.drawString("   is invulnerable", 0xb25bfd);
									this.ui.lineBreak();
								}
							} catch(Exception e){
								Failure.log("entity inspector, invulnerable");
							}

							if(Cfg.show_inspector){
								// name of armor and item (5 lines)
								try {
									showItemName("helmet", entity.GET_ARMOR_SLOT(4));
									showItemName("chest", entity.GET_ARMOR_SLOT(3));
									showItemName("pants", entity.GET_ARMOR_SLOT(2));
									showItemName("boots", entity.GET_ARMOR_SLOT(1));
									showItemName("hand", entity.getHeldItem());
								} catch(Exception e){
									Failure.log("entity inspector, armor/hand");
								}
							}

							if(Cfg.show_inspector){
								// NBT tags
								try {
									NBTTagCompound tags = new NBTTagCompound();
									entity.writeToNBT(tags);
									if(this.hasEntityTags(InfoHUD.ignore_entity_keys, tags)){
										this.ui.drawString("   NBT Data:", 0xaaaaaa);
										this.ui.lineBreak();
										this.displayEntityTags(InfoHUD.ignore_entity_keys, tags, "     ");
									}
								} catch(Exception e){
									Failure.log("entity inspector, NBT data");
								}
							}
						}
					} else if(mc.objectMouseOver.typeOfHit == MOVINGOBJECTTYPE_BLOCK){
						Block block = BlockCoord.getBlock(world, mc.objectMouseOver.blockX, mc.objectMouseOver.blockY, mc.objectMouseOver.blockZ);
						if(block != null){
							int inspectX = mc.objectMouseOver.blockX;
							int inspectY = mc.objectMouseOver.blockY;
							int inspectZ = mc.objectMouseOver.blockZ;
							int blockMetadata = world.getBlockMetadata(inspectX, inspectY, inspectZ);

							// hide silverfish blocks :)
							Block silverfish_block = block;
							Item silverfish_itemblock = ItemUtils.getItemBlock(MC_BLOCK_SILVERFISH);
							if(Cfg.hide_silverfish_blocks && block == MC_BLOCK_SILVERFISH){
								if(blockMetadata == 0){
									silverfish_block = MC_BLOCK.stone; blockMetadata = 0;
								} else if(blockMetadata == 1){
									silverfish_block = MC_BLOCK.cobblestone; blockMetadata = 0;
								} else if(blockMetadata == 2){
									silverfish_block = MC_BLOCK_STONEBRICK; blockMetadata = 0;
								}
								// block variable is reset after getting picked BLOCK:META below
							}

							Item blockItem = ItemUtils.getItemBlock(block);
							if(blockItem != this.lastDropItem || blockMetadata != this.lastDropMetadata){
								// reset drops
								this.lastDropItem = blockItem;
								this.lastDropMetadata = blockMetadata;
								this.lastDrops.clear();
							}

							// name and ID if block is picked
							try {
								ItemStack stackPicked = block.getPickBlock(mc.objectMouseOver, world, inspectX, inspectY, inspectZ);
								Item pickedItem = (stackPicked != null ? stackPicked.getItem() : null);
								if(pickedItem != null){
									int pickedMetadata = stackPicked.getItemDamage();

									// hide silverfish blocks :)
									if(Cfg.hide_silverfish_blocks && pickedItem == silverfish_itemblock){
										if(pickedMetadata == 0){
											pickedItem = ItemUtils.getItemBlock(MC_BLOCK.stone); pickedMetadata = 0; stackPicked = new ItemStack(pickedItem, 1, pickedMetadata);
											block = silverfish_block;
										} else if(pickedMetadata == 1){
											pickedItem = ItemUtils.getItemBlock(MC_BLOCK.cobblestone); pickedMetadata = 0; stackPicked = new ItemStack(pickedItem, 1, pickedMetadata);
											block = silverfish_block;
										} else if(pickedMetadata == 2){
											pickedItem = ItemUtils.getItemBlock(MC_BLOCK_STONEBRICK); pickedMetadata = 0; stackPicked = new ItemStack(pickedItem, 1, pickedMetadata);
											block = silverfish_block;
										}
									}

									String pickedName = ItemUtils.getDisplayName(stackPicked);
									this.ui.drawString(pickedName, "<Unknown Block>", 0xffffff);

									if(Cfg.show_inspector){
										if(blockItem == pickedItem && blockMetadata == pickedMetadata){
											// ID of placed block
											this.ui.drawString(" (", 0xaaaaaa);
											this.ui.drawString(String.format("%d:%d", GET_BLOCK_ID(block), blockMetadata), 0xffffff);
											this.ui.drawString(")", 0xaaaaaa);
										} else {
											// ID of picked block
											this.ui.drawString(" (i: ", 0xaaaaaa);
											this.ui.drawString(String.format("%d:%d", GET_ITEM_ID(pickedItem), pickedMetadata), 0xffffff);
											this.ui.drawString(")", 0xaaaaaa);
											// ID of placed block
											this.ui.drawString(" (b: ", 0xaaaaaa);
											this.ui.drawString(String.format("%d:%d", GET_BLOCK_ID(block), blockMetadata), 0xffffff);
											this.ui.drawString(")", 0xaaaaaa);
										}
									}
								} else {
									ItemStack stackBlock = new ItemStack(block, 1, blockMetadata);
									String blockName = ItemUtils.getDisplayName(stackBlock);
									this.ui.drawString(blockName, "<Unknown Block>", 0xffffff);

									if(Cfg.show_inspector){
										// ID of placed block
										this.ui.drawString(" (b: ", 0xaaaaaa);
										this.ui.drawString(String.format("%d:%d", GET_BLOCK_ID(block), blockMetadata), 0xffffff);
										this.ui.drawString(")", 0xaaaaaa);
									}
								}
								this.ui.lineBreak();
							} catch(Exception e){
								Failure.log("block name/ID info element");
							}

							if(Cfg.show_inspector){

								boolean hasTileEntity = block.hasTileEntity(blockMetadata);
								TileEntity tileEntity = (hasTileEntity ? BlockCoord.getTileEntity(world, inspectX, inspectY, inspectZ) : null);

								// creative tab name
								try {
									CreativeTabs tab = block.getCreativeTabToDisplayOn();
									if(tab == null){
										ItemStack stackPicked = block.getPickBlock(mc.objectMouseOver, world, inspectX, inspectY, inspectZ);
										if(stackPicked != null)
											tab = stackPicked.getItem().getCreativeTab();
									}
									if(tab != null){
										String tabName = tab.getTranslatedTabLabel();
										#if !defined MC147 && !defined MC152
										if(tabName != null)
											tabName = StatCollector.translateToLocal(tabName);
										#endif
										if(tabName.equals(""))
											tabName = tab.getTabLabel();
										if(!tabName.equals("")){
											this.ui.drawString("   tab ", 0xaaaaaa);
											this.ui.drawString(tabName, 0xffffff);
											this.ui.lineBreak();
										}
									}
								} catch(Exception e){
									Failure.log("block inspector, creative tab");
								}

								// required tools and levels
								try {
									#ifdef NO_IDS
										#define GET_BLOCK_HARVEST_LEVEL(block, meta, tool) \
											(block.getHarvestTool(meta) != null && block.getHarvestTool(meta).equals(tool) ? block.getHarvestLevel(meta) : -1);
									#else
										#define GET_BLOCK_HARVEST_LEVEL(block, meta, tool) \
											net.minecraftforge.common.MinecraftForge.getBlockHarvestLevel(block, meta, tool);
									#endif
									int swordLevel		= GET_BLOCK_HARVEST_LEVEL(block, blockMetadata, "sword")
									int axeLevel		= GET_BLOCK_HARVEST_LEVEL(block, blockMetadata, "axe")
									int pickaxeLevel	= GET_BLOCK_HARVEST_LEVEL(block, blockMetadata, "pickaxe")
									int shovelLevel		= GET_BLOCK_HARVEST_LEVEL(block, blockMetadata, "shovel")
									int scoopLevel		= GET_BLOCK_HARVEST_LEVEL(block, blockMetadata, "scoop") // Thaumcraft
// TODO: thaumcraft grafter?
									boolean shearable = block instanceof IShearable;
									boolean ic2_wrenchable = false;
									#ifdef WITH_API_IC2
									if(THIS_MOD.supportIC2)
										if(tileEntity instanceof IWrenchable)
											ic2_wrenchable = true;
									#endif
									if(swordLevel != -1 || axeLevel != -1 || pickaxeLevel != -1 || shovelLevel != -1 || scoopLevel != -1 || shearable || ic2_wrenchable){
										this.ui.drawString("   use ", 0xaaaaaa);
										if(swordLevel != -1) this.showTool("Sword", swordLevel);
										if(axeLevel != -1) this.showTool("Axe", axeLevel);
										if(pickaxeLevel != -1) this.showTool("Pickaxe", pickaxeLevel);
										if(shovelLevel != -1) this.showTool("Shovel", shovelLevel);
										if(scoopLevel != -1) this.showTool("Scoop");
										if(shearable) this.showTool("Shears");
										if(ic2_wrenchable) this.showTool("IC2 Wrench");
										this.ui.lineBreak();
									}
								} catch(Exception e){
									Failure.log("block inspector, required tool");
								}

								// item dropped when broken
								try {
									#ifdef NO_IDS
									int droppedMetadata = block.damageDropped(blockMetadata);
									Item droppedItem = block.getItemDropped(blockMetadata, this.random, 0);
									ItemStack stackDropped = ItemUtils.getItemStack(droppedItem, 1, droppedMetadata);
									#else
									int droppedID = block.idDropped(blockMetadata, this.random, 0);
									int droppedMetadata = block.damageDropped(blockMetadata);
									ItemStack stackDropped = ItemUtils.getItemStack(droppedID, 1, droppedMetadata);
									Item droppedItem = (stackDropped == null ? null : stackDropped.getItem());
									#endif
									if(droppedItem != null){
										String droppedName = ItemUtils.getDisplayName(stackDropped);

										if(droppedName != null){
											// add to drops list, if not already
											LastDrop thisDrop = new LastDrop(droppedItem, droppedMetadata, droppedName);
											if(!this.lastDrops.contains(thisDrop))
												this.lastDrops.add(thisDrop);
										}
										int nr_drops = this.lastDrops.size();
										boolean dropSelf = true;
										for(int i = 0; i < nr_drops; i++){
											LastDrop drop = this.lastDrops.get(i);
											if(drop.item == null){
												this.ui.drawString("   no drop", 0xaaaaaa);
												dropSelf = false;
											} else {
												if(drop.item == blockItem && drop.metadata == blockMetadata){
													// drops itself
													this.ui.drawString("   drops as is", 0xaaaaaa);
												} else {
													// drops something other than self
													this.ui.drawString("   drops ", 0xaaaaaa);
													this.ui.drawString((drop.name == null ? "<Unknown>" : drop.name), 0xffffff);
													this.ui.drawString(" (", 0xaaaaaa);
													this.ui.drawString(String.format("%d:%d", GET_ITEM_ID(drop.item), drop.metadata), 0xffffff);
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
								} catch(Exception e){
									Failure.log("block inspector, drops as");
								}

								// redstone
								try {
									int isProvidingSide = (mc.objectMouseOver.sideHit == 0 ? 1 : (mc.objectMouseOver.sideHit == 1 ? 0 : mc.objectMouseOver.sideHit));
									#ifdef MC147
									int isProvidingWeakPower = (block.isProvidingWeakPower(world, inspectX, inspectY, inspectZ, isProvidingSide) ? -1 : 0);
									int isProvidingStrongPower = (block.isProvidingStrongPower(world, inspectX, inspectY, inspectZ, isProvidingSide) ? -1 : 0);
									int blockPowerInput = (world.isBlockGettingPowered(inspectX, inspectY, inspectZ) ? -1 : 0);
									#else
									int isProvidingWeakPower = block.isProvidingWeakPower(world, inspectX, inspectY, inspectZ, isProvidingSide);
									int isProvidingStrongPower = block.isProvidingStrongPower(world, inspectX, inspectY, inspectZ, isProvidingSide);
									int blockPowerInput = world.getBlockPowerInput(inspectX, inspectY, inspectZ);
									#endif
									this.ui.drawString("   in: ", 0xaaaaaa);
									this.ui.drawString((blockPowerInput > 0 ? String.format("%d", blockPowerInput) : (blockPowerInput == -1 ? "x" : "_")), 0xffffff);
									this.ui.drawString(" out: ", 0xaaaaaa);
									this.ui.drawString((isProvidingWeakPower > 0 ? String.format("%d", isProvidingWeakPower) : (isProvidingWeakPower == -1 ? "x" : "_")), 0xffffff);
									this.ui.drawString(" / ", 0xaaaaaa);
									this.ui.drawString((isProvidingStrongPower > 0 ? String.format("%d", isProvidingStrongPower) : (isProvidingStrongPower == -1 ? "x" : "_")), 0xffffff);
									this.ui.lineBreak();
								} catch(Exception e){
									Failure.log("block inspector, redstone");
								}

								if(Cfg.enable_advanced_inspector){

									// brightness, hardness, resistance
									try {
										int brightness = block.getLightValue(world, inspectX, inspectY, inspectZ);
										float hardness = block.getBlockHardness(world, inspectX, inspectY, inspectZ);
										float resistance = block.getExplosionResistance(null);
										int opacity = block.getLightOpacity(world, inspectX, inspectY, inspectZ);
										this.ui.drawString("   b: ", 0xaaaaaa);
										this.ui.drawString((brightness > 0 ? String.format("%d", brightness) : "_"), 0xffffff);
										this.ui.drawString(" h: ", 0xaaaaaa);
										this.ui.drawString((hardness == -1 ? "unbreakable" : String.format("%.1f", hardness)), 0xffffff);
										this.ui.drawString(" r: ", 0xaaaaaa);
										this.ui.drawString(String.format("%.1f", resistance), 0xffffff);
										this.ui.drawString(" o: ", 0xaaaaaa);
										this.ui.drawString(String.format("%d%%", 100*opacity/255), 0xffffff);
										this.ui.lineBreak();
									} catch(Exception e){
										Failure.log("advanced block inspector, b.h.r.");
									}

									// has tile entity, has random ticks, has ticking tile entity, tile entities in block, total tile entities around player
									try {
										boolean tickRandomly = block.getTickRandomly();
										boolean tickingEntity = false;
										if(tileEntity != null)
											if(tileEntity.canUpdate())
												tickingEntity = true;
										this.ui.drawString("   e: ", 0xaaaaaa);
										this.ui.drawString((hasTileEntity ? "x" : "_"), 0xffffff);
										this.ui.drawString(" r: ", 0xaaaaaa);
										this.ui.drawString((tickRandomly ? "x" : "_"), 0xffffff);
										this.ui.drawString(" t: ", 0xaaaaaa);
										this.ui.drawString((tickingEntity ? "x" : "_"), 0xffffff);

										ArrayList tileEntityList = (ArrayList)world.loadedTileEntityList;
										int nr_tileEntities = tileEntityList.size();
										int nr_tileEntitiesAtPos = 0;
										int nr_tileEntitiesTicking = 0;
										for(int t = 0; t < nr_tileEntities; t++){
											TileEntity te = (TileEntity)tileEntityList.get(t);
											if(te.xCoord == inspectX && te.yCoord == inspectY && te.zCoord == inspectZ)
												nr_tileEntitiesAtPos++;
											if(te.canUpdate())
												nr_tileEntitiesTicking++;
										}
										if(nr_tileEntitiesAtPos == 0 && hasTileEntity)
											nr_tileEntitiesAtPos = BlockCoord.getTileEntity(world, inspectX, inspectY, inspectZ) == null ? 0 : 1;
										this.ui.drawString(" b: ", 0xaaaaaa);
										this.ui.drawString((nr_tileEntitiesAtPos > 0 ? String.format("%d", nr_tileEntitiesAtPos) : "_"), 0xffffff);
										this.ui.drawString(" a: ", 0xaaaaaa);
										this.ui.drawString((nr_tileEntities > 0 ? String.format("%d", nr_tileEntities) : "_"), 0xffffff);
										this.ui.drawString(" (", 0xaaaaaa);
										this.ui.drawString((nr_tileEntitiesTicking > 0 ? String.format("%d", nr_tileEntitiesTicking) : "_"), 0xffffff);
										this.ui.drawString(")", 0xaaaaaa);
										this.ui.lineBreak();
									} catch(Exception e){
										Failure.log("advanced block inspector, e.r.t.b.a.");
									}

									// normal, opaque, solid
									try {
										boolean solid = block.isBlockSolid(world, inspectX, inspectY, inspectZ, mc.objectMouseOver.sideHit);
										this.ui.drawString("   n: ", 0xaaaaaa);
										#ifdef NO_IDS
										this.ui.drawString((block.isNormalCube() ? "x" : "_"), 0xffffff);
										#else
										this.ui.drawString((Block.isNormalCube(block.blockID) ? "x" : "_"), 0xffffff);
										#endif
										this.ui.drawString(" o: ", 0xaaaaaa);
										this.ui.drawString((block.isOpaqueCube() ? "x" : "_"), 0xffffff);
										this.ui.drawString(" s: ", 0xaaaaaa);
										this.ui.drawString((solid ? "x" : "_"), 0xffffff);
										this.ui.lineBreak();
									} catch(Exception e){
										Failure.log("advanced block inspector, n.o.s.");
									}

								} // END Cfg.enable_advanced_inspector

								if(Cfg.show_inspector && hasTileEntity && tileEntity != null){
									// NBT tags
									try {
										NBTTagCompound tags = new NBTTagCompound();
										tileEntity.writeToNBT(tags);
										if(this.hasEntityTags(InfoHUD.ignore_tileentity_keys, tags)){
											this.ui.drawString("   NBT Data:", 0xaaaaaa);
											this.ui.lineBreak();
											this.displayEntityTags(InfoHUD.ignore_tileentity_keys, tags, "     ");
										}
									} catch(Exception e){
										Failure.log("block inspector, NBT data");
									}
								}

							} // END Cfg.show_inspector
						}
					}
				}
			}
		} catch(Exception e){
			Failure.log("block/entity inspector");
		}

		// SELF: armor value inspector
		try {
			if(Cfg.show_inspector && !player.capabilities.isCreativeMode){
				int armorValue = player.getTotalArmorValue();
				if(armorValue > 0){
					this.ui.setCursor(screen.getScaledWidth()/2 - 95, screen.getScaledHeight() - Math.round(62 + this.ui.unscaleValue(8)));
					this.ui.drawString(" armor: ", 0xaaaaaa);
					this.ui.drawString(String.format("%d", armorValue), 0xffffff);
				}
			}
		} catch(Exception e){
			Failure.log("armor value inspector");
		}

		// SELF: damage inspector
		try {
			if(Cfg.show_inspector && !player.capabilities.isCreativeMode){
				Entity entity = null;
				Block block = null;
				float damageEntity = 1.0F;
				float damageBlock = 1.0F;

				ItemStack hand = player.getHeldItem();
				Item item = (hand != null ? hand.getItem() : null);
				if(item != null){
					if(mc.objectMouseOver != null){
						if(mc.objectMouseOver.typeOfHit == MOVINGOBJECTTYPE_ENTITY){
							entity = mc.objectMouseOver.entityHit;
						} else if(mc.objectMouseOver.typeOfHit == MOVINGOBJECTTYPE_BLOCK)
							block = BlockCoord.getBlock(world, mc.objectMouseOver.blockX, mc.objectMouseOver.blockY, mc.objectMouseOver.blockZ);
					}

					try {
						#ifdef MC147
						damageEntity = (float)item.getDamageVsEntity(entity);
						#elif defined MC152
						damageEntity = (float)item.getDamageVsEntity(entity, hand);
						#else
						if(item instanceof ItemTool){
							entity = null;
							#ifdef MC164
							damageEntity = ((ItemTool)item).damageVsEntity;
							#else
							damageEntity = ((ItemTool)item).func_150913_i().getDamageVsEntity(); // public Item.ToolMaterial func_150913_i() return this.toolMaterial;
							#endif
						} else if(item instanceof ItemSword){
							entity = null;
//							damageEntity = (float)((AttributeModifier)item.getItemAttributeModifiers().get(SharedMonsterAttributes.attackDamage)).getAmount();
						#ifdef MC164
						} else {
							damageEntity = item.getDamageVsEntity(entity, hand); // DEPRECATED
						#endif
						}
						#endif
					} catch(Exception e){
						// possible?
					}
					if(block != null)
						try {
							#if defined MC147 || defined MC152 || defined MC164
							damageBlock = item.getStrVsBlock(hand, block);
							#else
							damageBlock = item.getDigSpeed(hand, block, world.getBlockMetadata(mc.objectMouseOver.blockX, mc.objectMouseOver.blockY, mc.objectMouseOver.blockZ));
							#endif
						} catch(Exception e){
							// possible?
						}
				}
				this.ui.setCursor(screen.getScaledWidth()/2 + 10, screen.getScaledHeight() - Math.round(62 + this.ui.unscaleValue(8)));
				this.ui.drawString("damage: ", 0xaaaaaa);
				this.ui.drawString(String.format("%s%.1f", (entity == null ? "*" : ""), damageEntity), 0xffffff);
				this.ui.drawString("e ", 0xaaaaaa);
				this.ui.drawString(String.format("%s%.1f", (block == null ? "*" : ""), damageBlock), 0xffffff);
				this.ui.drawString("b", 0xaaaaaa);
			}
		} catch(Exception e){
			Failure.log("damage inspector");
		}

		// SELF: food inspector
		try {
			if(Cfg.show_inspector && !player.capabilities.isCreativeMode){
				FoodStats food = player.getFoodStats();
				this.ui.setCursor(screen.getScaledWidth()/2 + 10, screen.getScaledHeight() - Math.round(42 + this.ui.unscaleValue(8)));
				this.ui.drawString("f: ", 0xaaaaaa);
				this.ui.drawString(String.format("%d%%", 100*food.getFoodLevel()/20), 0xffffff);
				this.ui.drawString("  s: ", 0xaaaaaa);
				this.ui.drawString(String.format("%d%%", (int)Math.round(100*food.getSaturationLevel()/20)), 0xffffff);
			}
		} catch(Exception e){
			Failure.log("food inspector");
		}

		// SELF: experience inspector
		try {
			if(Cfg.show_inspector && !player.capabilities.isCreativeMode){
				this.ui.setCursor(screen.getScaledWidth()/2, screen.getScaledHeight() - 30);
				this.ui.drawString(UI.ALIGN_CENTER,
									String.format("%d / %d", player.experienceTotal, player.experienceTotal + (int)((1.0F - player.experience) * player.xpBarCap())),
									0xffffff, 0);
			}
		} catch(Exception e){
			Failure.log("experience inspector");
		}

		GL11.glPopMatrix();
	}

	private static final Map<String,Integer> ignore_entity_keys;
	static {
		HashMap<String,Integer> m = new HashMap<String,Integer>();
		m.put("Health", 0); // max health is shown elsewhere
		m.put("Pos", 0);
		m.put("Motion", 0);
		m.put("Rotation", 0);
		m.put("FallDistance", 0);
		m.put("Fire", 0);
		m.put("Air", 0);
		m.put("OnGround", 0);
		m.put("Dimension", 0);
		m.put("Invulnerable", 0);
		m.put("PortalCooldown", 0);
		m.put("PersistenceRequired", 0);
		m.put("PersistentId", 0); // legacy 147
		m.put("PersistentIDMSB", 0); // legacy 147
		m.put("PersistentIDLSB", 0); // legacy 147
		m.put("UUIDMost", 0); // 152+
		m.put("UUIDLeast", 0); // 152+
		m.put("Riding", 0); // 152+
		m.put("CustomName", 0); // 152+
		m.put("CustomNameVisible", 0); // 152+
		ignore_entity_keys = Collections.unmodifiableMap(m);
	}

	private static final Map<String,Integer> ignore_tileentity_keys;
	static {
		HashMap<String,Integer> m = new HashMap<String,Integer>();
		m.put("id", 0);
		m.put("x", 0);
		m.put("y", 0);
		m.put("z", 0);
		ignore_tileentity_keys = Collections.unmodifiableMap(m);
	}

	private boolean hasEntityTags(Map<String,Integer> ignore_keys, NBTTagCompound tags){
		if(tags.hasNoTags()) return false;

		for(final Object tag_object : Hacks.getTagMap(tags).entrySet()){
			if(!(tag_object instanceof Map.Entry)) continue;
			final Map.Entry tag = (Map.Entry)tag_object;
			final String key = (String)tag.getKey();

			final Object value = tag.getValue();
			if(value instanceof NBTTagEnd) continue;

			// don't display empty compound tags
			if(value instanceof NBTTagCompound && !this.hasEntityTags(null, tags.getCompoundTag(key))) continue;

			// these types aren't yet supported -- if added, remove from displayEntityTags()
			if(value instanceof NBTTagByteArray
			|| value instanceof NBTTagIntArray
			|| value instanceof NBTTagList
			) continue;

			if(key != null){
				if(ignore_keys == null) return true;
				if(!ignore_keys.containsKey(key)) return true;
			}
		}
		return false;
	}

	private void displayEntityTags(Map<String,Integer> ignore_keys, NBTTagCompound tags, String indent){
		for(final Object tag_object : Hacks.getTagMap(tags).entrySet()){
			if(!(tag_object instanceof Map.Entry)) continue;

			final Map.Entry tag = (Map.Entry)tag_object;
			final String key = (String)tag.getKey();

			if(key != null && ignore_keys != null && ignore_keys.containsKey(key)) continue;

			final Object value = tag.getValue();
			if(value instanceof NBTTagEnd) continue;

			// don't display empty compound tags
			NBTTagCompound compound_value = null;
			if(value instanceof NBTTagCompound){
				compound_value = tags.getCompoundTag(key);
				if(!this.hasEntityTags(null, compound_value)) continue;
			}

			// these types aren't yet supported -- if added, remove from hasEntityTags()
			if(value instanceof NBTTagByteArray
			|| value instanceof NBTTagIntArray
			|| value instanceof NBTTagList
			) continue;
			// lists and arrays should never be supported as it would allow one to "see" into inventories on protected multiplayer servers

			this.ui.drawString(String.format("%s%s: ", indent, key == null ? "NULL KEY" : key), 0xaaaaaa);
			if(value instanceof NBTTagByte)				this.ui.drawString(String.format("%d", tags.getByte(key)), 0xffffff);
			else if(value instanceof NBTTagShort)		this.ui.drawString(String.format("%d", tags.getShort(key)), 0xffffff);
			else if(value instanceof NBTTagInt)			this.ui.drawString(String.format("%d", tags.getInteger(key)), 0xffffff);
			else if(value instanceof NBTTagLong)		this.ui.drawString(String.format("%d", tags.getLong(key)), 0xffffff);
			else if(value instanceof NBTTagFloat)		this.ui.drawString(String.format("%f", tags.getFloat(key)), 0xffffff);
			else if(value instanceof NBTTagDouble)		this.ui.drawString(String.format("%f", tags.getDouble(key)), 0xffffff);
			else if(value instanceof NBTTagString)		{ this.ui.drawString("\"", 0xaaaaaa); this.ui.drawString(tags.getString(key), 0xffffff); this.ui.drawString("\"", 0xaaaaaa); }
//			else if(value instanceof NBTTagByteArray)	this.ui.drawString("byte[?]", 0xaaaaaa);
//			else if(value instanceof NBTTagIntArray)	this.ui.drawString("int[?]", 0xaaaaaa);
			else if(value instanceof NBTTagCompound){	this.ui.lineBreak(); this.displayEntityTags(null, compound_value, String.format("%s  ", indent)); }
//			else if(value instanceof NBTTagList)		this.ui.drawString("(?)", 0xaaaaaa);
			else										this.ui.drawString("?", 0xaaaaaa);
			this.ui.lineBreak();
		}
	}
}
