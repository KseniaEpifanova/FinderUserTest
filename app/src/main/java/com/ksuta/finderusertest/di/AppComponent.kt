package com.ksuta.finderusertest.di

import android.content.Context
import com.ksuta.finderusertest.App
import com.ksuta.finderusertest.network.ContextProvider
import com.ksuta.finderusertest.network.NetworkProvider
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class],
    dependencies = [
        ContextProvider::class,
        NetworkProvider::class,
    ]
)
@Singleton
interface AppComponent : NetworkProvider{
    fun context(): Context

    fun inject(coroutineApp: App)

}