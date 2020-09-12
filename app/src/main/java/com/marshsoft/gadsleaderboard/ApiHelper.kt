package com.marshsoft.gadsleaderboard

import android.R
import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray


class ApiHelper {
    private val baseApiUrl = "https://gadsapi.herokuapp.com"
    private val skillUrl = "/api/skilliq"
    private val hoursUrl = "/api/hours"
    fun buildUrl(skillSearch: Boolean = true):String{
        return if(skillSearch){
            baseApiUrl+skillUrl
        } else{
            baseApiUrl+hoursUrl
        }
    }

    fun getLeadersFromJson(json: String):ArrayList<Leader>{
        val jsonLeaders = JSONArray(json)

        val leaders = ArrayList<Leader>()
        for(x in 0 until jsonLeaders.length()){
            val leaderJson = jsonLeaders.getJSONObject(x)

            val leader = Leader(
                leaderJson.getString("name"),
                if (leaderJson.isNull("hours")) 0 else leaderJson.getInt("hours"),
                if (leaderJson.isNull("score")) 0 else leaderJson.getInt("score"),
                leaderJson.getString("country"),
                leaderJson.getString("badgeUrl")
            )
            leaders.add(leader)

        }
        return leaders
    }
    fun submitProject(
        context: Context,
        email: String,
        firstName: String,
        lastName: String,
        projectLink: String
    ):Boolean
    {
        var responseBoolean = false;
        val postUrl = "https://docs.google.com/forms/d/e/1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse"
        val params = HashMap<String, String>()
        params["entry.1824927963"] = email
        params["entry.1877115667"] = firstName
        params["entry.2006916086"] = lastName
        params["entry.284483984"] = projectLink
        val headers = HashMap<String, String>()
        headers.put("Content-Type", "application/x-www-form-urlencoded")
        val queue = Volley.newRequestQueue(context)

        val request: StringRequest = object : StringRequest( Method.POST, postUrl,
            Response.Listener { response ->
                responseBoolean = true

            },
            Response.ErrorListener { error ->
                responseBoolean = false
            }) {
            override fun getParams(): Map<String, String> {

                return params
            }

            override fun getHeaders(): MutableMap<String, String> {
                return headers
            }
        }


        queue.add(request)
        return responseBoolean
    }
}