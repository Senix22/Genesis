package com.example.genesis.util

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> Gson.safeParseJson(jsonString: String?): T? {
    return try {
        fromJson(jsonString, T::class.java)
    } catch (e: Exception) {
        Log.e("Gson.safeParseJson", e.message, e)

        return null
    }
}


 fun <T> List<T>.prepareForTwoWayPaging(): List<T> {
    val first = first()
    val last = last()
    return toMutableList().apply {
        add(0, last)
        add(first)
    }
}
