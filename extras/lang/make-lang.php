#!/usr/bin/php -q
<?php

$lang = $argv[1];
if($lang == ""){
	echo "Syntax: $argv[0] [lang]\n";
	exit();
}

$color_in = @fopen(".$lang.lang-colors", "r");
$in = @fopen(".$lang.lang", "r");
$out = @fopen("$lang.lang", "w");
if($color_in && $in && $out){
	// read color file: unlocal_name,local_name
	$colors = array(); while(!@feof($color_in)) $colors[] = explode(",", trim(@fgets($color_in, 1024)));
	// read input file
	while(!@feof($in)){
		$line = @fgets($in, 1024);
		if(strstr($line, ".au.")){
			// found entry
			if(strstr($line, '${color}') || strstr($line, '${Color}'))
				for($c = 0; $c < 16; $c++)
					@fputs($out, str_replace('${color}', $colors[$c][0], str_replace('${Color}', $colors[$c][1], $line)));
			else if(strstr($line, '${value}') || strstr($line, '${valuepp}'))
				for($c = 0; $c < 16; $c++)
					@fputs($out, str_replace('${value}', $c, str_replace('${valuepp}', $c+1, $line)));
			else
				@fputs($out, $line);
		} else
			@fputs($out, $line);
	}

	@fclose($color_in);
	@fclose($in);
	@fclose($out);
} else {
	@fclose($out);
	@unlink("$lang.lang");
	echo "$lang is not a valid language file\n";
	exit();
}

?>
