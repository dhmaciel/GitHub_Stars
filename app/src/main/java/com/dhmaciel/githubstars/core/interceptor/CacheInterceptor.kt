package com.dhmaciel.githubstars.core.interceptor

import com.dhmaciel.githubstars.core.utils.Constants.CACHE_TIME
import java.io.IOException
import java.util.concurrent.TimeUnit.MINUTES
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

const val CACHE_CONTROL_HEADER = "Cache-Control"
const val CACHE_CONTROL_NO_CACHE = "no-cache"


class CacheInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalResponse = chain.proceed(request)

        val shouldUseCache = request.header(CACHE_CONTROL_HEADER) != CACHE_CONTROL_NO_CACHE
        if (!shouldUseCache) return originalResponse

        val cacheControl = CacheControl.Builder()
            .maxAge(CACHE_TIME, MINUTES)
            .build()

        return originalResponse.newBuilder()
            .header(CACHE_CONTROL_HEADER, cacheControl.toString())
            .build()
    }
}