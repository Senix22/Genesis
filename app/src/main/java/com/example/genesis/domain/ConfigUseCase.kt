package com.example.genesis.domain

import com.example.genesis.remote.BookDataModel
import com.example.genesis.remote.RCDataModel
import com.example.genesis.remote.RemoteConfigRepository
import javax.inject.Inject

class GetConfigsUseCase @Inject constructor(
    private val remoteConfigRepository: RemoteConfigRepository,
) {
    suspend operator fun invoke(): RCDataModel =
        remoteConfigRepository.getNetworkConfig(
            configExpirationDuration = CONFIG_EXPIRATION_DURATION_SECONDS
        )

    private companion object {
        const val CONFIG_EXPIRATION_DURATION_SECONDS = 3600L
    }
}
