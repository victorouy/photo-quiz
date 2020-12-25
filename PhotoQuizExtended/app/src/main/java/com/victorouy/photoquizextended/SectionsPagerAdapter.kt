package com.victorouy.photoquizextended

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.victorouy.photoquizextended.QuestionFragment.Companion as QuestionFragment

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 *
 * @author Victor Ouy
 */
class SectionsPagerAdapter(fm: FragmentManager)
    : FragmentPagerAdapter(fm) {

    private val tabTitles = arrayOf("Questions", "Scores")

    /**
     * Instantiate the fragment for the given page
     *
     * @param position: Int
     * @return Fragment
     */
    override fun getItem(position: Int): Fragment {
        var frag : Fragment? = null
        when (position) {
            0 -> frag = QuestionFragment.newInstance(position + 1)
            1 -> frag = ScoreFragment.newInstance(position + 1)
        }
        requireNotNull(frag) { "No matching fragment" }
        return frag
    }

    /**
     * Gets the CharSequence of tabTitles given a position
     *
     * @param position: Int
     * @return CharSequence?
     */
    override fun getPageTitle(position: Int): CharSequence? {
        return this.tabTitles[position]
    }

    /**
     * Gets the number of tabs
     *
     * @return Int
     */
    override fun getCount(): Int {
        return 2
    }
}