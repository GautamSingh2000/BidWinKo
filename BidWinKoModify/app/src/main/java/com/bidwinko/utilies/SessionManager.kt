package com.bidwinko.utilies

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class SessionManager (context: Context){
    private lateinit var context : Context
    private lateinit var sp : SharedPreferences
    private lateinit var editor : SharedPreferences.Editor

    init {
        this.context =  context
        sp =context.getSharedPreferences(Constants.BinwinKO_SP,0)
        editor = sp.edit()
    }

    fun InitializeValue(key : String , value : String)
    {
        Log.e("SessionManager","vale for $key is saved $value")
        editor.putString(key.toString(),value.toString())
        editor.commit()
    }

    fun GetValue(Key : String):String{
        val result = sp.getString(Key,"null")
        Log.e("SesseionManager","fetching vale for $Key is $result")
        return result.toString()
    }

    fun Sign() {
        editor.putBoolean(Constants.LOGIN_STATE, true)
        Log.e("SessionManager", "Signin")
        editor.commit()
    }

    fun CheckLogin():Boolean
    {
        return if (sp.contains(Constants.LOGIN_STATE)) {
            Log.e("SessionManager", "chceking Singin ans is " + true)
            true
        } else {
            Log.e("SessionManager", "chceking Singin ans is " + false)
            false
        }
    }

    fun LogOut()
    {
        editor.clear()
        editor.commit()
    }
}