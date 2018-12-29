import json
import glob
import ntpath
import os
import codecs
import re
import rdflib
from Loader import OWLBuilder

players = set()
teams = set()
lineup = {}

for fileName in glob.glob('../Description/*.json'):
    with open(fileName) as statsJson:
        # Load Game Files to a Json
        jsonFileName = ntpath.basename(fileName)
        stats = json.load(statsJson)
        # Create Set of all the Teams in the game files
        # print(stats['Away Team'])
        # print(stats['Home Team'])

        if stats['Home Team'] not in teams:
            teams.add(stats['Home Team'])

        if stats['Away Team'] not in teams:
            teams.add(stats['Away Team'])

        # Create Set of All the Player of the games
        lineup = stats['Lineup']
        # print(lineup)
        # Cycle through away Team
        for player in lineup:
            if re.search('Player', player) is not None:
                if lineup[player] not in players:
                    players.add(lineup[player])

# print(teams)
# print(players)
owlpath = '../ontology/ontology.owl'
OWLBuilder.load_owl(owlpath)
