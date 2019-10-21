package com.odoo.common.web.database.listdb

import io.gripxtech.odoo.core.entities.database.listdb.ListDb
import io.gripxtech.odoo.core.entities.database.listdb.ListDbReqBody
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ListDbRequest {

    @POST("/web/database/list")
    fun listDb(
            @Body listDbReqBody: ListDbReqBody
    ): Observable<Response<ListDb>>
}