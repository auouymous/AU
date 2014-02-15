#!/bin/sh

cd xpms

TEXTURES=../$1/textures
rm -rf $TEXTURES
mkdir -p $TEXTURES/blocks $TEXTURES/items $TEXTURES/gui

# gui
for g in gui/*.xpm; do
	convert -background transparent ${g} $TEXTURES/${g%.xpm}.png
done

# items
for i in items/*.xpm; do
	convert -background transparent ${i} $TEXTURES/${i%.xpm}.png
done

# blocks
for b in blocks/*.xpm; do
	convert -background transparent ${b} $TEXTURES/${b%.xpm}.png
done
php make-glass.php $TEXTURES/blocks/
php make-glass-tinted.php $TEXTURES/blocks/
php make-glass-tinted-noframe.php $TEXTURES/blocks/
php make-panes.php $TEXTURES/blocks/
php make-lamp.php $TEXTURES/blocks/
php make-enderCube.php $TEXTURES/blocks/

cp blocks/chromaInfuser_water.* $TEXTURES/blocks/
