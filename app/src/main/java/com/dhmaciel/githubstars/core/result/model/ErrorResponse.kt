package com.dhmaciel.githubstars.core.result.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("message")
    val message: String = "",

    @SerializedName("documentation_url")
    val documentationUrl: String = ""
) {
    fun isValid(): Boolean = message.isNotEmpty() && message.isNotEmpty()
}