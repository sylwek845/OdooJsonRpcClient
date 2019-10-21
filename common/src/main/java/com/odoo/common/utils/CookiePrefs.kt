package com.odoo.common.utils

import android.app.Application
import android.content.Context
import com.google.gson.reflect.TypeToken
import com.odoo.common.getActiveOdooUser
import com.odoo.common.gson
import okhttp3.Cookie

class CookiePrefs(context: Context) : Prefs(TAG, context) {

    companion object {
        const val TAG = "CookiePrefs"

        lateinit var app: Application

        @JvmStatic
        fun getCookiePrefs() =
            CookiePrefs(app)
    }

    private val type = object : TypeToken<ArrayList<ClonedCookie>>() {}.type

    fun getCookies(): ArrayList<Cookie> {
        val activeUser = context.getActiveOdooUser()
        if (activeUser != null) {
            val cookiesStr = getString(activeUser.androidName)
            if (cookiesStr.isNotEmpty()) {
                val clonedCookies: ArrayList<ClonedCookie> = gson.fromJson(cookiesStr, type)
                val cookies = arrayListOf<Cookie>()
                for (clonedCookie in clonedCookies) {
                    cookies += clonedCookie.toCookie()
                }
                return cookies
            }
        }
        return arrayListOf()
    }

    fun setCookies(cookies: ArrayList<Cookie>) {
        val clonedCookies = arrayListOf<ClonedCookie>()
        for (cookie in cookies) {
            clonedCookies += ClonedCookie.fromCookie(cookie)
        }
        val cookiesStr = gson.toJson(clonedCookies, type)
        val activeUser = context.getActiveOdooUser()
        if (activeUser != null) {
            putString(activeUser.androidName, cookiesStr)
        }
    }
}
