package com.ksuta.finderusertest.screens.details.di

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.ksuta.finderusertest.di.AppComponent
import com.ksuta.finderusertest.di.FragmentScope
import com.ksuta.finderusertest.di.ViewModelProviderModule
import com.ksuta.finderusertest.di.KeyViewModel
import com.ksuta.finderusertest.network.UsersApi
import com.ksuta.finderusertest.network.appComponent
import com.ksuta.finderusertest.screens.details.DetailUserFragment
import com.ksuta.finderusertest.screens.details.DetailUserViewModel
import dagger.*
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@FragmentScope
@Component(
    dependencies = [AppComponent::class],
    modules = [DetailUserFragmentModule::class, ViewModelProviderModule::class]
)
interface DetailUserFragmentComponent {
    fun inject(fragment: DetailUserFragment)

    companion object {
        fun init(activity: AppCompatActivity): DetailUserFragmentComponent =
            DaggerDetailUserFragmentComponent.factory().newInstance(activity, activity.appComponent)
    }

    @Component.Factory
    interface Factory {
        fun newInstance(
            @BindsInstance activity: AppCompatActivity,
            appComponent: AppComponent
        ): DetailUserFragmentComponent
    }
}

@Module
abstract class DetailUserFragmentModule {

    @Binds
    @IntoMap
    @KeyViewModel(DetailUserViewModel::class)
    @FragmentScope
    abstract fun bindViewModel(viewModel: DetailUserViewModel): ViewModel

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideApi(retrofit: Retrofit): UsersApi =
            retrofit.create(UsersApi::class.java)
    }
}
