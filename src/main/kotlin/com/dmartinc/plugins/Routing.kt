package com.dmartinc.plugins

import com.dmartinc.endpoints.client.ServiceFailure
import com.dmartinc.endpoints.client.ServiceResult
import com.dmartinc.futurama.FuturamaService
import com.dmartinc.model.Character
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.resources.Resources
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

val futuramaService = FuturamaService(
    HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true

            })
        }
        install(Resources)
        defaultRequest {
            host = "api.sampleapis.com"
            port = 443
            url { protocol = URLProtocol.HTTPS }
        }
    }
)

fun Application.configureRouting() {
    routing {
        get("/v1/futurama/characters") {
            println("characters invoked")
            val characters = runBlocking {
                futuramaService.characters().body<Character>()
            }
            println("characters: $characters")
            call.respond(characters)
            println("characters ended")
        }
        get("/v1/futurama/characters/{id}") {
            val characterId = call.parameters["id"]!!.toLong()
            println("character $characterId invoked")
            val character = runBlocking {
                futuramaService.character(characterId).body<Character>()
            }
            println("character: $character")
            call.respond(character)
            println("character $characterId ended")
        }
        get("/v2/futurama/characters") {
            println("characters invoked")
            val characters = runBlocking {
                futuramaService.characters2()
            }
            if (characters is ServiceResult.Failure) {
                call.respondText("No characters found", status = characters.error.toHttpStatusCode())
                return@get
            }
            println("characters: $characters")
            call.respond(characters)
            println("characters ended")
        }
        get("/v2/futurama/characters/{id}") {
            val characterId = call.parameters["id"]!!.toLong()
            println("character $characterId invoked")
            val character = runBlocking {
                futuramaService.character2(characterId)
            }
            println("character: $character")
            call.respond(character)
            println("character $characterId ended")
        }
    }
}

private fun ServiceFailure.toHttpStatusCode(): HttpStatusCode  = when(this) {
    ServiceFailure.NOT_FOUND -> HttpStatusCode.NotFound
    ServiceFailure.AUTHENTICATION -> HttpStatusCode.Unauthorized
    ServiceFailure.NO_CONNECTION -> HttpStatusCode.ServiceUnavailable
    ServiceFailure.UNKNOWN -> HttpStatusCode.InternalServerError
}
