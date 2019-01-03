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


    class Substitution(Event):
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


    class hasReplacedPlayer(Substitution >> Player):
        pass


    class wasPerformedByTeam(Substitution >> Team):
        pass


    class hasEnteringPlayer(Substitution >> Player):
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
# print('id ' + event[0])
# print('comment ' + event[1])
# print('match_time ' + event[2])
# print('team_one_score ' + event[3])
# print('team_two_socre ' + event[4])
# print('half_end ' + event[5])
# print('match_end ' + event[6])
# print('half_begins ' + event[7])
# print('shot_attempt ' + event[8])
# print('penalty_shot ' + event[9])
# print('shot_result' + event[10])
# print('shot_by_player' + event[11])
# print('shot_by_team ' + event[12])
# print('shot_with ' + event[13])
# print('shot_where ' + event[14])
# print('net_location ' + event[15])
# print('assist_by_player ' + event[16])
# print('foul ' + event[17])
# print('foul_by_player ' + event[18])
# print('foul_by_team ' + event[19])
# print('follow_set_piece ' + event[20])
# print('assist_type ' + event[21])
# print('follow_corner ' + event[22])
# print('offside' + event[23])
# print('offside_team ' + event[24])
# print('offside_player ' + event[25])
# print('offside_pass_from ' + event[26])
# print('shown_card ' + event[27])
# print('casrd_type ' + event[28])
# print('card_player ' + event[29])
# print('card_team ' + event[30])
# print('video_review ' + event[31])
# print('video_review_event ' + event[32])
# print('video_review_result ' + event[33])
# print('delay_in_match ' + event[34])
# print('delay_team ' + event[35])
# print('free_kick_won ' + event[36])
# print(' free_kick_player ' + event[37])
# print(' free_kick_team ' + event[38])
# print(' free_kick_where ' + event[39])
# print('corner ' + event[40])
# print('corner_team  ' + event[41])
# print('corner_conceded_by' + event[42])
# print('substitution  ' + event[43])
# print('sub_injury ' + event[44])
# print('sub_team ' + event[45])
# print('sub_player ' + event[46])
# print('replaced_player ' + event[47])
# print('penalty ' + event[48])
# print('team_drew_penalty ' + event[49])
# print('player_drew_penalty ' + event[50])
# print('player_conceded_penalty ' + event[51])
# print('team_conceded_penalty ' + event[52])
# print('half ' + event[53])
# print('comment_id ' + event[54])
# # # this dont work why
# print('stoppage_time ' + event[55])
# print('team_one_penalty_score ' + event[56])
# print('team_two_penalty_score ' + event[57])
# print('match_time_numeric ' + event[58])

