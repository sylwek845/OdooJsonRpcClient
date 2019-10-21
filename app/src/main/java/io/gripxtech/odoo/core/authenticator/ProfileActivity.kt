package io.gripxtech.odoo.core.authenticator

import androidx.databinding.DataBindingUtil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.odoo.common.getActiveOdooUser
import io.gripxtech.odoo.App
import io.gripxtech.odoo.R
import io.gripxtech.odoo.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var app: App
    private lateinit var binding: ActivityProfileBinding

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        app = application as App
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val user = getActiveOdooUser()
        if (user != null) {
            binding.user = user
        }
    }
}
