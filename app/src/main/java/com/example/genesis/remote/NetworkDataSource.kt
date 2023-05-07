package com.example.genesis.remote

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
class NetworkDataSource @Inject constructor() {

    suspend fun getConfig(
        configExpirationDuration: Long,
    ): Map<String, FirebaseRemoteConfigValue> =
        suspendCoroutine { continuation ->

            val remoteConfigInstance = FirebaseRemoteConfig.getInstance()

            val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setFetchTimeoutInSeconds(configExpirationDuration)
                .build()

            remoteConfigInstance.setConfigSettingsAsync(configSettings)

            remoteConfigInstance.fetchAndActivate().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(remoteConfigInstance.all)
                }
            }.addOnFailureListener {
                continuation.resumeWithException(it)
            }
        }

}
