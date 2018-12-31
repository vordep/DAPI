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

def add_event(event,home,away,date):
        home = "_".join(home.split())
        away = "_".join(away.split())
        minute = event[2]
        print(minute.replace("'", "-").split("-"))
        title = 'event '+ '_' + home + '_' + away + '_' + date

        # print('id '+event[0])
        # print('comment '+event[1])
        # print('match_time '+event[2])
        # print('team_one_score '+event[3])
        # print('team_two_socre '+event[4])
        # print('half_end '+event[5])
        # print('match_end '+event[6])
        # print('half_begins '+event[7])
        print('shot_attempt '+event[8])
        # print('penalty_shot '+event[9])
        # print('shot_result'+event[10])
        # print('shot_by_player'+event[11])
        # print('shot_by_team '+event[12])
        # print('shot_with '+event[13])
        # print('shot_where '+event[14])
        # print('net_location '+event[15])
        # print('assist_by_player '+event[16])
        # print('foul '+event[17])
        # print('foul_by_player '+event[18])
        # print('foul_by_team '+event[19])
        # print('follow_set_piece '+event[20])
        # print('assist_type '+event[21])
        # print('follow_corner '+event[22])
        # print('offside'+event[23])
        # print('offside_team '+event[24])
        # print('offside_player '+event[25])
        # print('offside_pass_from '+event[26])
        # print('shown_card '+event[27])
        # print('card_type '+event[28])
        # print('card_player '+event[29])
        # print('card_team '+event[30])
        # print('video_review '+event[31])
        # print('video_review_event '+event[32])
        # print('video_review_result '+event[33])
        # print('delay_in_match '+event[34])
        # print('delay_team '+event[35])
        # print('free_kick_won '+event[36])
        # print('corner '+event[37])
        # print('corner_team '+event[38])
        # print('corner_conceded_by '+event[39])
        # print('substitution '+event[40])
        # print('free_kick_player '+event[41])
        # print('free_kick_team '+event[42])
        # print('free_kick_where '+event[43])
        # print('sub_injury '+event[48])
        # print('sub_team '+event[49])
        # print('sub_player '+event[50])
        # print('replaced_player '+event[51])
        # print('penalty '+event[52])
        # print('team_drew_penalty '+event[53])
        # print('player_drew_penalty '+event[54])
        # print('player_conceded_penalty '+event[55])
        # print('team_conceded_penalty '+event[56])
        # print('half '+event[57])
        # print('comment_id '+event[58])
        # # this dont work why
        # print('stoppage_time '+event[59])
        # print('team_one_penalty_score '+event[60])
        # print('team_two_penalty_score '+event[61])
        # print('match_time_numeric '+event[62])
        if int(event[17]) == 1 :
            print('foul')
            foul = Foul(title,namespace=ontology)
