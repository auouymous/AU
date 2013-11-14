#!/bin/sh

VERSIONS="1.4.7 1.5.2 1.6.4"

[ -z "$1" ] && echo "$0 <field name>" && exit

echo
for v in $VERSIONS; do
	echo -e "\033[30;43;2m $v \033[0m"
	(cd ~/forge-$v/fml/conf ; search $1)
	(cd ~/forge-$v/mcp/temp ; search $1)
done
echo
