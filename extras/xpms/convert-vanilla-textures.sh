#!/bin/sh

function colorize_blocks(){
	# ItemDye.dyeColors
#	COLORS="1e1b1b b3312c 3b511a 51301a 253192 7b2fbe 287697 ababab 434343 d88198 41cd34 decf2a 6689d3 c354cd eb8844 f0f0f0"
	COLORS="221f1f b3312c 3b511a 51301a 253192 7b2fbe 287697 cfcfcf 494949 d88198 41cd34 decf2a 6689d3 c354cd eb8844 ffffff"
	# "black", "red", "green", "brown", "blue", "purple", "cyan", "silver",
	# "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white"

	convert ${1} -brightness-contrast 20x0 tmp.png
	i=`expr 0`
	for color in $COLORS; do
		C=`php -r 'function t($h,$o){return floor(hexdec(substr($h,$o,2))/4);}echo dechex((t($argv[1],0)<<16)+(t($argv[1],2)<<8)+t($argv[1],4));' ${color}`
		[ "${color}" = "ffffff" ] && C="5f5f5f"
		echo "$color -- $C"
		convert tmp.png +level-colors "#${C}","#${color}" blocks/${2}$i.xpm
		[ "$3" != "" ] && composite blocks/${2}$i.xpm $3 blocks/${2}$i.xpm
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
rm -f blocks/colorCobble*
rm -f blocks/colorStone*
rm -f blocks/colorStoneBrick*
rm -f blocks/colorChiseledBrick*
rm -f blocks/colorGravel*
rm -f blocks/colorSmoothBrick*
rm -f blocks/colorFlower*
colorize_blocks vanilla-cobblestone.png colorCobble
colorize_blocks vanilla-stone.png colorStone
colorize_blocks vanilla-stonebrick.png colorStoneBrick
colorize_blocks vanilla-chiseledbrick.png colorChiseledBrick
colorize_blocks vanilla-gravel.png colorGravel
colorize_blocks smoothbrick.png colorSmoothBrick
colorize_blocks flower-top.png colorFlower flower-stem.png
