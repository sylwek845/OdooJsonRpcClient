package com.odoo.common

import android.accounts.AccountManager
import android.content.Context
import android.os.Build
import android.os.Handler
import com.google.gson.*
import com.odoo.common.utils.CookiePrefs
import com.odoo.common.utils.LetterTileProvider

val gson: Gson by lazy {
    GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
}

fun String.toJsonElement(): JsonElement = gson.fromJson(this, JsonElement::class.java)

fun String.toJsonPrimitive(): JsonPrimitive = toJsonElement().asJsonPrimitive

fun String.toJsonObject(): JsonObject = toJsonElement().asJsonObject

fun String.toJsonArray(): JsonArray = toJsonElement().asJsonArray

val JsonArray.asIntList: List<Int>
    get() = this.map {
        it.asInt
    }

const val KEY_ACCOUNT_TYPE =
    "io.gripxtech.odoojsonrpcclient.auth" //FIXME(Implement Proper application ID)

fun Context.getActiveOdooUser(): OdooUser? {
    getOdooUsers()
        .filter { it.isActive }
        .forEach { return it }
    return null
}

fun Context.getOdooUsers(): List<OdooUser> {
    val manager = AccountManager.get(this)
    val odooUsers = ArrayList<OdooUser>()
    manager.getAccountsByType(KEY_ACCOUNT_TYPE)
        .map {
            Odoo.fromAccount(manager, it)
        }
        .forEach { odooUsers += it }
    return odooUsers.toList()
}

fun Context.odooUserByAndroidName(androidName: String): OdooUser? {
    getOdooUsers()
        .filter { it.androidName == androidName }
        .forEach { return it }
    return null
}


fun Context.loginOdooUser(odooUser: OdooUser): OdooUser? {
    do {
        val user = getActiveOdooUser()
        if (user != null) {
            logoutOdooUser(user)
        }
    } while (user != null)
    val accountManager = AccountManager.get(this)
    accountManager.setUserData(odooUser.account, "active", "true")

    return getActiveOdooUser()
}

fun Context.logoutOdooUser(odooUser: OdooUser) {
    val accountManager = AccountManager.get(this)
    accountManager.setUserData(odooUser.account, "active", "false")
}

fun String.trimFalse(): String = if (this != "false") this else ""


fun Context.deleteOdooUser(odooUser: OdooUser): Boolean {
    val accountManager = AccountManager.get(this)
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
        accountManager.removeAccountExplicitly(odooUser.account)
    } else {
        @Suppress("DEPRECATION")
        val result = accountManager.removeAccount(odooUser.account, {

        }, Handler(this.mainLooper))
        result != null && result.result != null && result.result!!
    }
}

fun Context.getLetterTile(displayName: String): ByteArray =
    LetterTileProvider(this).getLetterTile(displayName)

