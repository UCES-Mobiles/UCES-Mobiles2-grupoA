package com.example.espncito.model

import com.google.gson.annotations.SerializedName

data class ViewByTeamModel(
    @SerializedName("team") val team: Team
)

data class Team(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("abbreviation") val abbreviation: String?,
    @SerializedName("displayName") val displayName: String?,
    @SerializedName("color") val color: String?,
    @SerializedName("alternateColor") val alternateColor: String?,
    @SerializedName("isActive") val isActive: Boolean?,
    @SerializedName("logos") val logos: List<TeamLogo>?,
    @SerializedName("record") val record: Record?,
    @SerializedName("defaultLeague") val defaultLeague: DefaultLeague?,
    @SerializedName("leagueAbbrev") val leagueAbbrev: String?,
    @SerializedName("standingSummary") val standingSummary: String?,
    @SerializedName("nextEvent") val nextEvent: List<NextEvent>?
)

data class TeamLogo(
    @SerializedName("href") val href: String,
    @SerializedName("alt") val alt: String,
    @SerializedName("rel") val rel: List<String>,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int
)

data class Record(
    @SerializedName("items") val items: List<RecordItem>?
)

data class RecordItem(
    @SerializedName("description") val description: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("summary") val summary: String?,
    @SerializedName("stats") val stats: List<Stat>?
)

data class Stat(
    @SerializedName("name") val name: String,
    @SerializedName("value") val value: Any
)

data class DefaultLeague(
    @SerializedName("name") val name: String?
)

data class NextEvent(
    @SerializedName("id") val id: String?,
    @SerializedName("date") val date: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("shortName") val shortName: String?,
    @SerializedName("venue") val venue: Venue?,
    @SerializedName("status") val status: EventStatus?
)

data class Venue(
    @SerializedName("fullName") val fullName: String?
)

data class EventStatus(
    @SerializedName("type") val type: EventType?
)

data class EventType(
    @SerializedName("detail") val detail: String?
)