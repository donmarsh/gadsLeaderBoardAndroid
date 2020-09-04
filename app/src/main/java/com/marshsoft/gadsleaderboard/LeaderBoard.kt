package com.marshsoft.gadsleaderboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_leaderboard.*

class LeaderBoard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val leaderBoardTabsNamesArray = arrayOf<String>("Learning Leaders","Skill IQ Leaders")
        val leaderBoardViewAdapter = LeaderBoardViewAdapter (this, leaderBoardTabsNamesArray.size)
        leaderBoardViewPager.adapter = leaderBoardViewAdapter
        setContentView(R.layout.activity_leaderboard)
    }
}