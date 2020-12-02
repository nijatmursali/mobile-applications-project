const mongoose = require("mongoose");

const QuestionSchema = new mongoose.Schema({
  description: {
    type: String,
    required: true,
    unique: true,
  },
  category: {
    type: String,
    required: true,
  },
  image: {
    type: String,
    required: false,
  },
  choices: [
    {
      text: {
        type: String,
        required: true,
      },
      isCorrect: {
        type: Boolean,
        required: true,
        default: false,
      },
    },
  ],
});

module.exports = mongoose.model("Question", QuestionSchema);
