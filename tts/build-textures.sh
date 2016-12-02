#!/bin/sh

source ../.xpm_to_png_options

cd xpms

TEXTURES=../$1/textures
rm -rf $TEXTURES
mkdir -p $TEXTURES/blocks $TEXTURES/gui

# gui
for g in gui/*.xpm; do
	convert $XPM_TO_PNG_OPTIONS ${g} $TEXTURES/${g%.xpm}.png
done

# blocks
for b in blocks/*.xpm; do
	convert $XPM_TO_PNG_OPTIONS ${b} $TEXTURES/${b%.xpm}.png
done
