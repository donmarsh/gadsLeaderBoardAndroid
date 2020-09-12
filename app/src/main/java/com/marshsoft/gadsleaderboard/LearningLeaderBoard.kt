package com.marshsoft.gadsleaderboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_learning_leader_board.*

/**
 * A simple [Fragment] subclass.
 * Use the [LearningLeaderBoard.newInstance] factory method to
 * create an instance of this fragment.
 */
class LearningLeaderBoard : Fragment() {
    private lateinit var rootView: View
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: HoursRecyclerAdapter
    lateinit var prgHours: ProgressBar
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var queue:RequestQueue
    private var leaders=  ArrayList<Leader>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        queue = Volley.newRequestQueue(activity)
        onCreateComponent()

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(

            Request.Method.GET, ApiHelper().buildUrl(false),
            { response ->
                prglearningLeaders.visibility = View.INVISIBLE
                leaders.addAll(ApiHelper().getLeadersFromJson(response))
                val sortedLeaders = ArrayList(leaders.sortedWith(compareByDescending { it.hours }))
                leaders.clear()
                leaders.addAll(sortedLeaders)
                adapter.notifyDataSetChanged()



            },
            {
                prglearningLeaders.visibility = View.INVISIBLE
                val toast = Toast.makeText(context, "Error retrieving Learning Leaders", Toast.LENGTH_SHORT)
                toast.show()
            }
        )
        queue.add(stringRequest)


    }
    private fun onCreateComponent() {
        adapter = HoursRecyclerAdapter(leaders)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_learning_leader_board, container, false)
        prgHours= rootView.findViewById(R.id.prglearningLeaders)
        prgHours.visibility = View.VISIBLE
        initializeRecyclerView()
        return rootView
    }
    private fun initializeRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recyclerHours)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment LearningLeaderBoard.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = LearningLeaderBoard().apply {}
    }
}