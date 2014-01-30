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

function fade_color($color){
	$fade = 0.80;
	return substr("000000".dechex((floor(hexdec(substr($color,0,2))*$fade)<<16)+(floor(hexdec(substr($color,2,2))*$fade)<<8)+floor(hexdec(substr($color,4,2))*$fade)),-6);
}

function get_color($image, $color){
	return imagecolorallocate($image, hexdec(substr($color, 0, 2)), hexdec(substr($color, 2, 2)), hexdec(substr($color, 4, 2)));
}


$image = imagecreatetruecolor(16, 16);

for($c = 0; $c < 16; $c++){
	$gray = get_color($image, fade_color($colors[$c])); // use the faded corner color from glass
	imageline($image, $c,0, $c,15, $gray);
}

imagepng($image, $path.'colorPaneSides.png');
imagedestroy($image);

?>
