package com.odoo.common.utils

import androidx.constraintlayout.widget.ConstraintLayout
import android.util.Base64
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import com.odoo.common.OdooUser
import com.odoo.common.R
import com.odoo.common.getLetterTile
import com.odoo.common.trimFalse

class NavHeaderViewHolder(view: View) {
    val pic: CircleImageView = view.findViewById(R.id.userImage)
    val name: TextView = view.findViewById(R.id.header_name)
    val email: TextView = view.findViewById(R.id.header_details)
    val menuToggle: ConstraintLayout = view.findViewById(R.id.menuToggle)
    val menuToggleImage: ImageView = view.findViewById(R.id.ivDropdown)


    fun setUser(user: OdooUser) {
        name.text = user.name
        email.text = user.login
        if (user.imageSmall.trimFalse().isNotEmpty()) {
            val byteArray = Base64.decode(user.imageSmall, Base64.DEFAULT)
            Glide.with(pic.context)
                    .asBitmap()
                    .load(byteArray)
                    .into(pic)
        } else {
            Glide.with(pic.context)
                    .asBitmap()
                    .load((pic.context.applicationContext).getLetterTile(user.name))
                    .into(pic)
        }
    }
}
