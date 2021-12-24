package com.dhmaciel.githubstars.core.result

import com.dhmaciel.githubstars.core.result.model.ErrorResponse
import com.dhmaciel.githubstars.core.result.model.GitListError
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class RetrofitResultImplTest {

    private val requestMock = mockk<suspend () -> Response<Any>>()
    private val gsonMock = mockk<Gson>()

    private val errorMock = mockk<GitListError>(relaxed = true)
    private val responseMock = mockk<Response<Any>>(relaxed = true)
    private val responseBodyMock = mockk<ResponseBody>(relaxed = true)

    private val httpCodeStub = 400

    private val subject = RetrofitResultImpl(gsonMock)

    @BeforeEach
    fun setupMocks() {
        every { gsonMock.fromJson(any<String>(), GitListError::class.java) } returns errorMock
        coEvery { requestMock.invoke() } returns responseMock

        every { responseMock.errorBody() } returns responseBodyMock
        every { responseMock.body() } returns responseMock
        every { responseMock.code() } returns httpCodeStub
        every { responseMock.isSuccessful } returns true
    }

    @Test
    fun `Given a request When isSuccessful and body isn't null Then should return success`() {
        runBlockingTest {
            val body = "body"
            every { responseMock.isSuccessful } returns true
            every { responseMock.body() } returns body

            val result = subject.getResult(requestMock)
            assertEquals(Result.success(body), result)
        }
    }

    @Test
    fun `Given a request When isSuccessful is false and errorBody is null Then should return GENERIC_ERROR`() {
        runBlockingTest {
            every { responseMock.isSuccessful } returns false
            every { responseMock.body() } returns "body"
            every { responseMock.errorBody() } returns null

            val result = subject.getResult(requestMock)
            assertEquals(Result.failure<Any>(GENERIC_ERROR), result)
        }
    }

    @Test
    fun `Given a request When body is null and errorBody is null Then should return GENERIC_ERROR`() {
        runBlockingTest {
            every { responseMock.isSuccessful } returns true
            every { responseMock.body() } returns null
            every { responseMock.errorBody() } returns null

            val result = subject.getResult(requestMock)
            assertEquals(Result.failure<Any>(GENERIC_ERROR), result)
        }
    }

    @Test
    fun `Given a request When is valid Then should return the object`() {
        runBlockingTest {
            every { errorMock.isValid() } returns true
            val result = subject.getResult(requestMock)

            assertEquals(Result.success<Any>(responseMock), result)
        }
    }

    @Test
    fun `Given a request When fromJson throws JsonSyntaxException Then should return GENERIC_ERROR`() {
        runBlockingTest {
            val exception = JsonSyntaxException("")
            val jsonBody = "Error body"
            every { responseBodyMock.string() } returns jsonBody
            every { responseMock.isSuccessful } returns false
            every {
                gsonMock.fromJson(any<String>(), ErrorResponse::class.java)
            } throws exception

            val result = subject.getResult(requestMock)

            val error = GENERIC_ERROR.copy(code = httpCodeStub)
            assertEquals(Result.failure<Any>(error), result)
        }
    }

    @Test
    fun `Given a request When fromJson return null Then should return GENERIC_ERROR`() {
        runBlockingTest {
            every { responseMock.isSuccessful } returns false
            val jsonBody = "Error body"
            every { responseBodyMock.string() } returns jsonBody
            every {
                gsonMock.fromJson(any<String>(), ErrorResponse::class.java)
            } returns null

            val result = subject.getResult(requestMock)

            val error = GENERIC_ERROR.copy(code = httpCodeStub)
            assertEquals(Result.failure<Any>(error), result)
        }
    }

    @Test
    fun `Given a request When isValid is false Then should return GENERIC_ERROR`() {
        runBlockingTest {
            every { responseMock.isSuccessful } returns false
            every { errorMock.isValid() } returns false
            val result = subject.getResult(requestMock)

            assertEquals(Result.failure<Any>(GENERIC_ERROR), result)
        }
    }

    @Test
    fun `Given a request When throws an IOException Then should return GENERIC_ERROR`() {
        runBlockingTest {
            val exception = IOException()
            every { responseMock.body() } throws exception
            val result = subject.getResult(requestMock)

            assertEquals(Result.failure<Any>(GENERIC_ERROR), result)
        }
    }

    @Test
    fun `Given a request When throws an UnknownHostException Then should return CONNECTION_ERROR`() {
        runBlockingTest {
            val exception = UnknownHostException()
            every { responseMock.body() } throws exception
            val result = subject.getResult(requestMock)

            assertEquals(Result.failure<Any>(CONNECTION_ERROR), result)
        }
    }

}