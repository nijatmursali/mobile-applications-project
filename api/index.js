const express = require("express");
const fs = require("fs");
const path = require("path");
const app = express();
const mongoose = require("mongoose");

require("dotenv").config();

mongoose.connect(process.env.DATABASE_URL, {
  useNewUrlParser: true,
  useUnifiedTopology: true,
});
const db = mongoose.connection;
db.on("error", (error) => console.error(error));
db.once("open", () => console.log("database connected"));

app.get("/", (req, res) => {
  res.send("Hello World!");
});

app.get("/api/questions", (req, res) => {
  let rdata = fs.readFileSync(path.resolve(__dirname, "questions.json"));
  let questions = JSON.parse(rdata);
  res.json(questions);
});

app.get("/api/questions/:category", (req, res) => {
  let rdata = fs.readFileSync(path.resolve(__dirname, "questions.json"));
  let questions = JSON.parse(rdata);
  res.send(questions[0]["category"]);
});

const port = process.env.PORT || 5000;

app.listen(port, () => `Server running on port ${port}`);
