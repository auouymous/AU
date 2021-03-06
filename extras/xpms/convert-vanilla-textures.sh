#!/bin/sh

# this script is manually executed to generate textures

function colorize_texture(){
	# ItemDye.dyeColors
#	COLORS="1e1b1b b3312c 3b511a 51301a 253192 7b2fbe 287697 ababab 434343 d88198 41cd34 decf2a 6689d3 c354cd eb8844 f0f0f0"
	COLORS="221f1f b3312c 3b511a 51301a 253192 7b2fbe 287697 cfcfcf 494949 d88198 41cd34 decf2a 6689d3 c354cd eb8844 ffffff"
	# "black", "red", "green", "brown", "blue", "purple", "cyan", "silver",
	# "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"

	# 20% brighter
	# 282525 d63a34 46611f 61391f 2c3aaf 9338e4 308db5 f8f8f8 - 575757 ff9ab6 4ef63e fff832 7aa4fd ea64f6 ffa351 ffffff

	# the optional uncolorized image is overlaid on the colorized image
	OUTPUT_PATH="$1"
	BRIGHTNESS_CONTRAST="$2"
	COLORIZED_IMAGE="$3"
	OUTPUT="$4"
	UNCOLORIZED_IMAGE="$5"

	if [ "$BRIGHTNESS_CONTRAST" != "-" ]; then
		# change brightness and contrast of texture
		convert $COLORIZED_IMAGE -brightness-contrast $BRIGHTNESS_CONTRAST tmp.png
	elif [ "$OUTPUT" = "colorFlower" -o "$OUTPUT" = "colorFlowerDye" ]; then
		convert $COLORIZED_IMAGE tmp.png
		# amplify all colors by 20% except light gray, dark gray and white
		# amplify red by 40%, but leave its GB components at default
		COLORS="282525 fa312c 46611f 61391f 2c3aaf 9338e4 308db5 cfcfcf 494949 ff9ab6 4ef63e fff832 7aa4fd ea64f6 ffa351 ffffff"
	else
		convert $COLORIZED_IMAGE tmp.png
	fi
	i=`expr 0`
	for color in $COLORS; do
		# get 25% of the color, 50% for white
		if [ "$color" = "ffffff" ]; then
			C="7f7f7f"
		else
			C=`php -r 'function t($h,$o){return floor(hexdec(substr($h,$o,2))/4);}echo substr("000000".dechex((t($argv[1],0)<<16)+(t($argv[1],2)<<8)+t($argv[1],4)),-6);' $color`
		fi
		echo "$C -- $color -- $OUTPUT"
		convert tmp.png +level-colors "#$C","#$color" $OUTPUT_PATH/$OUTPUT$i.xpm
		[ "$UNCOLORIZED_IMAGE" != "" ] && composite $OUTPUT_PATH/$OUTPUT$i.xpm $UNCOLORIZED_IMAGE $OUTPUT_PATH/$OUTPUT$i.xpm
		i=`expr $i + 1`
	done
	rm -f tmp.png
}

##########
# BLOCKS #
##########

colorize_texture blocks 20x0 vanilla-stone_slab_top.png colorStoneHalfSlabs-top
colorize_texture blocks 20x0 vanilla-stone_slab_side.png colorStoneHalfSlabs-side

colorize_texture blocks 20x0 vanilla-cobblestone.png colorCobble

colorize_texture blocks 20x0 vanilla-stone.png colorStone

colorize_texture blocks 20x0 vanilla-stonebrick.png colorStoneBrick

colorize_texture blocks 20x0 vanilla-chiseledbrick.png colorChiseledBrick

colorize_texture blocks 20x0 vanilla-gravel.png colorGravel

colorize_texture blocks 20x0 vanilla-iron_bars.png colorIronBars

# custom texture, colorizing it here
colorize_texture blocks - flower-top.xpm colorFlower blocks/flowerStage1.xpm

# custom texture, colorizing it here
colorize_texture blocks 20x0 smoothbrick.png colorSmoothBrick

# mc sand -> grayscale
colorize_texture blocks - sand.png colorSand

# mc plank -> grayscale
colorize_texture blocks - plank_birch.png colorPlankBirch
colorize_texture blocks - plank_oak.png colorPlankOak
colorize_texture blocks - plank_spruce.png colorPlankSpruce

#########
# ITEMS #
#########

colorize_texture items - dye.xpm colorFlowerDye
RM_COLORS="1 5 6 7 8 9 10 11 12 13 14"
for color in $RM_COLORS; do
	rm -f items/colorFlowerDye${color}.xpm
done

#convert -fill "#000000" -tint 75 rottenFlesh.png rf_tint.xpm
#convert -fill "#000000" -colorize 75 rottenFlesh.png rf_color.xpm
#convert rf_tint.xpm rf_color.xpm rottenFlesh.png -composite rf_composite.xpm
#convert -colors 64 rf_composite.xpm items/cookedFlesh.xpm
