#!/usr/bin/env python

import json
import os

# config
DIR="/home/o__ni/code/study/android/MinecraftHelper/app/src/main/res/drawable-v24"

def pretty_name(tag):
    return ' '.join(word.title() for word in tag.split('_'))

def create_item(name):
    name = name.rsplit('.', 1)[0]
    return {"name": pretty_name(name), "tag": name}


def main():
    files = [i for i in os.listdir(DIR) if i.endswith(".png")]
    items = list(map(create_item, files))

    result = json.dumps(items, indent=4, sort_keys=True)
    print(result)

if __name__ == "__main__":
    main()
