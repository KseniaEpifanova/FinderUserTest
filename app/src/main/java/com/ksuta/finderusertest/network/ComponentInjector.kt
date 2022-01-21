package com.ksuta.finderusertest.network

import android.app.Application
import android.content.Context
import com.ksuta.finderusertest.App
import com.ksuta.finderusertest.di.AppComponent
import com.ksuta.finderusertest.di.AppModule
import com.ksuta.finderusertest.di.DaggerAppComponent
import okhttp3.Interceptor

class ComponentInjector(app: Application) {

    private val appComponent: AppComponent

    init {
        val contextProvider: ContextProvider = AndroidContextComponent
            .Initializer
            .init(app)
        val networkProvider: NetworkProvider =
            NetworkComponent.Initializer.init(contextProvider)

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule())
            .contextProvider(contextProvider)
            .networkProvider(networkProvider)
            .build()
    }

    fun getAppComponent(): AppComponent = appComponent

    companion object {
        fun get(context: Context) =
            (context.applicationContext as App).componentInjector
    }
}

val Context.componentInjector: ComponentInjector
    get() = ComponentInjector.get(this)

val Context.appComponent: AppComponent
    get() = componentInjector.getAppComponent()
