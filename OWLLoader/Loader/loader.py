import json
import glob
import ntpath
from Loader import OWLBuilder as builder
from owlready2 import *

players = set()
teams = set()
lineup = {}


def main():
    for fileName in glob.glob('../Description/*.json'):
        with open(fileName) as matchjson:
            # Load Game Files to a Json
            jsonFileName = ntpath.basename(fileName)
            match = json.load(matchjson)
            # Create Set of all the Teams in the game files
            # print(match['Away Team'])
            # print(match['Home Team'])

            if match['Home Team'] not in teams:
                teams.add(match['Home Team'])

            if match['Away Team'] not in teams:
                teams.add(match['Away Team'])

            # Create Set of All the Player of the games
            lineup = match['Lineup']
            # print(lineup)
            # Cycle through away Team
            for player in lineup:
                if re.search('Player', player) is not None:
                    if lineup[player] not in players:
                        players.add(lineup[player])

    # print(teams)
    # print(players)

    for team in teams:
        builder.add_team(team)

    for player in players:
        builder.add_player(player)

    # Add Exhibition of Players
    for fileName in glob.glob('../Description/*.json'):
        with open(fileName) as matchjson:

            match = json.load(matchjson)
            lineup = match['Lineup']
            exhibitions = []
            for player in lineup:
                if re.search('Home Player', player) is not None:
                    exhibition = builder.add_exhibition(match['Home Team'], lineup[player], match['Date'])
                    exhibitions.append(exhibition)
                elif re.search('Away Player', player) is not None:
                    exhibition = builder.add_exhibition(match['Away Team'], lineup[player], match['Date'])
                    exhibitions.append(exhibition)

                else:
                    pass

            m = builder.add_match(match['Home Team'], match['Away Team'], exhibitions, match['Date'])
            for match_comment in match["Commentary"]:
                builder.add_event(match_comment)
                # print(match_comment)


# builder.add_exhibition(match)
    builder.save('ontology.xml')
#     print(match)
if __name__ == '__main__':
    main()
