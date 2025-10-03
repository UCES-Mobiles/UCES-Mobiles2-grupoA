package com.example.espncito.model

import com.google.gson.annotations.SerializedName

data class ViewByTeamModel(
    @SerializedName("headlines") val headlines: String
)
