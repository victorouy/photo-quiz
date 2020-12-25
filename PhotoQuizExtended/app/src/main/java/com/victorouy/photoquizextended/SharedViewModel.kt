package com.victorouy.photoquizextended

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Inherits the ViewModel for the fragments to share MutableLiveData of class Quiz
 *
 * @author Victor Ouy
 */
class SharedViewModel : ViewModel()  {
    private val quiz = MutableLiveData<Quiz>()

    /**
     * Sets the quiz object
     *
     * @param quiz: Quiz
     */
    fun setQuiz(quiz : Quiz) {
        this.quiz.value = quiz
    }

    /**
     * Gets the LiveData<Quiz?>? object
     *
     * @return LiveData<Quiz?>?
     */
    fun getQuiz(): LiveData<Quiz?>? {
        return this.quiz
    }
}