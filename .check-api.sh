#!/bin/sh

VER=$1
APIJAR=$2
APISRC=$3

# Industrial Craft 2 -- http://forum.industrial-craft.net
# http://ic2api.player.to:8080/job/IC2_lf/
# http://ic2api.player.to:8080/job/IC2_experimental/
# http://wiki.industrial-craft.net/?title=Download
# unpack api to ~/forge-1.6.4/mcp/src/minecraft/ic2
# unpack api to ~/forge-1.7.2/src/api/java/ic2

# NEI + Core
# http://www.chickenbones.craftsaddle.org/Files/New_Versions/links.php

# Forge Multipart
# http://files.minecraftforge.net/ForgeMultipart/

WITH_APIS=.tmp_with-apis
rm -f $WITH_APIS
echo "" > $WITH_APIS

# APIs
if [ "$VER" = "147" ]; then
	JARS="CodeChickenCore_0.8.1.6.jar NotEnoughItems_1.4.7.4.jar"
	# industrialcraft-2-api-1.4.7-1.115.231-lf.zip
	SRCS="ic2"
elif [ "$VER" = "152" ]; then
	JARS="CodeChickenCore_0.8.7.3-fix1.jar NotEnoughItems_1.5.2.28.jar"
	# industrialcraft-2-api-1.5.2-1.116.364-lf.zip
	SRCS="ic2"
elif [ "$VER" = "164" ]; then
	JARS="CodeChickenCore_0.9.0.7.jar NotEnoughItems_1.6.1.8.jar ForgeMultipart-dev-1.6.4-1.0.0.248.jar"
	# industrialcraft-2-api-1.6.2-1.118.401-lf.zip
	SRCS="ic2"
elif [ "$VER" = "172" ]; then
	JARS=""
	# industrialcraft-2-api-1.7.2-2.1.484-experimental.jar
	SRCS="ic2"
elif [ "$VER" = "17A" ]; then
	JARS=""
	# industrialcraft-2-api-1.7.10-2.2.827-experimental.jar
	SRCS="ic2"
fi

# check JAR APIs
for a in $JARS; do
	if [ -f "$APIJAR/$a" ]; then
		echo "FOUND API: $a"
		[[ "$a" =~ "NotEnoughItems" ]] && echo -n "-DWITH_API_NEI " >> $WITH_APIS
		[[ "$a" =~ "ForgeMultipart-dev" ]] && echo -n "-DWITH_API_FMP " >> $WITH_APIS
	else
		echo "API NOT FOUND: $APIJAR/$a"
		exit 1
	fi
done

# check SRC APIs
for a in $SRCS; do
	if [ -d "$APISRC/$a" ]; then
		echo "FOUND API: $a"
		[ "$a" = "ic2" ] && echo -n "-DWITH_API_IC2 " >> $WITH_APIS
	else
		echo "API NOT FOUND: $APISRC/$a"
		exit 1
	fi
done
