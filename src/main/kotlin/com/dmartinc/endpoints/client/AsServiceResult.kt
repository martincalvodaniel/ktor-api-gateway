package com.dmartinc.endpoints.client

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.errors.IOException

suspend inline fun <reified T> asServiceResult(makeRequest: () -> HttpResponse): ServiceResult<T> {
    return try {
        val response = makeRequest()
        if (response.status.value in 200..299) {
            ServiceResult.Success(response.body())
        } else {
            val failure = when (response.status) {
                HttpStatusCode.NotFound -> ServiceFailure.NOT_FOUND
                HttpStatusCode.Unauthorized -> ServiceFailure.AUTHENTICATION
                else -> ServiceFailure.UNKNOWN
            }
            ServiceResult.Failure(failure)
        }
    } catch (e: IOException) {
        ServiceResult.Failure(ServiceFailure.NO_CONNECTION)
    }
}