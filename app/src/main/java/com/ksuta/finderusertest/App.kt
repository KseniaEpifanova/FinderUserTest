package com.ksuta.finderusertest

import androidx.lifecycle.LifecycleObserver
import androidx.multidex.MultiDexApplication
import com.ksuta.finderusertest.network.ComponentInjector

class App : MultiDexApplication(), LifecycleObserver {

    val componentInjector: ComponentInjector by lazy {
        ComponentInjector(this)
    }

    override fun onCreate() {
        super.onCreate()
        componentInjector.getAppComponent().inject(this)
    }

}
