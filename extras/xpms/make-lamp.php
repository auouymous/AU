#!/usr/bin/php -q
<?php

$path = $argv[1];
if($path == ""){
	echo "Syntax: $argv[0] [path]\n";
	exit();
}

////////////////////

function get_color($image, $color, $alpha){
	$colors = "1e1b1b b3312c 3b511a 51301a 253192 7b2fbe 287697 ababab 434343 d88198 41cd34 decf2a 6689d3 c354cd eb8844 f0f0f0";
	$colors = explode(' ', $colors);
	$color = $colors[$color];
	if($alpha == 0)
		return imagecolorallocate($image, hexdec(substr($color, 0, 2)), hexdec(substr($color, 2, 2)), hexdec(substr($color, 4, 2)));
	else
		return imagecolorallocatealpha($image, hexdec(substr($color, 0, 2)), hexdec(substr($color, 2, 2)), hexdec(substr($color, 4, 2)), $alpha*127);
}

function drawCT($c, $ct, $t, $r, $b, $l, $tl, $tr, $br, $bl){
	global $path, $max_ctm;

	$image = imagecreatetruecolor(16, 16);
	imagealphablending($image, true);
	imagefill($image, 0,0, get_color($image, $c, 0.25)); // 25% transparent
	imagesavealpha($image, true);
	$color = get_color($image, $c, 0);
	$gray = imagecolorallocate($image, 68, 68, 68); // #444444

	// sides
	if($t) imageline($image, 1,0, 14,0, $color);
	if($r) imageline($image, 15,1, 15,14, $color);
	if($b) imageline($image, 1,15, 14,15, $color);
	if($l) imageline($image, 0,1, 0,14, $color);

	// corners
	if($tl || $t || $l) imagesetpixel($image, 0,0, $color);
	if($tr || $t || $r) imagesetpixel($image, 15,0, $color);
	if($br || $b || $r) imagesetpixel($image, 15,15, $color);
	if($bl || $b || $l) imagesetpixel($image, 0,15, $color);

	// gray corners
	if($t && $r) imagesetpixel($image, 15,0, $gray);
	if($b && $r) imagesetpixel($image, 15,15, $gray);
	if($b && $l) imagesetpixel($image, 0,15, $gray);
	if($t && $l) imagesetpixel($image, 0,0, $gray);

	// inner corners
	if($t && $r) imagesetpixel($image, 14,1, $color);
	if($b && $r) imagesetpixel($image, 14,14, $color);
	if($b && $l) imagesetpixel($image, 1,14, $color);
	if($t && $l) imagesetpixel($image, 1,1, $color);

	imagepng($image, $path.'colorLamp'.$c.'-'.$ct.'.png');
	imagedestroy($image);

	if($c == 0){
		$ctm = 0;
		if($t) $ctm |= 1<<0;
			else if($tl) $ctm |= 2<<0;
		if($r) $ctm |= 1<<2;
			else if($tr) $ctm |= 2<<2;
		if($b) $ctm |= 1<<4;
			else if($br) $ctm |= 2<<4;
		if($l) $ctm |= 1<<6;
			else if($bl) $ctm |= 2<<6;
		echo "$ctm, ";
		if($ctm > $max_ctm) $max_ctm = $ctm;
	}
}
$max_ctm = 0;

////////////////////

