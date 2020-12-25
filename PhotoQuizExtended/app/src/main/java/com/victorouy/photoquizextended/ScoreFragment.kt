package com.victorouy.photoquizextended

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import org.w3c.dom.Text

/**
 * The fragment for the streak and highscore
 */
class ScoreFragment : Fragment() {
    private lateinit var quizStreak : TextView
    private lateinit var quizHighScore : TextView
    private lateinit var sharedViewModel: SharedViewModel

    /**
     * Override onCreate that sets options menu as true
     *
     * @param savedInstanceState: Bundle
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    /**
     * Override onCreateView that inflates the layout fragment and sets the instance variables
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_score, container, false)
        quizStreak = view.findViewById(R.id.streak_view)
        quizHighScore = view.findViewById(R.id.high_score_view)
        return view
    }

    /**
     * Instantiates the shareViewModel, sets the text of the streak and highscore TextView,
     * and adds the event listener onChanged whenever the Quiz object in the sharedViewModel updates
     *
     * @param savedInstanceState: Bundle?
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedViewModel = ViewModelProviders.of(activity!!).get(SharedViewModel::class.java)
        quizStreak.text = getString(R.string.streak) + sharedViewModel.getQuiz()?.value?.streak.toString()
        quizHighScore.text = getString(R.string.high_score) + sharedViewModel.getQuiz()?.value?.highScore.toString()

        // onChange that updates the streak and highscore TextView whenever the quiz changes
        sharedViewModel.getQuiz()?.observe(viewLifecycleOwner,
            Observer { quizChanged ->
                quizStreak.text = getString(R.string.streak) + quizChanged!!.streak
                quizHighScore.text = getString(R.string.high_score) + quizChanged!!.highScore
            })
    }

    /**
     * Inflates the options menu
     *
     * @param menu: Menu
     * @param inflater: MenuInflater
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.mymenu, menu)
    }

    /**
     * Event listerner whenever the reset options menu is clicked that resets the highscore and
     * streak of the quiz object and TextView
     *
     * @param item: MenuItem
     * @return Boolean
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.i("OPTION CLICK", "ScoreFragment")
        sharedViewModel.getQuiz()?.value?.resetHighScore()
        quizStreak.text = getString(R.string.streak) + sharedViewModel.getQuiz()?.value?.streak
        quizHighScore.text = getString(R.string.high_score) + sharedViewModel.getQuiz()?.value?.highScore
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Factory method to create a new instance of this fragment using
         * the provided parameters
         *
         * @param sectionNumber: Int
         * @return ScoreFragment
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): ScoreFragment {
            return ScoreFragment().apply {
                arguments = Bundle().apply {
                    putInt(ScoreFragment.ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}