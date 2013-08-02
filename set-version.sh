#!/bin/sh

mod=$1
ver=$2
if [ "$mod" != "" -a "$ver" != "" ]; then
	if [ -f "$mod/mcmod.info" -a -f "$mod/src/AU${mod^}.java" ]; then
		echo -e "set \"\033[33;2m$mod\033[0m\" version to \"\033[33;2m$ver\033[0m\""
		sed -E -i "s:\"version\"\: \"[^\"]*\":\"version\"\: \"$ver\":" $mod/mcmod.info
		sed -E -i "s:, version=\"[^\"]*\"\):, version=\"$ver\"\):" $mod/src/AU${mod^}.java
	else
		echo -e "Invalid mod \"\033[33;2m$mod\033[0m\""
	fi
else
	echo "Syntax: $0 [mod] [version]"
fi
