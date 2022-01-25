package com.ksuta.finderusertest.network

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import com.ksuta.finderusertest.network.NetworkProvider.Companion.SERVER_URL
import okhttp3.Cache
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideOkHttpClient(context: Context): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val authRequest = chain.request().newBuilder()
                    .build()
                chain.proceed(authRequest)
            }
            .cache(Cache(context.cacheDir, Integer.MAX_VALUE.toLong()))
            .build()


    @Provides
    @Singleton
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        val builder = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
        return builder.build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideGson(): Gson = GsonBuilder().create()
}

interface NetworkProvider {

    fun providesRetrofit(): Retrofit

    fun provideGson(): Gson

    companion object {
        const val SERVER_URL = "https://api.stackexchange.com/2.0/"
    }
}