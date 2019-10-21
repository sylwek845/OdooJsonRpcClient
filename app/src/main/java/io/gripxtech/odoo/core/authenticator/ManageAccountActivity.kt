package io.gripxtech.odoo.core.authenticator

import androidx.databinding.DataBindingUtil
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.odoo.common.getOdooUsers
import io.gripxtech.odoo.App
import io.gripxtech.odoo.R
import com.odoo.common.utils.recycler.decorators.VerticalLinearItemDecorator
import io.gripxtech.odoo.databinding.ActivityManageAccountBinding
import io.reactivex.disposables.CompositeDisposable

class ManageAccountActivity : AppCompatActivity() {

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    lateinit var app: App private set
    lateinit var compositeDisposable: CompositeDisposable private set
    lateinit var binding: ActivityManageAccountBinding private set
    lateinit var adapter: ManageAccountAdapter private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as App
        compositeDisposable = CompositeDisposable()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manage_account)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val users = getOdooUsers()
        val layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
        binding.rv.layoutManager = layoutManager
        binding.rv.addItemDecoration(
            VerticalLinearItemDecorator(
                resources.getDimensionPixelOffset(R.dimen.default_8dp)
            )
        )

        adapter = ManageAccountAdapter(this, ArrayList(users))
        binding.rv.adapter = adapter
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}