echo "\n";
for($c = 0; $c < 16; $c++){
	// corners are implicit when sides are rendered

	drawCT($c, 0,	1,1,1,1, 0,0,0,0); // TRBL
	drawCT($c, 1,	1,1,0,1, 0,0,0,0); // TR-L
	drawCT($c, 2,	1,1,1,0, 0,0,0,0); // TRB-
	drawCT($c, 3,	0,1,1,1, 0,0,0,0); // -RBL
	drawCT($c, 4,	1,0,1,1, 0,0,0,0); // T-BL
	drawCT($c, 5,	0,0,0,0, 0,0,0,0); // ----
	drawCT($c, 6,	1,0,0,0, 0,0,0,0); // T---
	drawCT($c, 7,	1,1,0,0, 0,0,0,0); // TR--
	drawCT($c, 8,	0,1,0,0, 0,0,0,0); // -R--
	drawCT($c, 9,	0,1,1,0, 0,0,0,0); // -RB-
	drawCT($c,10,	0,0,1,0, 0,0,0,0); // --B-
	drawCT($c,11,	0,0,1,1, 0,0,0,0); // --BL
	drawCT($c,12,	0,0,0,1, 0,0,0,0); // ---L
	drawCT($c,13,	1,0,0,1, 0,0,0,0); // T--L
	drawCT($c,14,	1,0,1,0, 0,0,0,0); // T-B-
	drawCT($c,15,	0,1,0,1, 0,0,0,0); // -R-L
	drawCT($c,16,	1,1,0,0, 0,0,0,1); // TR--		-- -- -- bl
	drawCT($c,17,	0,1,1,0, 1,0,0,0); // -RB-		tl -- -- --
	drawCT($c,18,	0,0,1,1, 0,1,0,0); // --BL		-- tr -- --
	drawCT($c,19,	1,0,0,1, 0,0,1,0); // T--L		-- -- br --
	drawCT($c,20,	1,0,0,0, 0,0,1,1); // T---		-- -- br bl
	drawCT($c,21,	1,0,0,0, 0,0,1,0); // T---		-- -- br --
	drawCT($c,22,	1,0,0,0, 0,0,0,1); // T---		-- -- -- bl
	drawCT($c,23,	0,1,0,0, 1,0,0,1); // -R--		tl--- -- bl
	drawCT($c,24,	0,1,0,0, 1,0,0,0); // -R--		tl -- -- --
	drawCT($c,25,	0,1,0,0, 0,0,0,1); // -R--		-- -- -- bl
	drawCT($c,26,	0,0,1,0, 1,1,0,0); // --B-		tl tr -- --
	drawCT($c,27,	0,0,1,0, 0,1,0,0); // --B-		-- tr -- --
	drawCT($c,28,	0,0,1,0, 1,0,0,0); // --B-		tl -- -- --
	drawCT($c,29,	0,0,0,1, 0,1,1,0); // ---L		-- tr br --
	drawCT($c,30,	0,0,0,1, 0,1,0,0); // ---L		-- tr -- --
	drawCT($c,31,	0,0,0,1, 0,0,1,0); // ---L		-- -- br --
	drawCT($c,32,	0,0,0,0, 1,1,1,1); // ----		tl tr br bl
	drawCT($c,33,	0,0,0,0, 1,1,1,0); // ----		tl tr br --
	drawCT($c,34,	0,0,0,0, 0,1,1,1); // ----		-- tr br bl
	drawCT($c,35,	0,0,0,0, 1,0,1,1); // ----		tl -- br bl
	drawCT($c,36,	0,0,0,0, 1,1,0,1); // ----		tl tr -- bl
	drawCT($c,37,	0,0,0,0, 1,1,0,0); // ----		tl tr -- --
	drawCT($c,38,	0,0,0,0, 0,1,1,0); // ----		-- tr br --
	drawCT($c,39,	0,0,0,0, 0,0,1,1); // ----		-- -- br bl
	drawCT($c,40,	0,0,0,0, 1,0,0,1); // ----		tl -- -- bl
	drawCT($c,41,	0,0,0,0, 1,0,1,0); // ----		tl -- br --
	drawCT($c,42,	0,0,0,0, 0,1,0,1); // ----		-- tr -- bl
	drawCT($c,43,	0,0,0,0, 1,0,0,0); // ----		tl -- -- --
	drawCT($c,44,	0,0,0,0, 0,1,0,0); // ----		-- tr -- --
	drawCT($c,45,	0,0,0,0, 0,0,1,0); // ----		-- -- br --
	drawCT($c,46,	0,0,0,0, 0,0,0,1); // ----		-- -- -- bl
}

echo "\nctm_icons = ".($max_ctm+1)."\n";

?>
