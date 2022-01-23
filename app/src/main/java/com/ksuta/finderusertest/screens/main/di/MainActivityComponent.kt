package com.ksuta.finderusertest.screens.main

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.ksuta.finderusertest.di.AppComponent
import com.ksuta.finderusertest.di.FragmentScope
import com.ksuta.finderusertest.di.ViewModelProviderModule
import com.ksuta.finderusertest.di.KeyViewModel
import com.ksuta.finderusertest.network.UsersApi
import com.ksuta.finderusertest.network.appComponent
import dagger.*
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@FragmentScope
@Component(
    dependencies = [AppComponent::class],
    modules = [MainActivityModule::class, ViewModelProviderModule::class]
)
interface MainActivityComponent {
    fun inject(activity: MainActivity)

    companion object {
        fun init(activity: AppCompatActivity): MainActivityComponent =
            DaggerMainActivityComponent.factory().newInstance(activity, activity.appComponent)
    }

    @Component.Factory
    interface Factory {
        fun newInstance(
            @BindsInstance activity: AppCompatActivity,
            appComponent: AppComponent
        ): MainActivityComponent
    }
}

@Module
abstract class MainActivityModule {

    @Binds
    @IntoMap
    @KeyViewModel(MainViewModel::class)
    @FragmentScope
    abstract fun bindViewModel(viewModel: MainViewModel): ViewModel

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideApi(retrofit: Retrofit): UsersApi =
            retrofit.create(UsersApi::class.java)
    }
}
