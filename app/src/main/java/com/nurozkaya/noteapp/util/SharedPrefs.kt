package com.nurozkaya.noteapp.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPrefs {

    companion object{
        private val TIME ="time"
        private var sharedPreferences:SharedPreferences?=null

        @Volatile private var instance:SharedPrefs?=null
        private val lock=Any()
        operator fun invoke(context: Context):SharedPrefs= instance?: synchronized(lock) {
            instance?:customSharedPrefs(context).also {
                instance=it
            }
        }

        private fun customSharedPrefs(context: Context): SharedPrefs {
            sharedPreferences=androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPrefs()
        }
    }

    fun saveTime(time:Long) {
        sharedPreferences?.edit(commit=true) {
            putLong(TIME,0)
        }
    }

    fun takeTime()= sharedPreferences?.getLong(TIME,0)


}