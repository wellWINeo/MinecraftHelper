#!/usr/bin/env python

import requests
import re
import json
from bs4 import BeautifulSoup as bs
from os.path import exists
from sys import stderr

# global variables
pattern = re.compile(r'(?<!^)(?=[A-Z])')
result = dict()
URLS= ("https://minecraft.fandom.com/wiki/Crafting/Building_blocks",
       "https://minecraft.fandom.com/wiki/Crafting/Decoration_blocks",
       "https://minecraft.fandom.com/wiki/Crafting/Redstone",
       "https://minecraft.fandom.com/wiki/Crafting/Transportation",
       "https://minecraft.fandom.com/wiki/Crafting/Foodstuffs",
       "https://minecraft.fandom.com/wiki/Crafting/Tools",
       "https://minecraft.fandom.com/wiki/Crafting/Combat",
       "https://minecraft.fandom.com/wiki/Crafting/Brewing",
       "https://minecraft.fandom.com/wiki/Crafting/Materials",
       "https://minecraft.fandom.com/wiki/Crafting/Miscellaneous"
       )
ASSETS="/home/o__ni/code/study/android/MinecraftHelper/app/src/main/res/drawable-v24/"

rename = {
            "nether_quartz": "quartz",
            "blockof_quartz": "quartz_bricks",
            "bone_block": "bone_block_top",
            "heartofthe_sea": "heart_of_the_sea",
            "dried_kelp_block": "dried_kelp_side",
            "oak_wood": "oak_log",
            "stripped_oak_wood": "stripped_oak_log",
            "blockof_emerald": "emerald_block",
            "blockof_coal": "coal_block",
            "blockof_netherite": "netherite_block",
            "blockof_gold": "gold_block",
            "waxed_cut_copper": "cut_copper",
            "snow_block": "snow",
            "blockof_lapis_lazuli": "lapis_block",
            "melon": "melon_top",
            "vines": "cave_vines",
            "blockof_iron": "iron_block",
            "blockof_amethyst": "amethyst_block",
            "scaffolding": "scaffolding_top",
            "blockof_copper": "copper_block",
            "waxed_blockof_copper": "copper_block",
            "blockof_raw_gold": "raw_gold_block",
            "blockof_raw_copper": "raw_copper_block",
            "blockof_raw_iron": "raw_iron_block",
            "blockof_diamond": "diamond_block",
            "chest": "chest_minecraft",
            "slimeball": "slime_ball",
            "blue_torch": "soul_torch",
            "barrel": "barrel_side",
            "glass_pane": "glass_pane_top",
            "white_stained_glass_pane": "white_stained_glass_pane_top",
            "furnace": "furnace_top",
            "honey_block": "honey_block_side",
            "lodestone": "lodestone_side",
            "beehive": "beehive_front_honey",
            "jukebox": "jukebox_side",
            "blast_furnace": "blast_furnace_fron",
            "enchanting_table": "enchanting_table_side",
            "grindstone": "grindstone_side",
            "respawn_anchor": "respawn_anchor_side1",
            "fletching_table": "fletching_table_side",
            "cartography_table": "cartography_table_side2",
            "stonecutter": "stonecutter_side",
            "smoker": "smoker_side",
            "loom": "loom_side",
            "smithing_table": "smithing_table_side",
            "crafting_table": "crafting_table_side",
            "eyeof_ender": "ender_eye",
            "redstone_dust": "redstone_dust_dot",
            "daylight_detector": "daylight_detector_top",
            "piston": "piston_side",
            "sticky_piston": "piston_top_sticky",
            "blockof_redstone": "redstone",
            "lectern": "lectern_front",
            "redstone_comparator": "comparator_on",
            "target": "target_top",
            "observer": "observer_front",
            "repeater": "repeater_on",
            "dispenser": "dispenser_fron",
            "carrotona_stick": "carrot_on_a_stick",
            "t_n_t": "tnt_side",
            "minecartwith_t_n_t": "tnt_minecart",
            "minecartwith_hopper": "hopper_minecart",
            "minecartwith_chest": "chest_minecart",
            "minecartwith_furnace": "furnace_minecart",
            "warped_fungusona_stick": "warped_fungus_on_a_stick",
            "flintand_steel": "flint_and_steel",
            "clock": "clock_00",
            "compass": "compass_00",
            "crossbow": "crossbow_arrow",
            "leather_pants": "leather_leggings_overlay",
            "leather_cap": "leather_helmet_overlay",
            "leather_tunic": "leather_chestplate_overlay",
            "pumpkin": "pumpkin_top",
            "lilyofthe_valley": "lily_of_the_valley",
            "rose_bush": "rose_bush_top",
            "peony": "peony_top",
            "lilac": "lilac_top",
            "empty_map": "map",
            "composter": "composter_side",
            "hay_bale": "hay_block_side"
        }

def convert_name(name):
    # idk, i'm stupid
    name = pattern.sub('_', name.replace(' ', '')).lower()
    if not exists(ASSETS + name + ".png"):
        try:
            name = rename[name]
        except:
            stderr.write(name + "\n") 
            #stderr.write("not exist: " + name + "\n") 
    return name

def parse_cell(cell):
    try:
        item = cell.span.a
        return item["title"]
    except:
        return "air"

def parse_target(block):
    item = block.div.span.find("span", class_="mcui-output").span.span.a
    title = convert_name(item["title"])
    return title 


def parse_block(elem):
    string = ""
    span = elem.div.span.find("span", class_="mcui-input")
    rows = span.find_all("span", class_="mcui-row")
    for row in rows:
        cells = row.find_all("span", class_="invslot")
        for cell in cells:
            value = parse_cell(cell)
            value = convert_name(value)
            string += value + '+'
    return string[:-1]

def parse_table(url):
    resp = requests.get(url)
    soup = bs(resp.text, 'html.parser')

    table = soup.find("table", class_="wikitable sortable collapsible")
    rows = table.find_all("td", style="padding:1px;text-align:center")

    for i in range(1, len(rows) - 1):
        key = parse_block(rows[i])
        value = parse_target(rows[i])
        result.update({key: value})

if __name__ == "__main__":
    for url in URLS:
        parse_table(url)
    
    result_json = json.dumps(result, indent=4, sort_keys=True)
    print(result_json)
