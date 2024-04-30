package com.dmartinc.futurama

import com.dmartinc.endpoints.client.defineEndPoint
import com.dmartinc.model.Character
import io.ktor.http.HttpMethod
import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Serializable
@Resource("/futurama")
class FuturamaResource {

    @Serializable
    @Resource("characters")
    class CharactersResource(val parent: FuturamaResource = FuturamaResource())

    @Serializable
    @Resource("{id}")
    class CharacterResource(val parent: CharactersResource = CharactersResource(), val id: Long)

    object EndPoints {
        val One = defineEndPoint<CharacterResource, Unit, Character>(HttpMethod.Get)
        val List = defineEndPoint<CharactersResource, Unit, List<Character>>(HttpMethod.Get)
    }
}
