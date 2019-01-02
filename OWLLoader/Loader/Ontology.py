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


    class Assist(Event):
        pass


    class Card(Event):
        pass


    class Delay(Event):
        pass


    class Offside(Event):
        pass


    class Substituition(Event):
        pass


    class VideoReview(Event):
        pass


    class Corner(Event):
        pass


    class Shot(Event):
        pass


    # Data Properties


    class hasDate(DataProperty):
        range = [str]


    class becausePlayerInjured(DataProperty):
        range = [bool]


    class hasAssistType(DataProperty):
        range = [str]


    class hasAwayTeamCorners(DataProperty):
        range = [int]


    class hasAwayTeamFouls(DataProperty):
        range = [int]


    class hasAwayTeamFullTimeGoals(DataProperty):
        range = [int]


    class hasAwayTeamHalfTimeGoals(DataProperty):
        range = [int]


    class hasAwayTeamRedCards(DataProperty):
        range = [int]


    class hasAwayTeamShots(DataProperty):
        range = [int]


    class hasAwayTeamShotsOnTarget(DataProperty):
        range = []


    class hasAwayTeamYellowCards(DataProperty):
        range = []


    class hasCardType(DataProperty):
        range = [str]


    class hasDelayEvent(DataProperty):
        range = [str]


    class hasDelayResult(DataProperty):
        range = [str]


    class hasDescription(DataProperty):
        range = [str]


    class hasEndMinute(DataProperty):
        range = [int]


    class hasFreeKickLocation(DataProperty):
        range = [str]


    class hasHomeTeamCorners(DataProperty):
        range = [int]


    class hasHomeTeamFouls(DataProperty):
        range = [int]


    class hasHomeTeamFullTimeGoals(DataProperty):
        range = [int]


    class hasHomeTeamHalfTimeGoals(DataProperty):
        range = [int]


    class hasHomeTeamRedCards(DataProperty):
        range = [int]


    class hasHomeTeamShots(DataProperty):
        range = [int]


    class hasHomeTeamShotsOnTarget(DataProperty):
        range = [int]


    class hasHomeTeamYellowCards(DataProperty):
        range = [int]


    class hasLocation(DataProperty):
        range = [str]


    class hasNetLocation(DataProperty):
        range = [str]


    class hasReferee(DataProperty):
        range = [str]


    class hasResult(DataProperty):
        range = [str]


    class hasStartMinute(DataProperty):
        range = [int]


    class hasVideoReviewEvent(DataProperty):
        range = [str]


    class hasVideoReviewResult(DataProperty):
        range = [str]


    class isPenaltyShot(DataProperty):
        range = [bool]


    class occuredAtMinute(DataProperty):
        range = [int]


    class wasFollowedByCorner(DataProperty):
        range = [bool]


    class wasFollowedBySetPiece(DataProperty):
        range = [bool]


    class wasFreeKickWon(DataProperty):
        range = [bool]


    class wasPenaltyKickWon(DataProperty):
        range = [bool]


    class withFoot(DataProperty):
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


    class hasEvent(Match >> Event):
        pass


    class hasAwayTeam(Match >> Team):
        pass


    class hasReplacedPlayer(Substituition >> Player):
        pass


    class wasPerformedByTeam(Substituition >> Team):
        pass


    class hasEnteringPlayer(Substituition >> Player):
        pass


    class followedByAssist(Shot >> Assist):
        pass


    class hasShootingPlayer(Shot >> Player):
        pass


    class hasShootingTeam(Shot >> Team):
        pass


    class wasFollowedByPassFrom(Offside >> Player):
        pass


    class wasFromTeam(Offside >> Team):
        pass


    class hasOffsidePlayer(Offside >> Player):
        pass


    class wasGivenToPlayer(Card >> Player):
        pass


    class wasGivenToTeam(Card >> Team):
        pass


    class wasWonByPlayer(Foul >> Player):
        pass


    class wasWonByTeam(Foul >> Team):
        pass


    class wasCommitedByPlayer(Foul >> Player):
        pass


    class wasCommitedByTeam(Foul >> Team):
        pass


    class WonByTeam(Corner >> Team):
        pass


    class concededByPlayer(Corner >> Player):
        pass


    class hasAssistingPlayer(Assist >> Player):
        pass
