package io.gripxtech.odoo.core.preferences

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import io.gripxtech.odoo.App
import io.gripxtech.odoo.R
import io.gripxtech.odoo.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    private lateinit var app: App
    lateinit var binding: ActivitySettingsBinding private set

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        app = application as App
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}
