package com.dmartinc.endpoints.client

/**
 * Result of a service cal. Can be either a success (with the data) or a failure
 */
sealed class ServiceResult<out SUCCESS> {
    data class Success<out SUCCESS>(val data: SUCCESS) : ServiceResult<SUCCESS>()
    data class Failure(val error: ServiceFailure) : ServiceResult<Nothing>()
}
