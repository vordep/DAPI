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
    print(title)
    # new exhibition
    player = add_player(player)
    team = add_team(team)
    print (team)
    exhibition = Exhibition(title,namespace=ontology)
    exhibition.hasTeam = team
    exhibition.hasPlayer = player
    # for i in Player.instances():
    #     print(i)



