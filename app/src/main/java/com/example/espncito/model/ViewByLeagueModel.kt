package com.example.espncito.model

import com.google.gson.annotations.SerializedName

data class ViewByLeagueModel(
    @SerializedName("sports") val sports: List<Sport>
)

data class Sport(
    @SerializedName("leagues") val leagues: List<League>
)

data class League(
    @SerializedName("teams") val teams: List<LeagueTeam>  // Cambié de Team a LeagueTeam
)

data class LeagueTeam(  // Cambié de Team a LeagueTeam
    @SerializedName("team") val team: TeamInfo
)

data class TeamInfo(
    @SerializedName("id") val id: String,
    @SerializedName("location") val location: String,
    @SerializedName("name") val name: String,
    @SerializedName("abbreviation") val abbreviation: String,
    @SerializedName("displayName") val displayName: String,
    @SerializedName("shortDisplayName") val shortDisplayName: String,
    @SerializedName("logo") val logo: String
)