package com.marshsoft.gadsleaderboard

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class HoursRecyclerAdapter(private val leaders: ArrayList<Leader>): RecyclerView.Adapter<HoursRecyclerAdapter.LeaderHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoursRecyclerAdapter.LeaderHolder {
        val leaderView = LayoutInflater.from(parent.context)
            .inflate(R.layout.hoursrowview, parent, false)
        return LeaderHolder(leaderView)
    }

    override fun onBindViewHolder(holder: HoursRecyclerAdapter.LeaderHolder, position: Int) {
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
            textViewHours.text = ""+leader.hours+" learning hours, "+leader.country
            Glide.with(itemView)
                .load(leader.badgeUrl)
                .fitCenter()
                .placeholder(R.drawable.toplearner)
                .error(R.drawable.toplearner)
                .into(itemView.findViewById<ImageView>(R.id.imgHours))
        }


        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }


    }
}
