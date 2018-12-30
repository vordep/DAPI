from Loader.Ontology import *


def add_team(team):
    team = "_".join(team.split())
    # print(team)
    return Team(team, namespace=ontology)


def add_player(player):
    player = "_".join(player.split())
    # print(player)
    return Player(player, namespace=ontology)

def save(file_name):
    ontology.save(file=file_name, format='rdfxml')

#TODO Test this code
def add_exhibition(team, player, date):
    team = "_".join(team.split())
    player = "_".join(player.split())
    # print('team :' +team)
    # print('player:'+player)
    # print('date:'+date)
    title = player + '_' + date
    # print(title)
    # new exhibition
    player = add_player(player)
    team = add_team(team)
    # print (team)
    exhibition = Exhibition(title,namespace=ontology)
    exhibition.hasTeam = team
    exhibition.hasPlayer = player
    # for i in Player.instances():
    #     print(i)
    return exhibition

def add_match(home,away,exhibitions,date):
    home = "_".join(home.split())
    away = "_".join(away.split())

    title = date + '_' + home + '_' + away
    team1 = add_team(home)
    team2 = add_team(away)

    match = Match(title, namespace=ontology)
    match.hasHomeTeam = team1
    match.hasAwayTeam = team2
    match.hasExhibition = exhibitions
    match.hasDate = [date]

    print(match)
