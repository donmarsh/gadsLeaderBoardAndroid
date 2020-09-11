package com.marshsoft.gadsleaderboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


/**
 * A simple [Fragment] subclass.
 * Use the [SkillLeaderBoard.newInstance] factory method to
 * create an instance of this fragment.
 */
class SkillLeaderBoard : Fragment() {
    private lateinit var rootView: View
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: SkillRecyclerAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var queue: RequestQueue
    private var leaders=  ArrayList<Leader>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        queue = Volley.newRequestQueue(activity)
        onCreateComponent()
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(

            Request.Method.GET, ApiHelper().buildUrl(true),
            { response ->

                leaders.addAll(ApiHelper().getLeadersFromJson(response))
                val sortedLeaders = ArrayList(leaders.sortedWith(compareByDescending { it.score }))
                leaders.clear()
                leaders.addAll(sortedLeaders)
                println("size ${leaders.size}")
                adapter.notifyDataSetChanged()
                println("Adapter count${adapter.itemCount}")

            },
            {
                val toast = Toast.makeText(context, "Error retrieving Learning Leaders", Toast.LENGTH_SHORT)
                toast.show()
            }
        )
        queue.add(stringRequest)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_skill_leader_board, container, false)
        initializeRecyclerView()
        return rootView
    }
    private fun onCreateComponent() {
        adapter = SkillRecyclerAdapter(leaders)
    }
    private fun initializeRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recyclerSkill)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment SkillLeaderBoard.
         */
        @JvmStatic
        fun newInstance() = SkillLeaderBoard().apply {}
    }
}