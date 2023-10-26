package com.example.ebook_younous

import android.content.Context

class SharedPreferencesManager {
    companion object {
        const val searchKey = "searchKey"
        const val preferencesFile = "preferencesFile"
    }

    fun saveSearchCriteria(search:String, context: Context) {
        val sharedPreferences = context.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE)
        sharedPreferences
            .edit()
            .putString(searchKey, search)
            .apply()
    }
}