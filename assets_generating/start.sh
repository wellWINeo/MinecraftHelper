#!/bin/sh

# generating recipes and writing it to `json` file
# missed items writing to stderr
./recipes_generate.py > recipes.json 2> ignores.txt

# removing from json recipes with missed items
./clean.sh
