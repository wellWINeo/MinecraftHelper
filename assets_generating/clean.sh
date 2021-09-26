#!/usr/bin/bash

IGNORE_ITEMS_FILE="ignores.txt"
JSON_FILE="my.json"
OUTPUT_FILE="output.txt"
json="$(cat $JSON_FILE)"

cat $IGNORE_ITEMS_FILE | uniq > $IGNORE_ITEMS_FILE

for line in $(cat $IGNORE_ITEMS_FILE); do
	echo $line
	json=$(echo "$json" | grep -v $line)
done

echo $json > $OUTPUT_FILE
