package com.dhmaciel.githubstars.core.result.model

const val CODE_ERRO_PADRAO = 500

data class GitListError(
    val code: Int = CODE_ERRO_PADRAO,
    val message: String = "",
    val documentationUrl: String = ""
) {
    fun isValid(): Boolean = code != -1 && message.isNotEmpty() && documentationUrl.isNotEmpty()
}