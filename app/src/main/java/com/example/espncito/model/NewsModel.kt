package com.example.appparcial2.model

import com.google.gson.annotations.SerializedName

data class NewsModel(
    @SerializedName("headlines") val headlines: List<Headline>
)

data class Headline(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String?, // nueva descripción
    @SerializedName("images") val images: List<Image>? // lista de imágenes
)

data class Image(
    @SerializedName("url") val url: String,
    @SerializedName("caption") val caption: String?
)
