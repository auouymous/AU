[OmegaID](https://github.com/HeisenBugMC/HeisenBugMC/blob/master/ideas/OmegaID.md) is a project to add block and item collision prevent into Forge.

My Ideas
==========

This system doesn't require any changes to the world format and doesn't require changes
to existing mods.

* load the ID mapping file on server, one file for entire map
* send ID mapping file to clients
* hook into net.minecraft.block.Block and net.minecraft.item.Item to remap IDs before Block or Item is added to the array
* existing Forge mods will not require any changes

The ID mapping file would contain an 8bit ID and a @Mod(modid) string for each mod.
Mod IDs are incrementally assigned the first time each one is loaded.

The next section in the file contains a map between mod IDs and real IDs.
* 8 bit: mod ID from first section
* 1 bit: item or block selector
* 15 bit: real block or item ID
* 1 bit: registered flag
* 15 bit: mod specific block or item ID

Maximum file size sent to clients would be 180k plus the size of the first section.
The registered flag would be cleared if the mod was not loaded or no longer registered
that block or item. Unregistered blocks and items would remain in the map until server
was started with a config setting to free unregistered IDs. This would prevent resets
caused by accidentally started a server without mods. I've seen it happen. :)

This system could be extended by adding a second Block and Item constructor that accepts
a string ID instead of integer ID. A third section would be added to the file to handle
maps between string IDs and real IDs. This would require changes to mods but removes the
burden of managing IDs.

==========

The ID mapping file could be distributed in the modpack instead of sent every time a
client connects to the server.
