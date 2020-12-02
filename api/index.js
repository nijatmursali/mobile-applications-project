const express = require("express");
const app = express();
const mongoose = require("mongoose");
const cors = require("cors");

//import routes
const routes = require("./routers/router");

require("dotenv").config();

app.use(cors());
app.use(express.json());
app.use(routes);

mongoose.connect(process.env.DATABASE_URL, {
  useNewUrlParser: true,
  useUnifiedTopology: true,
});
const db = mongoose.connection;
db.on("error", (error) => console.error(error));
db.once("open", () => console.log("database connected"));

const PORT = process.env.PORT || 5000;

app.listen(process.env.PORT, () => {
  console.log(`The server is listening at port number ${PORT}.`);
});
