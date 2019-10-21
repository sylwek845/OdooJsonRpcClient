package io.gripxtech.odoojsonrpcclient.core

import android.accounts.Account
import androidx.databinding.BindingAdapter
import android.util.Base64
import android.widget.ImageView
import com.google.gson.JsonObject
import io.gripxtech.odoojsonrpcclient.App
import io.gripxtech.odoojsonrpcclient.GlideApp
import io.gripxtech.odoojsonrpcclient.core.utils.Retrofit2Helper

data class OdooUser(
    val protocol: Retrofit2Helper.Companion.Protocol = Retrofit2Helper.Companion.Protocol.HTTP,
    val host: String = "",
    val login: String = "",
    val password: String = "",
    val database: String = "",
    val serverVersion: String = "",
    val isAdmin: Boolean = false,
    val id: Int = 0,
    val name: String = "",
    val imageSmall: String = "",
    val partnerId: Int = 0,
    val context: JsonObject = JsonObject(),
    val isActive: Boolean = false,
    val account: Account = Account("false", App.KEY_ACCOUNT_TYPE)
) {
    val androidName: String
        get() = "$login[$database]"

    val timezone: String
        get() = context["tz"].asString

    companion object {
        @JvmStatic
        @BindingAdapter("image_small", "name")
        fun ImageView.loadImage(imageSmall: String, name: String) {
            GlideApp.with(this)
                .asBitmap()
                .load(
                    if (imageSmall.isNotEmpty())
                        Base64.decode(imageSmall, Base64.DEFAULT)
                    else
                        (this.context.applicationContext as App)
                            .getLetterTile(if (name.isNotEmpty()) name else "X")
                )
                .into(this)
        }
    }
}
