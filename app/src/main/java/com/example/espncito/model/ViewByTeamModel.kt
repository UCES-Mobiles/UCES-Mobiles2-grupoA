package com.example.espncito.model

import com.google.gson.annotations.SerializedName

data class ViewByTeamModel(
    @SerializedName("team") val team: Team
)

data class Team(
    @SerializedName("slug") val slug: String,
    @SerializedName("location") val location: String,
    @SerializedName("name") val name: String,
    @SerializedName("abbreviation") val abbreviation: String,
    @SerializedName("displayName") val displayName: String,
    @SerializedName("shortDisplayName") val shortDisplayName: String,
    @SerializedName("color") val color: String,
    @SerializedName("alternateColor") val alternateColor: String,
)