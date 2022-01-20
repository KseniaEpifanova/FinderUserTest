package com.ksuta.finderusertest

import androidx.lifecycle.LifecycleObserver
import androidx.multidex.MultiDexApplication

class App: MultiDexApplication(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()
    }

}