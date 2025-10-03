package com.example.espncito.model

import com.google.gson.annotations.SerializedName

data class ViewByTeamModel(
    @SerializedName("team") val team: Team
)

data class Team(
    @SerializedName("name") val name: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("color") val color: String?,
    @SerializedName("standingSummary") val standingSummary: String?,
    @SerializedName("defaultLeague") val defaultLeague: DefaultLeague?,
    @SerializedName("record") val record: Record?
)

data class DefaultLeague(
    @SerializedName("name") val name: String
)

data class Record(
    @SerializedName("items") val items: List<RecordItem>
)

data class RecordItem(
    @SerializedName("stats") val stats: List<Stat>
)

data class Stat(
    @SerializedName("name") val name: String,
    @SerializedName("value") val value: Any
)