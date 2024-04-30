package com.dmartinc.endpoints.api

import com.dmartinc.endpoints.client.EndPoint
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.resources.handle
import io.ktor.server.resources.resource
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.method
import io.ktor.util.pipeline.PipelineContext
import kotlinx.serialization.serializer

inline fun <reified Resource : Any, reified Input : Any, reified Output: Any> Route.respondEndpoint(
    endPoint: EndPoint<Resource, Input, Output>,
    noinline body: suspend PipelineContext<Unit, ApplicationCall>.(Resource, Input) -> Output
): Route {
    lateinit var builtRoute: Route
    resource<Resource> {
        builtRoute = method(endPoint.httpMethod) {
            val serializer = serializer<Resource>()
            handle (serializer){ resource ->
                val input = if (endPoint.hasNoInput) Unit as Input else call.receive()
                val result = body(resource, input)
                call.respond(result)
            }
        }
    }
    return builtRoute
}
