package com.example.demoapp.repository

import com.example.demoapp.core.NetworkToUIProvider
import com.example.demoapp.core.State
import com.example.demoapp.data.apiresponses.UserData
import com.example.demoapp.network.NetworkEndpoints
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class DemoRepository @Inject constructor(
    private val networkEndpoints: NetworkEndpoints,
) {

    fun getDemo(): Flow<State<UserData>> {
        return object : NetworkToUIProvider<UserData>() {
            override suspend fun fetchFromRemote(): Response<UserData> {
                return networkEndpoints.getDemo()
            }
        }.asFlow()
    }
}