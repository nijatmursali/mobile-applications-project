# Introduction

This is the repository for Mobile Applications and Cloud Computing course in Sapienza University of Rome.

## Instructions

1. Just clone the project
2. For API part
   - Install `node` if you don't have. To install it go to [this link](https://nodejs.org/en/download/) and install for your machine.
   - After installing try running `npm i` or `npm install`.
   - For nodemon install the module globally with `npm install -g nodemon`.
   - Then, finally run `npm run dev`.
   - You are now running simple questions API on port `localhost:5000`.

## API

We created our own REST API with MEN(Mongo, Express, Node) stack

These are the routes

- Get all quiz questions `http://localhost:5000/api/questions`
- Get quiz question by ID `http://localhost:5000/api/questions/<id>`
- Get quiz question by category `http://localhost:5000/api/questions/category<category>`
- Add new quiz question `http://localhost:5000/api/questions` and select POST request
- Update the quiz question by ID `http://localhost:5000/api/questions/<id>`
- Delete the quiz question by ID `http://localhost:5000/api/questions/<id>`
