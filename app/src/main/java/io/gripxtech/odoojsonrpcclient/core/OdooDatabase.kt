package io.gripxtech.odoojsonrpcclient.core

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import io.gripxtech.odoojsonrpcclient.App
import io.gripxtech.odoojsonrpcclient.core.persistence.AppTypeConverters
import io.gripxtech.odoojsonrpcclient.customer.entities.Customer
import io.gripxtech.odoojsonrpcclient.customer.entities.CustomerDao

@Database(entities = [
    /* Add Room Entities here: BEGIN */

    Customer::class // res.partner

    /* Add Room Entities here: END */
], version = 1, exportSchema = true)
@TypeConverters(AppTypeConverters::class)
abstract class OdooDatabase : RoomDatabase() {

    companion object {

        lateinit var app: App

        var database: OdooDatabase? = null
            get() {
                if (field == null) {
                    field = Room.databaseBuilder(app, OdooDatabase::class.java, "${Odoo.user.androidName}.db").build()
                }
                return field
            }
    }

    /* Add Room DAO(s) here: BEGIN */

    abstract fun customerDao(): CustomerDao

    /* Add Room DAO(s) here: END */
}
