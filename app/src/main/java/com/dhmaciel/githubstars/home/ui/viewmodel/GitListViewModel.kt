package com.dhmaciel.githubstars.home.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhmaciel.githubstars.core.utils.ViewState
import com.dhmaciel.githubstars.core.utils.postFailure
import com.dhmaciel.githubstars.core.utils.postLoading
import com.dhmaciel.githubstars.core.utils.postSuccess
import com.dhmaciel.githubstars.home.domain.model.GitList
import com.dhmaciel.githubstars.home.domain.repository.GitListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class GitListViewModel @Inject constructor(private val gitListRepository: GitListRepository) :
    ViewModel() {

    private val _gitListViewState = MutableLiveData<ViewState<GitList>>()
    val gitListViewState: LiveData<ViewState<GitList>> = _gitListViewState

    fun fetchRepositories(pageNumber: Int = 1) {
        viewModelScope.launch {
            _gitListViewState.postLoading()
            gitListRepository.fetchGitHubList(pageNumber)
                .onSuccess {
                    _gitListViewState.postSuccess(it)
                }
                .onFailure {
                    _gitListViewState.postFailure(it)
                }
        }
    }
}