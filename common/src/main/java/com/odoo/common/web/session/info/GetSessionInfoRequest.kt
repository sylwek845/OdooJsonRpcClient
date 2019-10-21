package com.odoo.common.web.session.info

import io.gripxtech.odoo.core.entities.session.info.GetSessionInfo
import io.gripxtech.odoo.core.entities.session.info.GetSessionInfoReqBody
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GetSessionInfoRequest {

    @POST("/web/session/get_session_info")
    fun getSessionInfo(
            @Body getSessionInfoReqBody: GetSessionInfoReqBody
    ): Observable<Response<GetSessionInfo>>
}
