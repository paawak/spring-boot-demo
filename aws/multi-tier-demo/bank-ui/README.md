# About

Demo for Bank UI. Can be accessed here: <http://localhost:8100>. 

## Running on Node

    node index.js

## Docker
### Build Docker image

    docker build -t paawak/bank-ui:latest .

### Run Docker image

    docker run -it -p 8100:80 paawak/bank-ui:latest

### Logon into a running container

    docker exec -it musing_hodgkin bash

    