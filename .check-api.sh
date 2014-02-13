#!/bin/sh

VER=$1
APIJAR=$2
APISRC=$3

# Industrial Craft 2 -- http://forum.industrial-craft.net
# http://ic2api.player.to:8080/job/IC2_lf/
# http://ic2api.player.to:8080/job/IC2_experimental/

# NEI + Core
# http://www.chickenbones.craftsaddle.org/Files/New_Versions/links.php

# APIs
if [ "$VER" = "147" ]; then
	JARS="CodeChickenCore_0.8.1.6.jar NotEnoughItems_1.4.7.4.jar"
	# industrialcraft-2-api_1.115.231-lf.zip
	SRCS="ic2"
elif [ "$VER" = "152" ]; then
	JARS="CodeChickenCore_0.8.7.3-fix1.jar NotEnoughItems_1.5.2.28.jar"
	# industrialcraft-2-api_1.116.364-lf.zip
	SRCS="ic2"
elif [ "$VER" = "162" ]; then
	JARS="CodeChickenCore_0.9.0.5.jar NotEnoughItems_1.6.1.4.jar"
	# industrialcraft-2-api_1.118.401-lf.zip
	SRCS="ic2"
elif [ "$VER" = "164" ]; then
	JARS="CodeChickenCore_0.9.0.7.jar NotEnoughItems_1.6.1.8.jar"
	# industrialcraft-2-api_1.118.401-lf.zip
	SRCS="ic2"
elif [ "$VER" = "172" ]; then
	JARS=""
	SRCS=""
fi

# check JAR APIs
for a in $JARS; do
	if [ -f "$APIJAR/$a" ]; then
		echo "FOUND API: $a"
	else
		echo "API NOT FOUND: $APIJAR/$a"
		exit 1
	fi
done

# check SRC APIs
for a in $SRCS; do
	if [ -d "$APISRC/$a" ]; then
		echo "FOUND API: $a"
	else
		echo "API NOT FOUND: $APISRC/$a"
		exit 1
	fi
done
