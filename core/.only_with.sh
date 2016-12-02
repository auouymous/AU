#!/bin/sh

VER=$1
SUPPORT_BLOCKS=$2

# classes needed for au_hud
MINIMAL_CORE="au_core BlockCoord Button Color Config ItemUtils Light UI"
# TextField is required for 164+
MINIMAL_CORE_164="$MINIMAL_CORE TextField"

if [ "$VER" = "147" -o "$VER" = "152" ]; then
	# full core not supported
	echo "$MINIMAL_CORE"
elif [ "$VER" = "172" -o "$VER" = "17A" ]; then
	# full core not yet supported
	if [ "$SUPPORT_BLOCKS" = "no" ]; then
		echo "$MINIMAL_CORE_164"
	else
		echo "$MINIMAL_CORE_164 \
			 IConnectedTexture IRotatableBlock ISidedBlock ISidedRenderer \
			 NEICoreConfig NEIWidget Packets \
			 RenderUtils SlotAU SlotInventory SlotOutput SlotPattern \
		"

		# TODO: enable WITH_API_NEI and verify the two NEI classes compile

		# ContainerAU needs TileEntityAU
		# GuiContainerAU needs TileEntityAU / ContainerAU					*
		# ISidedTileEntity needs SidedBlockInfo
		# PacketHandlerClient needs PacketUtils / TileEntityAU				*
		# PacketUtils														WONT COMPILE
		# SidedBlockInfo needs SidedSlotInfo
		# SidedSlotInfo needs TileEntityAU
		# SlotBlockCamo needs TileEntityAU									*
		# TileEntityAU needs ISidedTileEntity / PacketUtils / Packets		*
	fi
fi
