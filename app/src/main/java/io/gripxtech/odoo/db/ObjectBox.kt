//package io.gripxtech.odoo.db
//
//import android.content.Context
//import com.odoo.customers.domain.MyObjectBox
//import io.objectbox.BoxStore
//
//object ObjectBox {
//    lateinit var boxStore: BoxStore
//        private set
//
//    fun init(context: Context) {
//        boxStore = MyObjectBox.builder()
//            .androidContext(context.applicationContext)
//            .build()
//    }
//}