#!/usr/bin/php -q
<?php

$path = $argv[1];
if($path == ""){
	echo "Syntax: $argv[0] [path]\n";
	exit();
}

////////////////////

$image = imagecreatetruecolor(16, 128);
imagealphablending($image, true);
imagesavealpha($image, true);

$obsidian1 = imagecreatefrompng("enderCube-obsidian1.png");
$obsidian2 = imagecreatefrompng("enderCube-obsidian2.png");
$obsidian3 = imagecreatefrompng("enderCube-obsidian3.png");
$frame = imagecreatefrompng("enderCube-frame.png");
$halo1 = imagecreatefrompng("enderCube-halo1.png");
$halo2 = imagecreatefrompng("enderCube-halo2.png");
$halo3 = imagecreatefrompng("enderCube-halo3.png");
$eye10 = imagecreatefrompng("enderCube-eye10-alpha.png");

//////////

// obsidian
imagecopy($image, $obsidian1, 0,0*16, 0,0,16,16);
imagecopy($image, $obsidian1, 0,1*16, 0,0,16,16);
imagecopy($image, $obsidian2, 0,2*16, 0,0,16,16);
imagecopy($image, $obsidian2, 0,3*16, 0,0,16,16);
imagecopy($image, $obsidian3, 0,4*16, 0,0,16,16);
imagecopy($image, $obsidian3, 0,5*16, 0,0,16,16);
imagecopy($image, $obsidian2, 0,6*16, 0,0,16,16);
imagecopy($image, $obsidian2, 0,7*16, 0,0,16,16);

// gold frame
for($y=0; $y < 8; $y++)
	imagecopy($image, $frame, 0,$y*16, 0,0,16,16);

// halos
imagecopy($image, $halo3, 0,0*16, 0,0,16,16);
imagecopy($image, $halo2, 0,1*16, 0,0,16,16);
imagecopy($image, $halo1, 0,2*16, 0,0,16,16);
imagecopy($image, $halo2, 0,3*16, 0,0,16,16);
imagecopy($image, $halo3, 0,4*16, 0,0,16,16);
imagecopy($image, $halo2, 0,5*16, 0,0,16,16);
imagecopy($image, $halo1, 0,6*16, 0,0,16,16);
imagecopy($image, $halo2, 0,7*16, 0,0,16,16);

// eyes
for($y=0; $y < 8; $y++)
	imagecopy($image, $eye10, 0,$y*16, 0,0,16,16);

//////////

$frametime = 6;

imagepng($image, $path.'enderCube.png');
imagedestroy($image);
file_put_contents($path.'enderCube.png.mcmeta', "{\n  \"animation\": {\n    \"frametime\": $frametime\n  }\n}");
$txt = ""; for($y=0; $y < 8; $y++) $txt .= "$y*$frametime\n";
file_put_contents($path.'enderCube.txt', $txt);

?>
