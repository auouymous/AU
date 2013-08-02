#!/usr/bin/php -q
<?php

$filename = $argv[1];
$width = $argv[2];
$height = $argv[3];
if($filename == "" || $width < 10 || $height < 10){
	echo "Syntax: $argv[0] [filename] [width] [height]\n";
	exit();
}

$fp = fopen($filename, "w");
if($fp){
	$a = $b = $c = $w = '';
	for($i = 0; $i < $width-4; $i++){
		$a .= '=';
		$b .= '-';
	}
	for($i = 0; $i < 256-$width; $i++) $w .= ' ';

	$h = '"';
	for($i = 0; $i < 256; $i++) $h .= ' ';
	$h .= '",'."\n";

	fwrite($fp, '/* XPM */'."\n");
	fwrite($fp, 'static char *icon[] = {'."\n");
	fwrite($fp, '"256 256 5 1",'."\n");
	fwrite($fp, '"  c None",'."\n");
	fwrite($fp, '". c #444444",'."\n");
	fwrite($fp, '"= c #747474",'."\n");
	fwrite($fp, '"- c #8f8f8f",'."\n");
	fwrite($fp, '"# c #0ee318",'."\n");
	fwrite($fp, '".#'.$a.'#.'.$w.'",'."\n");
	fwrite($fp, '"#-'.$b.'-#'.$w.'",'."\n");
	for($i = 0; $i < $height-4; $i++)
		fwrite($fp, '"=-'.$b.'-='.$w.'",'."\n");
	fwrite($fp, '"#-'.$b.'-#'.$w.'",'."\n");
	fwrite($fp, '".#'.$a.'#.'.$w.'",'."\n");
	for($i = 0; $i < 256-$height; $i++)
		fwrite($fp, $h);
	fwrite($fp, '};'."\n");

	fclose($fp);
}

?>
