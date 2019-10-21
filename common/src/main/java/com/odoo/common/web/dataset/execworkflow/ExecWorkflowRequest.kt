package com.odoo.common.web.dataset.execworkflow

import io.gripxtech.odoo.core.entities.dataset.execworkflow.ExecWorkflow
import io.gripxtech.odoo.core.entities.dataset.execworkflow.ExecWorkflowReqBody
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ExecWorkflowRequest {

    @POST("/web/dataset/exec_workflow")
    fun execWorkflow(
            @Body execWorkflowReqBody: ExecWorkflowReqBody
    ): Observable<Response<ExecWorkflow>>
}
