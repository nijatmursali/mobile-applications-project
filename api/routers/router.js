const express = require("express");
const router = express.Router();
const Question = require("../models/questions.js");

// get all quiz questions
router.get("/api/questions", async (req, res) => {
  try {
    const questions = await Question.find();
    return res.status(200).json(questions);
  } catch (error) {
    return res.status(500).json({ error: error });
  }
});
// create one quiz question
router.post("/api/questions", async (req, res) => {
  try {
    const { description } = req.body;
    const { image } = req.body;
    const { category } = req.body;
    const { choices } = req.body;

    const question = await Question.create({
      description,
      category,
      image,
      choices,
    });

    return res.status(201).json(question);
  } catch (error) {
    return res.status(500).json({ error: error });
  }
});

// get one quiz question by id
router.get("/api/questions/:id", async (req, res) => {
  try {
    const _id = req.params.id;

    const question = await Question.findOne({ _id });
    if (!question) {
      return res.status(404).json({});
    } else {
      return res.status(200).json(question);
    }
  } catch (error) {
    return res.status(500).json({ error: error });
  }
});

// get one quiz question by category
router.get("/api/questions/category/:category", async (req, res) => {
  const category = req.params.category;
  console.log(req.params);

  try {
    const question = await Question.find({
      category,
    });
    if (!question) {
      return res.status(404).json({});
    } else {
      return res.status(200).json(question);
    }
  } catch (error) {
    return res.status(500).json({ error: error });
  }
});
router.get("/api/questions/category/:category/:id", async (req, res) => {
  console.log(req.params);
  const category = req.params.category;
  const _id = req.params.id;
  try {
    const question = await Question.findOne({
      category,
      _id,
    });
    if (!question) {
      return res.status(404).json({});
    } else {
      return res.status(200).json(question);
    }
  } catch (error) {
    return res.status(500).json({ error: error });
  }
});

// update one quiz question
router.put("/api/questions/:id", async (req, res) => {
  try {
    const _id = req.params.id;
    const { description, category, choices, image } = req.body;

    let question = await Question.findOne({ _id });

    if (!question) {
      question = await Question.create({
        description,
        category,
        image,
        choices,
      });
      return res.status(201).json(question);
    } else {
      question.description = description;
      question.alternatives = alternatives;
      await question.save();
      return res.status(200).json(question);
    }
  } catch (error) {
    return res.status(500).json({ error: error });
  }
});

// delete one quiz question
router.delete("/api/questions/:id", async (req, res) => {
  try {
    const _id = req.params.id;

    const question = await Question.deleteOne({ _id });

    if (question.deletedCount === 0) {
      return res.status(404).json();
    } else {
      return res.status(204).json();
    }
  } catch (error) {
    return res.status(500).json({ error: error });
  }
});

module.exports = router;
