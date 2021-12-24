package com.dhmaciel.githubstars.utils

import com.dhmaciel.githubstars.utils.MockUtils.loadJsonFromResources
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

object MockUtils {
    fun loadJsonFromResources(filename: String): String = this::class.java.getResource("/$filename")?.readText() ?: ""
}

inline fun <reified T> fromJson(jsonPath: String): T {
    val json = loadJsonFromResources(jsonPath)
    val inputStream =  InputStreamReader(json.byteInputStream(),Charsets.UTF_8)
    return Gson().fromJson(inputStream, object : TypeToken<T>() {}.type)
}

