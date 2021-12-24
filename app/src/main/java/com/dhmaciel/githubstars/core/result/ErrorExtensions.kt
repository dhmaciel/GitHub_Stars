package com.dhmaciel.githubstars.core.result

import com.dhmaciel.githubstars.core.result.model.ErrorResponse
import com.dhmaciel.githubstars.core.result.model.GitListError

fun ErrorResponse.toError() = GitListError(
    message = message,
    documentationUrl = documentationUrl
)