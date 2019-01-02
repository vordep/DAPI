from builtins import print

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


# TODO Test this code
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
    exhibition = Exhibition(title, namespace=ontology)
    exhibition.hasTeam = team
    exhibition.hasPlayer = player
    # for i in Player.instances():
    #     print(i)
    return exhibition


def add_match(home, away, exhibitions, events,stats, date):
    home = "_".join(home.split())
    away = "_".join(away.split())

    title = date + '_' + home + '_' + away
    team1 = add_team(home)
    team2 = add_team(away)

    match = Match(title, namespace=ontology)
    match.hasHomeTeam = team1
    match.hasAwayTeam = team2
    match.hasExhibition = exhibitions
    match.hasDate = date
    match.hasEvent = events

    # Data Properties
    match.hasAwayTeamCorners = stats['Away Team Corners']
    match.hasAwayTeamFouls = stats['Away Team Fouls Committed']
    match.hasAwayTeamFullTimeGoals = stats['Full Time Away Team Goals']
    match.hasAwayTeamHalfTimeGoals = stats['Half Time Away Team Goals']
    match.hasAwayTeamRedCards = stats['Away Team Red Cards']
    match.hasAwayTeamShots =  stats['Away Team Shots']
    match.hasAwayTeamShotsOnTarget = stats['Away Team Shots on Target']
    match.hasAwayTeamYellowCards = stats ['Away Team Yellow Cards']

    match.hasHomeTeamCorners = stats['Home Team Corners']
    match.hasHomeTeamFouls = stats['Home Team Fouls Committed']
    match.hasHomeTeamFullTimeGoals = stats['Full Time Home Team Goals']
    match.hasHomeTeamHalfTimeGoals = stats['Half Time Home Team Goals']
    match.hasHomeTeamRedCards = stats['Home Team Red Cards']
    match.hasHomeTeamShots =  stats['Home Team Shots']
    match.hasHomeTeamShotsOnTarget = stats['Home Team Shots on Target']
    match.hasHomeTeamYellowCards = stats ['Home Team Yellow Cards']


def add_event(event, home, away, date):
    home = "_".join(home.split())
    away = "_".join(away.split())
    match_name = date + '_' + home + '_' + away
    match_clone = Match(match_name, namespace=ontology)
    minute = event[2]
    minute.replace("'", "-").split("-")
    title = 'event' + '_' + event[58] + '_' + home + '_' + away + '_' + date

    if int(event[17]) == 1:
        title = 'foul_' + title
        new_event = Foul(title, namespace=ontology)

    elif int(event[8]) == 1:
        print('shot attempt')
        title = 'shot_' + title
        new_event = Shot(title, namespace=ontology)
        penalty = bool(0)
        if int(event[9]) == 1:
            penalty = bool(1)

        player = add_player(event[11])
        team = add_team(event[12])

        new_event.hasShootingPlayer = player
        new_event.hasShootingTeam = team
        new_event.isPenaltyShot = penalty
        new_event.hasLocation = event[14]
        new_event.hasNetLocation = event[15]
        new_event.hasResult = event[10]
        new_event.withFoot = event[13]

        if event[16] != 'NA':
            title = 'assist_' + title
            assist = Assist(title, namespace=ontology)
            player = add_player(event[16])
            assist.hasAssistingPlayer = player
            assist.hasAssistType = event[21]
            assist.occuredAtMinute = event[58]
            new_event.followedByAssist = assist

    # print('offside'+event[23])
    elif int(event[23]) == 1:
        title = 'offside_' + title
        new_event = Offside(title, namespace=ontology)
        team = add_team(event[24])
        player1 = add_player(event[25])
        player2 = add_player(event[26])
        new_event.wasFromTeam = team
        new_event.hasOffsidePlayer = player1
        new_event.wasFollowedByPassFrom = player2


    # print('shown_card '+event[27])
    elif int(event[27]) == 1:
        print('card')
        title = 'card_' + title
        new_event = Card(title, namespace=ontology)
        team = add_team(event[30])
        player = add_player(event[29])

        new_event.wasGivenToPlayer = player
        new_event.wasGivenToTeam = team
        new_event.hasCardType = event[28]

    elif int(event[31]) == 1:

        print('video_review')
        title = 'video_review_' + title
        new_event = VideoReview(title, namespace=ontology)
        new_event.hasVideoReviewEvent = event[32]
        new_event.hasVideoReviewResult = event[33]

    # print('delay_in_match ' + event[34])
    elif int(event[34]) == 1:
        title = 'delay_in_match' + title
        new_event = Delay(title, namespace=ontology)
        team = add_team(event[35])
        print('delay_in_match '+event[34])
        print('delay_team '+event[35])
        new_event.causedByTeam = team

    # print('corner ' + event[37])
    elif event[37] != 'NA':
        print('corner')
        title = 'corner' + title
        new_event = Corner(title, namespace=ontology)
        team = add_team(event[38])
        player = add_player(event[39])
        new_event.wonByTeam = team
        new_event.concededByPlayer = player

    elif int(event[40]) == 1:
        print('substitution')
        title = 'substitution' + title
        team = add_team(event[45])
        player1 = add_player(event[46])
        player2 = add_player(event[47])
        injury = bool(0)
        if int(event[44]) == 1:
            injury = bool(1)

        new_event = Substituition(title, namespace=ontology)
        new_event.wasPerformedByTeam = team
        new_event.hasEnteringPlayer = player1
        new_event.hasReplacedPlayer = player2
        new_event.becausePlayerInjured = injury

    else:
        new_event = Event(title, namespace=ontology)

    new_event.occuredAtMinute = event[58]

    new_event.wasFollowedByConer = event[22]

    new_event.wasFollowedBySetPiece = event[20]
    new_event.hasDescription = event[1]

    return  new_event
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
# print('corner ' + event[37])
# print('corner_team ' + event[38])
# print('corner_conceded_by ' + event[39])
# print('substitution ' + event[40])
# print('free_kick_player ' + event[41])
# print('free_kick_team ' + event[42])
# print('free_kick_where ' + event[43])
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
