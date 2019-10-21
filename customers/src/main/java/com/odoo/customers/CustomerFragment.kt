package com.odoo.customers

//import io.gripxtech.odoo.core.Odoo
//import io.gripxtech.odoo.databinding.FragmentCustomerBinding
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.reflect.TypeToken
import com.odoo.common.Odoo
import com.odoo.common.gson
import com.odoo.customers.databinding.FragmentCustomerBinding
import com.odoo.customers.proxy.CustomersProxyImpl
import io.objectbox.android.AndroidScheduler
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CustomerFragment : Fragment() {

    companion object {

        enum class CustomerType {
            Customer,
            Supplier,
            Company
        }

        private const val TYPE = "type"

        fun newInstance(customerType: CustomerType) =
            CustomerFragment().apply {
                arguments = Bundle().apply {
                    putString(TYPE, customerType.name)
                }
            }
    }

    lateinit var binding: FragmentCustomerBinding private set
    lateinit var compositeDisposable: CompositeDisposable private set

    private lateinit var customerType: CustomerType
    private lateinit var drawerToggle: ActionBarDrawerToggle

    val adapter: CustomerAdapter by lazy {
        CustomerAdapter(this, arrayListOf())
    }

    private val customerListType =
        object : TypeToken<ArrayList<com.odoo.customers.entities.CustomerResponse>>() {}.type
    private val limit = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        compositeDisposable = CompositeDisposable()

        // Inflate the layout for this fragment
        binding = FragmentCustomerBinding.inflate(inflater, container, false)

        val p = CustomersProxyImpl()
        p.getRecordsNumber()

            .subscribe({ success ->
            Toast.makeText(context, success.toString(), Toast.LENGTH_LONG).show()
        },
            { e ->
                e.printStackTrace()
            }
        )
        return binding.root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        activity = getActivity()
//        arguments?.let {
//            customerType = CustomerType.valueOf(it.getString(
//                TYPE
//            ).orEmpty())
//        }
//
//        // Hiding MainActivity's AppBarLayout as well as NestedScrollView first
//        activity.binding.abl.visibility = View.GONE
//        activity.binding.nsv.visibility = View.GONE
//
//        when (customerType) {
//            CustomerType.Supplier -> {
//                activity.binding.nv.menu.findItem(R.id.nav_supplier).isChecked = true
//                activity.setTitle(R.string.action_supplier)
//            }
//            CustomerType.Company -> {
//                activity.binding.nv.menu.findItem(R.id.nav_company).isChecked = true
//                activity.setTitle(R.string.action_company)
//            }
//            else -> {
//                activity.binding.nv.menu.findItem(R.id.nav_customer).isChecked = true
//                activity.setTitle(R.string.action_customer)
//            }
//        }
//        activity.setSupportActionBar(binding.tb)
//        val actionBar = activity.supportActionBar
//        if (actionBar != null) {
//            actionBar.setHomeButtonEnabled(true)
//            actionBar.setDisplayHomeAsUpEnabled(true)
//        }
//
//        drawerToggle = ActionBarDrawerToggle(activity, activity.binding.dl,
//                binding.tb, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
//        activity.binding.dl.addDrawerListener(drawerToggle)
//        drawerToggle.syncState()
//
//        val layoutManager = LinearLayoutManager(
//            activity, LinearLayoutManager.VERTICAL, false
//        )
//        binding.rv.layoutManager = layoutManager
//        binding.rv.addItemDecoration(
//            DividerItemDecoration(
//                activity,
//                LinearLayoutManager.VERTICAL
//            )
//        )
//
//        adapter.setupScrollListener(binding.rv)
//
//        if (!adapter.hasRetryListener()) {
//            adapter.retryListener {
//                fetchCustomer()
//            }
//        }
//
//        binding.srl.setOnRefreshListener {
//            adapter.clear()
//            if (!adapter.hasMoreListener()) {
//                adapter.showMore()
//                fetchCustomer()
//            }
//            binding.srl.post {
//                binding.srl.isRefreshing = false
//            }
//        }
//
//        if (adapter.rowItemCount == 0) {
//            adapter.showMore()
//            fetchCustomer()
//        }
//
//        binding.rv.adapter = adapter
//    }
//
//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//        if (::drawerToggle.isInitialized) {
//            drawerToggle.onConfigurationChanged(newConfig)
//        }
//    }
//
//    override fun onDestroyView() {
//        compositeDisposable.dispose()
//        super.onDestroyView()
//    }
//
//    private fun fetchCustomer() {
//        Odoo.searchRead("res.partner", com.odoo.customers.entities.CustomerResponse.fields,
//                when (customerType) {
//                    CustomerType.Customer -> {
//                        listOf(listOf("customer", "=", true))
//                    }
//                    CustomerType.Supplier -> {
//                        listOf(listOf("supplier", "=", true))
//                    }
//                    CustomerType.Company -> {
//                        listOf(listOf("is_company", "=", true))
//                    }
//                }, adapter.rowItemCount, limit, "name ASC") {
//
//            onSubscribe { disposable ->
//                compositeDisposable.add(disposable)
//            }
//
//            onNext { response ->
//                if (response.isSuccessful) {
//                    val searchRead = response.body()!!
//                    if (searchRead.isSuccessful) {
//                        adapter.hideEmpty()
//                        adapter.hideError()
//                        adapter.hideMore()
//                        val items: ArrayList<com.odoo.customers.entities.CustomerResponse> = gson.fromJson(searchRead.result.records, customerListType)
//                        if (items.size < limit) {
//                            adapter.removeMoreListener()
//                            if (items.size == 0 && adapter.rowItemCount == 0) {
//                                adapter.showEmpty()
//                            }
//                        } else {
//                            if (!adapter.hasMoreListener()) {
//                                adapter.moreListener {
//                                    fetchCustomer()
//                                }
//                            }
//                        }
//                        adapter.addRowItems(items)
//                        compositeDisposable.dispose()
//                        compositeDisposable = CompositeDisposable()
//                    } else {
//                        adapter.showError(searchRead.errorMessage)
//                    }
//                } else {
//                    adapter.showError(response.errorBodySpanned)
//                }
//                adapter.finishedMoreLoading()
//            }
//
//            onError { error ->
//                error.printStackTrace()
//                adapter.showError(error.message ?: getString(R.string.generic_error))
//                adapter.finishedMoreLoading()
//            }
//        }
//    }
}
