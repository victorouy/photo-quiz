package com.victorouy.photoquizextended

import android.util.Log
import kotlin.random.Random

/**
 * Quiz class that deals with the logic of questions and answers of a quiz
 */
class Quiz(val answers: ArrayList<String>, val images: ArrayList<Int>, val wrongAnswers: ArrayList<String>) {
    //The wrong answers for one question will be the right answer to another one.
    private var question: Question = generateQuestion()
    var streak: Int = 0
    var highScore: Int = 0

    /**
     * Set up a new question
     *
     * @return Question
     */
    fun generateQuestion() : Question{

        val newQuestion: Question
        //list of options presented to the user
        val options: ArrayList<String> = ArrayList()

        //add 3 wrong answers
        wrongAnswers.shuffle()
        options.add(wrongAnswers[0])
        options.add(wrongAnswers[1])
        options.add(wrongAnswers[2])

        //randomly choose one of the 10 questions and add it
        //check to make sure it's not one of the wrong answers
        var questionNo:Int
        do {
            questionNo = Random.nextInt(10)
        }while(options.contains(answers[questionNo]))
        options.add(answers[questionNo])
        //shuffle the options
        options.shuffle()

        //generate the question
        newQuestion = Question(options,images[questionNo],answers[questionNo])
        Log.i("QUIZ", "New question generated")
        return newQuestion
    }

    /**
     * Confirm whether or not the answer is correct, update streak and high score accordingly,
     * generate new question
     */
    fun checkAnswer(guess: String): Boolean{
        var isCorrect = false
        val currentAnswer = question.correctAnswer

        if (guess == currentAnswer){
            Log.i("QUIZ", "User got answer correct")
            isCorrect = true
            streak++
            if (streak > highScore){
                Log.i("QUIZ", "New high score set")
                highScore = streak
            }
        }else{
            Log.i("QUIZ", "User got answer incorrect, streak reset")
            streak = 0
        }

        do{
            question = generateQuestion()
            //if the generated question has the same answer as the previous question,
            //generate a new one
        }while(currentAnswer == question.correctAnswer)

        return isCorrect
    }

    /**
     * Sets the streak and highscore to 0
     */
    fun resetHighScore(){
        streak = 0
        highScore = 0
    }

    /**
     * Sets the questions
     *
     * @param loadedQuestion: Question
     */
    fun setQuestion(loadedQuestion: Question) {
        question = loadedQuestion
    }

    /**
     * Gets the question
     *
     * @return Question
     */
    fun getQuestion() : Question{
        return question
    }

    /**
     * Gets the answer text given a position
     *
     * @param position: Int
     * @return String
     */
    fun getAnswerText(position: Int): String{
        return question.qAnswers[position]
    }

    /**
     * Gets the qImage of the question object
     *
     * @return Int
     */
    fun getImageRes() : Int{
        return question.qImage
    }
}