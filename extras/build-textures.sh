#!/bin/sh

cd xpms

rm -rf ../mods
TEXTURES=../mods/au_extras/textures
mkdir -p $TEXTURES/blocks $TEXTURES/items

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
php make-lamp.php $TEXTURES/blocks/
