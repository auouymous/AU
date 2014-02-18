#!/bin/sh

cd xpms

TEXTURES=../$1/textures
rm -rf $TEXTURES
mkdir -p $TEXTURES/blocks $TEXTURES/gui

# gui
for g in gui/*.xpm; do
	convert -background transparent ${g} $TEXTURES/${g%.xpm}.png
done

# blocks
for b in blocks/*.xpm; do
	convert -background transparent ${b} $TEXTURES/${b%.xpm}.png
done
