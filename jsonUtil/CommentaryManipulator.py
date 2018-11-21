import json
import glob
import ntpath
import os
from pprint import pprint
import codecs

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
			
			if comment[-1].isnumeric():
				sentence = comment[2] + " at minute " + comment[-1] + ". "
				description = description + sentence


    # data.append({"Description":description})
    data["Description"] = description

    with codecs.open(fileName, 'w', encoding='utf-8') as GameJson:
        json.dump(data, GameJson, ensure_ascii=False)
    GameJson.close()
