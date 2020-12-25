package com.victorouy.photoquizextended

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

/**
 * The fragment of the quiz for the questions and image
 */
class QuestionFragment : Fragment() {
    private lateinit var quiz: Quiz
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var fragView : View
    private lateinit var questionsList: ArrayList<String>

    /**
     * Override onCreate that sets options menu as true and initializes the ShareViewModel
     *
     * @param savedInstanceState: Bundle
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProviders.of(activity!!).get(SharedViewModel::class.java)
        setHasOptionsMenu(true)
    }

    /**
     * Override onCreateView that inflates the layout fragment, reads SavedInstances and sharedPreferences,
     * sets the quiz along with the custom grid adapter
     *
     * @param inflater: LayoutInflater
     * @param container: ViewGroup?
     * @param savedInstanceState: Bundle?
     * @return View?
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.fragView = inflater.inflate(R.layout.fragment_question, container, false)

        // Inflate the layout for this fragment
        val answers = resources.getStringArray(R.array.answers).toCollection(ArrayList())
        val wrongAnswers = resources.getStringArray(R.array.wrong_answers).toCollection(ArrayList())
        //mixing correct answers into wrong answers to make quiz more challenging
        wrongAnswers.addAll(answers)
        val images = intArrayOf(
            R.drawable.image_0,
            R.drawable.image_1,
            R.drawable.image_2,
            R.drawable.image_3,
            R.drawable.image_4,
            R.drawable.image_5,
            R.drawable.image_6,
            R.drawable.image_7,
            R.drawable.image_8,
            R.drawable.image_9
        ).toCollection(ArrayList())
        quiz = Quiz(answers, images, wrongAnswers)

        if(savedInstanceState != null){
            //load question
            val loadedAnswers = savedInstanceState.getStringArrayList("allAnswers") as ArrayList<String>
            val loadedCorrect = savedInstanceState.getString("correctAnswer") as String
            val loadedImage = savedInstanceState.getInt("answerImage")
            val loadedQuestion :Question = Question(loadedAnswers, loadedImage, loadedCorrect)
            quiz.setQuestion(loadedQuestion)

            //load scores
            quiz.streak = savedInstanceState.getInt("streak")
            quiz.highScore = savedInstanceState.getInt("highScore")
        }
        else{
            //if no bundle, load high score from preferences
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            if (sharedPref != null) {
                quiz.highScore = sharedPref.getInt("highScore", 0)
            }
        }
        setQuizUI()
        // Sets the custom adapter for the GridView
        val gridView : GridView = fragView.findViewById(R.id.questions_gridview)
        val gridAdapter = GridAdapter(activity!!, questionsList, this)
        gridView.adapter = gridAdapter

        return fragView
    }

    /**
     * Sets the quiz in the sharedViewModel object
     *
     * @param savedInstanceState: Bundle?
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedViewModel.setQuiz(quiz)
    }

    /**
     * When the activity stops, we want to save the high score to shared preferences
     */
    override fun onStop() {
        super.onStop()

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        editor?.putInt("highScore",quiz.highScore)
        editor?.apply()
    }

    /**
     * Invoked when the activity may be temporarily destroyed
     *
     * @param outState: Bundle
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState?.run {
            //save question
            putStringArrayList("allAnswers", quiz.getQuestion().qAnswers)
            putInt("answerImage", quiz.getQuestion().qImage)
            putString("correctAnswer",quiz.getQuestion().correctAnswer)
            //Save scores
            putInt("streak", quiz.streak)
            putInt("highScore", quiz.highScore)
        }
        super.onSaveInstanceState(outState)
    }

    /**
     * Inflates teh options menu
     *
     * @param menu: Menu
     * @param inflater: MenuInflate
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.mymenu, menu)
    }

    /**
     * Invoked when reset options menu is clicked and resets the streak and score
     * as well as updates the sharedViewModel quiz
     *
     * @param item: MenuItem
     * @return Boolean
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.i("OPTION CLICKED", "QuestionFragment")
        quiz.resetHighScore()
        sharedViewModel.setQuiz(quiz)
        return super.onOptionsItemSelected(item)
    }

    /**
     * Sets the updated image and sets the questionsList of questions
     */
    fun setQuizUI() {
        fragView.findViewById<ImageView>(R.id.answerImage).setImageResource(quiz.getImageRes())
        questionsList = arrayListOf(quiz.getAnswerText(0), quiz.getAnswerText(1),
            quiz.getAnswerText(2), quiz.getAnswerText(3))
    }

    /**
     * onClick for when the user selects an answer. Pops up a toast based on whether it was right or not.
     *
     * @param buttonText : String
     */
    fun answerButtonClick(buttonText : String) {
        Log.i("BUTTON", "User answered " + buttonText)
        if(quiz.checkAnswer(buttonText)){
            Toast.makeText(context, R.string.right_toast, Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, R.string.wrong_toast, Toast.LENGTH_SHORT).show()
        }
        sharedViewModel.setQuiz(quiz)
        setQuizUI()
    }

    /**
     * Gets the questionsList
     *
     * @return ArrayList<String>
     */
    fun getQuestionsList(): ArrayList<String> {
        return this.questionsList
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Factory method to create a new instance of this fragment using
         * the provided parameters
         *
         * @param sectionNumber: Int
         * @return QuestionFragment
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): QuestionFragment {
            return QuestionFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}