#!/usr/bin/php -q
<?php

$path = $argv[1];
if($path == ""){
	echo "Syntax: $argv[0] [path]\n";
	exit();
}

////////////////////

function mult_color($color, $multiplier, $s1, $s2){
	$color = round(hexdec(substr($color, $s1, $s2))*$multiplier);
	return ($color > 255 ? 255 : $color);
}

function get_color($image, $color, $alpha, $multiplier){
	$colors = "1e1b1b b3312c 3b511a 51301a 253192 7b2fbe 287697 ababab 434343 d88198 41cd34 decf2a 6689d3 c354cd eb8844 f0f0f0";
	$colors = explode(' ', $colors);
	$color = $colors[$color];
	if($alpha == 0)
		return imagecolorallocate($image, mult_color($color, $multiplier, 0, 2), mult_color($color, $multiplier, 2, 2), mult_color($color, $multiplier, 4, 2));
	else
		return imagecolorallocatealpha($image, mult_color($color, $multiplier, 0, 2), mult_color($color, $multiplier, 2, 2), mult_color($color, $multiplier, 4, 2), $alpha*127);
}

function drawCT($c, $alpha, $tag, $multiplier){
	global $path;

	$image = imagecreatetruecolor(16, 16);
	imagealphablending($image, true);
	imagefill($image, 0,0, get_color($image, $c, $alpha, $multiplier));
	imagesavealpha($image, true);

	// sides
	if($alpha == 0){
		$edge = get_color($image, $c, 0, $multiplier*0.9); // 90% outer
		imageline($image, 0,0, 15,0, $edge);
		imageline($image, 15,0, 15,15, $edge);
		imageline($image, 0,15, 15,15, $edge);
		imageline($image, 0,0, 0,15, $edge);
		$edge = get_color($image, $c, 0, $multiplier*0.95); // 95% inner
		imageline($image, 1,1, 14,1, $edge);
		imageline($image, 14,1, 14,14, $edge);
		imageline($image, 1,14, 14,14, $edge);
		imageline($image, 1,1, 1,14, $edge);
	}

	imagepng($image, $path.'colorLamp'.$c.'-'.$tag.'.png');
	imagedestroy($image);
}

////////////////////

for($c = 0; $c < 16; $c++){
	drawCT($c, 0.0, 'unlit', 0.7);
	drawCT($c, 0.0, 'lit', 1.0);
	drawCT($c, 0.4, 'glow', 1.2);
}

?>
