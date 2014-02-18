#!/bin/sh

APIJAR=$1

#TTS_MODS="cmu_us_kal.jar cmulex.jar en_us.jar freetts-jsapi10.jar freetts.jar mbrola.jar"
TTS_MODS="cmu_us_kal.jar cmulex.jar en_us.jar freetts.jar"

# remove freetts jars from forge
for j in $TTS_MODS; do
	rm -f $APIJAR/$j
done

# install freetts jars in forge
for j in $TTS_MODS; do
	cp -f freetts/$j $APIJAR
done

# extract freetss jars
cd freetts/extracted
for j in $TTS_MODS; do
	jar xvf ../$j
done

