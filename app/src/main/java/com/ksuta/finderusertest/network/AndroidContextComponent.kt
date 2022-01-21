package com.ksuta.finderusertest.network

import android.app.Application
import android.content.Context
import android.content.res.loader.ResourcesProvider
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidContextModule::class])
interface AndroidContextComponent
    : ContextProvider
{

    @Component.Builder
    interface Builder {
        fun build(): ContextProvider
        @BindsInstance
        fun app(app: Application): Builder
    }

    class Initializer private constructor() {
        companion object {

            fun init(app: Application): ContextProvider =
                DaggerAndroidContextComponent.builder()
                    .app(app)
                    .build()
        }
    }
}

@Module
object AndroidContextModule {
    @Provides
    @JvmStatic
    fun provideContext(app: Application): Context = app.applicationContext

}

interface ContextProvider {

    fun provideContext(): Context

    fun provideApp(): Application
}
