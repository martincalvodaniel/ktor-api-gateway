package com.dmartinc.endpoints.client

import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

/**
 * Call the endpoint that requires no input and returns the output as a ServiceResult
 */
suspend inline fun <reified Route : Any, reified Output>
        HttpClient.callEndpointForServiceResult(endPoint: EndPoint<Route, Unit, Output>, route: Route) =
    asServiceResult<Output> {
        callEndpoint(endPoint, route, Unit)
    }

/**
 * Calls the endpoint with the given input and returns the response
 */
suspend inline fun <reified Route : Any, reified Input, reified Output>
        HttpClient.callEndpoint(endPoint: EndPoint<Route, Input, Output>, route: Route, input: Input) =
    request(route) {
        method = endPoint.httpMethod
        contentType(ContentType.Application.Json)
        setBody(input)
    }