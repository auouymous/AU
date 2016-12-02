#!/bin/sh

source ../.xpm_to_png_options

cd xpms

TEXTURES=../$1/textures
rm -rf $TEXTURES
mkdir -p $TEXTURES/blocks $TEXTURES/items $TEXTURES/gui

# gui
for g in gui/*.xpm; do
	convert $XPM_TO_PNG_OPTIONS ${g} $TEXTURES/${g%.xpm}.png
done

# items
for i in items/*.xpm; do
	convert $XPM_TO_PNG_OPTIONS ${i} $TEXTURES/${i%.xpm}.png
done

# blocks
for b in blocks/*.xpm; do
	convert $XPM_TO_PNG_OPTIONS ${b} $TEXTURES/${b%.xpm}.png
done
php make-glass.php $TEXTURES/blocks/
php make-glass-tinted.php $TEXTURES/blocks/
php make-glass-tinted-noframe.php $TEXTURES/blocks/
php make-panes.php $TEXTURES/blocks/
php make-lamp.php $TEXTURES/blocks/
php make-enderCube.php $TEXTURES/blocks/

cp blocks/chromaInfuser_water.* $TEXTURES/blocks/
