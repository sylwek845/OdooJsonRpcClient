package com.odoo.common.utils.recycler.entities

import androidx.annotation.DrawableRes

data class EmptyItem(
        val message: CharSequence,
        @DrawableRes
        val drawableResId: Int
)
