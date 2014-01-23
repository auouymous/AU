#!/usr/bin/php -q
<?php

$path = $argv[1];
if($path == ""){
	echo "Syntax: $argv[0] [path]\n";
	exit();
}

////////////////////

$colors = "1e1b1b b3312c 3b511a 51301a 253192 7b2fbe 287697 ababab 434343 d88198 41cd34 decf2a 6689d3 c354cd eb8844 f0f0f0";
$colors = explode(' ', $colors);

function get_color($image, $color, $alpha){
	if($alpha == 0)
		return imagecolorallocate($image, hexdec(substr($color, 0, 2)), hexdec(substr($color, 2, 2)), hexdec(substr($color, 4, 2)));
	else
		return imagecolorallocatealpha($image, hexdec(substr($color, 0, 2)), hexdec(substr($color, 2, 2)), hexdec(substr($color, 4, 2)), $alpha*127);
}

function drawCT($c){
	global $path, $item_icon, $colors;

	$image = imagecreatetruecolor(16, 16);
	imagealphablending($image, true);
	imagefill($image, 0,0, get_color($image, $colors[$c], 0.5)); // 50% transparent
	imagesavealpha($image, true);

	if($item_icon){
		$clear = imagecolorallocatealpha($image, 0, 0, 0, 127); // 100% transparent
		imagealphablending($image, false);
		for($x = 2; $x < 14; $x++)
			for($y = 2; $y < 14; $y++)
				if($x % 2 == $y % 2)
					imagesetpixel($image, $x,$y, $clear);

		imageline($image, 0,0, 15,0, $clear);
		imageline($image, 15,0, 15,15, $clear);
		imageline($image, 0,15, 15,15, $clear);
		imageline($image, 0,0, 0,15, $clear);
	}

	imagepng($image, $path.'colorGlassTintedNoFrame'.$c.($item_icon ? '-item' : '').'.png');
	imagedestroy($image);
}

////////////////////

for($c = 0; $c < 16; $c++){
	$item_icon = 0;
	drawCT($c);

//	$item_icon = 1;
//	drawCT($c);
}

?>
