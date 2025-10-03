package com.example.appparcial2.model

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("headlines") val headlines: List<Headline>
)

data class Headline(
    @SerializedName("title") val title: String
)