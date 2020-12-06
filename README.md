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

   - For MongoDB,
     1. Run `npm i` again to install dependencies.
     2. Install the Community Server from [this link](https://www.mongodb.com/try/download/community)
     3. Install MongoDB Compass from [this link](https://www.mongodb.com/try/download/compass?tck=docs_compass)
     4. Open MongoDB Compass after installing.
     5. Connect to your own local server and paste it do `.env` file inside the `api` folder.
     6. You can now make requests to API as shown below. Example is shown inside `api/questions.json` file.
     7. Done.

## API

We created our own REST API with MEN(Mongo, Express, Node) stack

These are the routes

- Get all quiz questions `http://161.35.55.28:5000/api/questions`
- Get quiz question by ID `http://161.35.55.28:5000/api/questions/<id>`
- Get quiz question by category `http://161.35.55.28:5000/api/questions/category<category>`
- Get one quiz question by category and ID `http://161.35.55.28:5000/api/questions/category/<category>/<id>`
- Add new quiz question `http://161.35.55.28:5000/api/questions` and select POST request
- Update the quiz question by ID `http://161.35.55.28:5000/api/questions/<id>`
- Delete the quiz question by ID `http://161.35.55.28:5000/api/questions/<id>`
