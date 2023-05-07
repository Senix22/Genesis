package com.example.genesis.remote

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RemoteConfigRepository @Inject constructor(
    private val mapper: DataMapper,
    private val networkDataSource: NetworkDataSource,
) {

    suspend fun getNetworkConfig(configExpirationDuration: Long): RCDataModel =
        mapper(networkDataSource.getConfig(configExpirationDuration))

}
