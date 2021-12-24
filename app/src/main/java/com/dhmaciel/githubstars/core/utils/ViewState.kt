package com.dhmaciel.githubstars.core.utils

import androidx.lifecycle.MutableLiveData
import com.dhmaciel.githubstars.core.result.model.GitListError
import com.dhmaciel.githubstars.core.utils.ViewStatus.FAILURE
import com.dhmaciel.githubstars.core.utils.ViewStatus.LOADING
import com.dhmaciel.githubstars.core.utils.ViewStatus.SUCCESS

class ViewState<T> private constructor(
    val status: ViewStatus = SUCCESS,
    val data: T? = null,
    val failure: GitListError? = null
) {

    companion object {
        fun <T> success(data: T): ViewState<T> = ViewState(SUCCESS, data)

        fun <T> loading(): ViewState<T> = ViewState(LOADING)

        fun <T> failure(failure: GitListError): ViewState<T> = ViewState(FAILURE, failure = failure)
    }

    fun handleIt(
        onSuccess: (T) -> Unit = {},
        onFailure: (GitListError) -> Unit = {},
        isLoading: (Boolean) -> Unit = {}
    ): ViewState<T> {
        when (status) {
            SUCCESS -> {
                this.data?.let { onSuccess(it) }
                isLoading(false)
            }
            LOADING -> isLoading(true)
            FAILURE -> {
                this.failure?.let { onFailure(it) }
                isLoading(false)
            }
        }
        return this
    }

    override fun equals(other: Any?): Boolean {
        return if (other is ViewState<*>) {
            other.data == this.data && other.failure == this.failure && other.status == this.status
        } else {
            false
        }
    }
}

enum class ViewStatus {
    LOADING, SUCCESS, FAILURE
}

fun <T> MutableLiveData<ViewState<T>>.postSuccess(data: T) = postValue(ViewState.success(data))

fun <T> MutableLiveData<ViewState<T>>.postLoading() = postValue(ViewState.loading())

fun <T> MutableLiveData<ViewState<T>>.postFailure(failure: GitListError) = postValue(ViewState.failure(failure))