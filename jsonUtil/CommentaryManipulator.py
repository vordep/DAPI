import json
import glob
import ntpath
import os
from pprint import pprint

# with open('/home/vordep/Desktop/DAPI/MergedDataset/Final/Full/1.json') as f:
#     data = json.load(f)
#
# #Concatenate Description
# description = ""
# for comment in data["Commentary"]:
#     sentence = "{0} at minute {1} ".format(comment["Commentary"], comment["Match Time Numeric"])
#     description = description + sentence
#
# # data.append({"Description":description})
# data["Description"] = description
#
# teste = json.dumps(data)
#
# print(teste)

# add Description to a

for fileName in glob.glob('../MergedDataset/Final/Description/*.json'):
    with open(fileName) as GameJson:
        data = json.load(GameJson)
        description = ""
        for comment in data["Commentary"]:
            sentence = "{0} at minute {1} ".format(comment["Commentary"], comment["Match Time Numeric"])
            description = description + sentence

    # data.append({"Description":description})
    data["Description"] = description

    with open(fileName, 'w') as GameJson:
        json.dump(data, GameJson)
        print(data)
    GameJson.close()
