package com.dhmaciel.githubstars.home.ui.viewmodel

import androidx.lifecycle.Observer
import com.dhmaciel.githubstars.core.result.Result
import com.dhmaciel.githubstars.core.result.model.GitListError
import com.dhmaciel.githubstars.core.utils.ViewState
import com.dhmaciel.githubstars.home.domain.mapper.toDomain
import com.dhmaciel.githubstars.home.domain.model.GitList
import com.dhmaciel.githubstars.home.domain.repository.GitListRepository
import com.dhmaciel.githubstars.home.domain.repository.model.GitListResponse
import com.dhmaciel.githubstars.utils.CoroutinesTestExtension
import com.dhmaciel.githubstars.utils.InstantExecutorExtension
import com.dhmaciel.githubstars.utils.fromJson
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExperimentalCoroutinesApi
@Suppress("ClassName")
@ExtendWith(InstantExecutorExtension::class, CoroutinesTestExtension::class)
internal class GitListViewModelTest {

    private val repository = mockk<GitListRepository>()
    val viewModel = GitListViewModel(repository)

    private val gitListViewStateMock: Observer<ViewState<GitList>> = mockk()

    private val gitListMock =
        fromJson<GitListResponse>("gitlist/git_list_success_payload.json").toDomain()

    private val errorMock = GitListError(
        500,
        "Error",
        "123"
    )

    @BeforeEach
    fun setupMocks() {
        viewModel.gitListViewState.observeForever(gitListViewStateMock)

        justRun { gitListViewStateMock.onChanged(any()) }
    }

    @AfterEach
    fun resetMocks() {
        clearAllMocks()
    }

    @DisplayName("Given user wants see repositories")
    @Nested
    inner class GitRepositories {

        @Test
        @DisplayName("When fetch list Should have GitList data")
        fun fetchRepositoriesList() {
            coEvery { repository.fetchGitHubList(any()) } returns Result.success(gitListMock)

            viewModel.fetchRepositories()
            verifyOrder {
                gitListViewStateMock.onChanged(ViewState.loading())
                gitListViewStateMock.onChanged(ViewState.success(gitListMock))
            }
            verify(exactly = 0) { gitListViewStateMock.onChanged(ViewState.failure(errorMock)) }
        }

        @Test
        @DisplayName("When fetch list and has error Then Should has Error State")
        fun fetchRepositoriesListWithError() {
            val errorMock = GitListError(
                500,
                "Error",
                "123"
            )

            coEvery { repository.fetchGitHubList(any()) } returns Result.failure(errorMock)

            viewModel.fetchRepositories()
            verifyOrder {
                gitListViewStateMock.onChanged(ViewState.loading())
                gitListViewStateMock.onChanged(ViewState.failure(errorMock))
            }
            verify(exactly = 0) { gitListViewStateMock.onChanged(ViewState.success(gitListMock)) }
        }
    }
}