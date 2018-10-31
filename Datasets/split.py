import json

with open('Transformed/BPL2017-2018.json') as jsonFile:
	jsonArray = json.load(jsonFile)
	jsonFile.close()

for item in jsonArray:
	fileName = 'Refined/Game-' + item['Date'] + '-' + item['Home Team'] + '-' + item['Away Team'] + '.json'
	with open(fileName, 'w') as f:
		json.dump(item, f)
