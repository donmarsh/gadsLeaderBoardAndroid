package com.marshsoft.gadsleaderboard

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SkillRecyclerAdapter(private val leaders: ArrayList<Leader>): RecyclerView.Adapter<SkillRecyclerAdapter.LeaderHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillRecyclerAdapter.LeaderHolder {
        val leaderView = LayoutInflater.from(parent.context)
            .inflate(R.layout.skillrowview, parent, false)
        return LeaderHolder(leaderView)
    }

    override fun onBindViewHolder(holder: SkillRecyclerAdapter.LeaderHolder, position: Int) {
        holder.bindItems(leaders[position])
    }

    override fun getItemCount(): Int {
        return leaders.size
    }
    class LeaderHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        init {
            v.setOnClickListener(this)
        }
        fun bindItems(leader:Leader){
            val textViewName = itemView.findViewById(R.id.tvName) as TextView
            val textViewHours  = itemView.findViewById(R.id.tvHours) as TextView
            textViewName.text = leader.name
            textViewHours.text = "${leader.score} skill IQ Score, ${leader.country}"
            Glide.with(itemView)
                .load(leader.badgeUrl)
                .fitCenter()
                .placeholder(R.drawable.skilliqtrimmed)
                .error(R.drawable.skilliqtrimmed)
                .into(itemView.findViewById<ImageView>(R.id.imgSkill))
        }


        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }


    }
}
