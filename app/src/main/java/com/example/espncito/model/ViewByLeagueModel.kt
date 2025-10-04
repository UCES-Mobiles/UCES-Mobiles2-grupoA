package com.example.espncito.model

import com.google.gson.annotations.SerializedName

data class ViewByLeagueModel(
    @SerializedName("sports") val sports: List<Sport>
)

data class Sport(
    @SerializedName("leagues") val leagues: List<League>
)

data class League(
    @SerializedName("teams") val teams: List<LeagueTeam>
)

data class LeagueTeam(
    @SerializedName("team") val team: TeamInfo
)

data class TeamInfo(
    @SerializedName("id") val id: String,
    @SerializedName("location") val location: String,
    @SerializedName("name") val name: String,
    @SerializedName("abbreviation") val abbreviation: String,
    @SerializedName("displayName") val displayName: String,
    @SerializedName("shortDisplayName") val shortDisplayName: String,
    @SerializedName("color") val color: String?,
    @SerializedName("alternateColor") val alternateColor: String?,
    @SerializedName("logos") val logos: List<TeamLogo>?
)

data class TeamLogo(
    @SerializedName("href") val href: String,
    @SerializedName("alt") val alt: String,
    @SerializedName("rel") val rel: List<String>,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int
)