#!/bin/sh

SRC=$1
shift
VER=$1

if [ -f "./.only_with.sh" ]; then
	ONLY_WITH=`./.only_with.sh $VER`
fi

if [ ! -z "$ONLY_WITH" ]; then
	for f in `ls -1 $SRC`; do
		name=${f%.java}
		[ -z "`echo "$ONLY_WITH"|grep -E "(^| )$name( |$)"`" ] && rm $SRC/$f
	done
fi

echo "ONLY WITH: $ONLY_WITH"
