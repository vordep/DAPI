import csv
import json
import glob
import os
import codecs
import io

def unicode_csv_reader(utf8_data, dialect=csv.excel, **kwargs):
    csv_reader = csv.reader(utf8_data, dialect=dialect, **kwargs)
    for row in csv_reader:
        yield [unicode(cell, 'utf-8') for cell in row]

for filename in glob.glob('Refined/*.csv'):
    csvfile = os.path.splitext(filename)[0]
    jsonfile = csvfile + '.json'

    with open(csvfile+'.csv') as f:
		reader = unicode_csv_reader(f, delimiter=';')
		rows = list(reader)

    with codecs.open(jsonfile, 'w', encoding='utf-8') as f:
        json.dump(rows, f, ensure_ascii=False)
