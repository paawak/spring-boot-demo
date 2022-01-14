# About

Demo for Bank UI. 

## Running locally

    npm start

This will make the application available here: <http://localhost:3000>. 

## Docker
### Build Docker image

    docker build -t paawak/bank-ui:latest .

### Push image

    docker login
    docker push paawak/bank-ui:latest
      

### Run Docker image

    docker run --env REST_API_BASE_NAME=http://localhost:8080 -it -p 8100:80 paawak/bank-ui:latest

This will make the application available here: <http://localhost:8100>.     

### Logon into a running container

    docker container ls

    docker exec -it musing_hodgkin bash

# Passing env vars to React App

<https://blog.codecentric.de/en/2018/12/react-application-container-environment-aware-kubernetes-deployment/>


<https://javaadpatel.com/building-and-deploying-react-containers/>

# Getting Started with Create React App

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

## Available Scripts

In the project directory, you can run:

### `npm start`

Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

The page will reload if you make edits.\
You will also see any lint errors in the console.

### `npm test`

Launches the test runner in the interactive watch mode.\
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `npm run build`

Builds the app for production to the `build` folder.\
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.\
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

## Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).
