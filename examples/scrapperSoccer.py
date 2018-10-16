from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from bs4 import BeautifulSoup

import re
import datetime, time, os

import pandas as pd
from selenium.webdriver.common.actions.action_builder import ActionBuilder

run_time = time.time()

# DataFrames for Tables

games = pd.DataFrame(columns=['id_game', 'team_1_name', 'team_2_name', 'game_date'])
auxgames = pd.DataFrame(columns=['id_game', 'team_1_name', 'team_2_name', 'game_date'])
home_team_dataFrame = pd.DataFrame(
    columns=['home_player_1', 'home_player_2', 'home_player_3', 'home_player_4', 'home_player_5', 'home_player_6',
             'home_player_7', 'home_player_8', 'home_player_9', 'home_player_10', 'home_player_11'])
away_team_dataFrame = pd.DataFrame(
    columns=['away_player_1', 'away_player_2', 'away_player_3', 'away_player_4', 'away_player_5', 'away_player_6',
             'away_player_7', 'away_player_8', 'away_player_9', 'away_player_10', 'away_player_11'])
# URL where to find the data to be scrapped

url = "file:///home/vordep/PycharmProjects/untitled/htmlToParse/results1617.html"

url_lineup = "/#lineups;1"
base_url = "https://www.flashscore.com/match/"

# Setup of Driver

options = webdriver.ChromeOptions()
# options.add_argument('--ignore-ssl-errors')
# options.add_argument('disable-dev-shm-usage')
# options.add_argument("--headless")
# options.add_argument('no-sandbox')

# CheckFiles
f_game_lineup = 'gamesLineup.csv'

if os.path.exists(f_game_lineup):
    matches = pd.read_csv(f_game_lineup, index_col=0, sep=';')

# Open Driver
driver = webdriver.Chrome(chrome_options=options)
# driver.implicitly_wait(40)
driver.get(url)

# Get Round Info

# soup = BeautifulSoup(driver.page_source,"html.parser").find_all('tr',{'class': re.compile('event_round')})

# So gravamos o ficheiro do site em html para conseguir-mos retirar todas as equipas.
# causa : javascript

# Processo : Retirar Matches IDs

# Extrair Tabela do Html

soup = BeautifulSoup(driver.page_source, "html.parser").find_all('tr', {'id': re.compile('g_1_*')})
tableRowSize = len(soup)

# Tratar Tabela
for tr in soup:
    game_id = tr.get('id')
    game_date = tr.find('td', {'class': re.compile('time')}).getText()
    game_home_team = tr.find('td', {'class': re.compile('team-home')}).getText()
    game_away_team = tr.find('td', {'class': re.compile('team-away')}).getText()

    # print(game_id, game_date, game_home_team, game_away_team)

    # Add games to Frame

    games.loc[len(games)] = [game_id, game_home_team, game_away_team, game_date]
    # Delete after debug purpose

# games = auxgames.iloc[265:, :]
print(len(games))
while True:
    i=+1



for game_index, game_row in games.iterrows():
    def build_url(id):
        # trim id

        game_match_id = id.split("1_")

        return base_url + game_match_id[1] + url_lineup



    lineup_url = build_url(game_row['id_game'])

    print("URL : %s", lineup_url)

    while True:
        try:
            driver.get(lineup_url)
            driver.implicitly_wait(200)
            WebDriverWait(driver, 40).until(EC.visibility_of_element_located((By.ID, "tab-match-lineups")))

        except:
            driver.close()
            driver = webdriver.Chrome(chrome_options=options)
            driver.implicitly_wait(40)
            continue
        break

    soup = BeautifulSoup(driver.page_source, "html.parser")

    table = soup.find('div', {'class': 'lineups-wrapper'}).find_all('tr', {'class': re.compile('^even|^odd')})
    table_row_left = soup.find_all('td', {'class': 'fl'})
    table_row_right = soup.find_all('td', {'class': 'fr'})

    i = 0
    home_team = []
    away_team = []



    for tr in table_row_left:

        if i == 11:
            break
        else:

            home_team.append(tr.a['onclick'].split('/')[2])

        i += 1

    home_team_dataFrame.loc[game_index] = home_team

    i = 0
    for tr in table_row_right:

        if i == 11:
            break
        else:
            away_team.append(tr.a['onclick'].split('/')[2])
        i += 1

    away_team_dataFrame.loc[game_index] = away_team

    result = pd.concat([games,home_team_dataFrame,away_team_dataFrame], axis = 1 )
    # print(result)
    result.loc[game_index].to_frame().T.to_csv(f_game_lineup, sep=';', mode='a',header=(not os.path.exists(f_game_lineup)))

    print("game ",game_index,":", game_row)
    driver.close()
    driver = webdriver.Chrome(chrome_options=options)
    driver.implicitly_wait(40)

# Extrair para um ficheiro
