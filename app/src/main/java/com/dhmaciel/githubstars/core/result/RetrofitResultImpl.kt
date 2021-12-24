package com.dhmaciel.githubstars.core.result

import com.dhmaciel.githubstars.core.result.model.ErrorResponse
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject
import okhttp3.ResponseBody
import retrofit2.Response

class RetrofitResultImpl @Inject constructor(private val gson: Gson) : RetrofitResult {

    override suspend fun <T> getResult(request: suspend () -> Response<T>): Result<T> {
        return try {
            val response = request.invoke()
            val data = response.body()

            if (response.isSuccessful && data != null) {
                Result.success(data)
            } else {
                handleError(response.errorBody(), response.code())
            }
        } catch (expected: IOException) {
            handleException(expected)
        } catch (expected: RuntimeException) {
            handleException(expected)
        }
    }

    private fun <T> handleError(errorBody: ResponseBody?, httpCode: Int): Result<T> {
        if (errorBody == null) return Result.failure(GENERIC_ERROR)

        val json = errorBody.string()
        val error = try {
            val errorObject = gson.fromJson(json, ErrorResponse::class.java)

            if (errorObject == null || !errorObject.isValid()) {
                GENERIC_ERROR
            } else {
                errorObject.toError()
            }
        } catch (exception: JsonSyntaxException) {
            GENERIC_ERROR
        }.copy(code = httpCode)
        return Result.failure(error)
    }

    private fun <T> handleException(exception: Exception): Result<T> {
        return when (exception) {
            is UnknownHostException -> Result.failure(CONNECTION_ERROR)
            else -> Result.failure(GENERIC_ERROR)
        }
    }
}