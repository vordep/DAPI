from owlready2 import *

path = '/home/vordep/Desktop/DAPI/OWLLoader/ontology/'
file = 'ontology.owl'

try:
    ontology = get_ontology('file://' + path + file).load()
except FileNotFoundError:
    print('Couldnt find ontology')

with ontology:
    # Classes


    class Team(Thing):
        pass


    class Player(Thing):
        pass


    class Match(Thing):
        pass


    class Exhibition(Thing):
        pass


    class Event(Thing):
        pass


    class Foul(Event):
        pass


    class Goal(Event):
        pass


    class Shot(Event):
        pass


    # Data Properties


    class hasDate(DataProperty):
        range = [str]


    # Object Properties


    class hasAwayTeam(ObjectProperty):
        domain = [Match]
        range = [Team]


    class hasHomeTeam(ObjectProperty):
        domain = [Match]
        range = [Team]


    class hasExhibition(ObjectProperty):
        domain = [Match]
        range = [Exhibition]


    class hasPlayer(ObjectProperty):
        domain = [Exhibition]
        range = [Player]


    class hasTeam(ObjectProperty):
        domain = [Exhibition]
        range = [Team]



