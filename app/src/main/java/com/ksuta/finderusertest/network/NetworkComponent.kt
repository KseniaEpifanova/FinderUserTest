package com.ksuta.finderusertest.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.ksuta.finderusertest.network.NetworkProvider.Companion.SERVER_URL
import javax.inject.Singleton

@Singleton
@Component(
    modules = [NetworkModule::class],
    dependencies = [
        ContextProvider::class,
    ]
)
interface NetworkComponent : NetworkProvider {

    class Initializer private constructor() {

        companion object {
            fun init(
                contextProvider: ContextProvider,
            ): NetworkProvider {
                return DaggerNetworkComponent.builder()
                    .contextProvider(contextProvider)
                    .build()
            }
        }
    }
}

@Module
internal object NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val authRequest = chain.request().newBuilder()
                    .build()
                chain.proceed(authRequest)
            }
            .build()


    @Provides
    @Singleton
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit {
        val builder = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json(Json.Default) {
            ignoreUnknownKeys = true
        }
    }
}

interface NetworkProvider {

    fun providesRetrofit(): Retrofit

    fun provideJson(): Json

    companion object {
        const val SERVER_URL = "https://api.stackexchange.com/2.0/"
    }
}