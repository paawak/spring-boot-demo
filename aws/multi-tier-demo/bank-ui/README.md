# About

Demo for Bank UI. Can be accessed here: <http://localhost:8100>. 

## Running on Node

    node index.js

## Docker
### Build Docker image

    docker build -t bank-ui:latest .

### Run Docker image

    docker run -it --rm -d -p 8100:80 --name bank-ui bank-ui:latest

### Logon into a running container

    docker exec -it musing_hodgkin bash

    