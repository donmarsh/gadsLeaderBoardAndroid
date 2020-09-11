package com.marshsoft.gadsleaderboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_leaderboard.*

class LeaderBoard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)
        leaderBoardViewPager.adapter = MyAdapter(supportFragmentManager,lifecycle)
        TabLayoutMediator(tab_layout,leaderBoardViewPager,TabLayoutMediator.TabConfigurationStrategy{
            tab,position ->
            when(position){
                0->tab.text = "Learning Leaders"
                1->tab.text = "Skill IQ Leaders"
            }

        }).attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       menuInflater.inflate(R.menu.appbarmenu, menu)
        val item: MenuItem? = menu?.findItem(R.id.action_submit)
        item?.actionView?.findViewById<Button>(R.id.btnSubmit)?.setOnClickListener {
            val submitIntent = Intent(this,ProjectSubmission::class.java)
            startActivity(submitIntent)
        }
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_submit -> {
            val submitIntent = Intent(this,ProjectSubmission::class.java)
            startActivity(submitIntent)
            true
        }
        else -> {
           
            super.onOptionsItemSelected(item)
        }
    }
    private inner class MyAdapter(fm: FragmentManager?, lifecycle: Lifecycle) : FragmentStateAdapter(fm!!, lifecycle) {
        private val itemsCount = 2
        override fun createFragment(position: Int): Fragment {
            var fragment: Fragment? = null
            when (position) {
                0 -> fragment = LearningLeaderBoard()
                1 -> fragment = SkillLeaderBoard()

            }
            return fragment!!
        }
        override fun getItemCount(): Int {
            return itemsCount
        }
    }

}