#!/usr/bin/bash

IGNORE_ITEMS_FILE="ignores_output.txt"
JSON_FILE="recipes.json"
OUTPUT_FILE="output.json"
json="$(cat $JSON_FILE)"

#cat $IGNORE_ITEMS_FILE | sort | uniq > $IGNORE_ITEMS_FILE

for line in $(cat $IGNORE_ITEMS_FILE); do
	echo $line
	json=$(echo "$json" | grep -v $line)
done

echo $json > $OUTPUT_FILE
