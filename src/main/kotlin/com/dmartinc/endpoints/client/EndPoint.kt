package com.dmartinc.endpoints.client

import io.ktor.http.*
import io.ktor.util.reflect.*

/**
 * Endpoint details: route (resource, input, output and httpMethod).
 */
class EndPoint<Route, Input, Output>(val httpMethod : HttpMethod, inputTypeInfo: TypeInfo){
    val hasNoInput = inputTypeInfo.type == Unit::class
}

inline fun <Route, reified Input, reified Output> defineEndPoint(httpMethod : HttpMethod) : EndPoint<Route, Input, Output> {
    val inputTypeInfo : TypeInfo = typeInfo<Input>()
    return EndPoint(httpMethod, inputTypeInfo)
}