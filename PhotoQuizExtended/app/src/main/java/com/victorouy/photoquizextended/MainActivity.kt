package com.victorouy.photoquizextended

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

/**
 * Main activity class that inherits AppCompatActivity that is used to set up the fragment adapter
 * to function it with swipeable TabLayout
 *
 * @author Victor Ouy
 */
class MainActivity : AppCompatActivity() {
    /**
     * Overrides method onCreate. Sets the SectionsPagerAdapter that is used to set
     * up view pager to allow tablayout swipe
     *
     * @param savedInstanceState: Bundle?
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Removes the shadow from the action bar for design purposes
        supportActionBar?.elevation = 0F


        val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }
}