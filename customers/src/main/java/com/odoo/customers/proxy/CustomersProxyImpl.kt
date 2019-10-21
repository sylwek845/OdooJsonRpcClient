package com.odoo.customers.proxy

import com.odoo.common.Odoo
import com.odoo.customers.entities.CustomerResponse
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class CustomersProxyImpl(

) : CustomersProxy {
    private val compositeDisposable = CompositeDisposable()
    override fun getCustomers(): Observable<List<CustomerResponse>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRecordsNumber(): Maybe<Int> {
        Odoo.searchCount(
            model = "res.users",
            args = CustomerResponse.fields
        ) {
            onSubscribe { compositeDisposable.add(it) }

            onError { it.printStackTrace() }

            onNext { response ->
                Maybe.fromCallable { response.body()?.result }
            }
        }
        return Maybe.fromCallable { 0 }
    }
}