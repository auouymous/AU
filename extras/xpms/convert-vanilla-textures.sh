#!/bin/sh

function colorize_blocks(){
	# ItemDye.dyeColors
#	COLORS="1e1b1b b3312c 3b511a 51301a 253192 7b2fbe 287697 ababab 434343 d88198 41cd34 decf2a 6689d3 c354cd eb8844 f0f0f0"
	COLORS="221f1f b3312c 3b511a 51301a 253192 7b2fbe 287697 cfcfcf 494949 d88198 41cd34 decf2a 6689d3 c354cd eb8844 ffffff"
	# "black", "red", "green", "brown", "blue", "purple", "cyan", "silver",
	# "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"

	# 20% brighter
	# 282525 d63a34 46611f 61391f 2c3aaf 9338e4 308db5 f8f8f8 - 575757 ff9ab6 4ef63e fff832 7aa4fd ea64f6 ffa351 ffffff

	if [ "$2" = "colorFlower" -o "$2" = "colorFlowerDye" ]; then
		convert ${1} tmp.png
		# amplify all colors by 20% except light dray, dark gray and white
		# amplify red by 40%, but leave its GB components at default
		COLORS="282525 fa312c 46611f 61391f 2c3aaf 9338e4 308db5 cfcfcf 494949 ff9ab6 4ef63e fff832 7aa4fd ea64f6 ffa351 ffffff"
	elif [ "$2" = "colorSand" ]; then
		convert ${1} tmp.png
	else
		convert ${1} -brightness-contrast 20x0 tmp.png
	fi
	i=`expr 0`
	[ "$2" = "colorFlowerDye" ] && TPATH="items" || TPATH="blocks"
	for color in $COLORS; do
		# get 1/4 of the color
		C=`php -r 'function t($h,$o){return floor(hexdec(substr($h,$o,2))/4);}echo substr("000000".dechex((t($argv[1],0)<<16)+(t($argv[1],2)<<8)+t($argv[1],4)),-6);' ${color}`
		[ "${color}" = "ffffff" ] && C="7f7f7f"
		echo "$color -- $C -- $2"
		convert tmp.png +level-colors "#${C}","#${color}" $TPATH/${2}$i.xpm
		[ "$3" != "" ] && composite $TPATH/${2}$i.xpm $3 $TPATH/${2}$i.xpm
		i=`expr $i + 1`
	done
	rm -f tmp.png
}

# items
#rm -f items/cookedFlesh.xpm
#convert -fill "#000000" -tint 75 rottenFlesh.png rf_tint.xpm
#convert -fill "#000000" -colorize 75 rottenFlesh.png rf_color.xpm
#convert rf_tint.xpm rf_color.xpm rottenFlesh.png -composite rf_composite.xpm
#convert -colors 64 rf_composite.xpm items/cookedFlesh.xpm

# blocks
rm -f blocks/colorStoneHalfSlabs*
rm -f blocks/colorCobble*
rm -f blocks/colorStone*
rm -f blocks/colorStoneBrick*
rm -f blocks/colorChiseledBrick*
rm -f blocks/colorSmoothBrick*
rm -f blocks/colorGravel*
rm -f blocks/colorSand*
rm -f blocks/colorFlower*
rm -f blocks/colorIronBars*
colorize_blocks vanilla-stone_slab_top.png colorStoneHalfSlabs-top
colorize_blocks vanilla-stone_slab_side.png colorStoneHalfSlabs-side
colorize_blocks vanilla-cobblestone.png colorCobble
colorize_blocks vanilla-stone.png colorStone
colorize_blocks vanilla-stonebrick.png colorStoneBrick
colorize_blocks vanilla-chiseledbrick.png colorChiseledBrick
colorize_blocks smoothbrick.png colorSmoothBrick
colorize_blocks vanilla-gravel.png colorGravel
colorize_blocks sand.png colorSand
colorize_blocks flower-top.xpm colorFlower blocks/flowerStage1.xpm
colorize_blocks vanilla-iron_bars.png colorIronBars

# items
rm -f items/colorFlowerDye*
colorize_blocks dye.xpm colorFlowerDye
COLORS="1 5 6 7 8 9 10 11 12 13 14"
for color in $COLORS; do
	rm -f items/colorFlowerDye${color}.xpm
done
