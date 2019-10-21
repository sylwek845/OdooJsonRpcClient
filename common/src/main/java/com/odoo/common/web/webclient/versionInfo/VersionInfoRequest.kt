package com.odoo.common.web.webclient.versionInfo

import io.gripxtech.odoo.core.entities.webclient.versionInfo.VersionInfo
import io.gripxtech.odoo.core.entities.webclient.versionInfo.VersionInfoReqBody
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface VersionInfoRequest {

    @POST("/web/webclient/version_info")
    fun versionInfo(
            @Body versionInfoReqBody: VersionInfoReqBody
    ): Observable<Response<VersionInfo>>
}