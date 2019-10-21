package com.odoo.customers.proxy

import com.odoo.customers.entities.CustomerResponse
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*

interface CustomersProxy {

    fun getCustomers(): Observable<List<CustomerResponse>>

    fun getRecordsNumber(): Maybe<Int>
}


