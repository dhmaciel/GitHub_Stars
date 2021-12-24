package com.dhmaciel.githubstars.core.result

import com.dhmaciel.githubstars.core.result.model.GitListError

class Result<T> private constructor(private val value: T?, private val error: GitListError?) {

    suspend fun onSuccess(action: suspend (value: T) -> Unit): Result<T> {
        value?.let { action(it) }
        return this
    }

    suspend fun onFailure(action: suspend (GitListError) -> Unit): Result<T> {
        error?.let { action(it) }
        return this
    }

    suspend fun onAny(action: suspend (Result<T>) -> Unit): Result<T> {
        value?.let { action(this) }
        error?.let { action(this) }
        return this
    }
    //TODO: Necessary?
    suspend fun <V> flatMap(action: suspend (T) -> Result<V>): Result<V> {
        if (value == null) return Result(value, error)
        return action(value)
    }

    suspend fun <V> map(action: suspend (T) -> V): Result<V> {
        if (value == null) return Result(value, error)
        return success(action(value))
    }
    //TODO: End

    suspend fun mapError(action: suspend (GitListError) -> GitListError): Result<T> {
        if (error == null) return this
        return failure(action(error))
    }

    fun getOrDefault(defaultValue: T): Result<T> {
        return if (value == null) success(defaultValue) else this
    }

    fun getOrNull(): T? = value

    fun errorOrNull(): GitListError? = error

    companion object {
        fun <T> success(data: T) = Result(value = data, error = null)
        fun <T> failure(error: GitListError) = Result<T>(value = null, error = error)
//        fun <T> failure(errorResponse: ErrorResponse) = Result<T>(value = null, error = errorResponse.toError())
    }

    override fun toString(): String {
        return "Value: ${this.value}\n" +
                "Error: ${this.error}"
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Result<*>) {
            other.value == this.value && other.error == this.error
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = value?.hashCode() ?: 0
        result = 31 * result + (error?.hashCode() ?: 0)
        return result
    }
}