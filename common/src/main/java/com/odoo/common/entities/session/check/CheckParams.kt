package io.gripxtech.odoo.core.entities.session.check

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CheckParams(

        @field:Expose
        @field:SerializedName("context")
        val context: JsonObject = JsonObject()
)
