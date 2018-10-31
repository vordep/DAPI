import json
import glob
import ntpath

for fileName in glob.glob('../Datasets/Refined/*.json'):
	with open(fileName) as statsJson:
		jsonFileName = ntpath.basename(fileName)
		stats = json.load(statsJson)
		with open('../Commentary Datasets/Refined/' + jsonFileName) as commentaryJson:
			commentary = json.load(commentaryJson)
			commentaryJson.close()
		with open('../LineUpsDataset/Refined/' + jsonFileName) as lineupJson:
			lineup = json.load(lineupJson)
			lineupJson.close()
		game = {}
		game['Date'] = stats['Date']
		game['Home Team'] = stats['Home Team']
		game['Away Team'] = stats['Away Team']
		stats.pop('Date')
		stats.pop('Home Team')
		stats.pop('Away Team')
		for item in commentary:
			item.pop('Date')
			item.pop('Team One')
			item.pop('Team Two')
		lineup.pop('Date')
		lineup.pop('Team 1 Name')
		lineup.pop('Team 2 Name')
		game['Stats'] = stats
		game['Commentary'] = commentary
		game['Lineup'] = lineup
		with open('Final/' + jsonFileName, 'w') as mergedFile:
			json.dump(game, mergedFile)
		statsJson.close()
