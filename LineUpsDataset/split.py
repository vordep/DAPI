import json

with open('gamesLineupRefined.json') as jsonFile:
	jsonArray = json.load(jsonFile)
	jsonFile.close()

for item in jsonArray:
	fileName = 'Refined/Game-' + item['Date'] + '-' + item['Team 1 Name'] + '-' + item['Team 2 Name'] + '.json'
	with open(fileName, 'w') as f:
		json.dump(item, f)
