#!/bin/bash

for filename in Final/*.json; do
	curl -X POST "localhost:9200/match/_doc?pretty" -H 'Content-Type: application/json' --data-binary "@$filename"
done
