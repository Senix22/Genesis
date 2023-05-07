package com.example.genesis

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.BuildConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteConfigUtil @Inject constructor() {

    private val remoteConfig by lazy {
        val settings = remoteConfigSettings {
            fetchTimeoutInSeconds = 30
            minimumFetchIntervalInSeconds = if (BuildConfig.DEBUG) {
                0 // fetch every time in debug mode
            } else {
                60 * 180 // fetch every 3 hours in production mode
            }
        }
        // Initialize Remote Config default values.
        val defaults = mutableMapOf<String, Any>(
            "IS_WATCH_AD_TO_UNLOCK_FONTS_ENABLED" to true,
            "IS_WATCH_AD_TO_UNLOCK_PHOTOS_ENABLED" to true
        )
        Firebase.remoteConfig.apply {
            setConfigSettingsAsync(settings)
            setDefaultsAsync(defaults)
        }
    }
}