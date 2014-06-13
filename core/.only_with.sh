#!/bin/sh

if [ "$1" = "147" -o "$1" = "152" ]; then
	# classes needed for HUD
	echo "au_core BlockCoord Button Color Config ItemUtils Light UI"
elif [ "$1" = "172" ]; then
	# TEMP
	echo "au_core BlockCoord Button Color Config ItemUtils Light TextField UI"
fi
