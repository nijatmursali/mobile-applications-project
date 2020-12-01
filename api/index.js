const express = require("express");
const fs = require("fs");
const path = require("path");
const app = express();

app.get("/", (req, res) => {
  res.send("Hello World!");
});

app.get("/api/questions", (req, res) => {
  let rdata = fs.readFileSync(path.resolve(__dirname, "questions.json"));
  let questions = JSON.parse(rdata);
  res.send(questions);
});

const port = process.env.PORT || 5000;

app.listen(port, () => `Server running on port ${port}`);
