package io.gripxtech.odoojsonrpcclient.customer.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.databinding.BindingAdapter
import android.util.Base64
import android.widget.ImageView
import com.google.gson.JsonElement
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.gripxtech.odoojsonrpcclient.App
import io.gripxtech.odoojsonrpcclient.GlideApp
import io.gripxtech.odoojsonrpcclient.toJsonElement

@Entity(tableName = "res.partner", primaryKeys = ["_id", "server_id"])
data class Customer(

        @ColumnInfo(name = "_id")
        var _id: Long = 0,

        @Expose
        @SerializedName("id")
        @ColumnInfo(name = "server_id")
        var serverId: Long = 0,

        @Expose
        @SerializedName("name")
        @ColumnInfo(name = "name")
        var name: String = "",

        @Expose
        @SerializedName("email")
        @ColumnInfo(name = "email")
        var email: String = "",

        @Expose
        @SerializedName("company_name")
        @ColumnInfo(name = "company_name")
        var companyName: String = "false",

        @Expose
        @SerializedName("image_small")
        @ColumnInfo(name = "image_small")
        var imageSmall: String = "false",

        @Expose
        @SerializedName("website")
        @ColumnInfo(name = "website")
        var website: String = "false",

        @Expose
        @SerializedName("phone")
        @ColumnInfo(name = "phone")
        var phone: String = "false",

        @Expose
        @SerializedName("mobile")
        @ColumnInfo(name = "mobile")
        var mobile: String = "false",

        @Expose
        @SerializedName("full_address")
        @ColumnInfo(name = "full_address")
        var fullAddress: String = "false",

        @Expose
        @SerializedName("state_id")
        @ColumnInfo(name = "state_id")
        var stateId: JsonElement = "false".toJsonElement(),

        @Expose
        @SerializedName("country_id")
        @ColumnInfo(name = "country_id")
        var countryId: JsonElement = "false".toJsonElement(),

        @Expose
        @SerializedName("comment")
        @ColumnInfo(name = "comment")
        var comment: String = "false",

        @Expose
        @SerializedName("is_company")
        @ColumnInfo(name = "is_company")
        var isCompany: Boolean = false
) {
    companion object {
        @JvmStatic
        @BindingAdapter("image_small", "name")
        fun loadImage(view: ImageView, imageSmall: String, name: String) {
            GlideApp.with(view.context)
                    .asBitmap()
                    .load(
                            if (imageSmall.isNotEmpty())
                                Base64.decode(imageSmall, Base64.DEFAULT)
                            else
                                (view.context.applicationContext as App)
                                        .getLetterTile(if (name.isNotEmpty()) name else "X"))
                    .into(view)
        }

        @JvmField
        val fieldsMap: Map<String, String> = mapOf(
                "id" to "id", "name" to "Name", "email" to "Email",
                "company_name" to "Company Name", "image_small" to "Image", "website" to "Website",
                "phone" to "Phone Number", "mobile" to "Mobile Number",/* "full_address" to "Full Address",*/
                "state_id" to "State", "country_id" to "Country", "comment" to "Internal Note",
                "is_company" to "Is Company")

        @JvmField
        val fields: ArrayList<String> = fieldsMap.keys.toMutableList() as ArrayList<String>
    }
}