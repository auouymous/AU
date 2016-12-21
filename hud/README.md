AU HUD
==========

Display game information in upper left corner.
Also includes armor and potion HUDs that can be placed in any corner.

[Minecraft Forum](http://www.minecraftforum.net/topic/1945197-)

Required: AU CORE

***

'H' key opens HUD options menu.
'I' key toggles inspector.
<none> key zooms player view and reduces mouse sensitivity.
All keys are configurable in the Controls menu.

Info HUD elements
* dimension
* biome
* heading
* player coordinates with height at feet and eyes
* light level at feet
* minecraft time
* rain status
* number of used inventory slots, changes color and bounces out when full
* FPS and chunk updates
* visible/total entities and particles
* used memory, allocated memory
* ticks/second (tps percentage = slowdown factor)
* name of targeted block or entity (displays item frame contents)

Entity Inspector
* entity ID (e:entityID, i:itemFrameID)
* max health (pre-1.6), health/max_health (1.6+)
* armor value
* is invulnerable
* name of each armor piece on player or mob
* name of item held by player or mob
* NBT tags

Block Inspector
* block ID (c:picked and b:placed)
* creative tab
* minimum tool required to break block (sword, axe, pick, shovel, shears, scoop, IC2 wrench)
* name and ID of dropped item when block is broken
* silkable
* in:redstone input level, out:redstone output level weak/strong [1.4.7 only shows on or off]
* NBT tags if block has a tile entity
Advanced Block Inspector
* b:brightness, h:hardness, r:explosion resistance, o:light opacity
* n:normal, o:opaque, s:solid
* e:has tile entity, r:has random ticks, t:has ticking tile entity, b:tile entities in block, a:all loaded tile entities (all ticking tile entities)
* hides silverfish blocks, can be changed in the config file

Speed Inspector
* shows your current speed in blocks per second

Food Inspector
* f:food level percentage, s:saturation percentage

Armor Inspector
* shows your armor value

Damage Inspector
* shows damage dealt by the item you are holding (entity / block)
* an asterisk before entity/block damage represents base damage, targeting an entity/block shows true damage
* does not factor in the random multiplier for enchants
* some weapons don't report correct damage

Experience Inspector
* shows total xp points and total xp points for next level

Armor HUD
* icons for all four armor pieces and held item
* durability bar over item, durability percentage or durability/max_durability
* always switches to durability/max_durability when info inspector is active
* shows total quantity of held item in inventory
* shows quantity of arrows if bow is held item
* supports IC2 armors and tools (charge in 1.4.7 isn't precise)
* supports Tinkers Construct tools

Potion HUD
* icons for all active potion effects
* shows effect name and duration

Shop Signs HUD
* appears around cursor when targeting a chest shop sign
* colored buy/sell price and per unit price if more than one
* displays item name and icon when sign only shows the ID

Server Info
* server name, address, version, gamemode and motd
* difficulty and hardcore flag
* game rules
* map age
* spawn or player's home coordinates

***

Minimap and Waypoints
* use [REI's minimap](http://www.minecraftforum.net/topic/482147-)
