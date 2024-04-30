package com.dmartinc.futurama

import com.dmartinc.endpoints.client.callEndpointForServiceResult
import com.dmartinc.futurama.FuturamaResource.CharactersResource
import com.dmartinc.futurama.FuturamaResource.CharacterResource
import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get
import com.dmartinc.futurama.FuturamaResource.EndPoints.List
import com.dmartinc.futurama.FuturamaResource.EndPoints.One

class FuturamaService(private val client: HttpClient) {
    suspend fun characters2() = client.callEndpointForServiceResult(List, CharactersResource())
    suspend fun character2(id: Long) = client.callEndpointForServiceResult(One, CharacterResource(id = id))

    suspend fun characters() = client.get(CharactersResource())
    suspend fun character(id: Long) = client.get(CharacterResource(id = id))
}