from owlready2 import *


# TODO
# Change path for the script to work
# onto_path.append('file:///home/vordep/Desktop/DAPI/Ontology/ontology.owl').load()
# print(onto_path)
def load_owl(path):
    print(path)

    onto_path.append('file:///home/vordep/Desktop/DAPI/Ontology/ontology.owl')
    print(onto_path)
    ontology = get_ontology('file:///home/vordep/Desktop/DAPI/Ontology/ontology.owl').load()
    onto = get_ontology("http://www.lesfleursdunormal.fr/static/_downloads/pizza_onto.owl").load()
    print(list(ontology.classes()))
    print(list(onto.classes()))

def add_teams_to_owl(teams):
    pass
