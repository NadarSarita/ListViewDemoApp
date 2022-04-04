package com.example.demoapp.di

import android.util.Log
import com.example.demoapp.BuildConfig
import com.example.demoapp.network.NetworkEndpoints
import com.example.demoapp.network.NetworkInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Singleton
    @Provides
    fun provideNetworkInterceptor() = NetworkInterceptor()

    @Singleton
    @Provides
    fun provideDataRequestInterceptor() = HttpLoggingInterceptor { message ->
        Log.i("TAG", message)
    }.apply { level = HttpLoggingInterceptor.Level.BODY }


    @Singleton
    @Provides
    fun provideSupportClient(
        networkInterceptor: NetworkInterceptor,
        dataRequestInterceptor: HttpLoggingInterceptor,
    ) = OkHttpClient()
        .newBuilder()
        .addInterceptor(networkInterceptor)
        .addInterceptor(dataRequestInterceptor)
        .connectTimeout(1, TimeUnit.MINUTES)
        .callTimeout(1, TimeUnit.MINUTES)
        .build()

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideGsonFactory(
        gson: Gson?
    ): GsonConverterFactory = GsonConverterFactory.create(gson ?: Gson())

    @Singleton
    @Provides
    fun provideNetworkBuilder(
        baseUrl: String,
        supportClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(gsonConverterFactory)
        .client(supportClient)
        .build()

    @Singleton
    @Provides
    fun provideNetworkInstance(
        networkBuilder: Retrofit
    ): NetworkEndpoints = networkBuilder.create(NetworkEndpoints::class.java)


}