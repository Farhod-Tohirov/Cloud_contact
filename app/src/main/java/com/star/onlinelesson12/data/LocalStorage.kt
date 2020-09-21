package com.star.onlinelesson11.data

import android.content.Context
import android.content.SharedPreferences
import com.example.task1.Tools.BooleanPreference
import com.example.task1.Tools.StringPreference

class LocalStorage private constructor(context: Context) {
    companion object {
        lateinit var instance: LocalStorage; private set

        fun init(context: Context) {
            instance = LocalStorage(context)
        }
    }

    private val pref: SharedPreferences =
        context.getSharedPreferences("LocalStorage", Context.MODE_PRIVATE)

    var token by StringPreference(pref, "")
    var isRemembered by BooleanPreference(pref, false)
    var isChecked by BooleanPreference(pref, false)
}