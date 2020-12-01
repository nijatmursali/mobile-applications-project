const express = require("express");
const fs = require("fs");
const path = require("path");
const app = express();

app.get("/", (req, res) => {
  let rawdata = fs.readFileSync(path.resolve(__dirname, "questions.json"));
  let student = JSON.parse(rawdata);
  console.log(student);
  res.send(student);
});

app.get("/api/questions", (req, res) => {
  res.send("There will be questions!");
});

const port = process.env.PORT || 5000;

app.listen(port, () => `Server running on port ${port}`);
