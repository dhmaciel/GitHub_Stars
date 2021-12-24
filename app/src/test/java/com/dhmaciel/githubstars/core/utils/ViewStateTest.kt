package com.dhmaciel.githubstars.core.utils

import com.dhmaciel.githubstars.core.result.model.GitListError
import com.dhmaciel.githubstars.core.utils.ViewStatus.FAILURE
import com.dhmaciel.githubstars.core.utils.ViewStatus.LOADING
import com.dhmaciel.githubstars.core.utils.ViewStatus.SUCCESS
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ViewStateTest {
    private val successMock = mockk<Unit>(relaxed = true)
    private val failureMock = mockk<GitListError>(relaxed = true)

    private val viewState = mockk<ViewState<Any>>(relaxed = true)

    private val successSubject = ViewState.success(successMock)
    private val loadingSubject = ViewState.loading<Unit>()
    private val failureSubject = ViewState.failure<Unit>(failureMock)

    private val anyAction = mockk<(value: Any) -> Unit>(relaxed = true)

    @BeforeEach
    fun setupMock() {
        every { viewState.handleIt(anyAction, anyAction, anyAction) } returns viewState
    }

    @DisplayName("Given View State")
    @Nested
    inner class GitRepositories {
        @Test
        fun `when view state is success shouldn't data value be null`() {
            val state = ViewState.success(successMock)
            assertEquals(SUCCESS, state.status)
            assertEquals(successMock, state.data)
            assertEquals(null, state.failure)
        }

        @Test
        fun `when view state is failure shouldn't failure value be null`() {
            val state = ViewState.failure<Unit>(failureMock)
            assertEquals(FAILURE, state.status)
            assertEquals(failureMock, state.failure)

            assertEquals(null, state.data)
        }

        @Test
        fun `when view state method is success shouldn't data value be null`() {
            var success: Unit? = null
            val result = successSubject.handleIt(onSuccess = { success = it })
            assertEquals(successMock, result.data)
            assertEquals(successMock, success)
            assertEquals(null, result.failure)
            assertEquals(SUCCESS, result.status)
        }

        @Test
        fun `when view state method is failure shouldn't failure value be null`() {
            var failure: GitListError? = null
            val result = failureSubject.handleIt(onFailure = { failure = it })
            assertEquals(failureMock, result.failure)
            assertEquals(failureMock, failure)
            assertEquals(null, result.data)
            assertEquals(FAILURE, result.status)
        }

        @Test
        fun `when view state method is loading shouldn't loading value be null`() {
            var isLoading: Boolean? = null
            val resultLoading = loadingSubject.handleIt(
                isLoading = { isLoading = it },
            )

            assertEquals(true, isLoading)
            assertEquals(LOADING, resultLoading.status)

            val result = successSubject.handleIt(
                isLoading = { isLoading = it },
            )

            assertEquals(SUCCESS, result.status)
            assertEquals(false, isLoading)
        }

        @Test
        fun `given two ViewState with same data when call equals then should return true`() {
            val viewState1 = ViewState.success("view-state")
            val viewState2 = ViewState.success("view-state")

            assertEquals(viewState1, viewState2)
        }

        @Test
        fun `given two ViewState with diff data when call equals then should return false`() {
            val viewState1 = ViewState.success("view-state1")
            val viewState2 = ViewState.success("view-state2")

            assertNotEquals(viewState1, viewState2)
        }

        @Test
        fun `given two ViewState on loading when call equals then should return true`() {
            val viewState1 = ViewState.loading<Any>()
            val viewState2 = ViewState.loading<Any>()

            assertEquals(viewState1, viewState2)
        }

        @Test
        fun `given two ViewState with same error when call equals then should return true`() {
            val viewState1 = ViewState.failure<Any>(GitListError(code = 1234))
            val viewState2 = ViewState.failure<Any>(GitListError(code = 1234))

            assertEquals(viewState1, viewState2)
        }

        @Test
        fun `given two ViewState with diff error when call equals then should return false`() {
            val viewState1 = ViewState.failure<Any>(GitListError(code = 1234))
            val viewState2 = ViewState.failure<Any>(GitListError(code = 1235))

            assertNotEquals(viewState1, viewState2)
        }
    }
}