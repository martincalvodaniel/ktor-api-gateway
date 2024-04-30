package com.dmartinc

import com.dmartinc.plugins.configureHTTP
import com.dmartinc.plugins.configureMonitoring
import com.dmartinc.plugins.configureRouting
import com.dmartinc.plugins.configureSerialization
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureRouting()
}
