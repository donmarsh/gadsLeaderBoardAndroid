package com.marshsoft.gadsleaderboard

import org.json.JSONArray

class ApiHelper {
    private val baseApiUrl = "https://gadsapi.herokuapp.com"
    private val skillUrl = "/api/skilliq"
    private val hoursUrl = "/api/hours"
    fun buildUrl(skillSearch:Boolean = true):String{
        return if(skillSearch){
            baseApiUrl+skillUrl
        } else{
            baseApiUrl+hoursUrl
        }
    }

    fun getLeadersFromJson(json:String):ArrayList<Leader>{
        val jsonLeaders = JSONArray(json)

        val leaders = ArrayList<Leader>()
        for(x in 0 until jsonLeaders.length()){
            val leaderJson = jsonLeaders.getJSONObject(x)

            val leader = Leader(leaderJson.getString("name"),
                if(leaderJson.isNull("hours")) 0 else leaderJson.getInt("hours"),
                if(leaderJson.isNull("score")) 0 else leaderJson.getInt("score"),
                leaderJson.getString("country"),
                leaderJson.getString("badgeUrl"))
            leaders.add(leader)

        }
        return leaders
    }
}